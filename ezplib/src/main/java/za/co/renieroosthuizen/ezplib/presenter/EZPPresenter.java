package za.co.renieroosthuizen.ezplib.presenter;

import java.util.ArrayList;
import java.util.Locale;

import javax.inject.Inject;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import za.co.renieroosthuizen.ezplib.exceptions.EZPException;
import za.co.renieroosthuizen.ezplib.exceptions.EZPValidationException;
import za.co.renieroosthuizen.ezplib.interactor.CountryInteractor;
import za.co.renieroosthuizen.ezplib.model.Country;
import za.co.renieroosthuizen.ezplib.model.EZPPhoneNumber;
import za.co.renieroosthuizen.ezplib.utils.Utilities;
import za.co.renieroosthuizen.ezplib.view.IEZPView;

/**
 * Created by Renier on 2015/08/03.
 */
public class EZPPresenter implements IEZPPresenter {
    private IEZPView mView;

    @Inject
    CountryInteractor mCountryInteractor;


    @Inject
    public EZPPresenter() {

    }

    @Override
    public void setView(IEZPView view) {
        mView = view;
    }

    @Override
    public void init() {
        if (mView != null)
            mView.getEZPComponent().inject(this);

        if (mCountryInteractor != null) {
            mCountryInteractor.getCountries()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(countryList -> {
                                if (mView != null)
                                    mView.setCountries(countryList);

                                String lastSelectedCountryCode = mCountryInteractor.getLastSelectedCountryCode();

                                if (lastSelectedCountryCode.isEmpty() == true) {
                                    lastSelectedCountryCode = Locale.getDefault().getCountry();
                                }

                                int i = 0;
                                for (Country country : countryList)
                                {
                                    if (country.getCode().equalsIgnoreCase(lastSelectedCountryCode))
                                    {
                                        mView.setSelectedCountryIndex(i);
                                        break;
                                    }
                                    i++;
                                }
                            },
                            error -> {
                                if (mView != null)
                                    mView.onError("Gee, something went wrong!");
                            });
        }
        else
        {
            if (mView != null)
                mView.onError("Gee, something went wrong!");
        }
    }

    @Override
    public void setSelectedCountry(Country country) {
        if (mCountryInteractor != null)
            mCountryInteractor.setSelectedCountry(country);


        if (mView != null)
            mView.setSelectedCountryDialingCode(country != null ? country.getPhoneCode() : "");
    }

    @Override
    public void generatePreviewData() {
        ArrayList<Country> previewData = new ArrayList<Country>();
        previewData.add(new Country("us", "United States", "+1"));

        if (mView != null)
            mView.setCountries(previewData);
    }

    @Override
    public void validatePhoneNumber(String phoneNumber) throws EZPValidationException {
        if (mCountryInteractor == null)
            throw new EZPValidationException(EZPValidationException.ValidationExceptionType.General, "Country interactor is null");

        if (mCountryInteractor.getSelectedCountry() == null)
            throw new EZPValidationException(EZPValidationException.ValidationExceptionType.NoCountry, "No country selected");

        if (phoneNumber.isEmpty() == true)
            throw new EZPValidationException(EZPValidationException.ValidationExceptionType.NoNumber, "Phone number is empty");

        if (Utilities.isPhoneNumberValid(phoneNumber, mCountryInteractor.getSelectedCountry().getPhoneCode(), Locale.getDefault()) == false)
        {
            throw new EZPValidationException(EZPValidationException.ValidationExceptionType.InvalidNumber, "Phone number is not valid");
        }
    }

    @Override
    public EZPPhoneNumber getPhoneNumber(String phoneNumber) throws EZPException, EZPValidationException {
        validatePhoneNumber(phoneNumber);

        return new EZPPhoneNumber(mCountryInteractor.getSelectedCountry(), phoneNumber);
    }
}

package za.co.renieroosthuizen.ezplib.interactor;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import rx.Observable;
import za.co.renieroosthuizen.ezplib.model.Country;
import za.co.renieroosthuizen.ezplib.presenter.IEZPPresenter;
import za.co.renieroosthuizen.ezplib.utils.Utilities;

/**
 * Created by Renier on 2015/08/05.
 */
public class CountryInteractor {
    private final String SP_KEY_LAST_COUNTRY_CODE = "CountryInteractor.LastCountryCode";

    private Context mContext;
    private IEZPPresenter mPresenter;
    private SharedPreferences mPreferences;

    private Country mSelectedCountry;

    public CountryInteractor(Context context, IEZPPresenter presenter)
    {
        mContext = context;
        mPresenter = presenter;
        mPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public Observable<ArrayList<Country>> getCountries()
    {
        return Observable.create(subscriber -> {
            try {
                ArrayList<Country> countryList = new ArrayList<Country>();

                JSONArray jsaCountries = new JSONObject(Utilities.loadJSONFromAsset(mContext, "countries.json")).getJSONArray("countries");

                for (int i = 0; i < jsaCountries.length(); i++)
                {
                    JSONObject jsoCountry = jsaCountries.getJSONObject(i);
                    countryList.add(new Country(jsoCountry.getString("code"), jsoCountry.getString("name"), "+" + jsoCountry.getString("phoneCode")));
                }

                subscriber.onNext(countryList);
                subscriber.onCompleted();
            } catch (JSONException e) {
                e.printStackTrace();
                subscriber.onError(e);
            } catch (IOException e) {
                e.printStackTrace();
                subscriber.onError(e);
            }
        });
    }

    public String getLastSelectedCountryCode()
    {
        return mPreferences.getString(SP_KEY_LAST_COUNTRY_CODE, "");
    }

    public void setSelectedCountry(Country country)
    {
        mSelectedCountry = country;
        mPreferences.edit().putString(SP_KEY_LAST_COUNTRY_CODE, country.getCode()).commit();
    }

    public Country getSelectedCountry()
    {
        return mSelectedCountry;
    }
}

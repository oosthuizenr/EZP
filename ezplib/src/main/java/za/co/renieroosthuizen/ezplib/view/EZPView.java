package za.co.renieroosthuizen.ezplib.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.ButterKnife;
import za.co.renieroosthuizen.ezplib.R;
import za.co.renieroosthuizen.ezplib.adapter.CountryAdapter;
import za.co.renieroosthuizen.ezplib.dagger.DaggerEZPComponent;
import za.co.renieroosthuizen.ezplib.dagger.EZPComponent;
import za.co.renieroosthuizen.ezplib.dagger.EZPModule;
import za.co.renieroosthuizen.ezplib.exceptions.EZPException;
import za.co.renieroosthuizen.ezplib.exceptions.EZPValidationException;
import za.co.renieroosthuizen.ezplib.model.Country;
import za.co.renieroosthuizen.ezplib.model.EZPPhoneNumber;
import za.co.renieroosthuizen.ezplib.presenter.IEZPPresenter;

/**
 * Created by Renier on 2015/08/03.
 */
public class EZPView extends RelativeLayout implements IEZPView {

    private EZPComponent mEZPComponent;

    @Inject
    IEZPPresenter mPresenter;

    Spinner spnCountry;
    EditText edtCountryCode;
    EditText edtPhoneNumber;

    private CountryAdapter mCountryAdapter;

    public EZPView(Context context) {
        super(context);

        init(null);
    }

    public EZPView(Context context, AttributeSet attrs) {
        super(context, attrs);

        init(attrs);
    }

    public EZPView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init(attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public EZPView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

        init(attrs);
    }

    private void init(AttributeSet attrs)
    {
        if (attrs != null)
        {

        }

        inflate(getContext(), R.layout.view_ezp, this);

        //Damn you Jake Wharton!!
        spnCountry = (Spinner) findViewById(R.id.spnCountry);
        edtCountryCode = (EditText) findViewById(R.id.edtCountryCode);
        edtPhoneNumber = (EditText) findViewById(R.id.edtPhoneNumber);

        ButterKnife.bind(this);
        spnCountry.setOnItemSelectedListener(onItemSelectedListener);

        mEZPComponent = DaggerEZPComponent.builder().eZPModule(new EZPModule(getContext())).build();
        mEZPComponent.inject(this);

        mPresenter.setView(this);

        if (isInEditMode() == false) {
            mPresenter.init();
        }
        else
        {
            mPresenter.generatePreviewData();
        }
    }

    @Override
    public EZPComponent getEZPComponent() {
        return mEZPComponent;
    }

    @Override
    public void setCountries(ArrayList<Country> countries) {
        mCountryAdapter = new CountryAdapter(getContext(), -1, countries);
        spnCountry.setAdapter(mCountryAdapter);
    }

    @Override
    public void setSelectedCountryDialingCode(String dialingCode) {
        edtCountryCode.setText(dialingCode);
    }

    @Override
    public void onError(String message) {
        //Snackbar.make(this, message, Snackbar.LENGTH_LONG).show();
    }

    private AdapterView.OnItemSelectedListener onItemSelectedListener = new AdapterView.OnItemSelectedListener() {

        @Override
        public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
            if (mPresenter != null)
                mPresenter.setSelectedCountry(mCountryAdapter.getData().get(position));
        }

        @Override
        public void onNothingSelected(AdapterView<?> parentView) {
            if (mPresenter != null)
                mPresenter.setSelectedCountry(null);
        }
    };

    public void validatePhoneNumber() throws EZPValidationException
    {
        if (mPresenter != null)
            mPresenter.validatePhoneNumber(edtPhoneNumber.getText().toString());
        else
            throw new EZPValidationException(EZPValidationException.ValidationExceptionType.General, "Could not validate");
    }

    public EZPPhoneNumber getPhoneNumber() throws EZPException, EZPValidationException
    {
        if (mPresenter != null)
            return mPresenter.getPhoneNumber(edtPhoneNumber.getText().toString());
        else
            throw new EZPException("General Exception");
    }

    @Override
    public void setSelectedCountryIndex(int index) {
        spnCountry.setSelection(index);
    }
}

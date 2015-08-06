package za.co.renieroosthuizen.ezplib.dagger;

import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import za.co.renieroosthuizen.ezplib.interactor.CountryInteractor;
import za.co.renieroosthuizen.ezplib.presenter.EZPPresenter;
import za.co.renieroosthuizen.ezplib.presenter.IEZPPresenter;

/**
 * Created by Renier on 2015/08/03.
 */
@Module
public class EZPModule {

    private Context mContext;

    public EZPModule(Context context)
    {
        mContext = context;
    }

    @Provides
    @Singleton
    Context getContext() { return mContext; }

    @Provides
    @Singleton
    IEZPPresenter getEZPPresenter() { return new EZPPresenter(); }

    @Provides
    @Singleton
    CountryInteractor getCountryInteractor() { return new CountryInteractor(getContext(), getEZPPresenter()); }
}

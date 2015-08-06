package za.co.renieroosthuizen.ezplib.dagger;


import android.content.Context;

import javax.inject.Singleton;

import dagger.Component;
import za.co.renieroosthuizen.ezplib.interactor.CountryInteractor;
import za.co.renieroosthuizen.ezplib.presenter.EZPPresenter;
import za.co.renieroosthuizen.ezplib.view.EZPView;

/**
 * Created by Renier on 2015/08/03.
 */
@Singleton
@Component(modules = { EZPModule.class })
public interface EZPComponent {
    Context provideContext();

    CountryInteractor provideCountryInteractor();

    void inject(EZPView view);
    void inject(EZPPresenter presenter);
}

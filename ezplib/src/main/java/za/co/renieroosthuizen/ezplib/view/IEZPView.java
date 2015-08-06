package za.co.renieroosthuizen.ezplib.view;

import java.util.ArrayList;

import za.co.renieroosthuizen.ezplib.dagger.EZPComponent;
import za.co.renieroosthuizen.ezplib.model.Country;


/**
 * Created by Renier on 2015/08/03.
 */
public interface IEZPView {
    EZPComponent getEZPComponent();
    void                        setCountries(ArrayList<Country> countries);
    void                        setSelectedCountryDialingCode(String dialingCode);
    void                        onError(String message);
    void                        setSelectedCountryIndex(int index);

}

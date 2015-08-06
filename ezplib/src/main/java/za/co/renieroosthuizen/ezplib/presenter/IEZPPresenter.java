package za.co.renieroosthuizen.ezplib.presenter;


import za.co.renieroosthuizen.ezplib.exceptions.EZPException;
import za.co.renieroosthuizen.ezplib.exceptions.EZPValidationException;
import za.co.renieroosthuizen.ezplib.model.Country;
import za.co.renieroosthuizen.ezplib.model.EZPPhoneNumber;
import za.co.renieroosthuizen.ezplib.view.IEZPView;

/**
 * Created by Renier on 2015/08/03.
 */
public interface IEZPPresenter {
    void                    setView(IEZPView view);
    void                    init();
    void                    setSelectedCountry(Country country);
    void                    generatePreviewData();
    void                    validatePhoneNumber(String phoneNumber)         throws EZPValidationException;
    EZPPhoneNumber getPhoneNumber(String phoneNumber)              throws EZPException, EZPValidationException;
}

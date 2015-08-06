package za.co.renieroosthuizen.ezplib.model;

import java.util.Locale;

import lombok.Data;
import lombok.experimental.Accessors;
import za.co.renieroosthuizen.ezplib.exceptions.EZPException;
import za.co.renieroosthuizen.ezplib.utils.Utilities;

/**
 * Created by Renier on 2015/08/05.
 */
@Accessors(prefix = "m")
@Data
public class EZPPhoneNumber {
    public enum EZPPhoneNumberFormat {
        E164,
        INTERNATIONAL,
        NATIONAL,
        RFC3966
    }

    private Country mCountry;
    private String mPhoneNumber;

    public EZPPhoneNumber(Country country, String phoneNumber)
    {
        this.mCountry = country;
        this.mPhoneNumber = phoneNumber;
    }

    public String format(EZPPhoneNumberFormat format) throws EZPException
    {
        return Utilities.formatPhoneNumber(this, Locale.getDefault(), format);
    }


}

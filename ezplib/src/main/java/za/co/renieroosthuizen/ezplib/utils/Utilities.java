package za.co.renieroosthuizen.ezplib.utils;

import android.content.Context;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.Phonenumber;

import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;

import za.co.renieroosthuizen.ezplib.exceptions.EZPException;
import za.co.renieroosthuizen.ezplib.model.EZPPhoneNumber;


/**
 * Created by Renier on 2015/08/05.loadJSONFromAsset
 */
public class Utilities {

    public static String formatPhoneNumber(EZPPhoneNumber number, Locale locale, EZPPhoneNumber.EZPPhoneNumberFormat format) throws EZPException
    {
        try {
            PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();

            String num = number.getPhoneNumber();

            if (num.startsWith(number.getCountry().getPhoneCode()) == false)
            {
                num = number.getCountry().getPhoneCode() + num;
            }

            Phonenumber.PhoneNumber phoneNumber = phoneUtil.parse(num, number.getCountry().getPhoneCode().toUpperCase(locale));

            PhoneNumberUtil.PhoneNumberFormat tmp = PhoneNumberUtil.PhoneNumberFormat.E164;

            switch (format) {
                case E164:
                    tmp = PhoneNumberUtil.PhoneNumberFormat.E164;
                    break;
                case INTERNATIONAL:
                    tmp = PhoneNumberUtil.PhoneNumberFormat.INTERNATIONAL;
                    break;
                case NATIONAL:
                    tmp = PhoneNumberUtil.PhoneNumberFormat.NATIONAL;
                    break;
                case RFC3966:
                    tmp = PhoneNumberUtil.PhoneNumberFormat.RFC3966;
                    break;
            }

            return phoneUtil.format(phoneNumber, tmp);
        } catch (NumberParseException e) {
            e.printStackTrace();
            EZPException ex = new EZPException(e.getMessage());
            ex.initCause(e);
            throw ex;
        }
    }

    public static String loadJSONFromAsset(Context context, String assetName) throws IOException {
        String json = null;


        InputStream is = context.getAssets().open(assetName);

        int size = is.available();

        byte[] buffer = new byte[size];

        is.read(buffer);

        is.close();

        json = new String(buffer, "UTF-8");

        return json;

    }

    public static boolean isPhoneNumberValid(String num, String countryCode, Locale locale)
    {
        try
        {
            PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();

            if (num.startsWith(countryCode) == false)
            {
                num = countryCode + num;
            }

            Phonenumber.PhoneNumber phoneNumber = phoneUtil.parse(num, countryCode.toUpperCase(locale));

            if (phoneUtil.isValidNumber(phoneNumber) == false)
            {
                return false;
            }
        }
        catch (NumberParseException e)
        {
            return false;
        }

        return true;
    }
}

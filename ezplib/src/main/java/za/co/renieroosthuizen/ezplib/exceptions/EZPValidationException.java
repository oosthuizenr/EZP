package za.co.renieroosthuizen.ezplib.exceptions;

/**
 * Created by Renier on 2015/08/03.
 */
public class EZPValidationException extends Exception {
    public enum ValidationExceptionType {
        General,
        NoCountry,
        NoNumber,
        InvalidNumber
    }

    private ValidationExceptionType mType;
    private String mMessage;

    public EZPValidationException(ValidationExceptionType type, String message)
    {
        mType = type;
        mMessage = message;
    }

    public String getMessage()
    {
        return mMessage;
    }

    public ValidationExceptionType getValidationExceptionType() {
        return mType;
    }


}

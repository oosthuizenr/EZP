package za.co.renieroosthuizen.ezplib.exceptions;

/**
 * Created by Renier on 2015/08/05.
 */
public class EZPException extends Exception{
    private String mMessage;

    public EZPException(String message)
    {
        this.mMessage = message;
    }

    public String getMessage()
    {
        return mMessage;
    }
}

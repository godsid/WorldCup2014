package info.srihawong.worldcup2014.app;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Banpot.S on 6/12/14 AD.
 */
public class ThaiDate {
    long timestamps;
    SimpleDateFormat simpleDateFormat;
    public ThaiDate(long timestamps) {
        this.timestamps = timestamps;
    }
    public ThaiDate(long timestamps,String format) {
        this.timestamps = timestamps;

    }

    public static String format(String format){

        return new SimpleDateFormat(format).format(new Date(0));

    }
    public static String format(Long timestamp ,String format){

        return new SimpleDateFormat(format).format(new Date(timestamp));

    }

}

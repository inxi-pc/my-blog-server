package myblog;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * My blog helper class
 *
 */
public class Helper {

    /**
     * Check string value is null or empty
     *
     * @param param
     * @return boolean
     */
    public static boolean isNullOrEmpty(String param) {
        return param == null || param.length() <= 0;
    }

    /**
     * Convert the date to UTC datetime sting
     *
     * @param date
     * @return
     */
    public static String formatDatetimeUTC(Date date) {
        SimpleDateFormat formatUTC = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        formatUTC.setTimeZone(TimeZone.getTimeZone("UTC"));

        return formatUTC.format(date);
    }
}

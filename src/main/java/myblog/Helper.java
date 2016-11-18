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

    /**
     * Validate string if is DateFormat
     *
     * @param datetime
     * @return
     */
    public static boolean isValidDataTimeFormat(String datetime) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        try {
            return format.parse(datetime) != null;
        } catch (Exception e) {
            return false;
        }
    }
}

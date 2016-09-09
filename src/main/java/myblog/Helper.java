package myblog;

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
}

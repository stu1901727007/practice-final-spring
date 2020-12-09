package uni.finalproject.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {

    /**
     *
     * @return
     */
    public static Date today() {
        return new Date();
    }

    /**
     *
     * @param date
     * @return
     * @throws ParseException
     */
    public static Date formattedDb(String date) throws ParseException {

        SimpleDateFormat formaDate = new SimpleDateFormat("dd/MM/yyyy");
        return formaDate.parse(date);
    }

    /**
     *
     * @param date
     * @return
     * @throws ParseException
     */
    public static String formattedFront(Date date) throws ParseException {

        SimpleDateFormat formaDate = new SimpleDateFormat("dd/MM/yyyy");
        return formaDate.format(date);
    }
}

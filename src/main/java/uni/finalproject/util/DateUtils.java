package uni.finalproject.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {

    public static Date today() {
        return new Date();
    }
//
//    public static Date todayStr() {
//        SimpleDateFormat formaDate = new SimpleDateFormat("yyyy-MM-dd");
//        return formaDate.format(today());
//    }

    public static Date formattedDb(String date) throws ParseException {

        SimpleDateFormat formaDate = new SimpleDateFormat("dd/MM/yyyy");
        return formaDate.parse(date);
    }

    public static String formattedFront(Date date) throws ParseException {

        SimpleDateFormat formaDate = new SimpleDateFormat("dd/MM/yyyy");
        return formaDate.format(date);
    }
}

package util;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

import config.Config;

/**
 * 日期相关处理的工具类
 */

public class DateUtil {

    /**
     * 将日期字符串转化为日期对象
     *
     * @param datetStr 待转化的日期字符串
     * @return 日期对象
     */
    public static Date strToDate(String datetStr) {
        if (datetStr == null || "".equals(datetStr)) {
            return null;
        }
        try {
            return Config.SIMPLE_DATE_FORMAT.parse(datetStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将日期对象转化为字符串表示
     *
     * @param date 日期对象
     * @return 字符串表示
     */
    public static String dateToStr(Date date) {
        if (date == null) {
            return "";
        }
        return Config.SIMPLE_DATE_FORMAT.format(date);
    }

    /**
     * 得到一个日期的前N天的日期的字符串表示
     *
     * @param currentDate 传入的日期
     * @param beforeN     前n天
     * @return
     */
    public static String getBeforeNDays(String currentDate, int beforeN) {
        if (currentDate == null || "".equals(currentDate)) {
            return null;
        }

        if (beforeN < 0) {
            return null;
        }

        Calendar calendar = Calendar.getInstance();
        Date date = strToDate(currentDate);
        calendar.setTime(date);
        int day = calendar.get(Calendar.DATE);
        calendar.set(Calendar.DATE, day - beforeN);
        return dateToStr(calendar.getTime());
    }
}

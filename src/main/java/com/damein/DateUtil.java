package com.damein;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.temporal.ChronoUnit;
import java.util.*;

import static java.time.temporal.ChronoUnit.DAYS;

/**
 * 日期处理工具
 *
 * @author liushiyang
 */
public class DateUtil {

    public static final String DATE_TIME = "yyyy-MM-dd HH:mm:ss";
    public static final String DATE_TIME_d = "yyyy-MM-d HH:mm:ss";
    public static final String DATE = "yyyy-MM-dd";
    public static final String DATE_MINUTE = "yyyy-MM-dd HH:mm";
    public static final String DATE_D = "yyyy/MM/dd";
    public static final String DATE_D_MINUTE = "yyyy/MM/dd HH:mm";
    public static final String DATE_D_TIME = "yyyy/MM/dd HH:mm:ss";
    public static final String DATE_ = "yyyyMMdd";
    public static final String DATE_YEAR = "yyyy";
    public static final String DATE_MOTH = "yyyy-MM";
    public static final String DATE_TIME_PATTERN = "yyyyMMddHHmmss";
    public static final String DATE_MINUTE_PATTERN = "yyMMddHHmm";
    public static final String DATE_HOUR = "yyyyMMddHH";
    private static final String CRON_DATE_FORMAT = "ss mm HH dd MM ? yyyy";
    private static final String CRON_DATE_FORMAT_EVERY_DAY = "ss mm HH * * ?";
    public static final String DATE_MINUTE_CHINESE = "yyyy年MM月dd日 HH:mm";
    public static final String DATE_CHINESE = "yyyy年MM月dd日";
    public static final String TIME = "HH:mm:ss";
    public static final String TIME_SEC = "HH:mm:ss.SSS";
    public static final String DATE_TIME_ = "yyyyMMdd HH:mm:ss";
    public static final String DATE_MSEL_STR_FORMAT = "yyyyMMddHHmmssSSS";

    /**
     * 获取今天的日期，格式自定
     *
     * @param pattern - 设定显示格式
     * @return String - 返回今天的日期
     */
    public static String getToday(String pattern) {
        Calendar now = Calendar.getInstance(TimeZone.getDefault());
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        sdf.setTimeZone(TimeZone.getDefault());
        return (sdf.format(now.getTime()));
    }

    /***
     * @param date 时间
     * @return cron类型的日期
     */
    public static String getCron(final Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat(CRON_DATE_FORMAT);
        String formatTimeStr = "";
        if (date != null) {
            formatTimeStr = sdf.format(date);
        }
        return formatTimeStr;
    }

    /**
     * 格式化时间
     *
     * @param date
     * @param type
     * @return
     */
    public static String formatDate(final Date date, String type) {
        SimpleDateFormat sdf = new SimpleDateFormat(type);
        String formatTimeStr = "";
        if (date != null) {
            formatTimeStr = sdf.format(date);
        }
        return formatTimeStr;
    }

    /**
     * 获取cron表达式
     */
    public static String getCronByType(final Date date, Integer type) {
        if (date == null || type == null) {
            return null;
        }
        SimpleDateFormat sdf = null;
        if (0 == type) {
            sdf = new SimpleDateFormat(CRON_DATE_FORMAT);
        } else if (1 == type) {
            sdf = new SimpleDateFormat(CRON_DATE_FORMAT_EVERY_DAY);
        } else {
            return null;
        }
        return sdf.format(date);
    }


    /**
     * 获取当前日期
     *
     * @return
     */
    public static Date getCurrentDate() {
        return new Date();
    }

    /**
     * 获取当前时间
     *
     * @return
     */
    public static Timestamp getCurrentTime() {
        return new Timestamp(System.currentTimeMillis());
    }

    public static Long getNowTime() {
        return System.currentTimeMillis();
    }

    /**
     * 时间转字符串--默认格式
     *
     * @param date
     * @return
     */
    public static String convertDateToString(Date date) {
        return getDateTime(DATE_TIME, date);
    }

    /**
     * 时间转字符串--按指定格式
     *
     * @param pattern
     * @param date
     * @return
     */
    public static String convertDateToString(String pattern, Date date) {
        return getDateTime(pattern, date);
    }

    public static String getDateTime(String aMask, Date aDate) {
        SimpleDateFormat df = null;
        String returnValue = "";

        if (aDate == null) {
        } else {
            df = new SimpleDateFormat(aMask);
            returnValue = df.format(aDate);
        }

        return (returnValue);
    }

    /**
     * 字符串转时间--按默认格式
     * "yyyy-MM-dd HH:mm:ss"
     *
     * @param strDate
     * @return
     * @throws ParseException
     */
    public static Date convertStringToDate(final String strDate) throws ParseException {
        return convertStringToDate(DATE_TIME, strDate);
    }

    /**
     * Describe: 格式化日期
     * Date: 2018/8/9
     * Time: 17:27
     *
     * @author: lan.qing
     **/
    public static Date convertDateToDate(final Date strDate) throws ParseException {
        String date = convertDateToString(strDate);
        return convertStringToDate(date);
    }

    public static Date convertDateToDate(Date strDate, String pattern) throws ParseException {
        String date = convertDateToString(pattern, strDate);
        return convertStringToDate(date);
    }

    public static Date dateToDate(Date strDate, String pattern) throws ParseException {
        String date = convertDateToString(pattern, strDate);
        return convertStringToDate(pattern, date);
    }

    /**
     * 字符串转时间--按指定格式
     *
     * @param pattern
     * @param strDate
     * @return
     * @throws ParseException
     */
    public static Date convertStringToDate(String pattern, String strDate) throws ParseException {
        SimpleDateFormat df;
        Date date;
        df = new SimpleDateFormat(pattern);

        try {
            date = df.parse(strDate);
        } catch (ParseException pe) {
            // log.error("ParseException: " + pe);
            throw new ParseException(pe.getMessage(), pe.getErrorOffset());
        }

        return (date);
    }

    /**
     * 获得日期与本周一相差的天数
     *
     * @param date
     * @return
     */
    public static int getMondayPlus(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        // 获得今天是一周的第几天，星期日是第一天，星期二是第二天......
        int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
        if (dayOfWeek == 1) {
            return -6;
        } else {
            return 2 - dayOfWeek;
        }
    }

    /**
     * 获取当前日期的上n天
     *
     * @return
     */
    public static Date getBeforeday(int n) {
        Calendar cd = Calendar.getInstance();
        cd.add(Calendar.DATE, 0 - n);
        return cd.getTime();
    }

    /**
     * 如果date比当前时间早，返回true；反之返回false
     *
     * @param date
     * @return
     */
    public static boolean isBypastTime(Date date) {
        Calendar cd1 = Calendar.getInstance();
        Calendar cd2 = Calendar.getInstance();
        cd2.setTime(date);
        return cd1.after(cd2);
    }

    /**
     * 获取当前日期的n个月前日期
     *
     * @return
     */
    public static Date getBeforeNMonth(int n) {
        Calendar cd = Calendar.getInstance();
        cd.add(Calendar.MONTH, -n);
        return cd.getTime();
    }

    /**
     * 比较两个日期的大小
     *
     * @param date1
     * @param date2
     * @return
     */
    public static int compareDate(Date date1, Date date2) {
        if (date1.getTime() > date2.getTime()) {
            return 1;
        } else if (date1.getTime() < date2.getTime()) {
            return -1;
        } else {
            return 0;
        }
    }

    public static void main(String[] args) throws ParseException {
        Date date1 = new Date();
        Date date2 = convertStringToDate(DATE_TIME, "2018-07-23 00:00:00");
        System.out.println(compareDate(date1, date2));
    }

    /**
     * 两个日期相差的天数
     *
     * @param date1
     * @param date2
     * @return
     */
    public static int getGapOfTwoDate(Date date1, Date date2) {
        long day = (date1.getTime() - date2.getTime()) / (24 * 60 * 60 * 1000) > 0 ? (date1.getTime() - date2.getTime()) / (24 * 60 * 60 * 1000) : (date2.getTime() - date1.getTime())
                / (24 * 60 * 60 * 1000);
        return Integer.parseInt(String.valueOf(day));

    }

    /**
     * Date 转 localDate
     */
    public static LocalDate date2LocalDate(Date date) {
        Instant instant = date.toInstant();
        ZonedDateTime zdt = instant.atZone(ZoneId.systemDefault());
        LocalDate localDate = zdt.toLocalDate();
        return localDate;
    }

    /**
     * 计算两个日期相差多少个自然天
     *
     * @param beforeDate
     * @return
     */
    public static long intervalDays(Date beforeDate, Date afterDate) {
        LocalDate before = date2LocalDate(beforeDate);
        LocalDate after = date2LocalDate(afterDate);
        return DAYS.between(before, after);
    }

    public static String getStrBetweenDate(Date date1, Date date2) {

        if (date1 == null || date2 == null) {
            return null;
        }

        //前的时间
        Date fd = date1.getTime() <= date2.getTime() ? date1 : date2;
        //后的时间
        Date td = date1.getTime() > date2.getTime() ? date1 : date2;
        //两时间差,精确到毫秒
        long diff = td.getTime() - fd.getTime();
        //以天数为单位取整
        long day = diff / 86400000;
        //以小时为单位取整
        long hour = diff % 86400000 / 3600000;
        //以分钟为单位取整
        long min = diff % 86400000 % 3600000 / 60000;
        //以秒为单位取整
        long seconds = diff % 86400000 % 3600000 % 60000 / 1000;
        //天时分秒
        return day + "天" + hour + "小时" + min + "分" + seconds + "秒";
    }

    /**
     * 日期增加或者减少秒，分钟，天，月，年
     *
     * @param srcDate
     * @param type    类型 Y M D HH MM SS 年月日时分秒
     * @param offset  （整数）
     * @return 增加或者减少之后的日期
     */
    public static Date addDate(Date srcDate, String type, int offset) {
        GregorianCalendar gc = new GregorianCalendar();
        gc.setTime(srcDate);
        if ("SS".equals(type)) {
            gc.add(GregorianCalendar.SECOND, offset);
        } else if ("MM".equals(type)) {
            gc.add(GregorianCalendar.MINUTE, offset);
        } else if ("HH".equals(type)) {
            gc.add(GregorianCalendar.HOUR, offset);
        } else if ("D".equals(type)) {
            gc.add(GregorianCalendar.DATE, offset);
        } else if ("M".equals(type)) {
            gc.add(GregorianCalendar.MONTH, offset);
        } else if ("Y".equals(type)) {
            gc.add(GregorianCalendar.YEAR, offset);
        }
        return gc.getTime();
    }

    public static String addDate(String srcDate, String type, int offset) throws ParseException {
        return convertDateToString(addDate(convertStringToDate(srcDate), type, offset));
    }

    public static String addDate(String pattern, String srcDate, String type, int offset) throws ParseException {
        return convertDateToString(pattern, addDate(convertStringToDate(pattern, srcDate), type, offset));
    }

    /**
     * 根据日期计算年龄
     * @return int
     */
    public static int getAgeByBirthday(Date birthday) {
        Calendar cal = Calendar.getInstance();

        if (cal.before(birthday)) {
            throw new IllegalArgumentException("The birthDay is before Now.It's unbelievable!");
        }

        int yearNow = cal.get(Calendar.YEAR);
        int monthNow = cal.get(Calendar.MONTH) + 1;
        int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);

        cal.setTime(birthday);
        int yearBirth = cal.get(Calendar.YEAR);
        int monthBirth = cal.get(Calendar.MONTH) + 1;
        int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);

        int age = yearNow - yearBirth;

        if (monthNow <= monthBirth) {
            if (monthNow == monthBirth) {
                // monthNow==monthBirth
                if (dayOfMonthNow < dayOfMonthBirth) {
                    age--;
                }
            } else {
                // monthNow>monthBirth
                age--;
            }
        }
        return age;
    }


    public static boolean between(Date date, Date start, Date end) throws ParseException {
        return 0 <= getDay(start, date) && 0 >= getDay(end, date);
    }


    public static int compare(Date date1, Date date2) throws ParseException {
        return getDay(date1, date2);
    }

    public static int getDay(Date date1, Date date2) throws ParseException {
        if (null == date1 || null == date2) {
            return 0;
        }

        date1 = getDate(date1);
        date2 = getDate(date2);

        return Long.valueOf((date2.getTime() - date1.getTime()) / 86400000).intValue();
    }

    public static Date getDate(Date date) throws ParseException {
        if (null == date) {
            throw new IllegalArgumentException("date must not be a null!");
        }
        return getDate(DateUtil.datetimeToString(date));

    }

    public static Date getDate(String str) throws ParseException {

        if (null == str || "".equals(str)) {
            throw new IllegalArgumentException("date must not be a null!");
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        return sdf.parse(str);

    }

    public static String datetimeToString(Date date) {

        if (null == date) {
            throw new IllegalArgumentException("date must not be a null!");
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
        return sdf.format(date);
    }

    /**
     * 增加天数
     *
     * @param : Date date
     * @param : int days
     * @return Date
     */
    public static Date addDate(Date date, int days) {
        if (date == null) {
            return date;
        }
        Locale loc = Locale.getDefault();
        GregorianCalendar cal = new GregorianCalendar(loc);
        cal.setTime(date);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        int month = cal.get(Calendar.MONTH);
        int year = cal.get(Calendar.YEAR);
        cal.set(year, month, day + days);
        return cal.getTime();
    }

    /**
     * 将当前时间转换为当日零点零分零秒
     *
     * @param date
     * @return
     */
    public static Date getMonningDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTime();
    }

    /**
     * 获取上周一
     *
     * @param date
     * @return
     */
    public static Date getLastWeekMonday(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DAY_OF_MONTH, -1);
        // 一周
        cal.add(Calendar.WEEK_OF_YEAR, -1);
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        return cal.getTime();
    }

    /**
     * 获取省周日
     *
     * @param date
     * @return
     */
    public static Date getLastWeekSunday(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.DAY_OF_MONTH, -1);
        cal.set(Calendar.DAY_OF_WEEK, 1);
        return cal.getTime();
    }

    /**
     * 将日期改为当天的23点59分59秒
     *
     * @param date
     * @return
     */
    public static Date getEveningDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        return calendar.getTime();
    }

    /**
     * 获取当前月第一天
     *
     * @param date
     * @return
     */
    public static Date getMonthStart(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        return cal.getTime();
    }

    /**
     * 获取当前月最后一天
     *
     * @param date
     * @return
     */
    public static Date getMonthEnd(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MONTH, 1);
        cal.set(Calendar.DAY_OF_MONTH, 0);
        return cal.getTime();
    }

    /**
     * 获取当前年当前周周一
     *
     * @param year
     * @param week
     * @return
     */
    public static Date getYearWeekFirstDay(Integer year, Integer week) {
        Calendar cal = Calendar.getInstance();
        cal.setFirstDayOfWeek(Calendar.MONDAY);
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        cal.setMinimalDaysInFirstWeek(7);
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.WEEK_OF_YEAR, week);
        return cal.getTime();
    }

    /**
     * 获取当前年当前周周日
     *
     * @param year
     * @param week
     * @return
     */
    public static Date getYearWeekEndDay(Integer year, Integer week) {
        Calendar cal = Calendar.getInstance();
        cal.setFirstDayOfWeek(Calendar.MONDAY);
        cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        cal.setMinimalDaysInFirstWeek(7);
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.WEEK_OF_YEAR, week);
        return cal.getTime();
    }

    /**
     * 获取当前年
     *
     * @return Integer
     */
    public static Integer getNowYear() {
        Calendar cal = Calendar.getInstance();
        return cal.get(Calendar.YEAR);
    }

    /**
     * 获取该年有多少周
     *
     * @param year 年
     * @return Integer
     */
    public static Integer getWeeksOfYear(Integer year) {
        int result = 52;
        Date d = getYearWeekFirstDay(year, 53);
        String date = convertDateToString(DATE, d);
        //判断年度是否相符，如果相符说明有53个周。
        if (date.substring(0, 4).equals(year + "")) {
            result = 53;
        }
        return result;
    }

    /**
     * 获取下一个月第一天 时分秒清零
     *
     * @param date
     * @return Date
     */
    public static Date getNextMonthStartReset(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MONTH, 1);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    /**
     * 获取当前月第一天 时分秒清零
     *
     * @param date Date
     * @return Date
     */
    public static Date getMonthStartReset(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    public static Date getYearFirstDay() {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.MONTH, 0);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    public static Date getNextYearFirstDay() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.YEAR, 1);
        cal.set(Calendar.MONTH, 0);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    public static Date getNextQuarterStart(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int month = cal.get(Calendar.MONTH);
        if (month < Calendar.APRIL) {
            cal.set(Calendar.MONTH, Calendar.APRIL);
            return getMonthStartReset(cal.getTime());
        } else if (month < Calendar.JULY) {
            cal.set(Calendar.MONTH, Calendar.JULY);
            return getMonthStartReset(cal.getTime());
        } else if (month < Calendar.OCTOBER) {
            cal.set(Calendar.MONTH, Calendar.OCTOBER);
            return getMonthStartReset(cal.getTime());
        } else {
            cal.set(Calendar.MONTH, Calendar.JANUARY);
            cal.add(Calendar.YEAR, 1);
            return getMonthStartReset(cal.getTime());
        }
    }

    /**
     * 返回给前端的时间格式转换
     *
     * @param strDate    String
     * @param fromFormat String
     * @param toFormat   String
     * @return String
     */
    public static String formatDateString(String strDate, String fromFormat, String toFormat) throws ParseException {

        Objects.requireNonNull(strDate);
        Objects.requireNonNull(fromFormat);
        Objects.requireNonNull(toFormat);

        Date date = null;
        date = convertStringToDate(fromFormat, strDate);
        String resultDateString = convertDateToString(toFormat, date);
        return resultDateString;
    }

    public static Date firstDayOfMonth(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.MONTH, 0);
        // 设置为1号,当前日期既为本月第一天
        c.set(Calendar.DAY_OF_MONTH, 1);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        return c.getTime();
    }

    public static Date oneDayAfter(Date date) {
        return new Date(date.getTime() + 24 * 3600 * 1000);
    }

    public static Date firstDayOfRecentMonth() {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH, 0);
        // 设置为1号,当前日期既为本月第一天
        c.set(Calendar.DAY_OF_MONTH, 1);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        return c.getTime();
    }

    public static Date lastDayOfMonth(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.MONTH, 0);
        c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
        c.set(Calendar.HOUR_OF_DAY, 24);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        return c.getTime();
    }

    public static Date firstDayOfYear(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.MONTH, Calendar.JANUARY);
        c.set(Calendar.DAY_OF_MONTH, 1);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        return c.getTime();
    }

    public static Date firstDayOfRecentSeason(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int month = c.get(Calendar.MONTH);
        c.set(Calendar.DAY_OF_MONTH, 1);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);

        switch (month) {
            case Calendar.JANUARY:
                c.set(Calendar.MONTH, Calendar.JANUARY);
                break;
            case Calendar.FEBRUARY:
                c.set(Calendar.MONTH, Calendar.JANUARY);
                break;
            case Calendar.MARCH:
                c.set(Calendar.MONTH, Calendar.JANUARY);
                break;
            case Calendar.APRIL:
                c.set(Calendar.MONTH, Calendar.APRIL);
                break;
            case Calendar.MAY:
                c.set(Calendar.MONTH, Calendar.APRIL);
                break;
            case Calendar.JUNE:
                c.set(Calendar.MONTH, Calendar.APRIL);
                break;
            case Calendar.JULY:
                c.set(Calendar.MONTH, Calendar.JULY);
                break;
            case Calendar.AUGUST:
                c.set(Calendar.MONTH, Calendar.JULY);
                break;
            case Calendar.SEPTEMBER:
                c.set(Calendar.MONTH, Calendar.JULY);
                break;
            case Calendar.OCTOBER:
                c.set(Calendar.MONTH, Calendar.OCTOBER);
                break;
            case Calendar.NOVEMBER:
                c.set(Calendar.MONTH, Calendar.OCTOBER);
                break;
            case Calendar.DECEMBER:
                c.set(Calendar.MONTH, Calendar.OCTOBER);
                break;
            default:
                break;
        }
        return c.getTime();
    }

    public static Date firstDayOfLastSeason(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int month = c.get(Calendar.MONTH);
        c.set(Calendar.DAY_OF_MONTH, 1);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);

        switch (month) {
            case Calendar.JANUARY:
                c.add(Calendar.YEAR, -1);
                c.set(Calendar.MONTH, Calendar.OCTOBER);
                break;
            case Calendar.FEBRUARY:
                c.add(Calendar.YEAR, -1);
                c.set(Calendar.MONTH, Calendar.OCTOBER);
                break;
            case Calendar.MARCH:
                c.add(Calendar.YEAR, -1);
                c.set(Calendar.MONTH, Calendar.OCTOBER);
                break;
            case Calendar.APRIL:
                c.set(Calendar.MONTH, Calendar.JANUARY);
                break;
            case Calendar.MAY:
                c.set(Calendar.MONTH, Calendar.JANUARY);
                break;
            case Calendar.JUNE:
                c.set(Calendar.MONTH, Calendar.JANUARY);
                break;
            case Calendar.JULY:
                c.set(Calendar.MONTH, Calendar.APRIL);
                break;
            case Calendar.AUGUST:
                c.set(Calendar.MONTH, Calendar.APRIL);
                break;
            case Calendar.SEPTEMBER:
                c.set(Calendar.MONTH, Calendar.APRIL);
                break;
            case Calendar.OCTOBER:
                c.set(Calendar.MONTH, Calendar.JULY);
                break;
            case Calendar.NOVEMBER:
                c.set(Calendar.MONTH, Calendar.JULY);
                break;
            case Calendar.DECEMBER:
                c.set(Calendar.MONTH, Calendar.JULY);
                break;
            default:
                break;
        }

        return c.getTime();
    }

    public static Date firstDayOfLastMonth(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.MONTH, -1);
        // 设置为1号,当前日期既为本月第一天
        c.set(Calendar.DAY_OF_MONTH, 1);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        return c.getTime();
    }

    public static Date firstDayOfLastMonth() {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH, -1);
        ;// 设置为1号,当前日期既为本月第一天
        c.set(Calendar.DAY_OF_MONTH, 1);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        return c.getTime();
    }

    public static Date lastDayOfLastMonth() {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.MONTH, 0);
        // 设置为1号,当前日期既为本月第一天
        c.set(Calendar.DAY_OF_MONTH, 1);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        return c.getTime();
    }

    /**
     * 获取当天剩余时间（秒）
     *
     * @return Long
     */
    public static Long getRemainTimes() {

        LocalDateTime midnight = LocalDateTime.now().plusDays(1).withHour(0).withMinute(0).withSecond(0).withNano(0);
        return ChronoUnit.SECONDS.between(LocalDateTime.now(), midnight);
    }

    public static String timeStamp2Date(String seconds, String format) {
        if (seconds == null || seconds.isEmpty() || "null".equals(seconds)) {
            return "";
        }
        if (format == null || format.isEmpty()) {
            format = "yyyy-MM-dd HH:mm:ss";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(new Date(Long.valueOf(seconds)));
    }

    /**
     * 获取上个月第一天
     */
    public static Date getBeforeMonthDay() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -1);
        //设置为1号,当前日期既为本月第一天
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        return calendar.getTime();
    }

    /**
     * 获取上个月最后一天
     */
    public static Date getLastMonthDay() {
        //获取上个月的最后一天
        Calendar cale = Calendar.getInstance();
        cale.set(Calendar.DAY_OF_MONTH, 0);
        return cale.getTime();
    }

    /**
     * 获取日期
     */
    public static Date getDateExcel(String cellValue) {
        try {
            return convertStringToDate(DateUtil.DATE, cellValue);
        } catch (ParseException ignore) {

        }

        try {
            return convertStringToDate(DateUtil.DATE_D, cellValue);
        } catch (ParseException ignore) {

        }

        try {
            return convertStringToDate(DateUtil.DATE_CHINESE, cellValue);
        } catch (ParseException ignore) {

        }
        try {
            return convertStringToDate(DateUtil.DATE_, cellValue);
        } catch (ParseException ignore) {

        }
        throw new ApplicationException();
    }

    /**
     * 获取日期
     */
    public static Date getTimeExcel(String cellValue) {
        try {
            return convertStringToDate(DateUtil.DATE_TIME, cellValue);
        } catch (ParseException ignore) {

        }

        try {
            return convertStringToDate(DateUtil.DATE_D_TIME, cellValue);
        } catch (ParseException ignore) {

        }

        try {
            return convertStringToDate(DateUtil.DATE_TIME_, cellValue);
        } catch (ParseException ignore) {

        }
        throw new ApplicationException();
    }


    public static String converLongToDate(String format, Long time) {
        if (time == null || time == 0) {
            return "";
        }
        Date date = new Date(time);
        return convertDateToString(format, date);
    }

    public static long getTime(String format, String dateStr) {
        try {
            Date date = convertStringToDate(format, dateStr);
            return date.getTime();
        } catch (Exception e) {
            throw new ApplicationException(ReturnCode.ERROR_1002.getCode(), "时间格式有误，格式为：" + format);
        }

    }


}



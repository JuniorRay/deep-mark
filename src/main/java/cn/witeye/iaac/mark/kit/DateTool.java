package cn.witeye.iaac.mark.kit;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;

import com.jfinal.kit.StrKit;
import com.jfinal.log.Log;

import cn.witeye.iaac.mark.Const;


public class DateTool {
	private static final Log log = Log.getLog(DateTool.class);
	private static final String regEx0 = "20[0-9]{2}(0[1-9]|1[0-2])(0[1-9]|[1-2][0-9]|3[0-1])([0-1][0-9]|2[0-3])[0-5][0-9][0-5][0-9]"; //yyyyMMddHHmmss
    private static final String regEx1 = "1[0-9]{12}"; //长整型毫秒
    private static final String regEx2 = "1[0-9]{9}"; //长整型秒
    public static final String[] regExArr = {regEx0, regEx1, regEx2};

	/**
	 * 将java.util.Date 格式转换为字符串格式'yyyy-MM-dd HH:mm:ss'(24小时制)<br>
	 * 如Sat May 11 17:24:21 CST 2002 to '2002-05-11 17:24:21'<br>
	 *
	 * @param time Date 日期<br>
	 * @return String   字符串<br>
	 */

	public static String dateToString(Date time) {
		SimpleDateFormat formatter;
		formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String ctime = formatter.format(time);
		return ctime;
	}

	/**
	 * 将java.util.Date 当前时间 格式转换为字符串格式（自定义字符串） 
	 * 如Sat May 11 17:24:21 CST 2002 to '2002-05-11 17:24:21'<br>
	 *
	 * @param dateStr 自定义字符串 例如 yyyy-MM-dd HH:mm:ss
	 * @return String   字符串<br>
	 */
	public static String dateToString(String dateStr) {
		SimpleDateFormat formatter;
		formatter = new SimpleDateFormat(dateStr);
		String ctime = formatter.format(new Date());
		return ctime;
	}

	/**
	 * 字符串转换为java.util.Date<br>
	 * 支持格式为 yyyy.MM.dd G 'at' hh:mm:ss z 如 '2002-1-1 AD at 22:10:59 PSD'<br>
	 * yy/MM/dd HH:mm:ss 如 '2002/1/1 17:55:00'<br>
	 * yy/MM/dd HH:mm:ss pm 如 '2002/1/1 17:55:00 pm'<br>
	 * yy-MM-dd HH:mm:ss 如 '2002-1-1 17:55:00' <br>
	 * yy-MM-dd HH:mm:ss am 如 '2002-1-1 17:55:00 am' <br>
	 *
	 * @param time String 字符串<br>
	 * @return Date 日期<br>
	 */
	public static Date stringToDate(String time) {
		SimpleDateFormat formatter;
		int tempPos = time.indexOf("AD");
		time = time.trim();
		formatter = new SimpleDateFormat("yyyy.MM.dd G 'at' hh:mm:ss z");
		if (tempPos > -1) {
			time = time.substring(0, tempPos) + "公元" + time.substring(tempPos + "AD".length());//china
			formatter = new SimpleDateFormat("yyyy.MM.dd G 'at' hh:mm:ss z");
		}
		tempPos = time.indexOf("-");
		if (tempPos > -1 && (time.indexOf(" ") < 0)) {
			formatter = new SimpleDateFormat("yyyyMMddHHmmssZ");
		} else if ((time.indexOf("/") > -1) && (time.indexOf(" ") > -1)) {
			formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		} else if ((time.indexOf("-") > -1) && (time.indexOf(" ") > -1)) {
			formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		} else if ((time.indexOf("/") > -1) && (time.indexOf("am") > -1) || (time.indexOf("pm") > -1)) {
			formatter = new SimpleDateFormat("yyyy-MM-dd KK:mm:ss a");
		} else if ((time.indexOf("-") > -1) && (time.indexOf("am") > -1) || (time.indexOf("pm") > -1)) {
			formatter = new SimpleDateFormat("yyyy-MM-dd KK:mm:ss a");
		}
		ParsePosition pos = new ParsePosition(0);
		java.util.Date ctime = formatter.parse(time, pos);

		return ctime;
	}

//	public static String cameraStatus(Date date) {
//		DateTime old = new DateTime(date.getTime());
//		boolean offline = old.isBefore(DateTime.now().minusMinutes(Const.CAMERA_OFFLINE_TIME));
//		return offline ? old.toString(Const.DTM_FORMAT) : "在线";
//	}

	/**
	 * 获取传入月的第一天零时
	 *
	 * @return
	 */
	public static String getFirstDayOfMonth(String str) {
		Date date = null;
		try {
			date = new SimpleDateFormat(Const.DD_FORMAT).parse(str);
		} catch (ParseException e) {
			log.error("", e);
		}
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(Calendar.DAY_OF_MONTH, 1);// 设置为1号,当前日期既为本月第一天
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		return new SimpleDateFormat(Const.DTS_FORMAT).format(c.getTime());
	}

	/**
	 * 获取传入月最后一天
	 *
	 * @return
	 */
	public static String getEndDayOfMonth(String str) {
		Date date = null;
		try {
			date = new SimpleDateFormat(Const.DD_FORMAT).parse(str);
		} catch (ParseException e) {
			log.error("", e);
		}
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.MONTH, 1);
		c.add(Calendar.DAY_OF_MONTH, -1);
		c.set(Calendar.HOUR_OF_DAY, 23);
		c.set(Calendar.MINUTE, 59);
		c.set(Calendar.SECOND, 59);
		c.set(Calendar.MILLISECOND, 999);
		return new SimpleDateFormat(Const.DTS_FORMAT).format(c.getTime());
	}

	/**
	 * 获取某天的零时
	 *
	 * @param date
	 * @return
	 */
	public static Date getStartTimeOfDate(Date date) {
		Calendar day = Calendar.getInstance();
		day.setTime(date);
		day.set(Calendar.HOUR_OF_DAY, 0);
		day.set(Calendar.MINUTE, 0);
		day.set(Calendar.SECOND, 0);
		day.set(Calendar.MILLISECOND, 0);
		return day.getTime();
	}

	/**
	 * 获取某天的末时
	 *
	 * @param date
	 * @return
	 */
	public static Date getEndTimeOfDate(Date date) {
		Calendar day = Calendar.getInstance();
		day.setTime(date);
		day.set(Calendar.HOUR_OF_DAY, 23);
		day.set(Calendar.MINUTE, 59);
		day.set(Calendar.SECOND, 59);
		day.set(Calendar.MILLISECOND, 999);
		return day.getTime();
	}

	/**
	 * 获取传入时间的后一天的零时
	 *
	 * @param date
	 * @return
	 */
	public static Date getAfterStartTimeOfDate(Date date) {
		Calendar day = Calendar.getInstance();
		day.setTime(date);
		day.add(Calendar.DAY_OF_MONTH, 1);
		day.set(Calendar.HOUR_OF_DAY, 0);
		day.set(Calendar.MINUTE, 0);
		day.set(Calendar.SECOND, 0);
		day.set(Calendar.MILLISECOND, 0);
		return day.getTime();
	}

	/**
	 * 获取传入时间的后一天的末时
	 *
	 * @param date
	 * @return
	 */
	public static Date getAfterEndTimeOfDate(Date date) {
		Calendar day = Calendar.getInstance();
		day.setTime(date);
		day.add(Calendar.DAY_OF_MONTH, 1);
		day.set(Calendar.HOUR_OF_DAY, 23);
		day.set(Calendar.MINUTE, 59);
		day.set(Calendar.SECOND, 59);
		day.set(Calendar.MILLISECOND, 999);
		return day.getTime();
	}

	public static String getFirstDayOfMonth() {
		Calendar day = Calendar.getInstance();
		day.add(Calendar.MONTH, 0);
		day.set(Calendar.DAY_OF_MONTH, 1);
		day.set(Calendar.HOUR_OF_DAY, 0);
		day.set(Calendar.MINUTE, 0);
		day.set(Calendar.SECOND, 0);
		day.set(Calendar.MILLISECOND, 0);
		return new SimpleDateFormat(Const.DT_FORMAT).format(day.getTime());
	}

	/*
	 * 传入时间字符串，和字符串的格式。把字符串转换成时间Date对象
	 * @author Junior
	 * @Date 2018-4-25
	 */
	public static Date parseToDate(String dateStr,
			String format) throws Exception {
		SimpleDateFormat sf = new SimpleDateFormat(format);
		Date date = null;
		try {
			date = sf.parse(dateStr);

		} catch (Exception e) {

			throw new Exception("时间解析异常,请检查格式或者时间");
		}

		return date;
	}

	/*
	 * 案件信息excel专用时间解析，遇到只有日期没时间的情况也直接解析
	 * @author Junior
	 * @date 2018-4-25
	 */

	public static Date customParseDate(String dateStr) throws Exception {
		int time = dateStr.split("\n").length;
		SimpleDateFormat sf;
		dateStr = dateStr.replace("\n", " ");
		if (time >= 2) {
			sf = new SimpleDateFormat("yyyy/MM/dd HH:mm");

		} else {
			sf = new SimpleDateFormat("yyyy/MM/dd");
		}
		Date date = null;
		try {
			date = sf.parse(dateStr);

		} catch (Exception e) {

			throw new Exception("时间解析异常,请检查格式或者时间");
		}

		return date;
	}

	//根据任意格式化时间@author Junior @Date 2018-5-2
	public static String formatDate(Date date,
			String format) {
		SimpleDateFormat sf = new SimpleDateFormat(format);
		String strDate = sf.format(date);
		return strDate;
	}

	public static String getGMT(Date dateCST,
			String format) {
		//        format="yyyy-MM-dd HH:mm:ss z";
		DateFormat df = new SimpleDateFormat(format, Locale.ENGLISH);
		df.setTimeZone(TimeZone.getTimeZone("GMT")); // modify Time Zone.
		return (df.format(dateCST));
	}

	public static String formatDate2(Date date,
			String format) {
		DateFormat cstFormat = new SimpleDateFormat();
		DateFormat gmtFormat = new SimpleDateFormat();
		TimeZone gmtTime = TimeZone.getTimeZone("GMT");
		TimeZone cstTime = TimeZone.getTimeZone("CST");
		cstFormat.setTimeZone(gmtTime);
		gmtFormat.setTimeZone(cstTime);
		SimpleDateFormat sf = new SimpleDateFormat(format, Locale.US);
		String strDate = sf.format(date);
		return strDate;
	}

	/**
	 * 取得当月天数
	 * */
	public static int getCurrentMonthTotalDay() {
		Calendar a = Calendar.getInstance();
		a.set(Calendar.DATE, 1);//把日期设置为当月第一天
		a.roll(Calendar.DATE, -1);//日期回滚一天，也就是最后一天
		int maxDate = a.get(Calendar.DATE);
		return maxDate;
	}

	/**
	 * 当月天数集合
	 * 
	 * @return
	 */
	public static int[] getCurrentDatArray() {
		int totalday = DateTool.getCurrentMonthTotalDay();
		int[] mon = new int[totalday];
		for (int i = 1; i <= totalday; i++) {
			mon[i] = i;
		}
		return mon;

	}

	/**
	 * 月份集合
	 * 
	 * @return
	 */
	public static int[] getCurrentMonthArray() {
		int[] mon = new int[] { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12 };
		return mon;

	}
	
	// 从字符串中提取时间
    public static Date[] matcher(String str, String regEx, int index) {
        String starTime = "";
        String endTime = "";
        Pattern pat = Pattern.compile(regEx);
        Matcher mat = pat.matcher(str);
        while(mat.find()){
            String result = mat.group();
            if(StrKit.notBlank(result)) {
                System.out.println(result);
                if(StrKit.isBlank(starTime)){
                    starTime = result;
                } else {
                    endTime = result;
                    break;
                }
            }
        }
        if(StrKit.isBlank(starTime)) {
            return null;
        }
        if(StrKit.notBlank(starTime) && StrKit.isBlank(endTime)){
            endTime = starTime;
        }
        Date starDate = getDate(starTime, index);
        Date endDate = getDate(endTime, index);
        return new Date[]{starDate, endDate};
    }
    private static Date getDate(String dateStr, int index) {
        Date date = null;
        switch (index) {
            case 1:
                date = new Date(Long.valueOf(dateStr)); //长整型毫秒
                break;
            case 2:
                date = new Date(Long.valueOf(dateStr)*1000); //长整型秒
                break;
            default:
                date = DateTimeFormat.forPattern("yyyyMMddHHmmss").parseDateTime(dateStr).toDate(); //yyyyMMddHHmmss
                break;
        }
        return date;
    }

	public static void main(String[] args) throws Exception {
		//		String dateStr="2018/2/1 09:09";
		Date date = new Date();
		String format = "yyyy/MM/dd HH:mm";
		String dateStr = formatDate(date, format);
		System.out.println("dateStr==" + dateStr);
		List<String> dayList = getDays("20190225", "20190301");
		for (String string : dayList) {
			System.out.println(string);
		}
 		
	}
	
	public static List<String> getDays(String startTime, String endTime) {

        // 返回的日期集合
        List<String> days = new ArrayList<String>();

        DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        try {
            Date start = dateFormat.parse(startTime);
            Date end = dateFormat.parse(endTime);

            Calendar tempStart = Calendar.getInstance();
            tempStart.setTime(start);

            Calendar tempEnd = Calendar.getInstance();
            tempEnd.setTime(end);
            tempEnd.add(Calendar.DATE, +1);// 日期加1(包含结束)
            while (tempStart.before(tempEnd)) {
                days.add(dateFormat.format(tempStart.getTime()));
                tempStart.add(Calendar.DAY_OF_YEAR, 1);
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return days;
    }
}

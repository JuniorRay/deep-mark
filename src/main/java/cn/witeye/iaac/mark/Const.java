package cn.witeye.iaac.mark;

import java.nio.charset.Charset;
import java.util.regex.Pattern;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import com.jfinal.kit.PropKit;
import com.jfinal.kit.StrKit;


/**
 * 常量类
 */
public class Const {
	public static final Charset UTF8 = Charset.forName("UTF-8");
    public static final String MSG = "msg";
    // 最大上传文件大小
 	public static final int MAX_POST_SIZE = PropKit.getInt("max.post.size", 10 * 1024 * 1024);
    // 日期时间格式化
    public static final String DMY_FORMAT = "yyyyMMdd";
    public static final String DT_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String DTM_FORMAT = "yyyy-MM-dd HH:mm";
    public static final String DTS_FORMAT = "yyyy-MM-dd HH:mm:ss:SSS";
    public static final String DD_FORMAT = "yyyy-MM";
	public static final String DT_YMDHM = "yyyyMMddHHmm";
	public static final String DT_YMD = "yyyyMMdd";
	public static final String DT_YMDH = "yyyyMMddHH";
	public static final String DT_YMDHMS = "yyyyMMddHHmmss";
	public static final String DT_YMDHMSSSS = "yyyyMMddHHmmssSSS";
	public static final String DT_YMDHMSSSS_POINT = "yyyy-MM-dd HH:mm:ss SSS";
	
    // 日期格式化
    public static final String D_FORMAT = "yyyy-MM-dd";
    // 时间格式化
    public static final String T_FORMAT = "HH:mm";
    // 时间格式化
    public static final String TS_FORMAT = "HH:mm:ss";

    public static final DateTimeFormatter D_DTF = DateTimeFormat.forPattern("yyyy-MM-dd");
    public static final DateTimeFormatter DT_DTF = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm");
    public static final DateTimeFormatter DTF_DTF = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
    public static final DateTimeFormatter DTFS_DTF = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss:SSS");
    // 数据存放基础目录
 	public static final String BASE_PATH = PropKit.get("base.path", "D:/demo");
    // base upload path 上传路径
    public static final String BASE_UPLOAD_PATH = PropKit.get("base.upload.path","upload");
    // base.excelTemplate.path 2018-4-26 by junior
    public static final String BASE_EXCElTEMPLATE_PATH = PropKit.get("base.excelTemplate.path");
    // websocket path
    public static final String WS_PATH = "/ws/msg.ws";
    // websocket port
    public static final int WS_PORT = PropKit.getInt("ws.port", 10215);
	public static final boolean DEV_MODE = PropKit.getBoolean("devMode", false);

    // 是否显示百度地图
    public static final String[] MAP_CENTER = PropKit.get("map.center", "114.072584,22.679515").split(",");
   
    
}
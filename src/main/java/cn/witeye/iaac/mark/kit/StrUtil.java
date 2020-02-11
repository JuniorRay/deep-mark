package cn.witeye.iaac.mark.kit;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

import com.jfinal.kit.StrKit;
import com.jfinal.plugin.activerecord.Record;

/*
 * String工具类
 * @author Junior
 * @date 2013-4-25
 */
public class StrUtil {

    // 判断是否为数字
    public static boolean isNumeric(String str) {
        if (StrUtil.isBlank(str)) {
            return false;
        }
        String reg = "\\d+(\\.\\d+)?";

        if (!str.trim().matches(reg)) {
            return false;
        } else {
            return true;
        }

    }


    public static String getPTZTypeByInt(String PTZType) {
        switch (PTZType) {
            case "1":
                return "球机";
            case "2":
                return "半球";
            case "3":
                return "固定枪机";
            case "4":
                return "遥控枪机";

            default:
                return "未定义";
        }
    }

    public static String getRoomTypeByInt(String RoomType) {
        switch (RoomType) {
            case "1":
                return "室内";
            case "2":
                return "室外";
            default:
                return "未定义";
        }
    }

    // 国标的方向定义
    public static String getDirectionTypeByInt(String directionType) {
        int direction = Integer.parseInt(directionType);
        switch (direction) {
            case 1:
                return "东";
            case 5:
                return "东南";
            case 3:
                return "南";
            case 7:
                return "西南";
            case 2:
                return "西";
            case 8:
                return "西北";
            case 4:
                return "北";
            case 6:
                return "东北";
            default:
                return "未定义";
        }

    }

    // 国标的方向定义
    public static String getDMarkerByInt(String dMarker) {
        int dMarkerNum = Integer.parseInt(dMarker);
        switch (dMarkerNum) {
            case 0:
                return "未变化";
            case 1:
                return "增加";
            case 2:
                return "删除";
            case 3:
                return "修改";
            case 4:
                return "已同步，可删除";
            default:
                System.out.println("未定义");
                return "未定义";
        }

    }

    public static String isUseByBoolean(String str) {
        if ("0".equals(str)) {
            return "禁用";
        } else if ("1".equals(str)) {
            return "启用";
        } else {
            return "未定义";
        }
    }

    public static int isUseToBoolean(String str) {
        if ("禁用".equals(str)) {
            return 0;
        } else if ("启用".equals(str)) {
            return 1;
        } else {
            return 1;
        }
    }

    /*
     * 字符串转成数字BigDecimal
     *
     * @author Junior
     *
     * @date May 14, 2018
     *
     * @param str
     *
     * @return
     */
    public static BigDecimal toBigDecimal(String str) throws Exception {
        try {
            if (StrKit.isBlank(str)) {
                BigDecimal big = new BigDecimal("0");
                return big;
            }
            if (StrUtil.isNumeric(str)) {

                BigDecimal big = new BigDecimal(str.trim());
                return big;

            } else {
                throw new Exception("非法字符，不是数字导致转换BigDecimal出现异常");
            }
        } catch (Exception e) {
            e.printStackTrace();

            throw new Exception("字符串转换BigDecimal出现异常");
        }
    }

    // 数据库中存储的excel图片路径字符串转换成数组
    public static String[] photoPathToArray(String photoPath) throws Exception {
        if (photoPath != null) {
            String str = photoPath.replace("[", "").replace("]", "").replaceAll("\\s*", "");
            // System.out.println("str==="+str);
            String[] photoPathArr = str.split(",");
            return photoPathArr;
        } else {
            throw new Exception("photoPath为空！出现异常！");
        }
    }

    // 字符转换成int越界判断
    public static boolean isTooLong(String str) {
        int b = 0;
        try {
            b = Integer.parseInt(str);
            return false;
        } catch (Exception e) {
            return true;
        }

    }
    // @Test
    // public void testIsTooLong() {
    // String a = "11111";
    // boolean flag=isTooLong(a);
    // System.out.println("falg==="+flag);
    // }

    // 强转成long类型
    public static long castToLong(Object obj) throws Exception {
        long i = -1;
        try {
            i = Long.parseLong(castToStr(obj));
            return i;
        } catch (Exception e) {
            throw new Exception("非法的数字格式或者long越界，转换失败！");
        }

    }

    // 强转成int类型数字
    public static int castToInt(Object obj) throws Exception {
        int i = -1;
        try {
            i = Integer.parseInt(castToStr(obj));
            return i;
        } catch (Exception e) {
            throw new Exception("非法的数字格式或者int越界，转换失败！");
        }

    }

    // 强转成字符串
    public static String castToStr(Object obj) {
        String str = "";
        str = ((obj == null) || (obj == "")) ? " " : String.valueOf(obj);

        str = str.replaceAll("\\s*", "").replace("\n", "");// 去除所有空格和换行符号
        return str;
    }

    // 强转成字符串把换行符号替换成"/"
    public static String toStrHandlerTime(Object obj) throws Exception {
        // System.out.println("toStr==="+obj);
        String str = "";
        str = ((obj == null) || (obj == "")) ? " " : String.valueOf(obj);

        str = str.trim().replaceAll("\n", "/");// 去除所有空格和换行符号替换成空格
        SimpleDateFormat sf;

        if (str.length() <= 10) {
            sf = new SimpleDateFormat("yyyy/MM/dd");

        } else {
            sf = new SimpleDateFormat("yyyy/MM/dd/HH:mm");
        }
        Date date = null;
        try {
            date = sf.parse(str);

        } catch (Exception e) {

            throw new Exception("时间解析异常,请检查格式或者时间");
        }

        // System.out.println("date====="+date);
        str = DateTool.formatDate(date, "yyyy-MM-dd HH:mm:ss");
        return str;
    }

    // 强转成字符串把换行符号替换成空格
    public static String toStrHandlerName(Object obj) {
        // System.out.println("toStrHandlerName==="+obj);
        String str = "";
        str = ((obj == null) || (obj == "")) ? " " : String.valueOf(obj);

        str = str.replaceAll("\n", ",").replaceAll("[ ]+", ",");// 去除所有空格和换行符号替换成逗号
        return str;
    }

    // 格式替换成数字0，1
    public static int hasToBoolean(String str) throws Exception {
        if (("有".equals(str)) || ("是".equals(str)) || ("1".equals(str))) {
            return 1;
        } else if ((str == null || "".equals(str) || "无".equals(str)) || ("否".equals(str)) || ("0".equals(str))) {
            return 0;

        } else {
            throw new Exception("超出了hasToBoolean的定义格式");

        }

    }

    // 0,1替换成数字有，无
    public static String booleanToHas(String str) throws Exception {
        if (("1".equals(str))) {
            return "有";
        } else if (("0".equals(str))) {
            return "无";

        } else {
            throw new Exception("超出了booleanToHas的定义格式");

        }

    }

    public static boolean isNullOrEmpty(String str) {
        return (str == null) || ("".equals(str.trim()));
    }

    public static boolean hasContents(String str) {
        return !isNullOrEmpty(str);
    }

    public static String removeStr(String source, String str, String sp) {
        String r = source;
        if (source.equals(str)) {
            return "";
        }
        if (source.startsWith(str + sp)) {
            r = source.substring((str + sp).length());
        } else if (source.endsWith(sp + str)) {
            r = source.substring(0, source.length() - (sp + str).length());
        } else {
            r = source.replace(sp + str + sp, sp);
        }
        if (r.endsWith(sp)) {
            r = r.substring(0, r.length() - sp.length());
        }
        if (r.startsWith(sp)) {
            r = r.substring(sp.length());
        }
        return r;
    }

    public static String mergeString(String a, String b, String sp) {
        String r = b;
        if (r.endsWith(sp)) {
            r = r.substring(0, r.length() - sp.length());
        }
        String[] as = a.split(sp);
        for (String aa : as) {
            if (!"".equals(aa)) {
                boolean isRepeat = false;
                String[] bs = r.split(sp);
                for (String bb : bs) {
                    if (aa.equals(bb)) {
                        isRepeat = true;
                        break;
                    }
                }
                if (!isRepeat) {
                    r = r + sp + aa;
                }
            }
        }
        if (r.endsWith(sp)) {
            r = r.substring(0, r.length() - sp.length());
        }
        if (r.startsWith(sp)) {
            r = r.substring(sp.length());
        }
        return r;
    }

    public static boolean isBlank(String str) {
        if (str == null) {
            return true;
        }
        int len = str.length();
        if (len == 0) {
            return true;
        }
        for (int i = 0; i < len; i++) {
            switch (str.charAt(i)) {
                case ' ':
                case '\t':
                case '\n':
                case '\r':
                    // case '\b':
                    // case '\f':
                    break;
                default:
                    return false;
            }
        }
        return true;
    }

    /*
     * 通过数字获取方向转换
     *
     * @author Junior
     *
     * @date May 16, 2018
     *
     * @param num
     *
     * @return
     */
    public static String getCamDirectionByNum(int num) {
        switch (num) {
            case 0:
                System.out.println("东");
                return "东";
            case 1:
                System.out.println("东南");
                return "东南";
            case 2:
                System.out.println("南");
                return "南";
            case 3:
                System.out.println("西南");
                return "西南";
            case 4:
                System.out.println("西");
                return "西";
            case 5:
                System.out.println("西北");
                return "西北";
            case 6:
                System.out.println("北");
                return "北";
            case 7:
                System.out.println("东北");
                return "东北";
            default:
                System.out.println("未定义");
                return "未定义";
        }

    }

    /*
     * 通过方向获取数字转换
     *
     * @author Junior
     *
     * @date May 16, 2018
     *
     * @param num
     *
     * @return
     */
    public static int getCamNumByDirection(String direction) {
        direction = StrUtil.castToStr(direction);
        if (direction == null) {
            direction = "";
        }
        switch (direction) {
            case "东":
                return 0;
            case "东南":
                return 1;
            case "南":
                return 2;
            case "西南":
                return 3;
            case "西":
                return 4;
            case "西北":
                return 5;
            case "北":
                return 6;
            case "东北":
                return 7;
            case "":
                return 0;
            default:
                return -1;
        }

    }

    /**
     * 从数组生成sqlin的in 条件字符串
     *
     * @param aryStr
     * @param flag   0:加引号，1：不加引号
     * @return
     */
    public static String genSqlInStrFromArray(String[] aryStr, int flag) {
        String sSqlInStr = "";
        for (int i = 0; i < aryStr.length; i++) {
            String sItem = String.format("'%s'", aryStr[i].trim());
            if (flag == 1)
                sItem = String.format("%s", aryStr[i].trim());
            if (sSqlInStr.equals(""))
                sSqlInStr = sItem;
            else
                sSqlInStr = sSqlInStr + "," + sItem;
        }
        sSqlInStr = String.format("(%s)", sSqlInStr);
        return sSqlInStr;
    }

    /**
     * 转换为字符串，处理为空情况
     *
     * @return
     */
    public static String getStringByObject(Object obj) {
        if (obj != null) {
            return obj.toString();
        }
        return "";
    }

    /**
     * 转换为整形，处理为空情况
     *
     * @return
     */
    public static Integer getIntegerByObject(Object obj) {
        if (obj != null) {
            return Integer.valueOf(obj.toString());
        }
        return -1;
    }

    /**
     * 转换为整形，处理为空情况
     *
     * @return
     */
    public static float getFloatByObject(Object obj) {
        if (obj != null) {
            return Float.valueOf(obj.toString());
        }
        return -1;
    }
    
    //处理cid
    public static  String [] getDeviceCid (Collection<Record> devices){
  		String [] cIds = new String [devices.size()];
  		int i =0 ;
  		for (Record  model : devices) {
  			cIds[i]=model.getStr("cId");
  			i++;
  		}
  		return cIds;
  	}
}

package cn.witeye.iaac.mark.kit;

import com.jfinal.kit.Ret;
import com.jfinal.plugin.activerecord.Page;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class JsonKit {

	/**
	 * 根据传入的字符串，寻找含有该字符串的json返回
	 * @param array
	 * @param match
	 * @return
	 */
	public static JSONArray search(JSONArray array, String match, String search) {
		JSONArray searchArray = new JSONArray();
		JSONObject json = null;
		String name = null;
		for (int i = 0; i < array.size(); i++) {
			json = (JSONObject) array.get(i);
			name = json.getString(search);
			if (name.indexOf(match.trim()) > -1) {
				searchArray.add(json);
			}
		}
		return searchArray;
	}
	
	/** 
	 * @time 2018-10-29 
	 * layui- table 数据接口处理返回数据格式
	 * @param obj
	 * @return String
	 */
	public  static   String  pageDataToJSONStringRet(Object obj){
		try {
			if(obj==null){
				return "";
			}
			Page<Object> page = (Page<Object>) obj;
			Ret ret = Ret.ok("code","0");
			ret.set("msg","请稍候..");
			ret.set("count", page.getTotalRow());
			ret.set("data", page.getList());
			ret.set("limit",page.getPageSize()); 
			ret.set("page", page.getPageNumber());
			return com.jfinal.kit.JsonKit.toJson(ret);
		} catch (Exception e) {
			System.out.println("JsonKit.pageDataToJSONStringRet异常"+e.getMessage());
			return "";
		}
	}
	
	public  static   String  listDataToJSONStringRet(Object obj){
		try {
			if(obj==null){
				return "";
			}
			Ret ret = Ret.ok("code","0");
			ret.set("msg","请稍候..");
			ret.set("data", obj);
			return com.jfinal.kit.JsonKit.toJson(ret);
		} catch (Exception e) {
			System.out.println("JsonKit.pageDataToJSONStringRet异常"+e.getMessage());
			return "";
		}
	}
}

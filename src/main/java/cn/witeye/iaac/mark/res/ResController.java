package cn.witeye.iaac.mark.res;

import java.io.File;
import java.io.IOException;

import com.jfinal.aop.Before;
import com.jfinal.core.Controller;
import com.jfinal.kit.Ret;
import com.jfinal.kit.StrKit;
import com.jfinal.render.JsonRender;

import cn.witeye.iaac.mark.Const;

/**
* CreateAt：𝑑𝑎𝑡𝑒{time}
* ProjectName：new-year-face
* @author JuniorRay
* @version 1.0
* @since JDK 1.8.0_91
* FileName：ResController.java
* Description：
*/
public class ResController extends Controller  {
	
	//返回图片
	public void index() {
		try {
			
			String path = getPara("path");
			if(StrKit.isBlank(path)) {
				renderNull();
				return;
			}
			String fullPath = Const.BASE_UPLOAD_PATH +File.separator+ path;
			File file = new File(fullPath);
			if(file.exists()){
				renderFile(file);
				return;
			}
			renderNull();
		} catch (Exception e) {
			e.printStackTrace();
			renderNull();
		}
	}
		//删除图片
		public void remove() {
			Ret ret = null;
			try {
				
				String path = getPara("path");
				if(StrKit.isBlank(path)) {
					ret=Ret.fail(Const.MSG, "图片路径未传入!");
					JsonRender render = new JsonRender(ret.toJson());
					renderJson(render);
					return;
				}
				String fullPath = Const.BASE_UPLOAD_PATH +File.separator+ path;
				File file = new File(fullPath);
				if(file.exists()){
					file.delete();
					ret=Ret.ok(Const.MSG, "图片删除成功!");
				}else {
					ret=Ret.fail(Const.MSG, "图片不存在!");
				}
				
			} catch (Exception e) {
				ret=Ret.fail(Const.MSG, "图片删除失败!");
				e.printStackTrace();
			}finally {
				JsonRender render = new JsonRender(ret.toJson());
				renderJson(render);
			}
		}
	
	@Before(ResUploadValidator.class)
	public void upload() {
		boolean forIE = getParaToBoolean("forIE", false);
		String path = getAttr("path");
		Ret ret = Ret.ok(Const.MSG, "图片上传成功!");
		ret.set("path", path);
		
		JsonRender render = new JsonRender(ret.toJson());
		renderJson(forIE ? render.forIE() : render);
	}
	
}

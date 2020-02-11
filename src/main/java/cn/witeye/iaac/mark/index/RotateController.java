package cn.witeye.iaac.mark.index;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageReader;

import org.junit.Test;

import com.jfinal.core.Controller;
import com.jfinal.core.NotAction;
import com.jfinal.kit.Ret;

import cn.witeye.iaac.mark.Const;
import cn.witeye.iaac.mark.kit.RotateImage;



public class RotateController extends Controller {

	
	public void rotateImage() throws IOException{
		Ret ret=Ret.fail(Const.MSG,"旋转失败");
		String path=getPara("path");
		int angle=getParaToInt("angle");
		File file=new File(Const.BASE_UPLOAD_PATH+File.separator+path);

		
		String outputFolder=file.getParent();
		String fileName=file.getName();
		//获取文件后缀
		 String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);
		String fileNameNew=file.getName().replace("."+suffix, "")+"_"+angle+".jpg";
		
		try {
			RotateImage.rotate(file,outputFolder,fileNameNew,angle);
			String desSrc=(outputFolder+File.separator+fileNameNew).replace(Const.BASE_UPLOAD_PATH, "");

			ret=Ret.ok(Const.MSG,"旋转成功").set("path",desSrc);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		renderJson(ret);
		
	}
	
	//翻转
	public void  rollingOver() {
		Ret ret=Ret.fail(Const.MSG,"翻转失败");
		String path=getPara("path");
		int rotateFlag=getParaToInt("rotateFlag");
		File file=new File(Const.BASE_UPLOAD_PATH+File.separator+path);
		String outputFolder=file.getParent();
		String fileName=file.getName();
		String typeStr="null";
		if(rotateFlag==0) {
			typeStr="up";
		}else if(rotateFlag==1) {
			typeStr="left";

		}
		
		//获取文件后缀
		 String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);
		String fileNameNew=file.getName().replace("."+suffix, "")+"_"+typeStr+".jpg";
	
		try {
//			rotateFlag 0 为上下反转 1 为左右反转
			RotateImage.rollingOver(file,outputFolder,fileNameNew,rotateFlag);
			String desSrc=(outputFolder+File.separator+fileNameNew).replace(Const.BASE_UPLOAD_PATH, "");
			ret=Ret.ok(Const.MSG,"翻转成功").set("path",desSrc);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

		renderJson(ret);
	}
}



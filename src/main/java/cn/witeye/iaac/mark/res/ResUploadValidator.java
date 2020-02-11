package cn.witeye.iaac.mark.res;

import java.io.File;
import java.util.UUID;

import org.joda.time.DateTime;

import com.jfinal.core.Controller;
import com.jfinal.kit.Ret;
import com.jfinal.upload.UploadFile;
import com.jfinal.validate.Validator;

import cn.witeye.iaac.mark.Const;

/**
* CreateAt：𝑑𝑎𝑡𝑒{time}
* ProjectName：new-year-face
* @author JuniorRay
* @version 1.0
* @since JDK 1.8.0_91
* FileName：ResUploadValidator.java
* Description：
*/
public class ResUploadValidator extends Validator {
	@Override
	protected void validate(Controller c)  {
//		setShortCircuit(true);

		DateTime now = DateTime.now();
		String uploadPath = now.getYear() + "/" + now.getMonthOfYear() + "/" + now.getDayOfMonth() + "/";
		UploadFile uf = c.getFile("file", uploadPath);
		File file = uf.getFile();
		if (uf == null || file.length() == 0) {
			try {
				throw new Exception("文件上传失败");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		String oldFileName = uf.getFileName();
		String fileName =  UUID.randomUUID()+ oldFileName.substring(oldFileName.lastIndexOf("."));
		File dest = new File(Const.BASE_UPLOAD_PATH +File.separator+ uploadPath + fileName);
		File parentFolder = dest.getParentFile();
		if (parentFolder.isDirectory() == false) {
			parentFolder.mkdirs();
		}
		file.renameTo(dest);
		c.setAttr("path", uploadPath + fileName);
	}

	@Override
	protected void handleError(Controller c) {
		c.renderJson(Ret.fail(Const.MSG, c.getAttr("message")));
	}
}

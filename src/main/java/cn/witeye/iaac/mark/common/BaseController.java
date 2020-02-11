package cn.witeye.iaac.mark.common;

import com.jfinal.core.Controller;

public class BaseController extends Controller {
	public int logId() {
		return getAttrForInt("logId");
	}
}

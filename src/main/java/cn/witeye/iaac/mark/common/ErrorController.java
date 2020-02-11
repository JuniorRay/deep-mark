package cn.witeye.iaac.mark.common;

import com.jfinal.core.Controller;

public class ErrorController extends Controller {

	public void index() {
		render("404.html");
	}

}

package cn.witeye.iaac.mark.interceptor;

import com.jfinal.aop.Interceptor;
import com.jfinal.aop.Invocation;
import com.jfinal.core.Controller;

public class GlobalActionInterceptor implements Interceptor {
 
	public void intercept(Invocation inv) {
 
		Controller controller = inv.getController();
		//解决跨域访问问题
		controller.getResponse().addHeader("Access-Control-Allow-Origin", "*");
		inv.invoke();
	}


}

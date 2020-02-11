package cn.witeye.iaac.mark.common;

import com.jfinal.aop.Invocation;
import com.jfinal.aop.PrototypeInterceptor;
import com.jfinal.core.Controller;
/**
 * 通用翻页类
 */
public class PageInfo extends PrototypeInterceptor {
	protected Controller c;

	public Integer pageNum;
	public Integer numPerPage;

	protected void initPara(Invocation ai) {
		c = ai.getController();
		pageNum = c.getParaToInt("pageNum", c.getParaToInt("page",1));
		numPerPage = c.getParaToInt("numPerPage", c.getParaToInt("limit",20));

		c.keepPara();
	}

	public void doIntercept(Invocation ai) {
		initPara(ai);
		c.setAttr("pi", this);
		ai.invoke();
	}

}
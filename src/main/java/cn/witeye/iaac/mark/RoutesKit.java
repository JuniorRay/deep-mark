package cn.witeye.iaac.mark;


import com.jfinal.config.Routes;

import cn.witeye.iaac.mark.index.IndexController;
import cn.witeye.iaac.mark.index.RotateController;
import cn.witeye.iaac.mark.res.ResController;

public class RoutesKit extends Routes {

	@Override
	public void config() {
		setBaseViewPath("/_view");

		add("/", IndexController.class, "/index"); // 第三个参数为该Controller的视图存放路径
		add("/res", ResController.class);// 资源返回接口
		add("/rotate", RotateController.class);// 图片旋转接口

	}

}

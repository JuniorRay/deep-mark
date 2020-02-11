package cn.witeye.iaac.mark;


import com.jfinal.config.Constants;
import com.jfinal.config.Handlers;
import com.jfinal.config.Interceptors;
import com.jfinal.config.JFinalConfig;
import com.jfinal.config.Plugins;
import com.jfinal.config.Routes;
import com.jfinal.json.MixedJsonFactory;
import com.jfinal.kit.Prop;
import com.jfinal.kit.PropKit;
import com.jfinal.kit.Ret;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.druid.DruidPlugin;
import com.jfinal.server.undertow.UndertowServer;
import com.jfinal.template.Engine;

import cn.witeye.iaac.common.log.Log4j2LogFactory;
import cn.witeye.iaac.mark.interceptor.GlobalActionInterceptor;
import cn.witeye.iaac.mark.model._MappingKit;




/**
 * 本 demo 仅表达最为粗浅的 jfinal 用法，更为有价值的实用的企业级用法 详见 JFinal 俱乐部:
 * http://jfinal.com/club
 * 
 * API 引导式配置
 */
public class MarkServerConfig extends JFinalConfig {

	static Prop p;

	/**
	 * 启动入口，运行此 main 方法可以启动项目，此 main 方法可以放置在任意的 Class 类定义中，不一定要放于此
	 */
	public static void main(String[] args) {
		UndertowServer.create(MarkServerConfig.class)// 启动配置文件
		.configWeb( builder -> {
		})
		.addHotSwapClassPrefix("cn.witeye.iaac.common.log.") // 增加外部启动组件   Log4j2LogFactory 日志
		.start(); // 启动
	}

	/**
	 * PropKit.useFirstFound(...) 使用参数中从左到右最先被找到的配置文件
	 * 从左到右依次去找配置，找到则立即加载并立即返回，后续配置将被忽略
	 */
	static void loadConfig() {
		if (p == null) {
			p = PropKit.useFirstFound("config-dev.txt","config-pro.txt");
//			p = PropKit.useFirstFound("config-pro.txt","config-dev.txt");
		}
	}

	/**
	 * 配置常量
	 */
	public void configConstant(Constants me) {
		loadConfig();

		me.setDevMode(p.getBoolean("devMode", false));

		/**
		 * 支持 Controller、Interceptor、Validator 之中使用 @Inject 注入业务层，并且自动实现 AOP
		 * 注入动作支持任意深度并自动处理循环注入
		 */
		me.setInjectDependency(true);

		// 配置对超类中的属性进行注入
		me.setInjectSuperClass(true);
		
		me.setDevMode(Const.DEV_MODE);

		me.setJsonFactory(MixedJsonFactory.me());
		me.setLogFactory(new Log4j2LogFactory());
		me.setMaxPostSize(Const.MAX_POST_SIZE);
		// linux、mac 系统以字符 "/" 打头是绝对路径
		me.setBaseDownloadPath("/var/download");
		 
		// windows 系统以盘符打头也是绝对路径
		me.setBaseDownloadPath(Const.BASE_PATH);
	}

	/**
	 * 配置路由
	 */
	public void configRoute(Routes me) {
		me.add(new RoutesKit());

	}

	public void configEngine(Engine me) {
		   me.addSharedFunction("/_view/common/_admin_layout.html");
	}

	/**
	 * 配置插件
	 */
	public void configPlugin(Plugins me) {
	/** 注释数据库配置
	 	// 配置 druid 数据库连接池插件
		DruidPlugin druidPlugin = createDruidPlugin() ;
		me.add(druidPlugin);

		// 配置ActiveRecord插件
		ActiveRecordPlugin arp = new ActiveRecordPlugin(druidPlugin);
		// 所有映射在 MappingKit 中自动化搞定
		_MappingKit.mapping(arp);
		me.add(arp);
		**/
	}

	public static DruidPlugin createDruidPlugin() {
		loadConfig();
		DruidPlugin druidPlugin = new DruidPlugin(p.get("mysql.jdbcUrl"), p.get("mysql.user"),
				p.get("mysql.password").trim());
		druidPlugin.set(p.getInt("mysql.initialSize"), p.getInt("mysql.minIdle"), p.getInt("mysql.maxActive"));
		return druidPlugin;

	}

	/**
	 * 配置全局拦截器
	 */
	public void configInterceptor(Interceptors me) {
		me.add(new GlobalActionInterceptor());
	}

	/**
	 * 配置处理器
	 */
	public void configHandler(Handlers me) {

	}
	@Override
	public void onStart() {
		
	}

	@Override
	public void onStop() {
		
	}
	
	
	@Override
	public void afterJFinalStart() {
		
	}
}

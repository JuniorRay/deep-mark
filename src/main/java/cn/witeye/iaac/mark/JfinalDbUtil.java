package cn.witeye.iaac.mark;

import java.io.File;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.jfinal.kit.Prop;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.activerecord.CaseInsensitiveContainerFactory;
import com.jfinal.plugin.activerecord.Db;
import com.jfinal.plugin.druid.DruidPlugin;

import cn.witeye.iaac.mark.model._MappingKit;

/* @JfinalDb连接工具栏
 * @author Junior
 * @date Aug 14, 2018
 */
public class JfinalDbUtil {
	// private static String jdbcUrl =
	// "jdbc:mysql://127.0.0.1:3306/attenceSoftsz?characterEncoding=UTF-8&zeroDateTimeBehavior=convertToNull&useSSL=false";
	// private static String username = "attence";
	// private static String password = "attence";
	private static DruidPlugin dpMysql;
	private static ActiveRecordPlugin arpMysql;

	static Prop prop;

	public JfinalDbUtil() {
		// 配置mysql数据库连接池插件
		dpMysql = createDruidPlugin();
		// 配置ActiveRecord插件
		arpMysql = getActiveRecordPlugin(dpMysql);

	}

	/**
	 * PropKit.useFirstFound(...) 使用参数中从左到右最先被找到的配置文件
	 * 从左到右依次去找配置，找到则立即加载并立即返回，后续配置将被忽略
	 */
	static void loadConfig() {
		if (prop == null) {
			prop = PropKit.useFirstFound("config-pro.txt", "config-dev.txt");
		}
	}

	public static DruidPlugin createDruidPlugin() {
		loadConfig();
		DruidPlugin druidPlugin = new DruidPlugin(prop.get("mysql.jdbcUrl"), prop.get("mysql.user"),
				prop.get("mysql.password").trim());
		druidPlugin.set(prop.getInt("mysql.initialSize"), prop.getInt("mysql.minIdle"), prop.getInt("mysql.maxActive"));
		return druidPlugin;
	}

	// public DruidPlugin createDruidPlugin() {
	// DruidPlugin druidPlugin = new DruidPlugin(jdbcUrl, username, password);
	// return druidPlugin;
	// }

	private ActiveRecordPlugin getActiveRecordPlugin(DruidPlugin druidPlugin) {
		// 配置ActiveRecord插件
		ActiveRecordPlugin arpMysql = new ActiveRecordPlugin(druidPlugin);
		arpMysql.setContainerFactory(new CaseInsensitiveContainerFactory(true));
		arpMysql.setShowSql(false);// sql打印不可见
		// arpMysql.setCache(new J2Cache());
		// 所有配置在 MappingKit 中搞定
		_MappingKit.mapping(arpMysql);
		return arpMysql;
	}

	// 打开连接
	public boolean getConnection() {

		try {
			dpMysql.start();
			arpMysql.start();
			System.out.println("JfinalDB连接开启");
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			System.out.println("JfinalDB连接失败");
			return false;
		}

	}

	// 关闭连接
	public boolean closeConnection() {
		try {

			if (dpMysql != null) {
				dpMysql.stop();
			}
			if (arpMysql != null) {
				arpMysql.stop();
			}
			System.out.println("JfinalDB连接关闭");
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}
	}

	public static void main(String[] args) throws Exception {
		JfinalDbUtil jfinalDbUtil = new JfinalDbUtil();
		jfinalDbUtil.getConnection();
		try {
			System.out.println(Db.find("select * from t_account"));

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			jfinalDbUtil.closeConnection();
		}

	}

}
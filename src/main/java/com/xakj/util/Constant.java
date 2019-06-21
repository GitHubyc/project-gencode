package com.xakj.util;

/**
 * 项目常量 施先锋
 */
public final class Constant {
	// mysql数据源
//	public final static String URL = "jdbc:mysql://192.168.1.26:3306/iot";
//	public final static String URL = "jdbc:mysql://192.168.1.26:3306/fire_business_sevice";
//	public final static String URL = "jdbc:mysql://192.168.1.26:3306/fire_knowledge_service";
	public final static String URL = "jdbc:mysql://192.168.1.214:3306/fire_bas_service";
//	public final static String URL = "jdbc:mysql://localhost:3306/test";
	public final static String USERNAME = "fire_bas_service";
	public final static String PASSWORD = "wA5hebeYX8Aa5smK";
	public final static String DRIVER = "com.mysql.jdbc.Driver";
	// oracle数据源
//	public final static String URL = "jdbc:oracle:thin:@192.168.1.129:1521/orcl";
//	public final static String USERNAME = "alone";
//	public final static String PASSWORD = "alone";
//	public final static String DRIVER = "oracle.jdbc.driver.OracleDriver";

	// 项目包文件路径
	public final static String packageName = "com.xakj";
	public final static String ENTITY_PACKAGE = packageName + ".model"; // entity所在包
	public final static String DTO_PACKAGE = packageName + ".model.dto"; // dto所在包
	public final static String DAO_PACKAGE = packageName + ".dao"; // dao所在包
	public final static String DAO_IMPL_PACKAGE = DAO_PACKAGE + ".impl"; // daoImpl所在包
	public final static String SERVICE_PACKAGE = packageName + ".service"; // service所在包
	public final static String SERVICE_IMPL_PACKAGE = SERVICE_PACKAGE + ".impl";// serviceImpl所在包
	public final static String API_PACKAGE = packageName + ".api"; // api所在包
	public final static String CONTROLLER_PACKAGE = packageName + ".controller";// controller所在包
	public final static String UTIL_PACKAGE = packageName + ".util"; // util所在包
	// 磁盘文件根路径
	public final static String projectPath = "D://myeclipse//project-gencode";
	public final static String javaPath = projectPath + "//src//main//java//";
	public final static String PACKAGE_PATH_ENTITY = javaPath + packageConvertPath(ENTITY_PACKAGE); // 生成的entity存放路径
	public final static String PACKAGE_PATH_DTO = javaPath + packageConvertPath(DTO_PACKAGE); // 生成的dto存放路径
	public final static String PACKAGE_PATH_DAO = javaPath + packageConvertPath(DAO_PACKAGE); // 生成的dao存放路径
	public final static String PACKAGE_PATH_DAO_IMPL = javaPath + packageConvertPath(DAO_IMPL_PACKAGE); // 生成的daoImpl存放路径
	public final static String PACKAGE_PATH_SERVICE = javaPath + packageConvertPath(SERVICE_PACKAGE); // 生成的service存放路径
	public final static String PACKAGE_PATH_SERVICE_IMPL = javaPath + packageConvertPath(SERVICE_IMPL_PACKAGE); // 生成的serviceImpl实现存放路径
	public final static String PACKAGE_PATH_API = javaPath + packageConvertPath(API_PACKAGE); // 生成的api存放路径
	public final static String PACKAGE_PATH_CONTROLLER = javaPath + packageConvertPath(CONTROLLER_PACKAGE); // 生成的controller存放路径
	public final static String htmlPath = projectPath + "//src//main//resources//templates//"; // html包文件路径
	public final static String jsPath = projectPath + "//src//main//resources//templates//"; // js包文件路径
	// 数据字典
	public final static String DATADICTIONARY_PATH="C://Users//Administrator//Desktop//DataDictionary//";
	public final static String DATADICTIONARY_FILE_PATH="C://Users//Administrator//Desktop//数据字典.xml";
	public final static String DATADICTIONARY_FRONT_PATH=projectPath + "//src//main//resources//templates//datadictionary_front.xml";
	public final static String DATADICTIONARY_AFTER_PATH=projectPath + "//src//main//resources//templates//datadictionary_after.xml";
	// 接口文档生成路径
	public final static String INTERFACE_PATH="C://Users//Administrator//Desktop//Interface//";
	public final static String INTERFACE_FILE_PATH="C://Users//Administrator//Desktop//接口文档.xml";
	public final static String INTERFACE_FRONT_PATH=projectPath + "//src//main//resources//templates//interface_front.xml";
	public final static String INTERFACE_AFTER_PATH=projectPath + "//src//main//resources//templates//interface_after.xml";
	
	public static final String SERVERURL = "http://192.168.1.198:8762";// 服务端地址
	public static final String USER = "USER";
	public static final String AUTHORIZATION ="authorization";
	public static final String  APPAYID ="d2060e3ce66241eba4db978197a55b89";
	public static final String SECRET = "P@ssw02d"; // JWT密码
	public static final String TOKEN_PREFIX = "Bearer"; // Token前缀
	public static String LOGIN_SQLIT_DES = "\\@\\$\\@";// 解析用户名拼接应用ID

	public static String packageConvertPath(String packageName) {
		return String.format("/%s/", packageName.contains(".") ? packageName.replaceAll("\\.", "/") : packageName);
	}
}

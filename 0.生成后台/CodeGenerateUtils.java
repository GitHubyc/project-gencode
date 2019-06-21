package com.xakj.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import net.sf.json.JSONObject;

import oracle.jdbc.driver.OracleConnection;

import org.apache.commons.lang3.StringUtils;

import freemarker.template.Template;

/**
 * 描述：后台业务生成
 */
public class CodeGenerateUtils {

	private final String CURRENT_DATE = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());// 获取当前时间

	/**
	 * 数据库连接
	 */
	public Connection getConnection() throws Exception {
		Class.forName(Constant.DRIVER);
		Connection connection = DriverManager.getConnection(Constant.URL, Constant.USERNAME, Constant.PASSWORD);
		return connection;
	}

	/**
	 * 生成主方法
	 */
	public void generate(String tableName, String modelName, String tableAnnotation, String author) throws Exception {
		Connection connection = null;
		ResultSet resultSet = null;
		ResultSet primaryKeyResultSet = null;
		try {
			tableName = tableName.toUpperCase();// 表名转为大写
			connection = getConnection();
			if(Constant.URL.contains("oracle")) ((OracleConnection) connection).setRemarksReporting(true);
			DatabaseMetaData databaseMetaData = null;
			try {
				databaseMetaData = connection.getMetaData();
				System.out.println("数据库已知的用户: " + databaseMetaData.getUserName());
//				System.out.println("数据库的系统函数的逗号分隔列表: " + databaseMetaData.getSystemFunctions());
//				System.out.println("数据库的时间和日期函数的逗号分隔列表: " + databaseMetaData.getTimeDateFunctions());
//				System.out.println("数据库的字符串函数的逗号分隔列表: " + databaseMetaData.getStringFunctions());
//				System.out.println("数据库供应商用于 'schema' 的首选术语: " + databaseMetaData.getSchemaTerm());
				System.out.println("数据库URL: " + databaseMetaData.getURL());
				System.out.println("是否允许只读:" + databaseMetaData.isReadOnly());
				System.out.println("数据库的产品名称:" + databaseMetaData.getDatabaseProductName());
//				System.out.println("数据库的版本:" + databaseMetaData.getDatabaseProductVersion());
//				System.out.println("驱动程序的名称:" + databaseMetaData.getDriverName());
//				System.out.println("驱动程序的版本:" + databaseMetaData.getDriverVersion());
			} catch (SQLException e) {
				e.printStackTrace();
			}
			primaryKeyResultSet = databaseMetaData.getPrimaryKeys(null, null, tableName);// 获取表主键信息
			resultSet = databaseMetaData.getColumns(null, null, tableName, "%");// 获取表结构信息
			String table_key = "";
			String table_key_change = "";
			String table_key_type = "VARCHER";
			System.out.print("数据库表" + tableName);
			boolean has_primary = true;
			while (primaryKeyResultSet.next()) {
				table_key = primaryKeyResultSet.getString("COLUMN_NAME").toLowerCase();
				table_key_change = replaceUnderLineAndUpperCase(primaryKeyResultSet.getString("COLUMN_NAME"));
				table_key_type = tableKeyType(resultSet, table_key);
				System.out.println("主键:" + table_key_change);
				has_primary = false;
			}
			// 生成Entity文件
			generateEntityFile(databaseMetaData, table_key, table_key_change, table_key_type, tableName, modelName, tableAnnotation, author);
			// 生成Dto文件
			generateDtoFile(databaseMetaData, table_key, table_key_change, table_key_type, tableName, modelName, tableAnnotation, author);
			if(has_primary) return ;// 不存在主键则无法生成后续文件
			// 生成Dao文件
			generateDaoFile(databaseMetaData, table_key, table_key_change, table_key_type, tableName, modelName, tableAnnotation, author);
			// 生成DaoImpl文件
			generateDaoImplFile(databaseMetaData, table_key, table_key_change, table_key_type, tableName, modelName, tableAnnotation, author);
			// 生成Service文件
			generateServiceFile(databaseMetaData, table_key, table_key_change, table_key_type, tableName, modelName, tableAnnotation, author);
			// 生成ServiceImpl文件
			generateServiceImplFile(databaseMetaData, table_key, table_key_change, table_key_type, tableName, modelName, tableAnnotation, author);
			// 生成api文件
			generateApiFile(databaseMetaData, table_key, table_key_change, table_key_type, tableName, modelName, tableAnnotation, author);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private void generateEntityFile(DatabaseMetaData databaseMetaData, String table_key, String table_key_change, String table_key_type, String tableName, String modelName,
			String tableAnnotation, String author) throws Exception {
		mkdirs(new File(Constant.PACKAGE_PATH_ENTITY));
		String hasDateType = "false";
		final String path = Constant.PACKAGE_PATH_ENTITY + modelName + "Entity" + ".java";
		File createPath = new File(path);
		if (!createPath.exists()) {// 避免生成覆盖
			final String templateName = "Entity.ftl";
			File mapperFile = new File(path);
			Map<String, Object> dataMap = dataMap(modelName, Constant.ENTITY_PACKAGE, hasDateType, databaseMetaData.getColumns(null, null, tableName, "%"), table_key, table_key_change, table_key_type);
			generateFileByTemplate(templateName, mapperFile, dataMap, tableName, modelName, tableAnnotation, author);
		} else {
			System.out.println("文件已存在,如需重新生成请手动删除现存文件再次执行！" + path);
		}
	}

	private void generateDtoFile(DatabaseMetaData databaseMetaData, String table_key, String table_key_change, String table_key_type, String tableName, String modelName,
			String tableAnnotation, String author) throws Exception {
		mkdirs(new File(Constant.PACKAGE_PATH_DTO));
		String hasDateType = "false";
		final String path = Constant.PACKAGE_PATH_DTO + modelName + "Dto" + ".java";
		File createPath = new File(path);
		if (!createPath.exists()) {// 避免生成覆盖
			final String templateName = "Dto.ftl";
			File mapperFile = new File(path);
			Map<String, Object> dataMap = dataMap(modelName, Constant.DTO_PACKAGE, hasDateType, databaseMetaData.getColumns(null, null, tableName, "%"), table_key, table_key_change, table_key_type);
			generateFileByTemplate(templateName, mapperFile, dataMap, tableName, modelName, tableAnnotation, author);
		} else {
			System.out.println("文件已存在,如需重新生成请手动删除现存文件再次执行！" + path);
		}

	}

	private void generateDaoFile(DatabaseMetaData databaseMetaData, String table_key, String table_key_change, String table_key_type, String tableName, String modelName,
			String tableAnnotation, String author) throws Exception {
		mkdirs(new File(Constant.PACKAGE_PATH_DAO));
		final String path = Constant.PACKAGE_PATH_DAO + modelName + "Dao.java";
		File createPath = new File(path);
		if (!createPath.exists()) {// 避免生成覆盖
			final String templateName = "Dao.ftl";
			File mapperFile = new File(path);
			Map<String, Object> dataMap = dataMap(modelName, Constant.DAO_PACKAGE, "", databaseMetaData.getColumns(null, null, tableName, "%"), table_key, table_key_change, table_key_type);
			generateFileByTemplate(templateName, mapperFile, dataMap, tableName, modelName, tableAnnotation, author);
		} else {
			System.out.println("文件已存在,如需重新生成请手动删除现存文件再次执行！" + path);
		}

	}

	private void generateDaoImplFile(DatabaseMetaData databaseMetaData, String table_key, String table_key_change, String table_key_type, String tableName, String modelName,
			String tableAnnotation, String author) throws Exception {
		mkdirs(new File(Constant.PACKAGE_PATH_DAO_IMPL));
		final String path = Constant.PACKAGE_PATH_DAO_IMPL + modelName + "DaoImpl.java";
		File createPath = new File(path);
		if (!createPath.exists()) {// 避免生成覆盖
			File mapperFile = new File(path);
			Map<String, Object> dataMap = dataMap(modelName, Constant.DAO_IMPL_PACKAGE, "", databaseMetaData.getColumns(null, null, tableName, "%"), table_key, camelName(table_key), table_key_type);
			if(Constant.URL.contains("oracle")) {
				generateFileByTemplate("DaoImplOracle.ftl", mapperFile, dataMap, tableName, modelName, tableAnnotation, author);
			}else{
				generateFileByTemplate("DaoImpl.ftl", mapperFile, dataMap, tableName, modelName, tableAnnotation, author);
			}
		} else {
			System.out.println("文件已存在,如需重新生成请手动删除现存文件再次执行！" + path);
		}
	}

	private void generateServiceFile(DatabaseMetaData databaseMetaData, String table_key, String table_key_change, String table_key_type, String tableName, String modelName,
			String tableAnnotation, String author) throws Exception {
		mkdirs(new File(Constant.PACKAGE_PATH_SERVICE));
		final String path = Constant.PACKAGE_PATH_SERVICE + modelName + "Service.java";
		File createPath = new File(path);
		if (!createPath.exists()) {// 避免生成覆盖
			final String templateName = "Service.ftl";
			File mapperFile = new File(path);
			Map<String, Object> dataMap = dataMap(modelName, Constant.SERVICE_PACKAGE, "", databaseMetaData.getColumns(null, null, tableName, "%"), table_key, camelName(table_key), table_key_type);
			generateFileByTemplate(templateName, mapperFile, dataMap, tableName, modelName, tableAnnotation, author);
		} else {
			System.out.println("文件已存在,如需重新生成请手动删除现存文件再次执行！" + path);
		}

	}

	private void generateServiceImplFile(DatabaseMetaData databaseMetaData, String table_key, String table_key_change, String table_key_type, String tableName,
			String modelName, String tableAnnotation, String author) throws Exception {
		mkdirs(new File(Constant.PACKAGE_PATH_SERVICE_IMPL));
		final String path = Constant.PACKAGE_PATH_SERVICE_IMPL + modelName + "ServiceImpl.java";
		File createPath = new File(path);
		if (!createPath.exists()) {// 避免生成覆盖
			final String templateName = "ServiceImpl.ftl";
			File mapperFile = new File(path);
			Map<String, Object> dataMap = dataMap(modelName, Constant.SERVICE_IMPL_PACKAGE, "", databaseMetaData.getColumns(null, null, tableName, "%"), table_key, camelName(table_key), table_key_type);
			generateFileByTemplate(templateName, mapperFile, dataMap, tableName, modelName, tableAnnotation, author);
		} else {
			System.out.println("文件已存在,如需重新生成请手动删除现存文件再次执行！" + path);
		}
	}

	private void generateApiFile(DatabaseMetaData databaseMetaData, String table_key, String table_key_change, String table_key_type, String tableName, String modelName,
			String tableAnnotation, String author) throws Exception {
		mkdirs(new File(Constant.PACKAGE_PATH_API));
		final String path = Constant.PACKAGE_PATH_API + modelName + "Api.java";
		File createPath = new File(path);
		if (!createPath.exists()) {// 避免生成覆盖
			final String templateName = "Api.ftl";
			File mapperFile = new File(path);
			Map<String, Object> dataMap = dataMap(modelName, Constant.API_PACKAGE, "", databaseMetaData.getColumns(null, null, tableName, "%"), table_key, camelName(table_key), table_key_type);
			generateFileByTemplate(templateName, mapperFile, dataMap, tableName, modelName, tableAnnotation, author);
		} else {
			System.out.println("文件已存在,如需重新生成请手动删除现存文件再次执行！" + path);
		}
	}

	private void generateFileByTemplate(final String templateName, File file, Map<String, Object> dataMap, String tableName, String modelName,
			String tableAnnotation, String author) throws Exception {
		Template template = FreeMarkerTemplateUtils.getTemplate(templateName);
		FileOutputStream fos = new FileOutputStream(file);
		dataMap.put("table_name_small", tableName);
		dataMap.put("table_name", tableName);
		dataMap.put("modelName", modelName);
		dataMap.put("author", author);
		dataMap.put("date", CURRENT_DATE);
		dataMap.put("table_annotation", tableAnnotation);
		Writer out = new BufferedWriter(new OutputStreamWriter(fos), 10240);
		template.process(dataMap, out);
	}

	public String replaceUnderLineAndUpperCase(String str) {
		StringBuffer sb = new StringBuffer();
		sb.append(str);
		int count = sb.indexOf("_");
		while (count != 0) {
			int num = sb.indexOf("_", count);
			count = num + 1;
			if (num != -1) {
				char ss = sb.charAt(count);
				char ia = Character.toUpperCase(ss);
				sb.replace(count, count + 1, ia + "");
			}
		}
		String result = sb.toString().replaceAll("_", "");
		return StringUtils.capitalize(result);
	}

	/**
	 * 将下划线大写方式命名的字符串转换为驼峰式。如果转换前的下划线大写方式命名的字符串为空，则返回空字符串。</br>
	 * 例如：HELLO_WORLD->HelloWorld
	 * 
	 * @param name
	 *            转换前的下划线大写方式命名的字符串
	 * @return 转换后的驼峰式命名的字符串
	 */
	public static String camelName(String name) {
		StringBuilder result = new StringBuilder();
		// 快速检查
		if (name == null || name.isEmpty()) {
			// 没必要转换
			return "";
		} else if (!name.contains("_")) {
			// 不含下划线，仅将字母小写
			return name.toLowerCase();
		}
		// 用下划线将原始字符串分割
		String camels[] = name.split("_");
		for (String camel : camels) {
			// 跳过原始字符串中开头、结尾的下换线或双重下划线
			if (camel.isEmpty()) {
				continue;
			}
			// 处理真正的驼峰片段
			if (result.length() == 0) {
				// 第一个驼峰片段，全部字母都小写
				result.append(camel.toLowerCase());
			} else {
				// 其他的驼峰片段，首字母大写
				result.append(camel.substring(0, 1).toUpperCase());
				result.append(camel.substring(1).toLowerCase());
			}
		}
		return result.toString();
	}

	/**
	 * 将驼峰式命名的字符串转换为下划线大写方式。如果转换前的驼峰式命名的字符串为空，则返回空字符串。</br>
	 * 例如：HelloWorld->HELLO_WORLD
	 * 
	 * @param name
	 *            转换前的驼峰式命名的字符串
	 * @return 转换后下划线大写方式命名的字符串
	 */
	public static String underscoreName(String name) {
		StringBuilder result = new StringBuilder();
		if (name != null && name.length() > 0) {
			// 将第一个字符处理成大写
			result.append(name.substring(0, 1).toUpperCase());
			// 循环处理其余字符
			for (int i = 1; i < name.length(); i++) {
				String s = name.substring(i, i + 1);
				// 在大写字母前添加下划线
				if (s.equals(s.toUpperCase()) && !Character.isDigit(s.charAt(0))) {
					result.append("_");
				}
				// 其他字符直接转成大写
				result.append(s.toUpperCase());
			}
		}
		return result.toString();
	}

	// 数据库字段转化为map集合
	public Map<String, Object> resultSetToMap(ResultSet resultSet) throws Exception {
		Map<String, Object> columnClassList = new HashMap<String, Object>();
		while (resultSet.next()) {
			JSONObject object = new JSONObject();
			// 获取字段名称
			object.put("ColumnName", resultSet.getString("COLUMN_NAME").toLowerCase());
			// 获取字段类型
			object.put("ColumnType", resultSet.getString("TYPE_NAME"));
			// 转换字段名称，如 sys_name 变成 SysName
			object.put("ChangeColumnName", camelName(resultSet.getString("COLUMN_NAME")));
			// 字段在数据库的注释
			object.put("Remarks", resultSet.getString("REMARKS") != null ? resultSet.getString("REMARKS") : "未定义");
			columnClassList.put(object.getString("ColumnName"), object);
		}
		return columnClassList;
	}

	// 各映射条件处理
	public Map<String, Object> dataMap(String modelName, String packageName, String hasDateType, ResultSet resultSet, String table_key,
			String table_key_change, String table_key_type) throws Exception {
		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put("package_name", packageName);
		dataMap.put("daoPackage", Constant.DAO_PACKAGE + "." + modelName + "Dao");
		dataMap.put("entityPackage", Constant.ENTITY_PACKAGE + "." + modelName + "Entity");
		dataMap.put("dtoPackage", Constant.DTO_PACKAGE + "." + modelName + "Dto");
		dataMap.put("servicePackage", Constant.SERVICE_PACKAGE + "." + modelName + "Service");
		dataMap.put("utilPackage", Constant.UTIL_PACKAGE);

		dataMap.put("hasDateType", hasDateType);
		dataMap.put("model_column", resultSetToMap(resultSet));
		dataMap.put("table_key", table_key);
		dataMap.put("table_key_change", table_key_change);
		dataMap.put("table_key_type", table_key_type);
		return dataMap;
	}

	// 获取主键类型, 默认为VARCHAR
	public String tableKeyType(ResultSet resultSet, String table_key) throws Exception {
		for (Entry<String, Object> entry : resultSetToMap(resultSet).entrySet()) {
			if (table_key.equals(entry.getKey().toLowerCase())) {
				JSONObject jsonObject = (JSONObject) entry.getValue();
				return jsonObject.getString("ColumnType");
			}
		}
		return "VARCHAR";
	}
	
	//创建文件夹
	public void mkdirs(File file) throws Exception {
		if (!file.exists()) {
			file.mkdirs();
		}
	}
}
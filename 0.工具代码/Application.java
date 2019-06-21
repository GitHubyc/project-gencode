package com.xakj;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import com.xakj.util.CodeGenerateUtils;
import com.xakj.util.Constant;
import com.xakj.util.FileUtils;


public class Application {
	/**
	 * 数据库连接
	 */
	public static Connection getConnection() throws Exception {
		String url = Constant.URL;
		Properties props = new Properties();
		props.setProperty("user", Constant.USERNAME);
		props.setProperty("password", Constant.PASSWORD);
		props.setProperty("remarks", "true"); // 设置可以获取remarks信息
		props.setProperty("useInformationSchema", "true");// 设置可以获取tables remarks信息
		Class.forName(Constant.DRIVER);
		Connection connection = DriverManager.getConnection(Constant.URL, props);
		return connection;
	}
	// 直接连接数据库，获取所有表，然后生成所有表的代码
	public static void main(String[] args) {
		try {
			Connection connection = null;
			ResultSet resultSet = null;
			ResultSet primaryKeyResultSet = null;
			DatabaseMetaData databaseMetaData = null;
			try {
				// 1. JDBC连接MYSQL的代码很标准。
				connection = getConnection();
				databaseMetaData = connection.getMetaData();
				// 2. 下面就是获取表的信息。
				ResultSet tableRet = connection.getMetaData().getTables(null, "%", "%", new String[] { "TABLE" });
				/*
				 * 其中"%"就是表示*的意思，也就是任意所有的意思。其中m_TableName就是要获取的数据表的名字，如果想获取所有的表的名字
				 * ，就可以使用"%"来作为参数了。
				 */
				// 3. 提取表的名字。
				List<String> tableNames = new ArrayList<>();
				List<String> modelNames = new ArrayList<>();
				List<String> tableAnnotations = new ArrayList<>();
				while (tableRet.next()){
					System.out.println(tableRet.getString("TABLE_NAME") + tableRet.getString("REMARKS"));
					tableNames.add(tableRet.getString("TABLE_NAME"));
					modelNames.add(camelName(tableRet.getString("TABLE_NAME")));
					tableAnnotations.add(tableRet.getString("REMARKS"));
				}
				for (int i = 0; i < tableNames.size(); i++) {
					CodeGenerateUtils codeGenerateUtils = new CodeGenerateUtils();
					codeGenerateUtils.generate(tableNames.get(i), modelNames.get(i), tableAnnotations.get(i), "袁文彪");
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static String camelName(String name) {
		name = name.replace("_r_", "_");
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
		String string = result.toString().substring(0, 1).toUpperCase() + result.toString().substring(1, result.toString().length());
		return string;
	}
}

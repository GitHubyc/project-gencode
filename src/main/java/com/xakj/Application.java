package com.xakj;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import com.xakj.util.CodeGenerateUtils;
import com.xakj.util.Constant;
import com.xakj.util.FileUtils;

public class Application {

	public static void main(String[] args) {
		Connection connection = null;
		DatabaseMetaData databaseMetaData = null;
		try {
			// 1. JDBC连接MYSQL
			connection = (new CodeGenerateUtils()).getConnection();
			databaseMetaData = connection.getMetaData();
			// 2. 获取所有表信息
			ResultSet tableRet = connection.getMetaData().getTables(null, "%", "%", new String[] { "TABLE" });
			// 3. 提取表名
			List<String> tableNames = new ArrayList<>();
			List<String> modelNames = new ArrayList<>();
			List<String> tableAnnotations = new ArrayList<>();

			// 想要生成的表名称
			List<String> generateTableNames = new ArrayList<>();
			generateTableNames.add("t_fire_control_room_on_duty_record_fire_alarm_log");

			while (tableRet.next()) {
				if (generateTableNames.size() != 0 && !generateTableNames.contains(tableRet.getString("TABLE_NAME"))) {
					continue;
				}
				System.out.println(tableRet.getString("TABLE_NAME") + tableRet.getString("REMARKS"));
				tableNames.add(tableRet.getString("TABLE_NAME"));
				modelNames.add(camelName(tableRet.getString("TABLE_NAME")));
				tableAnnotations.add(tableRet.getString("REMARKS"));
			}
			for (int i = 0; i < tableNames.size(); i++) {
//				CodeGenerateUtils codeGenerateUtils = new CodeGenerateUtils();
//				codeGenerateUtils.generate(tableNames.get(i), modelNames.get(i), tableAnnotations.get(i), "袁文彪");
				System.out.println("ALTER TABLE `"+tableNames.get(i)+"` CHANGE build_floor_number build_floor_number VARCHAR(200) comment '建筑楼层号';");
				System.out.println("ALTER TABLE `"+tableNames.get(i)+"` CHANGE build_room_numbers build_room_numbers VARCHAR(200) comment '建筑房间号';");
			}
//			// 接口文档生成文件的头部
//			FileUtils.fileToFile(Constant.INTERFACE_FRONT_PATH, Constant.INTERFACE_FILE_PATH);
//			// 接口文档生成文件的内容
//			FileUtils.folderToFile(Constant.INTERFACE_PATH, Constant.INTERFACE_FILE_PATH);
//			// 接口文档生成文件的尾部
//			FileUtils.fileToFile(Constant.INTERFACE_AFTER_PATH, Constant.INTERFACE_FILE_PATH);
//			// 数据字典生成文件的头部
//			FileUtils.fileToFile(Constant.DATADICTIONARY_FRONT_PATH, Constant.DATADICTIONARY_FILE_PATH);
//			// 数据字典生成文件的内容
//			FileUtils.folderToFile(Constant.DATADICTIONARY_PATH, Constant.DATADICTIONARY_FILE_PATH);
//			// 数据字典生成文件的尾部
//			FileUtils.fileToFile(Constant.DATADICTIONARY_AFTER_PATH, Constant.DATADICTIONARY_FILE_PATH);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 自定义生成实体名
	 */
	public static String camelName(String name) {
		if (name.substring(0, 2).equals("t_")) {
			name = "_" + name.substring(2, name.length());
		}
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
		String string = result.toString().substring(0, 1).toUpperCase()
				+ result.toString().substring(1, result.toString().length());
		return string;
	}
}

package com.xakj.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
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
import freemarker.template.TemplateException;

/**
 * 描述：文本替换生成
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
	
	public static void main(String[] args) throws TemplateException, IOException {
		final String path = "C://Users//Administrator//Desktop//String.txt";
		File createPath = new File(path);
		if (!createPath.exists()) {// 避免生成覆盖
			final String templateName = "String.ftl";
			File mapperFile = new File(path);
			Template template = FreeMarkerTemplateUtils.getTemplate(templateName);
			FileOutputStream fos = new FileOutputStream(mapperFile);
			Map<String, Object> dataMap = new HashMap<String, Object>();
			dataMap.put("table_annotation", "table_annotation1");
			Writer out = new BufferedWriter(new OutputStreamWriter(fos), 10240);
			template.process(dataMap , out);
		} else {
			System.out.println("文件已存在,如需重新生成请手动删除现存文件再次执行！" + path);
		}
	}

	/**
	 * 生成主方法
	 */
	public void generate(String tableName, String modelName, String tableAnnotation, String author) throws Exception {
		
	}
}
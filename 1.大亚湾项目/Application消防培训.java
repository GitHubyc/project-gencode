package com.xakj;

import com.xakj.util.CodeGenerateUtils;
import com.xakj.util.Constant;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONObject;

public class Application {

	public static void main(String[] args) {
		try {
//			String[] tableNames = new String[] { 
//					"publicity_material",
//					"publicity_material_attachment",
//					"publicity_material_feedback",
//					"publicity_itembank",
//					"publicity_knowledge_sharing",
//					"publicity_knowledge_sharing_reply",
//					"publicity_examination",
//					"publicity_examination_paper_question",
//					"publicity_training_offline",
//					"publicity_training_offline_sign_in",
//					"publicity_fire_alarm_practical_operation_test",
//					"publicity_train",
//					"publicity_examination_record",
//					"publicity_examination_record_r_question",
//					"publicity_training_online",
//					"publicity_train_r_material",
//					"publicity_material_r_question",
//					"publicity_train_online_r_examination",
//					"publicity_itembank_option",
//					"publicity_material_science_knowledge_type",
//					"publicity_fire_alarm_practical_operation_test_sub",
//					"publicity_fire_alarm_practical_operation_test_item",
//					"publicity_examination_of_paper",
//					"publicity_certificate",
//					"publicity_examination_r_paper"};
//			String[] modelNames = new String[tableNames.length];
//			String[] tableAnnotations = new String[] { 
//					"材料",
//					"附件",
//					"反馈与建议",
//					"题库",
//					"知识分享",
//					"知识分享回复",
//					"考试",
//					"试卷与题目",
//					"线下培训",
//					"线下培训签到",
//					"火警响应实操测试",
//					"培训",
//					"考试记录",
//					"考试记录与题目",
//					"线上培训",
//					"培训与材料",
//					"材料与题目",
//					"线上培训与考试",
//					"选项",
//					"材料知识类型",
//					"火警响应实操测试项",
//					"火警响应实操测试项配置",
//					"试卷",
//					"证件",
//					"考试与试卷"};
			String[] tableNames = new String[] {
					"publicity_examination_r_paper",
					"publicity_examination_of_paper"};
			String[] modelNames = new String[tableNames.length];
			String[] tableAnnotations = new String[] { 
					"考试与试卷",
					"试卷"};
			for(int i = 0; i < tableNames.length; i++){
				modelNames[i]=camelName(tableNames[i]);
				System.out.println(modelNames[i]);
			}

//			String[] tableNames = new String[] { "T_BOOK" };
//			String[] tableAnnotations = new String[] { "书籍" };
//			String[] modelNames = new String[] { "Book" };
			for (int i = 0; i < tableNames.length; i++) {
				CodeGenerateUtils codeGenerateUtils = new CodeGenerateUtils();
				codeGenerateUtils.generate(tableNames[i], modelNames[i], tableAnnotations[i], "袁文彪");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static String camelName(String name) {
		if(name.substring(0, 2).equals("t_")){
			name = "_"+name.substring(2, name.length());
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
		String string = result.toString().substring(0, 1).toUpperCase() + result.toString().substring(1, result.toString().length());
		return string;
	}
}

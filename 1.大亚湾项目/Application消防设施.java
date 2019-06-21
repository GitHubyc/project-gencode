package com.xakj;

import com.xakj.util.CodeGenerateUtils;
import com.xakj.util.Constant;

import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.List;

public class Application {

	public static void main(String[] args) {
		try {
			String[] tableNames = new String[] { 
					"t_build_facility_documentation",
					"t_build_facility_documentation_logs",
					"t_build_facility_electrical_fire_alarm_system",
					"t_build_facility_emergency_power_distribution_box",
					"t_build_facility_evacuation",
					"t_build_facility_fire_damper",
					"t_build_facility_fire_proof_door",
					"t_build_facility_lighting_system",
					"t_build_facility_lightning_protection_system",
					"t_build_facility_pump_adapter",
					"t_build_facility_special_appliances",
					"t_build_facility_special_appliances_logs",
					"t_build_facility_special_video_system",
					"t_facility_outdoor_hydrant" };
			String[] modelNames = new String[tableNames.length];
			String[] tableAnnotations = new String[] { 
					"建筑消防系统配套的文件资料台账",
					"建筑消防系统配套的文件资料台账操作记录",
					"建筑消防设施电气火灾报警系统",
					"建筑消防设施紧急断电配电箱",
					"建筑消防设施疏散指示",
					"建筑消防设施防火阀",
					"建筑消防设施防火门",
					"建筑消防应急照明系统",
					"建筑消防设施防雷系统",
					"建筑消防设施水泵接合器",
					"建筑消防专用工器具",
					"建筑消防专用工器具记录",
					"建筑消防设施消防配套专用视频系统",
					"建筑消防设施室外消火栓"};
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
		return result.toString().substring(1, result.toString().length());
	}
}

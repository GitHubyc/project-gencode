package com.xakj;

import com.xakj.util.CodeGenerateUtils;
import com.xakj.util.Constant;
import com.xakj.util.FileUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONObject;

public class Application {

	public static void main(String[] args) {
		try {
//			String[] tableNames = new String[] { 
//			"t_project_info",
//			"t_project_r_personnel",
//			"t_property_services_department",
//			"t_property_services_department_r_personnel",
//			"t_fire_control_room_daily_shift_r_psersonel_set",
//			"t_fire_control_room_daily_shift_set",
//			"t_fire_control_room_on_duty_record_fault_log",
//			"t_fire_control_room_on_duty_record_fire_alarm_log",
//			"t_fire_control_room_change_shifts_set",
//			"t_fire_control_room_on_duty_record_log",
//			"t_personnel_r_passport",
//			"t_fire_control_room_r_personnel",
//			"t_fire_control_room_info",
//			"t_fire_control_room_change_shifts_detail",
//			"t_fire_control_room_daily_shift_detail",
//			"t_fire_control_room_daily_shift_r_psersonel_detail",
//			"t_form_leave_application",
//			"t_form_break_off",
//			"t_fire_emergency_key",
//			"t_fire_emergency_key_logs",
//			"t_fire_control_room_monitor_build",
//			"t_fire_control_room_daily_shift_log"};
//	String[] modelNames = new String[tableNames.length];
//	String[] tableAnnotations = new String[] { 
//			"项目信息",
//			"项目与人员关联关系",
//			"物业服务处台账",
//			"物业服务处与人员关联关系",
//			"消防控制室班次时间与人员关联关系",
//			"消防控制室班次时间设置",
//			"班次值班记录故障记录",
//			"班次值班记录火警记录",
//			"消防控制室倒班设置",
//			"班次值班记录",
//			"值班人员与通行证关联关系",
//			"消防控制室与值班人员关联关系",
//			"消防监控室信息",
//			"消防控制室倒班详细情况",
//			"消防控制室班次详情",
//			"消防控制室班次与人员详情",
//			"请假申请单",
//			"调班申请单",
//			"消防应急钥匙台账",
//			"消防应急钥匙使用记录",
//			"监控范围",
//			"班次交接班记录表"};
			String[] tableNames = new String[] { 
					"t_fire_control_room_daily_shift_set",
					"t_fire_control_room_change_shifts_set"
					};
			String[] modelNames = new String[tableNames.length];
			String[] tableAnnotations = new String[] { 
					"消防控制室班次时间设置",
					"消防控制室倒班设置"
					};
			for(int i = 0; i < tableNames.length; i++){
				modelNames[i]=camelName(tableNames[i]);
				System.out.println(modelNames[i]);
			}
			for (int i = 0; i < tableNames.length; i++) {
				CodeGenerateUtils codeGenerateUtils = new CodeGenerateUtils();
				codeGenerateUtils.generate(tableNames[i], modelNames[i], tableAnnotations[i], "袁文彪");
			}
			// 接口文档生成文件的头部
			FileUtils.fileToFile(Constant.INTERFACE_FRONT_PATH, Constant.INTERFACE_FILE_PATH);
			// 接口文档生成文件的内容
			FileUtils.folderToFile(Constant.INTERFACE_PATH, Constant.INTERFACE_FILE_PATH);
			// 接口文档生成文件的尾部
			FileUtils.fileToFile(Constant.INTERFACE_AFTER_PATH, Constant.INTERFACE_FILE_PATH);
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

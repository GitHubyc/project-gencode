package ${package_name};

import java.util.List;
import java.util.UUID;

import net.sf.json.JSONObject;
import net.sf.json.JSONArray;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import ${daoPackage};
import ${entityPackage};
import com.xakj.service.${modelName}Service;
import com.xakj.util.Response;
import com.xakj.model.dto.${modelName}Dto;
import com.xakj.model.dto.User;

import com.xiaoleilu.hutool.date.DateUtil;

/**
 * ${table_annotation}接口实现层
 *作者:${author}
 *时间:${date}
 */
@Service
public class ${modelName}ServiceImpl implements ${modelName}Service {
	@Autowired
	private ${modelName}Dao ${modelName?uncap_first}Dao;

	@Transactional
	@Override
	public Object save(User user, ${modelName}Dto ${modelName?uncap_first}Dto){
		HttpStatus status = HttpStatus.OK;
		String msg = "操作成功！";
		try{
			${modelName}Entity ${modelName?uncap_first}Entity = new ${modelName}Entity();
			BeanUtils.copyProperties(${modelName?uncap_first}Entity,${modelName?uncap_first}Dto);
			${modelName?uncap_first}Entity.set${table_key_change?cap_first}(UUID.randomUUID().toString().replace("-", ""));
			${modelName?uncap_first}Dao.save(${modelName?uncap_first}Entity);
		}catch (Exception e) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			status = HttpStatus.INTERNAL_SERVER_ERROR;
			msg = "操作失败！";
			e.printStackTrace();
		}
		
		return new Response(status.value(), msg);
	}

	@Transactional
	@Override
	<#assign tablekeytype="" />
	<#--主键类型-->
	<#if table_key_type?default('NVARCHAR2') == "NUMBER">
		<#assign tablekeytype="Integer" />
	<#elseif table_key_type?default('NVARCHAR2') == "FLOAT">
		<#assign tablekeytype="Float" />
	<#else>
		<#assign tablekeytype="String" />
	</#if>
	public Object delete(String id) {
		HttpStatus status = HttpStatus.OK;
		String msg = "操作成功！";
		try{
			${modelName}Entity ${modelName?uncap_first}Entity = ${modelName?uncap_first}Dao.findOne(id);
			if(${modelName?uncap_first}Entity != null){
				${modelName?uncap_first}Dao.delete(id);
			} else {
				status = HttpStatus.NO_CONTENT;
				msg = "找不到相关信息！";
			}
		}catch(Exception e){
			TransactionAspectSupport.currentTransactionStatus()
					.setRollbackOnly();
			status = HttpStatus.INTERNAL_SERVER_ERROR;
			msg = "操作失败！";
			e.printStackTrace();
		}
		
		return new Response(status.value(), msg);
	}

	@Transactional
	@Override
	public Object update(${modelName}Dto ${modelName?uncap_first}Dto) {
		HttpStatus status = HttpStatus.OK;
		String msg = "操作成功！";
		try{
			${modelName}Entity ${modelName?uncap_first}Entity = ${modelName?uncap_first}Dao.findOne(${modelName?uncap_first}Dto.getId());
			if(${modelName?uncap_first}Entity != null){
				BeanUtils.copyProperties(${modelName?uncap_first}Entity,${modelName?uncap_first}Dto);
				${modelName?uncap_first}Dao.update(${modelName?uncap_first}Entity);
			}else{
				status = HttpStatus.NO_CONTENT;
				msg = "找不到相关信息！";
			}
		}catch(Exception e){
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			status = HttpStatus.INTERNAL_SERVER_ERROR;
			msg = "操作失败！";
			e.printStackTrace();
		}
		
		return new Response(status.value(), msg);
	}

	@Override
	<#assign tablekeytype="" />
	<#--主键类型-->
	<#if table_key_type?default('NVARCHAR2') == "NUMBER">
		<#assign tablekeytype="Integer" />
	<#elseif table_key_type?default('NVARCHAR2') == "FLOAT">
		<#assign tablekeytype="Float" />
	<#else>
		<#assign tablekeytype="String" />
	</#if>
	public Object get(String id) {
		HttpStatus status = HttpStatus.OK;
		String msg = "操作成功！";
		try{
			${modelName}Entity ${modelName?uncap_first}Entity = ${modelName?uncap_first}Dao.findOne(id);
			if(${modelName?uncap_first}Entity != null){
				JSONObject dto = JSONObject.fromObject(${modelName?uncap_first}Entity);
				return new Response(status.value(), msg, dto);
			}else{
				status = HttpStatus.NO_CONTENT;
				msg = "找不到相关信息！";
			}
		}catch(Exception e){
			status = HttpStatus.INTERNAL_SERVER_ERROR;
			msg = "操作失败！";
			e.printStackTrace();
		}
		return new Response(status.value(), msg);
	}

	@Override
	public Object list(Integer currentPage, Integer pageSize, ${modelName}Dto ${modelName?uncap_first}Dto) {
		HttpStatus status = HttpStatus.OK;
		String msg = "操作成功！";
		try {
			// 查询
			${modelName}Entity ${modelName?uncap_first}Entity = new ${modelName}Entity();
			BeanUtils.copyProperties(${modelName?uncap_first}Entity,${modelName?uncap_first}Dto);
			int totalRows = ${modelName?uncap_first}Dao.count(${modelName?uncap_first}Entity);
			List<${modelName}Entity> ${modelName?uncap_first}Entities = ${modelName?uncap_first}Dao.findAll(currentPage, pageSize, ${modelName?uncap_first}Entity);
			// 处理结果集
			JSONArray dtos = new JSONArray();
			for (int i = 0; i < ${modelName?uncap_first}Entities.size(); i++) {
				JSONObject dto = JSONObject.fromObject(${modelName?uncap_first}Entities.get(i));
				dtos.add(dto);
			}
			// 返回封装结果集
			return new Response(status.value(), msg, currentPage, pageSize, totalRows, dtos);
		} catch (Exception e) {
			e.printStackTrace();// 打印异常信息
			status = HttpStatus.INTERNAL_SERVER_ERROR;
			msg = "操作失败！";
			return new Response(status.value(), msg);
		}
	}
}

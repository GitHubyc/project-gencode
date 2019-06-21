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
import org.springframework.util.StringUtils;

import ${daoPackage};
import ${entityPackage};
import com.xakj.service.${modelName}Service;
import com.xakj.util.Response;
import com.xakj.model.dto.${modelName}Dto;

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
	public Object save(${modelName}Dto ${modelName?uncap_first}Dto){
		HttpStatus status = HttpStatus.OK;
		String msg = "操作成功！";
		try{
			// 主键为空则自动生成，不为空且存在则中断，不为空且不存在则使用该主键
			${modelName}Entity ${modelName?uncap_first}Entity = new ${modelName}Entity();
			if(!StringUtils.isEmpty(${modelName?uncap_first}Dto.get${table_key_change?cap_first}())){
				${modelName?uncap_first}Entity.set${table_key_change?cap_first}(${modelName?uncap_first}Dto.get${table_key_change?cap_first}());
				if(${modelName?uncap_first}Dao.count(${modelName?uncap_first}Entity) > 0){
					status = HttpStatus.INTERNAL_SERVER_ERROR;
					msg = "${table_annotation}已存在！";
					return new Response(status.value(), msg);
				}
			}else{
				${modelName?uncap_first}Dto.set${table_key_change?cap_first}(UUID.randomUUID().toString().replace("-", ""));
			}
			// 赋值后新增
			BeanUtils.copyProperties(${modelName?uncap_first}Entity,${modelName?uncap_first}Dto);
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
			// findOne存在与否，delete删除与否
			${modelName?uncap_first}Dao.findOne(id);
			${modelName?uncap_first}Dao.delete(id);
		}catch(Exception e){
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			status = HttpStatus.NO_CONTENT;
			msg = "找不到相关信息！";
		}
		
		return new Response(status.value(), msg);
	}

	@Transactional
	@Override
	public Object update(${modelName}Dto ${modelName?uncap_first}Dto) {
		HttpStatus status = HttpStatus.OK;
		String msg = "操作成功！";
		try{
			// 判断主键是否为空
			if(StringUtils.isEmpty(${modelName?uncap_first}Dto.get${table_key_change?cap_first}())){
				status = HttpStatus.INTERNAL_SERVER_ERROR;
				msg = "找不到相关信息！";
				return new Response(status.value(), msg);
			}
			// 判断主键是否存在
			${modelName}Entity ${modelName?uncap_first}Entity = new ${modelName}Entity();
			${modelName?uncap_first}Entity.set${table_key_change?cap_first}(${modelName?uncap_first}Dto.get${table_key_change?cap_first}());
			if(${modelName?uncap_first}Dao.count(${modelName?uncap_first}Entity) > 0){
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
			// 由主键获取信息后转化为JSONObject返回
			${modelName}Entity ${modelName?uncap_first}Entity = ${modelName?uncap_first}Dao.findOne(id);
			JSONObject dto = JSONObject.fromObject(${modelName?uncap_first}Entity);
			
			return new Response(status.value(), msg, dto);
		}catch(Exception e){
			status = HttpStatus.INTERNAL_SERVER_ERROR;
			msg = "操作失败！";
			return new Response(status.value(), msg);
		}
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

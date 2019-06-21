package ${package_name};

import com.xakj.model.dto.${modelName}Dto;
import com.xakj.model.dto.User;

/**
 * ${table_annotation}接口层
 *作者:${author}
 *时间:${date}
 */
public interface ${modelName}Service {

	/**
	 * 新增
	 */
	public Object save(User user, ${modelName}Dto ${modelName?uncap_first}Dto);

	/**
	 * 删除
	 */
	<#assign tablekeytype="" />
	<#--主键类型-->
	<#if table_key_type?default('NVARCHAR2') == "NUMBER">
		<#assign tablekeytype="Integer" />
	<#elseif table_key_type?default('NVARCHAR2') == "FLOAT">
		<#assign tablekeytype="Float" />
	<#else>
		<#assign tablekeytype="String" />
	</#if>
	public Object delete(String id);

	/**
	 * 更新
	 */
	public Object update(${modelName}Dto ${modelName?uncap_first}Dto);

	/**
	 * 详细
	 */
	public Object get(String id);

	/**
	 * 列表
	 */
	public Object list(Integer currentPage, Integer pageSize, ${modelName}Dto ${modelName?uncap_first}Dto);
}

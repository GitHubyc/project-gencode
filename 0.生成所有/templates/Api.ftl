package ${package_name};

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;
import com.xakj.model.dto.${modelName}Dto;
import com.xakj.service.${modelName}Service;

/**
 *描述:${table_annotation}接口
 *作者:${author}
 *时间:${date}
 */
@Api(tags = "${table_annotation}")
@RestController
@RequestMapping(value = "/${modelName?uncap_first}")
public class ${modelName}Api {
    
	@Autowired
	private ${modelName}Service ${modelName?uncap_first}Service;

	@ApiOperation(value = "新增${table_annotation}", notes = "新增${table_annotation}")
	@RequestMapping(value = "/", method = RequestMethod.POST)
	@JsonView(${modelName}Dto.BaseView.class)
	public Object save(
		@ApiParam(required = true) @Validated({${modelName}Dto.AddValidated.class}) @RequestBody ${modelName}Dto ${modelName?uncap_first}Dto){
		return ${modelName?uncap_first}Service.save(${modelName?uncap_first}Dto);
	}
	<#assign tablekeytype="" />
	<#--主键类型-->
	<#if table_key_type?default('NVARCHAR2') == "NUMBER">
		<#assign tablekeytype="Integer" />
	<#elseif table_key_type?default('NVARCHAR2') == "FLOAT">
		<#assign tablekeytype="Float" />
	<#else>
		<#assign tablekeytype="String" />
	</#if>

	@ApiOperation(value = "删除${table_annotation}", notes = "删除${table_annotation}")
	@RequestMapping(value = "/{${table_key_change?uncap_first}}", method = RequestMethod.DELETE)
	public Object delete(
		@PathVariable(name = "${table_key_change?uncap_first}") String ${table_key_change?uncap_first}) {
		return ${modelName?uncap_first}Service.delete(${table_key_change?uncap_first});
	}

	@ApiOperation(value = "更新${table_annotation}", notes = "更新${table_annotation}")
	@RequestMapping(value = "/", method = RequestMethod.PUT)
	@JsonView(${modelName}Dto.BaseView.class)
	public Object update(
		@ApiParam(required = true) @Validated({${modelName}Dto.UpdateValidated.class}) @RequestBody ${modelName}Dto ${modelName?uncap_first}Dto) {
		return ${modelName?uncap_first}Service.update(${modelName?uncap_first}Dto);
	}

	@ApiOperation(value = "${table_annotation}详细", notes = "${table_annotation}详细")
	@RequestMapping(value = "/{${table_key_change?uncap_first}}", method = RequestMethod.GET)
	public Object get(
		@PathVariable(name = "${table_key_change?uncap_first}") String ${table_key_change?uncap_first}) {
		return ${modelName?uncap_first}Service.get(${table_key_change?uncap_first});
	}

	@ApiOperation(value = "${table_annotation}列表", notes = "${table_annotation}列表")
	@RequestMapping(value = "/", method = RequestMethod.GET)
	@JsonView(${modelName}Dto.ListView.class)
	public Object list(
		@ModelAttribute @Validated({${modelName}Dto.ListValidated.class}) ${modelName}Dto ${modelName?uncap_first}Dto,
		@RequestParam(value = "currentPage", defaultValue = "1", required = false) Integer currentPage,
		@RequestParam(value = "pageSize", defaultValue = "12", required = false) Integer pageSize) {
		return ${modelName?uncap_first}Service.list(currentPage, pageSize, ${modelName?uncap_first}Dto);
	}
}

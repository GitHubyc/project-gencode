package com.xakj.model.dto;

import com.xakj.util.GeneralViews;
import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
<#if hasDateType == "true">

import java.util.Date;
</#if>
/**
 *描述:${table_annotation}
 *表名:${table_name}
 *作者:${author}
 *时间:${date}
 */
 
@ApiModel(value = "${table_annotation}", description = "${table_annotation}")
public class ${modelName?cap_first}Dto implements Serializable {
    private static final long serialVersionUID = 1L;
<#list model_column?keys as key>
    <#if model_column[key].ColumnType == "NUMBER">

    @ApiModelProperty(value = "${model_column[key].Remarks}", name = "${model_column[key].ChangeColumnName}", example = "${model_column[key].Remarks}")
    @NotEmpty(message = "${model_column[key].Remarks}", groups = {})
    @JsonView(BaseView.class)
    private Integer ${model_column[key].ChangeColumnName};//${model_column[key].Remarks}
    <#elseif model_column[key].ColumnType == "INT">

    @ApiModelProperty(value = "${model_column[key].Remarks}", name = "${model_column[key].ChangeColumnName}", example = "${model_column[key].Remarks}")
    @NotEmpty(message = "${model_column[key].Remarks}", groups = {})
    @JsonView(BaseView.class)
    private Integer ${model_column[key].ChangeColumnName};//${model_column[key].Remarks}
    <#elseif model_column[key].ColumnType == "FLOAT">

    @ApiModelProperty(value = "${model_column[key].Remarks}", name = "${model_column[key].ChangeColumnName}", example = "${model_column[key].Remarks}")
    @NotEmpty(message = "${model_column[key].Remarks}", groups = {})
    @JsonView(BaseView.class)
    private Float ${model_column[key].ChangeColumnName};//${model_column[key].Remarks}
    <#elseif model_column[key].ColumnType == "DOUBLE">

    @ApiModelProperty(value = "${model_column[key].Remarks}", name = "${model_column[key].ChangeColumnName}", example = "${model_column[key].Remarks}")
    @NotEmpty(message = "${model_column[key].Remarks}", groups = {})
    @JsonView(BaseView.class)
    private Double ${model_column[key].ChangeColumnName};//${model_column[key].Remarks}
    <#elseif model_column[key].ColumnType == "DATE">

    @ApiModelProperty(value = "${model_column[key].Remarks}", name = "${model_column[key].ChangeColumnName}", example = "${model_column[key].Remarks}")
    @NotEmpty(message = "${model_column[key].Remarks}", groups = {})
    @JsonView(BaseView.class)
    private Date ${model_column[key].ChangeColumnName};//${model_column[key].Remarks}
    <#else>

    @ApiModelProperty(value = "${model_column[key].Remarks}", name = "${model_column[key].ChangeColumnName}", example = "${model_column[key].Remarks}")
    @NotEmpty(message = "${model_column[key].Remarks}", groups = {})
    @JsonView(BaseView.class)
    private String ${model_column[key].ChangeColumnName};//${model_column[key].Remarks}
    </#if>
</#list>

<#list model_column?keys as key>
    <#if model_column[key].ColumnType == "NUMBER">

    public Integer get${model_column[key].ChangeColumnName?cap_first}() {
        return ${model_column[key].ChangeColumnName};
    }

    public void set${model_column[key].ChangeColumnName?cap_first}(Integer ${model_column[key].ChangeColumnName}) {
        this.${model_column[key].ChangeColumnName} = ${model_column[key].ChangeColumnName};
    }
    <#elseif model_column[key].ColumnType == "INT">
	public Integer get${model_column[key].ChangeColumnName?cap_first}() {
        return ${model_column[key].ChangeColumnName};
    }

    public void set${model_column[key].ChangeColumnName?cap_first}(Integer ${model_column[key].ChangeColumnName}) {
        this.${model_column[key].ChangeColumnName} = ${model_column[key].ChangeColumnName};
    }
    
    <#elseif model_column[key].ColumnType == "FLOAT">

    public Float get${model_column[key].ChangeColumnName?cap_first}() {
        return ${model_column[key].ChangeColumnName};
    }

    public void set${model_column[key].ChangeColumnName?cap_first}(Float ${model_column[key].ChangeColumnName}) {
        this.${model_column[key].ChangeColumnName} = ${model_column[key].ChangeColumnName};
    }
    <#elseif model_column[key].ColumnType == "DOUBLE">

    public Double get${model_column[key].ChangeColumnName?cap_first}() {
        return ${model_column[key].ChangeColumnName};
    }

    public void set${model_column[key].ChangeColumnName?cap_first}(Double ${model_column[key].ChangeColumnName}) {
        this.${model_column[key].ChangeColumnName} = ${model_column[key].ChangeColumnName};
    }
    <#elseif model_column[key].ColumnType == "DATE">

    public Date get${model_column[key].ChangeColumnName?cap_first}() {
        return ${model_column[key].ChangeColumnName};
    }

    public void set${model_column[key].ChangeColumnName?cap_first}(Date ${model_column[key].ChangeColumnName}) {
        this.${model_column[key].ChangeColumnName} = ${model_column[key].ChangeColumnName};
    }
    <#else>

    public String get${model_column[key].ChangeColumnName?cap_first}() {
        return ${model_column[key].ChangeColumnName};
    }

    public void set${model_column[key].ChangeColumnName?cap_first}(String ${model_column[key].ChangeColumnName}) {
        this.${model_column[key].ChangeColumnName} = ${model_column[key].ChangeColumnName};
    }
    </#if>
</#list>

    public interface BaseView extends GeneralViews.NormalView {// 基本信息（共用的）
    };

    public interface ErrorView extends GeneralViews.ErrorView {// 错误信息（数据格式验证错误）
    };

    public interface ListView extends BaseView, GeneralViews.ListView {// 列表接口(含分页，页码，每页个数，总记录数字段)
    };

    public interface AddValidated {// 新增接口验证
    };

    public interface UpdateValidated {// 修改接口验证
    };

    public interface ListValidated {// 列表接口验证
    };
}
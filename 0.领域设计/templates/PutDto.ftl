package ${package_name};



import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
<#if hasDateType == "true">

import java.util.Date;
</#if>
/**
 *描述:更新${table_annotation}
 *表名:${table_name}
 *作者:${author}
 *时间:${date}
 */
 
@ApiModel(value = "更新${table_annotation}", description = "更新${table_annotation}")
@Data
public class Put${modelName?cap_first}InputDto implements Serializable {
    private static final long serialVersionUID = 1L;

<#list model_column?keys as key>
    <#assign msg=model_column[key].Remarks?split("：")>
    <#if model_column[key].ColumnType == "NUMBER">

    @ApiModelProperty(value = "${msg[0]}", name = "${model_column[key].ChangeColumnName}", example = "${model_column[key].Remarks}")
    private Integer ${model_column[key].ChangeColumnName};//${model_column[key].Remarks}
    <#elseif model_column[key].ColumnType == "FLOAT">
    
    @ApiModelProperty(value = "${msg[0]}", name = "${model_column[key].ChangeColumnName}", example = "${model_column[key].Remarks}")
    private Float ${model_column[key].ChangeColumnName};//${model_column[key].Remarks}
    <#elseif model_column[key].ColumnType == "DATE">

    @ApiModelProperty(value = "${msg[0]}", name = "${model_column[key].ChangeColumnName}", example = "${model_column[key].Remarks}")
    private Date ${model_column[key].ChangeColumnName};//${model_column[key].Remarks}
    <#else>

    @ApiModelProperty(value = "${msg[0]}", name = "${model_column[key].ChangeColumnName}", example = "${model_column[key].Remarks}")
    private String ${model_column[key].ChangeColumnName};//${model_column[key].Remarks}
    </#if>
</#list>
}

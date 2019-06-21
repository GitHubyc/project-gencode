package ${package_name};

<#if hasDateType == "true">
import java.util.Date;
</#if>
/**
 *描述:${table_annotation}
 *表名:${table_name}
 *作者:${author}
 *时间:${date}
 */
public class ${modelName?cap_first}Entity {
<#list model_column?keys as key>
    <#if model_column[key].ColumnType == "NUMBER">
    
    private Integer ${model_column[key].ChangeColumnName};//${model_column[key].Remarks}    
    <#elseif model_column[key].ColumnType == "INT">
    
    private Integer ${model_column[key].ChangeColumnName};//${model_column[key].Remarks} 
    <#elseif model_column[key].ColumnType == "FLOAT">
    
    private Float ${model_column[key].ChangeColumnName};//${model_column[key].Remarks}
    <#elseif model_column[key].ColumnType == "DOUBLE">

    private Double ${model_column[key].ChangeColumnName};//${model_column[key].Remarks}
    <#elseif model_column[key].ColumnType == "DATE">
    
    private Date ${model_column[key].ChangeColumnName};//${model_column[key].Remarks}    
    <#else>
    
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
}

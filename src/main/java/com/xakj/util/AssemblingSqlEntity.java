package com.xakj.util;

import java.util.List;

/**
 * 语句拼装实体类
 */
public class AssemblingSqlEntity {
	private StringBuffer sql;// sql语句
	private List<Object> params;// 参数集合

	public StringBuffer getSql() {
		return sql;
	}

	public void setSql(StringBuffer sql) {
		this.sql = sql;
	}

	public List<Object> getParams() {
		return params;
	}

	public void setParams(List<Object> params) {
		this.params = params;
	}

	public AssemblingSqlEntity(StringBuffer sql, List<Object> params) {
		super();
		this.sql = sql;
		this.params = params;
	}
}

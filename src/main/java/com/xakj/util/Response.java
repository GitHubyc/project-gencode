package com.xakj.util;

import java.io.Serializable;

import net.sf.json.JSONObject;

import com.fasterxml.jackson.annotation.JsonView;

/**
 * 响应工具类
 * 
 * @author W-Tao
 * @param <T>
 */
public class Response<T> implements Serializable {

	private static final long serialVersionUID = 500682286333049016L;

	/**
	 * 状态： 200 成功 400 参数错误 500 服务器错误
	 */
	@JsonView(GeneralViews.ErrorView.class)
	private int status;

	/**
	 * 提示信息： 200 成功 400 具体参数错误 500 具体服务器错误
	 */
	@JsonView(GeneralViews.ErrorView.class)
	private Object msg;
	/**
	 * 描述信息
	 */
	@JsonView(GeneralViews.ErrorView.class)
	private Object describe = "";

	/**
	 * 当前页数
	 */
	@JsonView(GeneralViews.ListView.class)
	private int currentPage;

	/**
	 * 页面大小
	 */
	@JsonView(GeneralViews.ListView.class)
	private int pageSize;

	/**
	 * 总页数
	 */
	@JsonView(GeneralViews.ListView.class)
	private int totalPage;

	/**
	 * 总记录数
	 */
	@JsonView(GeneralViews.ListView.class)
	private int totalRows;

	/**
	 * 数据
	 */
	@JsonView(GeneralViews.NormalView.class)
	private Object data;

	public Object getDescribe() {
		return describe;
	}

	public void setDescribe(Object describe) {
		this.describe = describe;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Object getMsg() {
		return msg;
	}

	public void setMsg(Object msg) {
		this.msg = msg;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public int getTotalRows() {
		return totalRows;
	}

	public void setTotalRows(int totalRows) {
		this.totalRows = totalRows;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public Response(int status, String msg) {
		this.status = status;
		this.msg = msg;
	}

	public Response(int status, String msg, int currentPage, int pageSize,
			int totalRows, T data) {
		this.status = status;
		this.msg = msg;
		this.currentPage = currentPage;
		this.pageSize = pageSize;
		this.totalPage = totalRows % pageSize == 0 ? totalRows / pageSize
				: totalRows / pageSize + 1;
		this.totalRows = totalRows;
		this.data = data;
	}

	public Response(int status, String msg, int currentPage, int pageSize,
			int totalPage, int totalRows, T data) {
		this.status = status;
		this.msg = msg;
		this.currentPage = currentPage;
		this.pageSize = pageSize;
		this.totalPage = totalPage;
		this.totalRows = totalRows;
		this.data = data;
	}

	public Response(int status, String msg, T data) {
		this.status = status;
		this.msg = msg;
		this.data = data;
	}

	public Response() {
		super();
	}

	/**
	 * 对象转JSON字符串
	 */
	public static JSONObject fillResultString(int status, String msg,
			Object data) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("status", status);
		jsonObject.put("msg", msg);
		jsonObject.put("data", data);
		return jsonObject;
	}

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
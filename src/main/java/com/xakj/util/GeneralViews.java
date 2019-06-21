package com.xakj.util;

/**
 *视图返回
 */
public class GeneralViews {
	/**
	 * 错误视图
	 */
	public interface ErrorView {
	};

	/**
	 * 成功视图组装
	 */
	public interface NormalView extends ErrorView {
	};

	/**
	 * 成功列表视图组装
	 */
	public interface ListView extends NormalView {
	};
}
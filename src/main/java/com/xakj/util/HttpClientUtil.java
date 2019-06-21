package com.xakj.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.xiaoleilu.hutool.http.HttpException;
import com.xiaoleilu.hutool.http.HttpRequest;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import net.sf.json.JSONObject;

public class HttpClientUtil {
	static final String SECRET = "P@ssw02d"; // JWT密码
	static final String TOKEN_PREFIX = "Bearer"; // Token前缀
	static final String HEADER_STRING = "authorization";// 存放Token的Header Key

	public static void main(String[] args) {
		HashMap<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("username", "admin");
		paramMap.put("password", "123456");
		paramMap.put("appid", "d2060e3ce66241eba4db978197a55b89");
		JSONObject object = postByBody("http://192.168.1.200:5555/login", null,
				paramMap);
		if (object.getString("msg").equals("success")) {
			System.out.println("登录成功！");
			String token = object.getString("data");
			System.out.println("授权码" + token);
			if (token != null) {
				// 解析 Token
				Claims claims = Jwts.parser()
						// 验签
						.setSigningKey(SECRET)
						// 去掉 Bearer
						.parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
						.getBody();

				// 拿用户名
				String user = claims.getSubject();
				System.out.println(user);
			}
		} else {
			System.out.println("登录失败！");
		}

	}

	/**
	 * 获取令牌
	 * 
	 */
	public static Map<String, List<String>> getToken() {

//		Map<String, List<String>> map = new HashMap<String, List<String>>();
//		List<String> value = new ArrayList<String>();
//		Session session = SecurityUtils.getSubject().getSession();
//		User user = (User) session.getAttribute(Constant.USER);
//		value.add(user.getToken());
//		map.put(Constant.AUTHORIZATION, value);
//		return map;
		return null;
	}

	/**
	 * 获取上传头部
	 * 
	 */
	public static Map<String, List<String>> getTokenUpload() {

		Map<String, List<String>> map = new HashMap<String, List<String>>();
//		List<String> value = new ArrayList<String>();
//		List<String> value1 = new ArrayList<String>();
//		Session session = SecurityUtils.getSubject().getSession();
//		User user = (User) session.getAttribute(Constant.USER);
//		value.add(user.getToken());
//		value1.add("form-data");
//		map.put(Constant.AUTHORIZATION, value);
//		map.put("enctype", value1);
		return map;
	}

	/**
	 * 获取解析token令牌的值
	 * 
	 */
	public static String[] getTokenall(String token) {

//		// 解析 Token
//		Claims claims = Jwts.parser()
//				// 验签
//				.setSigningKey(Constant.SECRET)
//				// 去掉 Bearer
//				.parseClaimsJws(token.replace(Constant.TOKEN_PREFIX, ""))
//				.getBody();
//
//		// 拿用拼接数据
//		String resultdata = claims.getSubject();
		String[] arr = null;
//		arr = resultdata.split(Constant.LOGIN_SQLIT_DES);
		return arr;
	}

	/**
	 * GET请求
	 * 
	 * @param url
	 * @param header
	 *            Header参数
	 * @param paramMap
	 *            Body参数
	 * @return
	 */
	public static JSONObject get(String url, Map<String, List<String>> header,
			HashMap<String, Object> paramMap) {
		JSONObject json = new JSONObject();
		try {
			if (paramMap == null) {
				paramMap = new HashMap<String, Object>();
			}
			Object a = HttpRequest.get(url).header(header)
					.form(paramMap).execute().body();
			json = JSONObject.fromObject(HttpRequest.get(url).header(header)
					.form(paramMap).execute().body());
		} catch (HttpException e) {
			e.printStackTrace();
		} finally {
//			try {
//				int status = json.getInt("status");
//				if (status == 401) {
//					System.err.println("未授权,重新登录");
//					// 1、重新登录
//					Session session = SecurityUtils.getSubject().getSession();
//					User user = (User) session.getAttribute(Constant.USER);
//					List<String> value = new ArrayList<String>();
//					value.add(user.getToken());
//					header.put(Constant.AUTHORIZATION, value);
//					System.err.println("重新请求");
//				}
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
		}
		return json;
	}

	/**
	 * 表单POST请求
	 * 
	 * @param url
	 * @param header
	 *            Header参数
	 * @param paramMap
	 *            Body参数
	 * @return
	 */
	public static JSONObject postByForm(String url,
			Map<String, List<String>> header, HashMap<String, Object> paramMap) {
		JSONObject json = new JSONObject();
		try {
			if (paramMap == null) {
				paramMap = new HashMap<String, Object>();
			}
			json = JSONObject.fromObject(HttpRequest.post(url).header(header)
					.form(paramMap).execute().body());
		} catch (HttpException e) {
			e.printStackTrace();
		} finally {
//			try {
//				int status = json.getInt("status");
//				if (status == 401) {
//					System.err.println("未授权,重新登录");
//					// 1、重新登录
//					Session session = SecurityUtils.getSubject().getSession();
//					User user = (User) session.getAttribute(Constant.USER);
//					List<String> value = new ArrayList<String>();
//					value.add(user.getToken());
//					header.put(Constant.AUTHORIZATION, value);
//				}
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
		}
		return json;
	}

	/**
	 * 请求内容POST请求
	 * 
	 * @param url
	 * @param header
	 *            Header参数
	 * @param paramMap
	 *            Body参数
	 * @return
	 */
	public static JSONObject postByBody(String url,
			Map<String, List<String>> header, HashMap<String, Object> paramMap) {
		JSONObject json = new JSONObject();
		try {
			if (paramMap == null) {
				paramMap = new HashMap<String, Object>();
			}
			json = JSONObject.fromObject(HttpRequest.post(url).header(header)
					.contentType("application/json;charset=UTF-8")
					.body(MapToObject(paramMap).toString()).execute().body());
		} catch (HttpException e) {
			e.printStackTrace();
		} finally {
//			try {
//				int status = json.getInt("status");
//				if (status == 401) {
//					System.err.println("未授权,重新登录");
//					// 1、重新登录
//					Session session = SecurityUtils.getSubject().getSession();
//					User user = (User) session.getAttribute(Constant.USER);
//					List<String> value = new ArrayList<String>();
//					value.add(user.getToken());
//					header.put(Constant.AUTHORIZATION, value);
//					System.err.println("重新请求");
//				}
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
		}
		return json;
	}
	
	/**
	 * 请求内容POST请求,传输JSON字符串进行批量添加
	 * 
	 * @param url
	 * @param header
	 *            Header参数
	 * @param jsonString
	 *            Body参数
	 * @return
	 */
	public static JSONObject postByBodyForJson(String url,
			Map<String, List<String>> header, String jsonString) {
		JSONObject json = new JSONObject();

		try {
			json = JSONObject
					.fromObject(HttpRequest.post(url).header(header).contentType("application/json;charset=UTF-8")
							.body(jsonString).execute().body());
		} catch (HttpException e) {
			e.printStackTrace();
		} finally {
//			try {
//				int status = json.getInt("status");
//				if (status == 401) {
//					System.err.println("未授权,重新登录");
//					// 1、重新登录
//					Session session = SecurityUtils.getSubject().getSession();
//					User user = (User) session.getAttribute(Constant.USER);
//					List<String> value = new ArrayList<String>();
//					value.add(user.getToken());
//					header.put(Constant.AUTHORIZATION, value);
//					System.err.println("重新请求");
//				}
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//			if (json.getInt("status")!=200) {
//				
//			}System.out.println(json.toString());
		}
		return json;
	}
	/**
	 * DELETE请求
	 * 
	 * @param url
	 * @param header
	 *            Header参数
	 * @param paramMap
	 *            Body参数
	 * @return
	 */
	public static JSONObject delete(String url,
			Map<String, List<String>> header, HashMap<String, Object> paramMap) {
		JSONObject json = new JSONObject();

		try {
			if (paramMap == null) {
				paramMap = new HashMap<String, Object>();
			}
			json = JSONObject.fromObject(HttpRequest.delete(url).header(header)
					.form(paramMap).execute().body());
		} catch (HttpException e) {
			e.printStackTrace();
		} finally {
//			try {
//				int status = json.getInt("status");
//				if (status == 401) {
//					System.err.println("未授权,重新登录");
//					// 1、重新登录
//					Session session = SecurityUtils.getSubject().getSession();
//					User user = (User) session.getAttribute(Constant.USER);
//					List<String> value = new ArrayList<String>();
//					value.add(user.getToken());
//					header.put(Constant.AUTHORIZATION, value);
//					System.err.println("重新请求");
//				}
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
		}
		return json;
	}

	/**
	 * 表单PUT请求
	 * 
	 * @param url
	 * @param header
	 *            Header参数
	 * @param paramMap
	 *            Body参数
	 * @return
	 */
	public static JSONObject putByForm(String url,
			Map<String, List<String>> header, HashMap<String, Object> paramMap) {
		JSONObject json = new JSONObject();

		try {
			if (paramMap == null) {
				paramMap = new HashMap<String, Object>();
			}
			json = JSONObject.fromObject(HttpRequest.put(url).header(header)
					.form(paramMap).execute().body());
		} catch (HttpException e) {
			e.printStackTrace();
		} finally {
//			try {
//				int status = json.getInt("status");
//				if (status == 401) {
//					System.err.println("未授权,重新登录");
//					// 1、重新登录
//					Session session = SecurityUtils.getSubject().getSession();
//					User user = (User) session.getAttribute(Constant.USER);
//					List<String> value = new ArrayList<String>();
//					value.add(user.getToken());
//					header.put(Constant.AUTHORIZATION, value);
//					System.err.println("重新请求");
//				}
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
		}
		return json;
	}

	/**
	 * 请求内容PUT请求
	 * 
	 * @param url
	 * @param header
	 *            Header参数
	 * @param paramMap
	 *            Body参数
	 * @return
	 */
	public static JSONObject putByBody(String url,
			Map<String, List<String>> header, HashMap<String, Object> paramMap) {
		JSONObject json = new JSONObject();

		try {
			if (paramMap == null) {
				paramMap = new HashMap<String, Object>();
			}
			json = JSONObject.fromObject(HttpRequest.put(url).header(header)
					.contentType("application/json;charset=UTF-8")
					.body(MapToObject(paramMap).toString()).execute().body());
		} catch (HttpException e) {
			e.printStackTrace();
		} finally {
//			try {
//				int status = json.getInt("status");
//				if (status == 401) {
//					System.err.println("未授权,重新登录");
//					// 1、重新登录
//					Session session = SecurityUtils.getSubject().getSession();
//					User user = (User) session.getAttribute(Constant.USER);
//					List<String> value = new ArrayList<String>();
//					value.add(user.getToken());
//					header.put(Constant.AUTHORIZATION, value);
//					System.err.println("重新请求");
//				}
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
		}
		return json;
	}

	/**
	 * HashMap集合转JsonObject对象
	 */
	public static JSONObject MapToObject(HashMap<String, Object> hashMap) {
		JSONObject object = new JSONObject();
		if (hashMap != null) {
			Iterator iter = hashMap.entrySet().iterator();
			while (iter.hasNext()) {
				Map.Entry entry = (Map.Entry) iter.next();
				Object key = entry.getKey();
				Object value = entry.getValue();
				object.put(key, value);
			}
		}
		return object;
	}

	/**
	 * JsonObject转Map
	 */
	public static HashMap<String, Object> toMap(String json) {
		HashMap<String, Object> data = new HashMap<String, Object>();
		JSONObject jsonObject = JSONObject.fromObject(json);
		Iterator ite = jsonObject.keys();
		while (ite.hasNext()) {
			String key = ite.next().toString();
			String value = jsonObject.get(key).toString();
			data.put(key, value);
		}
		return data;
	}

}

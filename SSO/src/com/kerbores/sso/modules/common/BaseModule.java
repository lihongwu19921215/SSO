package com.kerbores.sso.modules.common;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.log.Log;
import org.nutz.log.Logs;
import org.nutz.mvc.annotation.Encoding;
import org.nutz.mvc.annotation.Fail;
import org.nutz.mvc.annotation.Ok;

import com.kerbores.utils.entries.OperationState;
import com.kerbores.utils.entries.Result;

/**
 * 
 * 提供controller的数据封装统标准操作
 * 
 * @author Kerbores <br>
 *         每个模块只需要继承此模块，配置@At和@Fail即可
 * 
 * @Fail 建议直接放到统一的处理view进行处理 @Fail("jsp:jsp.exception.exception")
 */
@Encoding(input = "UTF-8", output = "UTF-8")
@IocBean
@Fail("jsp:jsp.exception.exception")
@Ok("json")
public class BaseModule {
	protected Result result = Result.me();
	protected Map<Object, Object> data = new LinkedHashMap<Object, Object>();
	protected OperationState state = OperationState.DEFAULT;
	protected String title = "";
	protected static final Log log = Logs.get();

	public BaseModule() {
		data.clear();
	}

	/**
	 * 填充结果
	 * 
	 * @return
	 * @throws Exception
	 */
	protected Result fillResult() {
		try {
			result.setData(data);
			result.setOperationState(state);
			result.setTitle(title);
		} catch (Exception e) {
			System.err.println("装配结果失败!");
			log.error(e);
		}
		return result;
	}

	/**
	 * 获取int参数
	 * 
	 * @param request
	 *            请求
	 * @param key
	 *            参数�?
	 * @return 参数int值，为了提高效率异常不再抛出而是返回默认�?如果在某些情况需要处理参数异常请�?进行判断
	 */
	protected int getParameterAsInt(HttpServletRequest request, String key) {
		int i = 0;
		try {
			i = Integer.parseInt(request.getParameter(key));
		} catch (Exception e) {
			log.error(request.getParameter(key) + "不能转换成数字");
		}
		return i;
	}

	/**
	 * 清除结果
	 */
	protected void clear() {
		result.clear();
		data.clear();
	}
}

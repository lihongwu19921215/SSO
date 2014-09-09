package com.kerbores.sso.modules;

import java.util.Enumeration;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.lang.Lang;
import org.nutz.lang.Strings;
import org.nutz.lang.Times;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Fail;
import org.nutz.mvc.annotation.IocBy;
import org.nutz.mvc.annotation.Modules;
import org.nutz.mvc.annotation.Ok;
import org.nutz.mvc.ioc.provider.ComboIocProvider;

import com.ixion.mongo.entity.Query;
import com.kerbores.sso.bean.App;
import com.kerbores.sso.bean.Token;
import com.kerbores.sso.bean.User;
import com.kerbores.sso.dao.IAppDao;
import com.kerbores.sso.dao.ITokenDao;
import com.kerbores.sso.dao.IUserDao;
import com.kerbores.sso.modules.common.BaseModule;
import com.kerbores.utils.entries.Result;
import com.kerbores.utils.ua.UserAgent;

/**
 * @author 贵源
 *
 *         create at 2014年8月19日下午4:57:04
 */
@Modules(scanPackage = true)
@IocBy(type = ComboIocProvider.class, args = { "*org.nutz.ioc.loader.json.JsonLoader", "config/config.json",
		"*org.nutz.ioc.loader.annotation.AnnotationIocLoader", "com.kerbores.sso" })
@At("/")
@Fail("json")
@Ok("json")
public class MainModule extends BaseModule {

	@Inject
	private ITokenDao tokenDao;
	@Inject
	private IUserDao userDao;
	@Inject
	private IAppDao appDao;

	/**
	 * 单点登出
	 * 
	 * @param token
	 *            当前登录会话中的cookie中的token
	 * @return 操作状态
	 */
	@At("logOut")
	public Result logOut(String token) {
		Token tokenTicket = tokenDao.findOneByCnd(Query.me().append("token", token));
		if (tokenTicket == null) {
			return Result.success();
		} else {
			tokenTicket.setAvaiable(false);
		}
		return tokenDao.update(tokenTicket) ? Result.success() : Result.fail("重置token状态失败,为了你的账户安全建议重试");
	}

	/**
	 * 单点登录
	 * 
	 * @param user
	 *            用户名
	 * @param pwd
	 *            密码
	 * @param app
	 *            应用名称
	 * @param browerInfo
	 *            浏览器信息
	 * @param osInfo
	 *            操作系统信息
	 * @return 登录结果描述
	 */
	@At("login")
	public Result login(String user, String pwd, String app, String browerInfo, String osInfo) {
		data.clear();
		// 需要完善用户检查机制和客户端信息记录机制
		User checkUser = userDao.findOneByCnd(Query.me().append("userName", user));
		if (checkUser == null) {
			return Result.fail("用户名不存在");
		} else if (!Strings.equals(Lang.md5(pwd), checkUser.getPwd())) {
			return Result.fail("密码不正确");
		} else {
			Token token = new Token();
			String tokenKey = Lang.md5(System.nanoTime() + "");
			token.setAppName(app);
			token.setToken(tokenKey);
			token.setBrowerInfo(browerInfo);
			token.setLoginName(user);
			token.setOsInfo(osInfo);
			boolean flag = tokenDao.save(token);
			if (flag) {
				data.put("token", tokenKey);
				data.put("tokenTicket", token);
				List<App> apps = appDao.listAll();
				data.put("apps", apps);
				return Result.success().setData(data);
			} else {
				return Result.fail("token持久化失败,请重试登录");
			}
		}

	}

	/**
	 * 会话状态检查
	 * 
	 * @param token
	 *            当前会话token
	 * @return 检查结果
	 */
	@At("checkLogin")
	public Result checkLogin(String token, String browerInfo, String osInfo) {
		// token检查机制 判断由第一步生成的token记录的lastActive 到目前的时间差是否大于30分钟
		Token tokenTicket = tokenDao.findOneByCnd(Query.me().append("token", token));
		// 有token token有效且未过期 过期时间为30分钟
		if (tokenTicket != null && tokenTicket.isAvaiable() && (Times.now().getTime() - tokenTicket.getLastActive().getTime()) < 1000 * 60 * 60 * 30) {
			data.clear();
			tokenTicket.setLastActive(Times.now());
			tokenTicket.setBrowerInfo(browerInfo);
			tokenTicket.setOsInfo(osInfo);
			tokenDao.update(tokenTicket);// 暂时不关注更新成功与否 半小时时间之后全部更新失败的可能性太小
			List<App> apps = appDao.listAll();
			data.put("token", token);
			data.put("tokenTicket", tokenTicket);
			data.put("apps", apps);
			return Result.success().setData(data);
		}
		return Result.fail("没有登录或者会话超时");
	}

	@At("test")
	public Result test(HttpServletRequest request) {
		data.clear();
		Enumeration<String> headers = request.getHeaderNames();
		while (headers.hasMoreElements()) {
			String key = headers.nextElement();
			data.put(key, request.getHeader(key));
			if (Strings.equals(key, "user-agent")) {
				UserAgent ua = new UserAgent(request.getHeader(key));
				data.put("uaInfo", ua);
				data.put("brVersion", ua.getBrowserVersion());
			}
		}
		return Result.success().setData(data);
	}

}

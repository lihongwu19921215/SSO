package com.kerbores.sso.bean;

import java.util.Date;

import org.nutz.lang.Times;

import com.ixion.mongo.annotation.Collection;
import com.ixion.mongo.annotation.Sequence;

/**
 * 单点登录token持久化对象
 * 
 * @author Ixion
 *
 *         create at 2014年9月3日
 */
@Collection("sso_token")
@Sequence("sso_token_id")
public class Token {
	/**
	 * 唯一编码
	 */
	private long id;
	/**
	 * token值
	 */
	private String token;
	/**
	 * 初次登录时间
	 */
	private Date firstLogin = Times.now();
	/**
	 * 最后激活时间
	 */
	private Date lastActive = Times.now();
	/**
	 * 操作系统信息
	 */
	private String osInfo;
	/**
	 * 浏览器信息
	 */
	private String browerInfo;
	/**
	 * 状态
	 */
	private boolean avaiable = true;
	/**
	 * 应用名称
	 */
	private String appName;
	/**
	 * 登录名称 唯一键 比如QQ号,企业员工号等唯一的能标识用户身份的键
	 */
	private String loginName;

	/**
	 * @return the loginName
	 */
	public String getLoginName() {
		return loginName;
	}

	/**
	 * @param loginName the loginName to set
	 */
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	/**
	 * @return the appName
	 */
	public String getAppName() {
		return appName;
	}

	/**
	 * @param appName
	 *            the appName to set
	 */
	public void setAppName(String appName) {
		this.appName = appName;
	}

	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}

	/**
	 * @return the token
	 */
	public String getToken() {
		return token;
	}

	/**
	 * @param token
	 *            the token to set
	 */
	public void setToken(String token) {
		this.token = token;
	}

	/**
	 * @return the firstLogin
	 */
	public Date getFirstLogin() {
		return firstLogin;
	}

	/**
	 * @param firstLogin
	 *            the firstLogin to set
	 */
	public void setFirstLogin(Date firstLogin) {
		this.firstLogin = firstLogin;
	}

	/**
	 * @return the lastActive
	 */
	public Date getLastActive() {
		return lastActive;
	}

	/**
	 * @param lastActive
	 *            the lastActive to set
	 */
	public void setLastActive(Date lastActive) {
		this.lastActive = lastActive;
	}

	/**
	 * @return the osInfo
	 */
	public String getOsInfo() {
		return osInfo;
	}

	/**
	 * @param osInfo
	 *            the osInfo to set
	 */
	public void setOsInfo(String osInfo) {
		this.osInfo = osInfo;
	}

	/**
	 * @return the browerInfo
	 */
	public String getBrowerInfo() {
		return browerInfo;
	}

	/**
	 * @param browerInfo
	 *            the browerInfo to set
	 */
	public void setBrowerInfo(String browerInfo) {
		this.browerInfo = browerInfo;
	}

	/**
	 * @return the avaiable
	 */
	public boolean isAvaiable() {
		return avaiable;
	}

	/**
	 * @param avaiable
	 *            the avaiable to set
	 */
	public void setAvaiable(boolean avaiable) {
		this.avaiable = avaiable;
	}

}

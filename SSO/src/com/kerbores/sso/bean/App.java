package com.kerbores.sso.bean;

import com.ixion.mongo.annotation.Collection;

/**
 * @author Ixion
 *
 *         create at 2014年9月3日
 */
@Collection("sso_app")
public class App {
	/**
	 * 唯一编号
	 */
	private long id;
	/**
	 * 名称
	 */
	private String name;
	/**
	 * cookie分发路径
	 */
	private String cookieProvider;

	/**
	 * 无参构造
	 */
	public App() {
		super();
	}

	/**
	 * @param name
	 *            应用名称
	 * @param cookieProvider
	 *            cookie分发地址
	 */
	public App(String name, String cookieProvider) {
		super();
		this.name = name;
		this.cookieProvider = cookieProvider;
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
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the cookieProvider
	 */
	public String getCookieProvider() {
		return cookieProvider;
	}

	/**
	 * @param cookieProvider
	 *            the cookieProvider to set
	 */
	public void setCookieProvider(String cookieProvider) {
		this.cookieProvider = cookieProvider;
	}
}

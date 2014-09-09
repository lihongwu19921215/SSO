package com.kerbores.sso.dao.impl;

import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.Lang;

import com.ixion.mongo.EntityMapping;
import com.ixion.mongo.dao.MongoDao;
import com.ixion.mongo.utils.MongoClientPool;
import com.kerbores.sso.bean.App;
import com.kerbores.sso.dao.IAppDao;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;

/**
 * @author Ixion
 *
 *         create at 2014年9月3日
 */
@IocBean(name = "appDao")
public class AppDaoImpl extends MongoDao<App> implements IAppDao {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ixion.mongo.dao.MongoDao#init()
	 */
	@Override
	protected void init() {
		mapping = new EntityMapping<App>() {

			@Override
			public DBObject toDBObject(App t) {
				return MongoClientPool.obj2dbo(t);
			}

			@Override
			public DBCollection getCollection() {
				return MongoClientPool.getCollection(App.class);
			}

			@Override
			public App get(DBObject object) {
				return Lang.map2Object(MongoClientPool.dbo2map(object), App.class);
			}
		};
		collection = mapping.getCollection();
	}

}

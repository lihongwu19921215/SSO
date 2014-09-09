package com.kerbores.sso.dao.impl;

import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.Lang;

import com.ixion.mongo.EntityMapping;
import com.ixion.mongo.dao.MongoDao;
import com.ixion.mongo.utils.MongoClientPool;
import com.kerbores.sso.bean.User;
import com.kerbores.sso.dao.IUserDao;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;

/**
 * @author Ixion
 *
 *         create at 2014年9月3日
 */
@IocBean(name = "userDao")
public class UserDaoImpl extends MongoDao<User> implements IUserDao {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ixion.mongo.dao.MongoDao#init()
	 */
	@Override
	protected void init() {

		mapping = new EntityMapping<User>() {

			@Override
			public DBObject toDBObject(User t) {
				return MongoClientPool.obj2dbo(t);
			}

			@Override
			public DBCollection getCollection() {
				return MongoClientPool.getCollection(User.class);
			}

			@Override
			public User get(DBObject object) {
				return Lang.map2Object(MongoClientPool.dbo2map(object), User.class);
			}
		};
		collection = mapping.getCollection();
	}

}

package com.kerbores.sso.dao.impl;

import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.Lang;

import com.ixion.mongo.EntityMapping;
import com.ixion.mongo.dao.MongoDao;
import com.ixion.mongo.utils.MongoClientPool;
import com.kerbores.sso.bean.Token;
import com.kerbores.sso.dao.ITokenDao;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;

/**
 * @author Ixion
 *
 *         create at 2014年9月3日
 */
@IocBean(name = "tokenDao")
public class TokenDaoImpl extends MongoDao<Token> implements ITokenDao {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.ixion.mongo.dao.MongoDao#init()
	 */
	@Override
	protected void init() {
		mapping = new EntityMapping<Token>() {

			@Override
			public Token get(DBObject object) {
				return Lang.map2Object(MongoClientPool.dbo2map(object), Token.class);
			}

			@Override
			public DBCollection getCollection() {
				return MongoClientPool.getCollection(Token.class);
			}

			@Override
			public DBObject toDBObject(Token t) {
				return MongoClientPool.obj2dbo(t);
			}
		};
		collection = mapping.getCollection();
	}

}

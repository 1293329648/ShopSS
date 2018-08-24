package com.cjf.dao.Impl;
import java.sql.SQLException;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate5.HibernateCallback;
import org.springframework.orm.hibernate5.support.HibernateDaoSupport;

import com.cjf.dao.UserDao;
import com.cjf.entity.User;

public class UserDaoImpl extends HibernateDaoSupport implements UserDao{
	//校验用户名是否存在
   public  List checkUsername(String username) {	   
	    List list = this.getHibernateTemplate().find("from User where username = ? ", username);
		 return list;   
   }   
   //注册
	@Override
	public void regist(User user) throws SQLException {		
	    getHibernateTemplate().save(user);	    
	}
	//激活
	@Override
	public void active(String activeCode) throws SQLException {
		             //回调函数
		getHibernateTemplate().execute(new HibernateCallback<User>() {
			@Override
			public User doInHibernate(Session session) throws HibernateException {
			            String hql = "from User where code = ? ";   //查询确定炫耀的函数
						Query query = session.createQuery(hql);
						query.setParameter(0, activeCode);
						User user = (User) query.uniqueResult();
						user.setState(1);
						getHibernateTemplate().update(user);
						return user;  //返回更新的用户				
			}			
		});	  
	}
	//根据用户名获得用户密码
	@Override  
	public User getByUserCode(String username) throws SQLException {
		return getHibernateTemplate().execute(new HibernateCallback<User>() {
			@Override
			public User doInHibernate(Session session) throws HibernateException {
			    String hql = "from User where username = ? ";
				Query query = session.createQuery(hql);
				query.setParameter(0, username);
				User user = (User) query.uniqueResult();
			return user;
			}
		});		  
	}
}
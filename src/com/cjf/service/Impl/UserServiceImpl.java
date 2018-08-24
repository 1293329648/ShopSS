package com.cjf.service.Impl;

import java.sql.SQLException;
import java.util.List;

import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.cjf.dao.UserDao;
import com.cjf.entity.User;
import com.cjf.service.UserService;

public class UserServiceImpl implements UserService {

	UserDao userdao;
	
    public void setUserdao(UserDao userdao) {
		this.userdao = userdao;
	}
	 
    @Override
	public User getUserByCodePassword(User user) throws SQLException {
			//1 根据登陆名称查询登陆用户
			User existU = userdao.getByUserCode(user.getUsername());
			//2 判断用户是否存在.不存在=>抛出异常,提示用户名不存在
			if(existU==null){
				throw new RuntimeException("用户名不存在!");
			}
			//3 判断用户密码是否正确=>不正确=>抛出异常,提示密码错误
			if(!existU.getPassword().equals(user.getPassword())){
				throw new RuntimeException("密码错误!");
			}		
		return existU;
	}
	@Override
	@Transactional(isolation=Isolation.REPEATABLE_READ,propagation=Propagation.REQUIRED,readOnly=false)
	public boolean regist(User user) {		
		try {
			userdao.regist(user);
			return true;	
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}				
	}
	
	@Override
	@Transactional(isolation=Isolation.REPEATABLE_READ,propagation=Propagation.REQUIRED,readOnly=false)
	public void active(String activeCode) {		
		try {
			userdao.active(activeCode);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	//核查用户名是否存在
	@Override
	public boolean checkUsername(String name) {			
		  List list = userdao.checkUsername(name);	
		  if(list.size()>0) {
			  return true;
		  }else {
			return false;
		}
	}


}

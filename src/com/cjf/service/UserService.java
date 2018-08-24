package com.cjf.service;

import java.sql.SQLException;

import com.cjf.entity.User;

public interface UserService {
   


	public boolean regist(User user);

	public void active(String activeCode);

	User getUserByCodePassword(User user) throws SQLException ;

	public boolean checkUsername(String name);
	 
}

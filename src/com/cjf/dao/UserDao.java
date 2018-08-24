package com.cjf.dao;

import java.sql.SQLException;
import java.util.List;

import com.cjf.entity.User;

public interface UserDao {


	public void regist(User user) throws SQLException;

	public void active(String activeCode) throws SQLException;

	public User getByUserCode(String username) throws SQLException;
	
	public List checkUsername(String username);

	
}

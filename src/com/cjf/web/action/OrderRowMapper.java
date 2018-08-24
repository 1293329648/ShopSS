package com.cjf.web.action;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;

import com.cjf.entity.Category;
import com.cjf.entity.Order;
import com.cjf.entity.OrderItem;
import com.cjf.entity.Product;
import com.cjf.entity.User;

public class OrderRowMapper implements RowMapper<Order> {

	@Override
	public Order mapRow(ResultSet rs, int arg1) throws SQLException {
		
		Order order=new Order();
		
		order.setOid(rs.getString("oid"));
		order.setOrdertime(rs.getDate("ordertime"));
		order.setTotal(rs.getDouble("total"));
		order.setState(rs.getInt("state"));
		order.setAddress(rs.getString("address"));
		order.setName(rs.getString("name"));
		order.setTelephone(rs.getString("telephone"));
		
		User user=new User();
		user.setUid(rs.getString("uid"));
		order.setUser(user);
		
		List<OrderItem> orderItems = new ArrayList<OrderItem>();
		for (OrderItem orderItem : orderItems) {
			orderItem.setItemid(rs.getString("itemid"));			
		}        
		order.setOrderItems(orderItems);
		return  order;			
	}
}

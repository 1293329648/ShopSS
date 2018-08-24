package com.cjf.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.cjf.entity.Category;
import com.cjf.entity.Order;
import com.cjf.entity.Product;

public interface ProductDao {
	   

	List<Product> findHotProductList()throws SQLException ;
	
	List<Product> findNewProductList()throws SQLException ;

	List<Category> findCategoryList() throws SQLException;

	List<Product> findProductByPage(String cid, int index, int currentCount) throws SQLException;

	int getCount(String cid) throws SQLException;
	
	Product findInfo(String pid) throws SQLException;

	void addOrders(Order order) throws SQLException;

	void addOrderItem(Order order) throws SQLException;

	void updateOrderAdrr(Order order) throws SQLException;
	
	void updateOrderState(String r6_Order) throws SQLException;

	List<Order> findOrderListByUid(String uid) throws SQLException;
	
	List<Map<String, Object>> findAllOrderItemByOid(String oid) throws SQLException;
	
	void addProduct(Product product) throws SQLException;

	List<Order> findAllOrder() throws SQLException;
	
	List<Product> findAllProduct() throws SQLException;

	void delProductByPid(String pid) throws SQLException;
	
}

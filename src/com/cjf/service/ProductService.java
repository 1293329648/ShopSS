package com.cjf.service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.cjf.entity.Category;
import com.cjf.entity.Order;
import com.cjf.entity.PageBean;
import com.cjf.entity.Product;

public interface ProductService {
  
	List<Product> findHotProductList()throws SQLException ;
	
	List<Product> findNewProductList()throws SQLException ;

	List<Category> findCategoryList() throws SQLException;

	PageBean findProductBycid(String cid, int currentPage, int currentCount) throws SQLException;

	Product findInfo(String pid) throws SQLException;

	void submitOrder(Order order);

	void updateOrderAdrr(Order order);

	void updateOrderState(String r6_Order);

	List<Order> findOrderListByUid(String uid) throws SQLException;
	
	List<Map<String, Object>> findAllOrderItemByOid(String oid) throws SQLException;

	void addProduct(Product product) throws SQLException;

	List<Order> findAllOrder() throws SQLException;

	List<Product> findAllProduct() throws SQLException;

	void delProductByPid(String pid) throws SQLException;;

	
	
}

package com.cjf.service.Impl;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.cjf.dao.ProductDao;
import com.cjf.entity.Category;
import com.cjf.entity.Order;
import com.cjf.entity.PageBean;
import com.cjf.entity.Product;
import com.cjf.service.ProductService;


public class ProductServiceImpl implements ProductService {
	
	ProductDao productdao;

	public ProductDao getProductdao() {
		return productdao;
	}

	public void setProductdao(ProductDao productdao) {
		this.productdao = productdao;
	}

	@Override
	public List<Product> findHotProductList() throws SQLException {
		// TODO Auto-generated method stub
		return  productdao.findHotProductList();
	}

	@Override
	public List<Product> findNewProductList() throws SQLException {
		// TODO Auto-generated method stub
		return productdao.findNewProductList();
	}

	@Override
	public List<Category> findCategoryList() throws SQLException{
		// TODO Auto-generated method stub
		return  productdao.findCategoryList();
	}

	@Override             //            cid       总页数       该页的总条数
	public PageBean findProductBycid(String cid,int currentPage, int currentCount) throws SQLException {
	   
		 PageBean<Product> pageBean = new PageBean<Product>();
     
		 pageBean.setCurrentPage(currentPage);
		 pageBean.setCurrentCount(currentCount);
		   
		int totalCount =  productdao.getCount(cid);		  
		pageBean.setTotalCount(totalCount);
		
	    int totalPage = (int)Math.ceil(1.0*totalCount/currentCount);
	    pageBean.setTotalPage(totalPage);
	    
	    int index=(currentPage-1)*currentCount;
	    
        List<Product> list=productdao.findProductByPage(cid,index,currentCount);	    
        pageBean.setList(list);
        
		return pageBean;
	}

	@Override
	public Product findInfo(String pid) throws SQLException {		 
	       return	productdao.findInfo(pid);		
	}

	@Override
	public void submitOrder(Order order) {	   
		try {
	
			productdao.addOrders(order);
			productdao.addOrderItem(order);
			
		} catch (SQLException e) {
		  e.printStackTrace();
		}finally {
		}		
	}

	public void updateOrderAdrr(Order order) {

		try {
			productdao.updateOrderAdrr(order);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void updateOrderState(String r6_Order) {

		try {
			productdao.updateOrderState(r6_Order);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public List<Order> findOrderListByUid(String uid) throws SQLException {		
		return 	productdao.findOrderListByUid(uid);
	}

	@Override
	public List<Map<String, Object>> findAllOrderItemByOid(String oid) throws SQLException {
		return productdao.findAllOrderItemByOid(oid);
	}

	@Override
	public void addProduct(Product product) throws SQLException {
		
		productdao.addProduct(product);
	}

	@Override
	public List<Order> findAllOrder() throws SQLException {
		
		return productdao.findAllOrder();
	}

	@Override
	public List<Product> findAllProduct() throws SQLException {
		// TODO Auto-generated method stub
		return productdao.findAllProduct();
	}

	@Override
	public void delProductByPid(String pid) throws SQLException {
		
		productdao.delProductByPid(pid);
	}

}

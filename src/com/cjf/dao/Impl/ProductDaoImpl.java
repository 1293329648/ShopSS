package com.cjf.dao.Impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.cjf.dao.ProductDao;
import com.cjf.entity.Category;
import com.cjf.entity.Order;
import com.cjf.entity.OrderItem;
import com.cjf.entity.Product;
import com.cjf.web.action.OrderRowMapper;
import com.cjf.web.action.ProductRowMapper;

public class ProductDaoImpl extends JdbcDaoSupport implements ProductDao {
	// 最新商品
	@Override
	public List<Product> findHotProductList() throws SQLException {
		String sql = "select * from product where is_hot = ? limit ?,? ";
		List<Product> list = getJdbcTemplate().query(sql, new ProductRowMapper(), 1, 0, 9);
		return list;
	}

	// 最热商品
	@Override
	public List<Product> findNewProductList() throws SQLException {
		String sql = "select * from product order by pdate desc limit ?,?";
		List<Product> list = getJdbcTemplate().query(sql, new ProductRowMapper(), 0, 9);
		return list;
	}
	// 查询商品类别
	@Override
	public List<Category> findCategoryList() throws SQLException {
		String sql = "select * from category";
		List<Category> list = getJdbcTemplate().query(sql, new RowMapper<Category>() {
			@Override
			public Category mapRow(ResultSet rs, int arg1) throws SQLException {
				Category category = new Category();
				category.setCid(rs.getString("cid"));
				category.setCname(rs.getString("cname"));
				return category;
			}
		});
		return list;
	}
  //统计该类的所有商品数
	@Override
	public int getCount(String cid) throws SQLException {

		String sql = "select count(*) from product where cid=?";
		Integer integer = getJdbcTemplate().queryForObject(sql, Integer.class, cid);
		return integer.intValue();
	}
  //通过分页信息查询
	@Override
	public List<Product> findProductByPage(String cid, int index, int currentCount) throws SQLException {

		String sql = "select * from product where cid=? limit ?,? ";
		List<Product> list = getJdbcTemplate().query(sql, new ProductRowMapper(), cid, index, currentCount);
		return list;
	}

	@Override
	public Product findInfo(String pid) throws SQLException {
		String sql = "select * from product where pid=?";
		// 查询单一商品 queryForObject
		Product product = getJdbcTemplate().queryForObject(sql, new ProductRowMapper(), pid);
		return product;
	}

	// 向orders表插入数据
	public void addOrders(Order order) throws SQLException {
		String sql = "insert into orders values(?,?,?,?,?,?,?,?)";
		getJdbcTemplate().update(sql, order.getOid(), order.getOrdertime(), order.getTotal(), order.getState(),
				order.getAddress(), order.getName(), order.getTelephone(), order.getUser().getUid());
	}

	// 向orderitem表插入数据
	public void addOrderItem(Order order) throws SQLException {
		String sql = "insert into orderitem values(?,?,?,?,?)";
		List<OrderItem> orderItems = order.getOrderItems();
		for (OrderItem item : orderItems) {
			getJdbcTemplate().update(sql, item.getItemid(), item.getCount(), item.getSubtotal(),
					item.getProduct().getPid(), item.getOrder().getOid());
		}
	}

	@Override
	// 更新订单地址
	public void updateOrderAdrr(Order order) throws SQLException {
		String sql = "update orders set address=?,name=?,telephone=? where oid=?";
		getJdbcTemplate().update(sql, order.getAddress(), order.getName(), order.getTelephone(), order.getOid());
	}
   //更新订单状态
	public void updateOrderState(String r6_Order) throws SQLException {
		String sql = "update orders set state=? where oid=?";
		getJdbcTemplate().update(sql, 1, r6_Order);
	}
    //查询所有的订单
	@Override 
	public List<Order> findOrderListByUid(String uid) throws SQLException {

		String sql = "select * from orders where uid=?";
		return getJdbcTemplate().query(sql, new OrderRowMapper(), uid);
	}
  //根据oid查询
	public List<Map<String, Object>> findAllOrderItemByOid(String oid) throws SQLException {
		String sql = "select i.count,i.subtotal,p.pimage,p.pname,p.shop_price from orderitem i,product p where i.pid=p.pid and i.oid=?";         
		List<Map<String, Object>> mapList = getJdbcTemplate().queryForList(sql, oid);
		return mapList;
	}
  //添加商品
	@Override
	public void addProduct(Product product) throws SQLException {
		String sql = "insert into product values(?,?,?,?,?,?,?,?,?,?)";
		getJdbcTemplate().update(sql, product.getPid(), product.getPname(), product.getMarket_price(),
				product.getShop_price(), product.getPimage(), product.getPdate(), product.getIs_hot(),
				product.getPdesc(), product.getPflag(), product.getCategory().getCid());
	}

	// 查询所有订单
	@Override
	public List<Order> findAllOrder() throws SQLException {
		String sql = "select * from orders";
		return getJdbcTemplate().query(sql, new OrderRowMapper());
	}

	// 查询所有商品
	@Override
	public List<Product> findAllProduct() throws SQLException {
		String sql = "select * from product";
		List<Product> productList = getJdbcTemplate().query(sql, new ProductRowMapper());
		return productList;
	}

	// 根据 pid 删除商品
	@Override
	public void delProductByPid(String pid) throws SQLException {
		String sql = "delete from product where pid=?";
		getJdbcTemplate().update(sql, pid);
	}

}

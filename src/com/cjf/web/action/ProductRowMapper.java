package com.cjf.web.action;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.cjf.entity.Category;
import com.cjf.entity.Product;

public class ProductRowMapper implements RowMapper<Product> {

	@Override
	public Product mapRow(ResultSet rs, int arg1) throws SQLException {
		
		Product product=new Product();
		product.setPid(rs.getString("pid"));
		product.setPname(rs.getString("pname"));
		product.setMarket_price(rs.getDouble("market_price"));
		product.setShop_price(rs.getDouble("shop_price"));
		product.setPimage(rs.getString("pimage"));
		product.setPdate(rs.getDate("pdate"));
		product.setIs_hot(rs.getInt("is_hot"));
		product.setPdesc(rs.getString("pdesc"));
		product.setPflag(rs.getInt("pflag"));
		Category category=new Category();
		category.setCid(rs.getString("cid"));
		product.setCategory(category);
		return product;	
		
	}

}

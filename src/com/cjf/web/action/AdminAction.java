package com.cjf.web.action;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.struts2.ServletActionContext;

import com.cjf.entity.Category;
import com.cjf.entity.Order;
import com.cjf.entity.Product;
import com.cjf.service.ProductService;
import com.cjf.service.Impl.ProductServiceImpl;
import com.cjf.utils.CommonsUtils;
import com.google.gson.Gson;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

public class AdminAction extends ActionSupport  {

	private static final long serialVersionUID = 1L;
	ProductService productService;
	private String pid;
	private String oid;
	// 上传图片
	private File pimage; // 得到上传的文件
	private String pimageFileName; // 得到文件的名称
	
	private String pname;
	private String is_hot;
	private String market_price;
	private String shop_price;
	private String cid;
	private String pdesc;
	
	public String getPname() {
		return pname;
	}

	public void setPname(String pname) {
		this.pname = pname;
	}

	public String getIs_hot() {
		return is_hot;
	}

	public void setIs_hot(String is_hot) {
		this.is_hot = is_hot;
	}

	public String getMarket_price() {
		return market_price;
	}

	public void setMarket_price(String market_price) {
		this.market_price = market_price;
	}

	public String getShop_price() {
		return shop_price;
	}

	public void setShop_price(String shop_price) {
		this.shop_price = shop_price;
	}

	public String getCid() {
		return cid;
	}

	public void setCid(String cid) {
		this.cid = cid;
	}

	public String getPdesc() {
		return pdesc;
	}

	public void setPdesc(String pdesc) {
		this.pdesc = pdesc;
	}

	public ProductService getProductService() {
		return productService;
	}

	public void setProductService(ProductService productService) {
		this.productService = productService;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

	public String getOid() {
		return oid;
	}

	public void setOid(String oid) {
		this.oid = oid;
	}

	public File getPimage() {
		return pimage;
	}

	public void setPimage(File pimage) {
		this.pimage = pimage;
	}

	public String getPimageFileName() {
		return pimageFileName;
	}

	public void setPimageFileName(String pimageFileName) {
		this.pimageFileName = pimageFileName;
	}


	public String delProduct() throws SQLException {
		// 传递pid到service层
		productService.delProductByPid(pid);
		return "toAllproduct";
	}

	// queryAllProduct
	public String queryAllProduct() {
		  Map<String, Object> map = ActionContext.getContext().getContextMap();
		List<Product> productList = null;
		try {
			productList = productService.findAllProduct();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		// 将productList放到request域
		map.put("productList", productList);
		return "plist";
	}

	// findOrderInfoByOid
	public String findOrderInfoByOid() throws SQLException, IOException {
		HttpServletResponse response = ServletActionContext.getResponse();
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		List<Map<String, Object>> orderItems = productService.findAllOrderItemByOid(oid);
		Gson gson = new Gson();
		String json = gson.toJson(orderItems);
		// 设置 编码
		response.setContentType("text/html;charset=UTF-8");
		response.getWriter().write(json);
		return null;
	}

	public String queryAllOrders() throws ServletException, IOException, SQLException {
		Map<String, Object> map = ActionContext.getContext().getContextMap();
		List<Order> orderList = productService.findAllOrder();
		map.put("orderList", orderList);
		return "olist";
	}

	// 异步加载所有分类
	public String findAllCategory() throws ServletException, IOException, SQLException {
		HttpServletResponse response = ServletActionContext.getResponse();
		List<Category> categoryList = productService.findCategoryList();
		Gson gson = new Gson();
		String json = gson.toJson(categoryList);
		response.setContentType("text/html;charset=UTF-8");
		response.getWriter().write(json);
		return null;
	}

	public String adminAddProduct()throws ServletException, IOException, IllegalAccessException, InvocationTargetException, SQLException {

		Map<String, Object> map = new HashMap<String, Object>();
		////目的：收集表单的数据 封装一个Product实体 将上传图片存到服务器磁盘上 
		Product product = new Product();
		
        File file = new File("D:/Tomcat/upload");
        if(!file.exists())file.mkdirs();
        
        try {
            //保存文件
            FileUtils.copyFile(pimage, new File(file,pimageFileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
      //封装表单的数据
		map.put("pname", pname);
		map.put("is_hot", is_hot);
		map.put("market_price", market_price);
		map.put("shop_price", shop_price);
		map.put("cid", cid);
		map.put("pdesc", pdesc);	
	    map.put("pimage", pimageFileName);
		BeanUtils.populate(product, map);
        //对为封装的数据进行封装
		product.setPid(CommonsUtils.getUUID());
		product.setPdate(new Date());
		product.setPflag(0);
		// 封装 product 里的category 对象
		Category category = new Category();
		category.setCid(map.get("cid").toString());
		product.setCategory(category);
		//调用 service 
		productService.addProduct(product);		
		return "toAllproduct";
	}
}

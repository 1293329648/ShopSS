package com.cjf.web.action;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.sql.SQLException;
import java.util.Map;

import javax.mail.MessagingException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.interceptor.CookiesAware;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.apache.struts2.interceptor.SessionAware;

import com.cjf.entity.User;
import com.cjf.service.UserService;
import com.cjf.utils.CommonsUtils;
import com.cjf.utils.MailUtils;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

public class UserAction extends ActionSupport implements ModelDriven<User>,ServletRequestAware,ServletResponseAware, SessionAware{

	private static final long serialVersionUID = 1L;	
	
	private User user=new User();
    
	private UserService userService ;
	
	public void setUserService(UserService userService) {
		this.userService = userService;
	}	
	private String activeCode;
	
	public String getActiveCode() {
		return activeCode;
	}

	public void setActiveCode(String activeCode) {
		this.activeCode = activeCode;
	}	
	private HttpServletResponse response;
	private HttpServletRequest request;
	private Map<String, Object> session;
	private String autoLogin;
	

	public String getAutoLogin() {
		return autoLogin;
	}

	public void setAutoLogin(String autoLogin) {
		this.autoLogin = autoLogin;
	}

	@Override
	public User getModel() {
		return user;
	}
	
	public String logout() {
		Map<String, Object> map = ActionContext.getContext().getSession();
		map.remove("user");
		//用户退出清除cookie
		Cookie cookie_username = new Cookie("cookie_username","");
		Cookie cookie_password = new Cookie("cookie_password","");
		//将path设置成与要删除cookie的path一致
		cookie_username.setPath(request.getContextPath());
		cookie_password.setPath(request.getContextPath());
		//设置时间是0
		cookie_username.setMaxAge(0);	
		cookie_password.setMaxAge(0);
		response.addCookie(cookie_username);
		response.addCookie(cookie_password);	
		return "tologin";
	}			
	//验证用户名是否存在user  使用Ajax
	public String checkUsername() {	
		HttpServletResponse response = ServletActionContext.getResponse();	      
	    boolean isExist =userService.checkUsername(user.getUsername());
	    System.out.println(isExist);
		String json = "{\"isExist\":"+isExist+"}";	
		try {
			response.getWriter().write(json);
		} catch (IOException e) {
			e.printStackTrace();
		}      
		return null;
	}	
	//激活
	public String activeUser() {		
		userService.active(activeCode);		
		return "tologin";			
	}		
	//注册
	public String register() {   
		user.setUid(CommonsUtils.getUUID());		
		user.setTelephone(null);		
		user.setState(0);
		String activeCode = CommonsUtils.getUUID();
		user.setCode(activeCode);		
		boolean isRegisterSuccess = userService.regist(user);		
		//是否注册成功
		if(isRegisterSuccess){
			//发送激活邮件
			String emailMsg = "恭喜您注册成功，请点击下面的连接进行激活账户"
					+ "<a href='http://localhost:8080/ShopSS/UserAction_activeUser.action?activeCode="+activeCode+"'>"
							+ "http://localhost:8080/ShopSS/UserAction_activeUser.action?activeCode="+activeCode+"</a>";
			try {
				MailUtils.sendMail(user.getEmail(), emailMsg);
			} catch (MessagingException e) {
				e.printStackTrace();
			}			
			//跳转到注册成功页面
			return "registerSuccess";
		}else{
			//跳转到失败的提示页面
			return "registerFail";
		}				
	}	
	public String login() {		           		  		
		User userLogin = null;
		try {  
			//获得登陆的用户
			userLogin = userService.getUserByCodePassword(user);
		} catch (SQLException e) {
			e.printStackTrace();
		}			
		if(userLogin!=null){
			//登录成功
			//判断用户是否勾选自动登录
			if(autoLogin!=null){				
				//对中文张三进行编码
				String username_code = null;
				try {
					username_code = URLEncoder.encode(user.getUsername(), "UTF-8");// %AE4%kfj		
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}						
				Cookie cookie_username = new Cookie("cookie_username",username_code);
				Cookie cookie_password = new Cookie("cookie_password",user.getPassword());
				//设置cookie的持久化时间
				cookie_username.setMaxAge(60*60);
				cookie_password.setMaxAge(60*60);
				//设置cookie的携带路径
				cookie_username.setPath(request.getContextPath());
				cookie_password.setPath(request.getContextPath());
				//发送cookie
				response.addCookie(cookie_username);
				response.addCookie(cookie_password);
				System.out.println(cookie_username);
			}		
			//将登录的用户的user对象存到session中
			   session.put("user", userLogin);	
			//重定向到首页
		    return "toHome";			
		}else{
			return "tologin";
		}		
	}

	public void setServletResponse(HttpServletResponse response) {
		this.response = response;
	}
 
	public void setSession(Map<String, Object> session) {
		this.session = session;
	}
 
	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}

}

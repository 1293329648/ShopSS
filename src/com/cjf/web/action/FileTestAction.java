package com.cjf.web.action;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

import com.opensymphony.xwork2.ActionSupport;

public class FileTestAction  extends ActionSupport {

 
	private static final long serialVersionUID = 1L;


	private File pimage; //得到上传的文件
    public File getPimage() {
		return pimage;
	}

	public void setPimage(File pimage) {
		this.pimage = pimage;
	}

	private String pimageFileName; //得到文件的名称
	
	public String getPimageFileName() {
		return pimageFileName;
	}
	public void setPimageFileName(String pimageFileName) {
		this.pimageFileName = pimageFileName;
	}




	public String uploadTest() {	  
	        System.out.println("fileName:"+this.getPimageFileName());
	        System.out.println("File:"+this.getPimage());
	        
	        //获取要保存文件夹的物理路径(绝对路径)
	      //  String realPath=ServletActionContext.getServletContext().getRealPath("/upload");
	        //System.out.println(realPath);
	        File file = new File("D:/Tomcat/upload");
	        
	        //测试此抽象路径名表示的文件或目录是否存在。若不存在，创建此抽象路径名指定的目录，包括所有必需但不存在的父目录。
	        if(!file.exists())file.mkdirs();
	        
	        try {
	            //保存文件
	            FileUtils.copyFile(pimage, new File(file,pimageFileName));
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	        return SUCCESS;
	    }

	
		
	
	
}

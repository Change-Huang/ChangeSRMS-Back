package com.change.changesrmsback.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletResponse;

/**
 * 文件下载封装类
 * @author Change
 *
 */
public class DownloadFile {
	
	/**
	 * 文件下载
	 * @param fileName 下载的文件名，显示在浏览器中
	 * @param suffix 文件后缀，不包含“.”
	 * @param contentType 文件类型
	 * @param filePath 文件在服务器的位置
	 * @param response 响应流
	 */
	public static void download(String fileName, String suffix, String contentType, String filePath, HttpServletResponse response) {
		//文件类型
		response.setContentType(contentType);
		
		//设置文件头，注意中文的内容要进行编码
		try {
			fileName = java.net.URLEncoder.encode(fileName, "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
        response.setHeader("Content-Disposition", 
        		"attachment;fileName="+ fileName + "." + suffix); 
		
        //文件流写出
		try {
			InputStream inputStream = new FileInputStream(new File(filePath));
			byte[] b = new byte[100];
			int len;
			while ((len = inputStream.read(b)) > 0)
				response.getOutputStream().write(b, 0, len);
			inputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

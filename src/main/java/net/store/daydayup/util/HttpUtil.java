/*
 * Copyright © 2020 ctwing
 */
package net.store.daydayup.util;


import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Map;

public class HttpUtil {

	/**
	 *
	 * @param url
	 * @param header
	 * @param paramContent
	 * @return
	 */
	public static StringBuffer submitDelete(String url,final Map<String,String> header,String paramContent){
		StringBuffer responseMessage = null;
		final HttpURLConnection connection;
		java.net.URL reqUrl = null;
		OutputStreamWriter reqOut = null;
		InputStream in = null;
		BufferedReader br = null;
		String param = paramContent;
		try {
			System.out.println("com.ctwing.scep.aep.adapter.util.HttpUtil.submitPost:url="+url+",param="+paramContent);
			responseMessage = new StringBuffer();
			reqUrl = new java.net.URL(url);
			connection = (HttpURLConnection) reqUrl.openConnection();
			connection.setRequestMethod("DELETE");
			//添加头部参数
			if(header!=null&&header.size()>0){
				header.forEach((k,v)->{
					connection.setRequestProperty(k,v);
				});
			}
			connection.setDoOutput(true);
			//设置编码
			reqOut = new OutputStreamWriter(connection.getOutputStream(),"UTF-8");
			reqOut.write(param);
			reqOut.flush();
			int charCount = -1;
			in = connection.getInputStream();

			br = new BufferedReader(new InputStreamReader(in, "UTF-8"));
			while ((charCount = br.read()) != -1) {
				responseMessage.append((char) charCount);
			}
			System.out.println("com.ctwing.scep.aep.adapter.util.HttpUtil.submitPost:response="+responseMessage);
		} catch (Exception ex) {
			System.out.println("com.ctwing.scep.aep.adapter.util.HttpUtil.submitPost:exception");
			ex.printStackTrace();
		} finally {
			try {
				in.close();
				reqOut.close();
			} catch (Exception e) {
				System.out.println("com.ctwing.scep.aep.adapter.util.HttpUtil.submitPost:closeException");
				e.printStackTrace();
			}

		}
		return responseMessage;
	}

	/**
	 * java.net实现 HTTP POST方法提交
	 * 
	 * @param url
	 * @param paramContent
	 * @return
	 */
	public static StringBuffer submitPost(String url, final Map<String,String> header, String paramContent) {
		StringBuffer responseMessage = null;
		final HttpURLConnection connection;
		java.net.URL reqUrl = null;
		OutputStreamWriter reqOut = null;
		InputStream in = null;
		BufferedReader br = null;
		String param = paramContent;
		try {
			System.out.println("com.ctwing.scep.aep.adapter.util.HttpUtil.submitPost:url="+url+",param="+paramContent);
			responseMessage = new StringBuffer();
			reqUrl = new java.net.URL(url);
			connection = (HttpURLConnection) reqUrl.openConnection();
			connection.setRequestMethod("POST");
			//添加头部参数
			if(header!=null&&header.size()>0){
				header.forEach((k,v)->{
					connection.setRequestProperty(k,v);
				});
			}
			connection.setDoOutput(true);
			//设置编码
			reqOut = new OutputStreamWriter(connection.getOutputStream(),"UTF-8");
			reqOut.write(param);
			reqOut.flush();
			int charCount = -1;
			in = connection.getInputStream();

			br = new BufferedReader(new InputStreamReader(in, "UTF-8"));
			while ((charCount = br.read()) != -1) {
				responseMessage.append((char) charCount);
			}
			System.out.println("com.ctwing.scep.aep.adapter.util.HttpUtil.submitPost:response="+responseMessage);
		} catch (Exception ex) {
			System.out.println("com.ctwing.scep.aep.adapter.util.HttpUtil.submitPost:exception");
			ex.printStackTrace();
		} finally {
			try {
				in.close();
				reqOut.close();
			} catch (Exception e) {
				System.out.println("com.ctwing.scep.aep.adapter.util.HttpUtil.submitPost:closeException");
				e.printStackTrace();
			}

		}
		return responseMessage;
	}

	/**
	 * java.net实现 HTTP或HTTPs GET方法提交
	 * 
	 * @param strUrl
	 *            提交的地址及参数
	 * @return 返回的response信息
	 */
	public static String submitGet(String strUrl) {
		URLConnection connection = null;
		BufferedReader reader = null;
		String str = null;
		try {
			System.out.println("com.ctwing.scep.aep.adapter.util.HttpUtil.submitGet:url="+strUrl);
			URL url = new URL(strUrl);
			connection = url.openConnection();
			connection.setDoInput(true);
			connection.setDoOutput(false);
			// 取得输入流，并使用Reader读取
			reader = new BufferedReader(new InputStreamReader(
					connection.getInputStream(),"UTF-8"));
			String lines;
			StringBuffer linebuff = new StringBuffer("");
			while ((lines = reader.readLine()) != null) {
				linebuff.append(lines);
			}
			str = linebuff.toString();
			System.out.println("com.ctwing.scep.aep.adapter.util.HttpUtil.submitGet:url="+str);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(reader!=null)
				    reader.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return str;
	}

}
/**
 * @(#)QQHttpService.java 2013-1-21
 *
 * Copyright (c) 2004-2013 Lakala, Inc.
 * zhongjiang Road, building 22, Lane 879, shanghai, china 
 * All Rights Reserved.
 *
 * This software is the confidential and proprietary information of Lakala, Inc.  
 * You shall not disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license agreement you entered into
 * with Lakala.
 */
package org.okj.im.core.service;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.util.zip.GZIPInputStream;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.okj.commons.logger.LogUtils;
import org.okj.im.core.constants.HttpMethod;

/**
 * QQ的HTTP服务
 * @author Administrator
 * @version $Id: QQHttpService.java, v 0.1 2013-1-21 下午4:13:21 Administrator Exp $
 */
public class QQHttpService {
    /* logger */
    private static final Logger LOGGER = Logger.getLogger(QQHttpService.class);

    /* QQ服务的refer,必须的，否则无法发送请求 */
    private static final String refer  = "http://d.web2.qq.com/proxy.html?v=20110331002&callback=1&id=3";
    
    private String proxyHost;
    private Integer proxyPort;

    /**
     * 发送httpclient消息
     * @param url
     * @param method
     * @param exParam
     * @return
     */
    public String sendHttpMessage(String url, String method, ExParam exParam) {
        if (exParam == null) {
            exParam = new ExParam();
        }
        HttpURLConnection conn = null;
        do {
            conn = connect(url, method, exParam); //创建连接
        } while (!checkResponseCode(conn));

        //处理cookies
        if (conn.getHeaderFields().get("Set-Cookie") != null) {
            StringBuffer cookies = new StringBuffer();
            for (String s : conn.getHeaderFields().get("Set-Cookie")) {
                cookies.append(s);
            }
            exParam.setCookie(cookies.toString());
        }

        if (StringUtils.equalsIgnoreCase("gzip", conn.getHeaderField("Content-Encoding"))) {
            return getGzipResponseString(conn);
        }
        return getResponseString(conn);
    }

    /**
     * 连接HTTP
     * @param url
     * @param method
     * @param exParam
     * @return
     */
    protected HttpURLConnection connect(String url, String method, ExParam exParam) {
        HttpURLConnection conn = null;
        try {
            URL serverUrl = new URL(url);
            if(proxyHost != null && proxyPort != null && !"".equals(proxyHost)){
            	Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(proxyHost, proxyPort));
     			conn = (HttpURLConnection)serverUrl.openConnection(proxy);
            }else{
            	conn = (HttpURLConnection) serverUrl.openConnection();
            }
            conn.setConnectTimeout(60000);
            conn.setReadTimeout(100000);
            System.setProperty("sun.net.client.defaultConnectTimeout", "60000");
            System.setProperty("sun.net.client.defaultReadTimeout", "100000");
            conn.setRequestMethod(method);// "POST" ,"GET"
            conn.addRequestProperty("Referer", refer);
            conn.addRequestProperty("Cookie", exParam.getCookie());
            conn.addRequestProperty("Connection", "Keep-Alive");
            conn.addRequestProperty("Accept-Language", "zh-cn");
            conn.addRequestProperty("Accept-Encoding", "gzip, deflate");
            conn.addRequestProperty("Cache-Control", "no-cache");
            conn.addRequestProperty("Accept-Charset", "UTF-8;");
            conn.addRequestProperty("Host", "d.web2.qq.com");
            conn.addRequestProperty(
                "User-Agent",
                "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/535.19 (KHTML, like Gecko) Ubuntu/12.04 Chromium/18.0.1025.168 Chrome/18.0.1025.168 Safari/535.19");
            if (StringUtils.equalsIgnoreCase(method, HttpMethod.GET)) {
                conn.setDoInput(true);
                conn.connect();
            } else if (StringUtils.equalsIgnoreCase(method, HttpMethod.POST)) {
                conn.setDoInput(true);
                conn.setDoOutput(true);
                if (StringUtils.isNotBlank(exParam.getFilename())) {
                    conn.setRequestProperty("Content-Type", "multipart/form-data");
                }
                conn.connect();
                if (StringUtils.isNotBlank(exParam.getContents())) {
                    conn.getOutputStream().write(exParam.getContents().getBytes());
                }
                //上传文件
                if (StringUtils.isNotBlank(exParam.getFilename())) {
                    uploadFile(exParam.getFilename(), conn.getOutputStream());
                    LogUtils.info(LOGGER, "上传文件完成, filename={0}", exParam.getFilename());
                }
            } else {
                LogUtils.warn(LOGGER, "your method is not implement");
                //throw new RuntimeException("your method is not implement");
            }
        } catch (Exception ex) {
            LogUtils.error(LOGGER, "创建httpclient连接时发生异常", ex);
        }
        return conn;
    }

    /**
     * 上传文件
     * @param filename
     * @param os
     */
    protected void uploadFile(String filename, OutputStream os) {
        FileInputStream is = null;
        try {
            if (StringUtils.isNotBlank(filename)) {
                is = FileUtils.openInputStream(new File(filename)); //从文件读取输入流

                // POST
                // Read bytes until EOF to write
                byte[] buffer = new byte[4096];
                int bytes_read; // How many bytes in buffer
                while ((bytes_read = is.read(buffer)) != -1) {
                    os.write(buffer, 0, bytes_read);
                }
                os.flush(); //刷新缓冲区
            }
        } catch (IOException ex) {
            LogUtils.error(LOGGER, "上传文件时发生异常", ex);
        } finally {
            //关闭流
            IOUtils.closeQuietly(is);
            IOUtils.closeQuietly(os);
        }
    }

    /**
     * 检查返回码
     * @param conn
     * @return
     */
    protected boolean checkResponseCode(final HttpURLConnection conn) {
        boolean success = false;

        //如果连接不存在，直接返回false;
        if (conn == null) {
            return false;
        }

        try {
            InputStream is = conn.getInputStream();
            if (is == null) {
                return false;
            }

            InputStream isrs = conn.getErrorStream();
            if (isrs != null) {
                return false;
            }

            int status = conn.getResponseCode();
            switch (status) {
                case java.net.HttpURLConnection.HTTP_GATEWAY_TIMEOUT://504
                    LogUtils.warn(LOGGER, "网络超时! status：{0}", status);
                    break;
                case java.net.HttpURLConnection.HTTP_FORBIDDEN://403
                    LogUtils.warn(LOGGER, "网络禁止访问! status：{0}", status);
                    break;
                case java.net.HttpURLConnection.HTTP_INTERNAL_ERROR://500
                    LogUtils.warn(LOGGER, "连接错误，无法连接WebQQ或不存在! status：{0}", status);
                    break;
                case java.net.HttpURLConnection.HTTP_NOT_FOUND://404
                    LogUtils.warn(LOGGER, "连接地址不存在! status：{0}", status);
                    break;
                case java.net.HttpURLConnection.HTTP_OK:
                    LogUtils.warn(LOGGER, "Connect OK! status：{0}", status);
                    success = true;
            }
        } catch (IOException ex) {
            LogUtils.error(LOGGER, "检查返回码时发生异常", ex);
        }
        return success;
    }

    /**
     * 返回gzip压缩的响应字符串
     * @param conn
     * @return
     */
    protected String getGzipResponseString(HttpURLConnection conn) {
        InputStream ins = null;
        GZIPInputStream gzip = null;
        ByteArrayOutputStream baos = null;
        try {
            ins = conn.getInputStream();
            gzip = new GZIPInputStream(ins);
            baos = new ByteArrayOutputStream();
            byte[] buf = new byte[1024 * 8];
            int num = -1;
            while ((num = gzip.read(buf, 0, buf.length)) != -1) {
                baos.write(buf, 0, num);
            }
            byte[] b = baos.toByteArray();
            baos.flush();
            return new String(b).trim();
        } catch (IOException ex) {
            LogUtils.error(LOGGER, "接收返回时发生异常[gzip]", ex);
        } finally {
            IOUtils.closeQuietly(gzip);
            IOUtils.closeQuietly(ins);
            IOUtils.closeQuietly(baos);
        }
        return null;
    }

    /**
     * 返回请求的响应字符串
     * @param conn
     * @return
     */
    protected String getResponseString(HttpURLConnection conn) {
        InputStream ins = null;
        BufferedReader br = null;
        try {
            ins = conn.getInputStream();
            br = new BufferedReader(new InputStreamReader(ins, "UTF-8"));
            StringBuffer sb = new StringBuffer();
            String line = null;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            return sb.toString();
        } catch (IOException ex) {
            LogUtils.error(LOGGER, "接收返回时发生异常", ex);
        } finally {
            IOUtils.closeQuietly(ins);
            IOUtils.closeQuietly(br);
        }
        return null;
    }

	public String getProxyHost() {
		return proxyHost;
	}

	public void setProxyHost(String proxyHost) {
		this.proxyHost = proxyHost;
	}

	public Integer getProxyPort() {
		return proxyPort;
	}

	public void setProxyPort(Integer proxyPort) {
		this.proxyPort = proxyPort;
	}
    
    
}

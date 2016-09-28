package com.xuanru.servlet;

import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 
 * desc:自定义调度servlet，设置编码格式
 * <p>
 * 创建人：Liaoxf 创建日期：2015-12-10
 * </p>
 * 
 * @version V1.0
 */
public class EncodingDispatcherServlet extends DispatcherServlet {

	/**
	 * long:serialVersionUID
	 */
	private static final long serialVersionUID = -619476256687963353L;

	private String encoding;

	public void init(ServletConfig config) throws ServletException {
		encoding = config.getInitParameter("encoding");
		super.init(config);
	}

	protected void doService(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		request.setCharacterEncoding(encoding);
		super.doService(request, response);
	}
}

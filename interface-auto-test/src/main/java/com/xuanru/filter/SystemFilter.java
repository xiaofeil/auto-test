package com.xuanru.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/*创建Filter 必须实现javax.servlet.Filter 接口，在该接口中定义了三个方法。 
 • void init(FilterConfig config): 用于完成Filter 的初始化。 
 • void destroy(): 用于Filter 销毁前，完成某些资源的回收。 
 • void doFilter(ServletRequest request, ServletResponse response,FilterChain chain): 实现过滤功能，该方法就是对每个请求及响应增加的额外处理。 
 过滤器Filter也具有生命周期：init()->doFilter()->destroy()，由部署文件中的filter元素驱动。
 */
public class SystemFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(SystemFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        // 请求的uri
        String url = request.getRequestURI();
        logger.info("request url=[{}]", new Object[]{url});
        if (!"/interface-auto-test/".equals(url) && url.indexOf("login") == -1
                && url.indexOf("/css/") == -1 && url.indexOf("/js/") == -1
                && url.indexOf("/image/") == -1 && url.indexOf("favicon") != -1) {
//			if (StringUtil.isIntegerBlank(user_id)) {
//				response.sendRedirect("/cdrf-meeting/login");
//				return;
//			}

        }
        filterChain.doFilter(request, response);
    }
}

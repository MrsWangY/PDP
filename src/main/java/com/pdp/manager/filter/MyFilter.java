package com.pdp.manager.filter;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;
import com.pdp.manager.common.IStatusMessage;
import com.pdp.manager.common.utils.JwtUtil;

import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author LIXr
 */
@Slf4j
public class MyFilter  implements  Filter{

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest)servletRequest;
        HttpServletResponse response = (HttpServletResponse)servletResponse;
        
        //如果是login就直接放行
        String url = request.getRequestURL().toString();
        int index = url.lastIndexOf("/");
//        boolean isApiLogin ="/getToken".equals(url.substring(index));
        if(("/getToken".equals(url.substring(index))) 
//    		|| ("/verifyRecipel".equals(url.substring(index)))
    		|| ("/loginCA".equals(url.substring(index)))
        	|| ("/login".equals(url.substring(index)))){
            filterChain.doFilter(request,response);
            return ;
        }
        String xToken = request.getHeader("xToken");
        boolean verify = JwtUtil.verify(xToken);
        if(verify){
            filterChain.doFilter(request,response);
        }else{
            log.info("请求：{},notoken!",url);
//            request.getRequestDispatcher("/error/noToken").forward(request,response);
            
            JSONObject result = new JSONObject();
    	    result.put("msgCode", IStatusMessage.SystemStatus.NO_TOKEN.getCode());
    	    result.put("message", IStatusMessage.SystemStatus.NO_TOKEN.getMessage());
            PrintWriter out = null;
    		try {
    			out = response.getWriter();
    			out.write(result.toJSONString());
    			out.flush();
    		} catch (IOException e) {
    			e.printStackTrace();
    		}

        }
    }

}

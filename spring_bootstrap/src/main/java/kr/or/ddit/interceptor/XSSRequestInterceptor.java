package kr.or.ddit.interceptor;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.josephoconnell.html.HTMLInputFilter;

public class XSSRequestInterceptor extends HandlerInterceptorAdapter {

	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
	
			Enumeration<String> crossParamNames = request.getParameterNames();
			
			
			while(crossParamNames.hasMoreElements()) {
				String paramName = crossParamNames.nextElement();
				String paramValue = request.getParameter(paramName);
				
				request.setAttribute("XSS"+paramName, HTMLInputFilter.htmlSpecialChars(paramValue));
			}
			
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub

	}

}

package com.technokryon.ecommerce;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.technokryon.ecommerce.pojo.User;
import com.technokryon.ecommerce.service.UserService;

@Component
public class Interceptor implements HandlerInterceptor {

	@Autowired
	private UserService O_UserService;

	@Override
	public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
			Object handler) throws Exception {

		String apikey = (httpServletRequest.getHeader("X-Auth-Token"));

		System.out.println("apikey" + apikey);

		if (apikey == null) {

			httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

			return false;
		}

		User O_User = O_UserService.getUserDetailAPIKey(apikey);

		if (O_User == null) {

			httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

			return false;
		}

		httpServletRequest.setAttribute("uId", O_User.getUId());
		httpServletRequest.setAttribute("usApiKey", apikey);

		return true;
	}
}

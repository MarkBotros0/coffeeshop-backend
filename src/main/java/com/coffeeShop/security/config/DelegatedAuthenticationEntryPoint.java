//package com.coffeeShop.security.config;
//
//import java.io.IOException;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.web.AuthenticationEntryPoint;
//import org.springframework.stereotype.Component;
//import org.springframework.web.servlet.HandlerExceptionResolver;
//
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//
//@Component("delegatedAuthenticationEntryPoint")
//public class DelegatedAuthenticationEntryPoint implements AuthenticationEntryPoint {
//
//	@Autowired
//	@Qualifier("handlerExceptionResolver")
//	private HandlerExceptionResolver resolver;
//
//	@Override
//	public void commence(HttpServletRequest request, HttpServletResponse response,
//			AuthenticationException authException) throws IOException, ServletException {
//		String exceptionMessage = authException.getMessage();
//
////		response.setContentType("application/json");
////
////		response.getWriter().write("{\"message\": \"" + exceptionMessage + "\"}");
//
//		resolver.resolveException(request, response, null, authException);
//
//	}
//}
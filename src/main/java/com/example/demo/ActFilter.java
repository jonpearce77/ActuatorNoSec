package com.example.demo;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(1)
public class ActFilter implements Filter {
	
	@Value("${monitoring.host}")
	private String monitoringHost;

	@Override
	public void doFilter(ServletRequest request,
			ServletResponse response,
			FilterChain chain) throws IOException, ServletException 
	{
		System.out.println(request.getLocalAddr());
		System.out.println(request.getLocalName());

		final HttpServletRequest req = (HttpServletRequest) request;
		System.out.println(req.getRequestURL());
		
		// Only allow access to actuator end points if the request originates from a specific host.
		if(req.getRequestURI().contains("metrics"))
		{
			if(req.getLocalName()==null
					|| monitoringHost==null
					|| monitoringHost.isEmpty()
					|| !req.getLocalName().contains(monitoringHost))
			{
				final HttpServletResponse resp = (HttpServletResponse) response;
				resp.reset();
				resp.sendError(HttpServletResponse.SC_FORBIDDEN, "");

				return;
			}
		}
		
		// Continue to process the request and send to EPS controller
		chain.doFilter(request, response);	
	}

}

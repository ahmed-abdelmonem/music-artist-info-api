package com.aabdelmonem.artist.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * Request Interceptor to log HTTP requests and responses.
 * 
 * @author ahmed.abdelmonem
 * 
 */
@Component
public class ArtistRequestInterceptor extends HandlerInterceptorAdapter {

	private static final Logger logger = LoggerFactory.getLogger(ArtistRequestInterceptor.class);

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
			Exception ex) {
		StringBuilder logMessage = new StringBuilder();
		logMessage.append("method: ").append(request.getMethod()).append("\t");
		logMessage.append("uri: ").append(request.getRequestURI()).append("\t");
		logMessage.append("status: ").append(response.getStatus()).append("\t");
		logMessage.append("remoteAddress: ").append(request.getRemoteAddr()).append("\t");

		if (ex != null) {
			logger.error(logMessage.toString(), ex);
		} else {
			logger.info(logMessage.toString());
		}
	}

}

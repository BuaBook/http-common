package com.buabook.http.common.jersey;

import java.io.IOException;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.UriInfo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <h3>URL Logging Filter for Jersey</h3>
 * <p>This filter can be added to a Jersey instance via a <code>ResourceConfig</code> and
 * will print the following when a URL is hit by a client:</p>
 * <ul>
 *  <li>INFO: HTTP method, full URL path, content-length (if applicable) and content-type (if applicable)</li>
 *  <li>DEBUG: Query parameters</li>
 *  <li>TRACE: Request headers</li>
 * </ul>
 * <br/><br/>(c) 2017 Sport Trades Ltd
 * 
 * @author Jas Rajasansir
 * @version 1.0.0
 * @since 13 Feb 2016
 */
public class UrlPrinterFilter implements ContainerRequestFilter {
	private static final Logger log = LoggerFactory.getLogger(UrlPrinterFilter.class);
	
	
	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {
		UriInfo info = requestContext.getUriInfo();
		
		StringBuilder requestStr = new StringBuilder()
										.append("HTTP ")
										.append(requestContext.getMethod())
										.append(": ")
										.append(info.getAbsolutePath());
		
		if(requestContext.getLength() != -1)
			requestStr
				.append(" [ Content-Length: ")
				.append(requestContext.getLength())
				.append(" ]");
		
		if(requestContext.getMediaType() != null)
			requestStr
				.append(" [ Content-Type: ")
				.append(requestContext.getMediaType())
				.append(" ]");
		
		log.info(requestStr.toString());
			
		if(! info.getQueryParameters().isEmpty())
			log.debug("Query Params: " + info.getQueryParameters());
		
		if(! requestContext.getHeaders().isEmpty())
			log.trace("Headers: " + requestContext.getHeaders());
	}

}

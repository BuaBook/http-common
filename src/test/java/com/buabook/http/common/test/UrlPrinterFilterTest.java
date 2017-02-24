package com.buabook.http.common.test;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedHashMap;
import javax.ws.rs.core.UriInfo;

import org.junit.Before;
import org.junit.Test;

import com.buabook.http.common.jersey.UrlPrinterFilter;

public class UrlPrinterFilterTest {
	
	private ContainerRequestContext crc;

	@Before
	public void initialise() throws URISyntaxException {
		UriInfo uriInfo = mock(UriInfo.class);
		when(uriInfo.getAbsolutePath()).thenReturn(new URI("http://test.url.com/with/path"));
		when(uriInfo.getQueryParameters()).thenReturn(testQueryParams());
		
		this.crc = mock(ContainerRequestContext.class);
		when(crc.getUriInfo()).thenReturn(uriInfo);
		when(crc.getMediaType()).thenReturn(MediaType.APPLICATION_JSON_TYPE);
		when(crc.getHeaders()).thenReturn(testHeaders());
	}

	
	@Test
	public void testFilterLogsWithNoError() throws IOException {
		new UrlPrinterFilter().filter(crc);
	}
	
	
	private MultivaluedHashMap<String, String> testQueryParams() {
		MultivaluedHashMap<String, String> params = new MultivaluedHashMap<>();
		params.add("param1", "value1");
		params.add("param2", "value2");
		
		return params;
	}
	
	private MultivaluedHashMap<String, String> testHeaders() {
		MultivaluedHashMap<String, String> headers = new MultivaluedHashMap<>();
		headers.add("header1", "value1");
		headers.add("header2", "value2");
		
		return headers;
	}
}

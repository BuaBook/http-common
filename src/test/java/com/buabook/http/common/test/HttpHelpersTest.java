package com.buabook.http.common.test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.buabook.http.common.HttpHelpers;
import com.google.common.collect.ImmutableMap;

public class HttpHelpersTest {

	// HttpHelpers.appendUrlParameters
	
	@Test(expected=IllegalArgumentException.class)
	public void testAppendUrlParametersThrowsExceptionIfNullUrl() {
		HttpHelpers.appendUrlParameters(null, new HashMap<String, Object>());
	}
	
	@Test
	public void testAppendUrlParametersReturnsUrlIfNoMapOrEmptyMap() {
		String url = "http://test.url.com";
		
		assertThat(HttpHelpers.appendUrlParameters(url, null), is(equalTo(url)));
		assertThat(HttpHelpers.appendUrlParameters(url, new HashMap<String, Object>()), is(equalTo(url)));
	}
	
	@Test
	public void testAppendUrlParametersAppendsWithBaseUrlWithExistingParameters() {
		String url = "http://test.url.com?parameter=bob";
		Map<String, Object> params = ImmutableMap.<String, Object>builder()
													.put("testParam", true)
													.put("testParam2", "test")
													.build();
		
		assertThat(HttpHelpers.appendUrlParameters(url, params), is(equalTo(url + "&testParam=true&testParam2=test")));
	}
	
	@Test
	public void testAppendUrlParametersAppendsWithBaseUrlWithNoExistingParameters() {
		String url = "http://test.url.com";
		Map<String, Object> params = ImmutableMap.<String, Object>builder()
				.put("testParam", true)
				.put("testParam2", 123)
				.build();
		
		assertThat(HttpHelpers.appendUrlParameters(url, params), is(equalTo(url + "?testParam=true&testParam2=123")));
	}

	@Test
	public void testAppendUrlParametersEscapesParameterValues() {
		String url = "http://test.url.com";
		Map<String, Object> params = ImmutableMap.<String, Object>builder()
				.put("spaceParam", "param with space")
				.build();
		
		assertThat(HttpHelpers.appendUrlParameters(url, params), is(equalTo(url + "?spaceParam=param+with+space")));
	}
	
	@Test
	public void testAppendUrlParametersEscapesParameterKeys() {
		String url = "http://test.url.com";
		Map<String, Object> params = ImmutableMap.<String, Object>builder()
				.put("space param", "value")
				.build();
		
		assertThat(HttpHelpers.appendUrlParameters(url, params), is(equalTo(url + "?space+param=value")));
	}
}

package com.buabook.http.common;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;
import java.util.Map.Entry;

import com.google.common.base.Strings;

public final class HttpHelpers {

	/**
	 * Generates a URL with HTTP GET parameters from the specified URL and parameters map
	 * @param url Base URL to add parameters to. It is permitted to include other GET parameters.
	 * @param parameters {@link Object#toString()} will be called on each object value in the map
	 */
	public static String appendUrlParameters(String url, Map<String, Object> parameters) {
		if(Strings.isNullOrEmpty(url))
			throw new IllegalArgumentException("Base URL cannot be null or empty");
		
		if(parameters == null || parameters.isEmpty())
			return url;
		
		StringBuilder urlWithParams = new StringBuilder(url);
		
		if(url.contains("?"))
			urlWithParams.append("&");
		else
			urlWithParams.append("?");
			
		for(Entry<String, Object> parameter : parameters.entrySet())
			try {
				String urlParamKey = URLEncoder.encode(parameter.getKey(), "UTF-8");
				String urlParamVal = URLEncoder.encode(parameter.getValue().toString(), "UTF-8");
				
				urlWithParams.append(urlParamKey + "=" + urlParamVal + "&");
			} catch (UnsupportedEncodingException e) {}
		
		if(urlWithParams.charAt(urlWithParams.length() - 1) == '&')
			urlWithParams.deleteCharAt(urlWithParams.length() - 1);
		
		return urlWithParams.toString();
	}
	
}

package com.buabook.http.common.auth;

import java.io.IOException;

import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.common.base.Strings;

public class HttpBearerAuthenticationInitializer implements HttpRequestInitializer {

	private final String bearerAuthToken;
	
	public HttpBearerAuthenticationInitializer(String bearerAuthToken) throws IllegalArgumentException {
		if(Strings.isNullOrEmpty(bearerAuthToken))
			throw new IllegalArgumentException("Bearer authorisation token cannot be null or empty");
		
		this.bearerAuthToken = bearerAuthToken;
	}
	
	@Override
	public void initialize(HttpRequest request) throws IOException {
		request.getHeaders().setAuthorization("Bearer " + bearerAuthToken);
	}

}

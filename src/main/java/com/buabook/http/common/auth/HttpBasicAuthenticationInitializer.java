package com.buabook.http.common.auth;

import java.io.IOException;

import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.common.base.Strings;

public class HttpBasicAuthenticationInitializer implements HttpRequestInitializer {

	private final String username;
	
	private final String password;
	
	public HttpBasicAuthenticationInitializer(String username, String password) throws IllegalArgumentException {
		if(Strings.isNullOrEmpty(username) || Strings.isNullOrEmpty(password))
			throw new IllegalArgumentException("No username or password specified");
		
		this.username = username;
		this.password = password;
	}

	@Override
	public void initialize(HttpRequest request) throws IOException {
		request.getHeaders().setBasicAuthentication(username, password);
	}

}

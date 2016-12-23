package com.buabook.http.common.auth;

import javax.xml.bind.DatatypeConverter;

import com.google.common.base.Strings;

public class HttpBasicAuthorisationHeader {
	
	private final String username;
	
	private final String password;

	private HttpBasicAuthorisationHeader(String username, String password) {
		this.username = username;
		this.password = password;
	}
	
	
	public String getUsername() {
		return username;
	}
	
	public String getPassword() {
		return password;
	}
	
	/** Note that this function will only print the length of the password supplied, not the actual password. */
	@Override
	public String toString() {
		return "HttpBasicAuthenticationHeader [username=" + username + ", password=(Length) " + password.length() + "]";
	}
	
	/** @return Similar to {@link #toString()} but also <i>includes</i> the password in plain-text. */
	public String toStringWithPassword() {
		return "HttpBasicAuthenticationHeader [username=" + username + ", password=" + password + "]";
	}
	
	
	public static HttpBasicAuthorisationHeader fromAuthorisationHeader(String headerString) throws IllegalArgumentException, UnsupportedOperationException {
		if(Strings.isNullOrEmpty(headerString))
			throw new IllegalArgumentException("Cannot generate authentication object from null or empty string");
		
		String[] headerSplit = headerString.split(" ");
		
		if(headerSplit == null || headerSplit.length != 2)
			throw new IllegalArgumentException("Unsupported authorisation header");
		
		if(! "BASIC".equals(headerSplit[0].toUpperCase()))
			throw new UnsupportedOperationException("Only Basic authorisation supported");
		
		byte[] decoded = DatatypeConverter.parseBase64Binary(headerSplit[1]);
		
		if(decoded == null || decoded.length == 0)
			throw new UnsupportedOperationException("Base64 decoded of authorisation header failed");
		
		String[] usernameAndPassword = new String(decoded).split(":", 2);
		
		if(usernameAndPassword == null || usernameAndPassword.length != 2)
			throw new UnsupportedOperationException("Username and password not correctly defined in authorisation header");
		
		return new HttpBasicAuthorisationHeader(usernameAndPassword[0], usernameAndPassword[1]);
	}

}

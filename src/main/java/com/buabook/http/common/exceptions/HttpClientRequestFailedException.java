package com.buabook.http.common.exceptions;

import com.buabook.http.common.HttpClient;

/**
 * <h3>General Exception for {@link HttpClient}</h3>
 * <br/><br/>(c) 2016 Sport Trades Ltd
 * 
 * @author Jas Rajasansir
 * @version 1.0.0
 * @since 22 Dec 2016
 */
public class HttpClientRequestFailedException extends Exception {
	private static final long serialVersionUID = 4137841376976036502L;
	
	private static final String message = "The HTTP client request failed.";
	

	public HttpClientRequestFailedException() {
		super(message);
	}
	
	public HttpClientRequestFailedException(String message) {
		super(HttpClientRequestFailedException.message + " " + message);
	}
	
	public HttpClientRequestFailedException(Throwable cause) {
		super(cause);
	}
	
	public HttpClientRequestFailedException(String message, Throwable cause) {
		super(HttpClientRequestFailedException.message + " " + message, cause);
	}
	
	public HttpClientRequestFailedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(HttpClientRequestFailedException.message + " " + message, cause, enableSuppression, writableStackTrace);
	}

}

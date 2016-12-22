package com.buabook.http.common.response.pojos;

/**
 * <h3>BuaBook <i>Failure</i> Response Object</h3>
 * <p>This class provides the a response object when a request fails and there <i>is</i> content to return. 
 * It will default to returning a HTTP 500 Internal Server Error but any status can be provided.</p>
 * (c) 2016 Sport Trades Ltd
 * 
 * @author Jas Rajasansir
 * @version 1.0.0
 * @since 28 Nov 2016
 */
public class FailureResponse extends EmptyFailureResponse {

	private final Object response;
	
	public FailureResponse(Object response) {
		this.response = response;
	}
	
	public Object getResponse() {
		return response;
	}

}

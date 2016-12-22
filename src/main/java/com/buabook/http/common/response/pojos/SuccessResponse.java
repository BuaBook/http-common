package com.buabook.http.common.response.pojos;

/**
 * <h3>BuaBook <i>Success</i> Response Object</h3>
 * <p>This class provides the a response object when a request succeeds and there <i>is</i> content to return. 
 * It will always return as a HTTP 200 status code.</p>
 * (c) 2016 Sport Trades Ltd
 * 
 * @author Jas Rajasansir
 * @version 1.0.0
 * @since 28 Nov 2016
 */
public class SuccessResponse extends EmptySuccessResponse {

	private final Object response;
	
	public SuccessResponse(Object response) {
		this.response = response;
	}

	public Object getResponse() {
		return response;
	}
	
}

package com.buabook.http.common.response.pojos;

import javax.ws.rs.core.Response;

/**
 * <h3>BuaBook Empty Response Object</h3>
 * <p>This class provides the minimal response object that should be returned by any BuaBook Web API. Note
 * that this class is abstract as it requires converting into a {@link Response}.</p>
 * (c) 2016 Sport Trades Ltd
 * 
 * @author Jas Rajasansir
 * @version 1.0.0
 * @since 28 Nov 2016
 */
public abstract class EmptyResponse {

	final Boolean success;

	
	public EmptyResponse(Boolean success) {
		this.success = success;
	}
	
	public Boolean getSuccess() {
		return success;
	}

	/** @return The current object as a Response to be returned to the client */
	public abstract Response asResponse();
	
}

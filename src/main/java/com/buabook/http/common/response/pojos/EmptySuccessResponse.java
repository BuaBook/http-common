package com.buabook.http.common.response.pojos;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

/**
 * <h3>BuaBook Empty <i>Success</i> Response Object</h3>
 * <p>This class provides the a response object when a request succeeds and there is no content to return. 
 * It will always return as a HTTP 200 status code.</p>
 * (c) 2016 Sport Trades Ltd
 * 
 * @author Jas Rajasansir
 * @version 1.0.0
 * @since 28 Nov 2016
 */
public class EmptySuccessResponse extends EmptyResponse {

	public EmptySuccessResponse() {
		super(true);
	}

	@Override
	public Response asResponse() {
		return Response
					.status(Status.OK)
					.entity(this)
					.build();
	}
}

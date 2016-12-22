package com.buabook.http.common.response.pojos;

import java.util.List;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.Response.Status.Family;

import com.google.common.collect.ImmutableList;

/**
 * <h3>BuaBook Empty <i>Failure</i> Response Object</h3>
 * <p>This class provides the a response object when a request fails and there is no content to return. 
 * It will default to returning a HTTP 500 Internal Server Error but any status can be provided.</p>
 * (c) 2016 Sport Trades Ltd
 * 
 * @author Jas Rajasansir
 * @version 1.0.0
 * @since 28 Nov 2016
 */
public class EmptyFailureResponse extends EmptyResponse {
	private static final List<Family> ERROR_STATUS_FAMILIES = ImmutableList.<Family>builder()
																						.add(Family.CLIENT_ERROR)
																						.add(Family.SERVER_ERROR)
																						.build();
	

	public EmptyFailureResponse() {
		super(false);
	}

	@Override
	public Response asResponse() {
		return asResponse(Status.INTERNAL_SERVER_ERROR);
	}
	
	/** @throws IllegalArgumentException If the status supplied is not in the 4xx or 5xx range */
	public Response asResponse(Status httpErrorStatus) throws IllegalArgumentException {
		if(httpErrorStatus == null)
			httpErrorStatus = Status.INTERNAL_SERVER_ERROR;
		
		if(! ERROR_STATUS_FAMILIES.contains(httpErrorStatus.getFamily()))
			throw new IllegalArgumentException("Cannot build error response without an error status");
		
		return Response
					.status(httpErrorStatus)
					.entity(this)
					.build();
	}
}

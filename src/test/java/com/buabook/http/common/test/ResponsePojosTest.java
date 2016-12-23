package com.buabook.http.common.test;

import javax.ws.rs.core.Response.Status;

import org.junit.Test;

import com.buabook.http.common.response.pojos.EmptyFailureResponse;
import com.buabook.http.common.response.pojos.EmptySuccessResponse;
import com.buabook.http.common.response.pojos.FailureResponse;
import com.buabook.http.common.response.pojos.SuccessResponse;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class ResponsePojosTest {

	
	// EmptyFailureResponse
	
	@Test(expected=IllegalArgumentException.class)
	public void testEmptyFailureResponseThrowsExceptionIfNonErrorStatusProvided() {
		new EmptyFailureResponse().asResponse(Status.OK);
	}
	
	@Test
	public void testEmptyFailureResponseReturnsStatus500WithNoArgument() {
		assertThat(new EmptyFailureResponse().asResponse().getStatus(), is(equalTo(500)));
	}
	
	public void testEmptyFailureResponseReturnsStatus500WithNullArgument() {
		assertThat(new EmptyFailureResponse().asResponse(null).getStatus(), is(equalTo(500)));
	}
	
	@Test
	public void testEmptyFailureResponseReturnsSpecifiedStatus() {
		assertThat(new EmptyFailureResponse().asResponse(Status.FORBIDDEN).getStatus(), is(equalTo(403)));
	}
	
	
	// EmptySuccessResponse
	
	@Test
	public void testEmptyFailureResponseSetsSuccessToFalse() {
		assertThat(new EmptyFailureResponse().getSuccess(), is(equalTo(false)));
	}
	
	@Test
	public void testEmptySuccessResponseSetsSuccessToTrue() {
		assertThat(new EmptySuccessResponse().getSuccess(), is(equalTo(true)));
	}
	
	@Test
	public void testEmptySuccessResposneReturnsStatus200() {
		assertThat(new EmptySuccessResponse().asResponse().getStatus(), is(equalTo(200)));
	}

	
	// FailureResponse
	
	@Test
	public void testFailureResponseContainsResponseField() {
		FailureResponse response = new FailureResponse("string");
		assertThat(response.getResponse(), is(equalTo("string")));
	}
	
	
	// SuccessResponse
	
	@Test
	public void testSuccessResponseContainsResponseField() {
		SuccessResponse response = new SuccessResponse("string");
		assertThat(response.getResponse(), is(equalTo("string")));
	}
	
}

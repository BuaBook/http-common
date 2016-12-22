package com.buabook.http.common.test;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Test;

import com.buabook.http.common.oauth.JwtIdTokenDecoder;
import com.google.common.collect.Lists;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.Matchers.*;

import java.util.List;

public class JwtIdTokenDecoderTest {

	private String idToken = "eyJ0eXAiOiJKV1QiLCJhbGciOiJub25lIn0.eyJhdWQiOiI3NzM4M2YzOC1iMzk1LTRhYjEtYmJkOS1kOGQ0MGE3ZTg5NjMiLCJpc3MiOiJodHRwczovL3N0cy53aW5kb3dzLm5ldC8yNTI1YTg2Zi1mZmM2LTQwYjQtOTFlYy02NzVmNjcwNDZjMDEvIiwiaWF0IjoxNDY3OTg4Njc0LCJuYmYiOjE0Njc5ODg2NzQsImV4cCI6MTQ2Nzk5MjU3NCwiYW1yIjpbInB3ZCIsIm1mYSJdLCJmYW1pbHlfbmFtZSI6IlJhamFzYW5zaXIiLCJnaXZlbl9uYW1lIjoiSmFza2lyYXQiLCJpcGFkZHIiOiI5Mi4yMDcuMTc4LjE5OSIsIm5hbWUiOiJKYXNraXJhdCBSYWphc2Fuc2lyIiwib2lkIjoiOWJhYzlhZGEtZDBlMC00MjRhLWEwZWQtZDY2ODRlZjhjNzEwIiwicm9sZXMiOlsiY3AtYWRtaW4iXSwic3ViIjoiS2x1dHRXLXNxNi14TGg1X1BPSTlJZjBNZDJ1b3g0aFFSd213aW9WQzZOZyIsInRpZCI6IjI1MjVhODZmLWZmYzYtNDBiNC05MWVjLTY3NWY2NzA0NmMwMSIsInVuaXF1ZV9uYW1lIjoiamFzQGJ1YWJvb2suY29tIiwidXBuIjoiamFzQGJ1YWJvb2suY29tIiwidmVyIjoiMS4wIn0.";
	
	private String badIdToken = "eyJ0eXAiOiJKV1QiLCJhbGciOiJzb21ldGhpbmcifQ==.."; 

	
	@Test
	public void testDecodeIdTokenSuccessfullyDecodesExample() {
		JSONObject decodedToken = JwtIdTokenDecoder.decodeIdToken(idToken);
		
		List<String> tokenFieldNames = Lists.newArrayList(JSONObject.getNames(decodedToken));
		
		assertThat(tokenFieldNames, hasSize(greaterThan(0)));
		
		assertThat(tokenFieldNames, hasItem("oid"));
		assertThat(decodedToken.get("oid"), is(instanceOf(String.class)));
		
		assertThat(tokenFieldNames, hasItem("unique_name"));
		assertThat(decodedToken.get("unique_name"), is(instanceOf(String.class)));
		
		assertThat(tokenFieldNames, hasItem("roles"));
		assertThat(decodedToken.get("roles"), is(instanceOf(JSONArray.class)));
	}
	
	@Test(expected=UnsupportedOperationException.class)
	public void testDecodeTokenIdThrowsExceptionIfInvalidAlg() {
		JwtIdTokenDecoder.decodeIdToken(badIdToken);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testDecodeTokenIdThrowsExceptionIfNoTokenSupplied() {
		JwtIdTokenDecoder.decodeIdToken(null);
	}
	
	@Test(expected=UnsupportedOperationException.class)
	public void testDecodeTokenIdThrowsExceptionIfBadlyFormattedToken() {
		JwtIdTokenDecoder.decodeIdToken("abc.adc");
	}
}

package com.buabook.http.common.test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;

import org.junit.Test;

import com.buabook.http.common.auth.HttpBasicAuthorisationHeader;

public class HttpBasicAuthorisationHeaderTest {

	// testUsername:testPassword
	private static final String GOOD_BASE_64_AUTH_TEST = "Basic dGVzdFVzZXJuYW1lOnRlc3RQYXNzd29yZA==";
	
	// notValidForAuthHeader
	private static final String BAD_BASE_64_AUTH_TEST = "Basic bm90VmFsaWRGb3JBdXRoSGVhZGVy";

	
	// HttpBasicAuthorisationHeader.fromAuthorisationHeader
	
	@Test(expected=IllegalArgumentException.class)
	public void testFromAuthorisationHeaderThrowsExceptionIfNullArgument() {
		HttpBasicAuthorisationHeader.fromAuthorisationHeader(null);
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testFromAuthorisationHeaderThrowsExceptionIfEmptyString() {
		HttpBasicAuthorisationHeader.fromAuthorisationHeader(" ");
	}
	
	@Test(expected=IllegalArgumentException.class)
	public void testFromAuthorisationHeaderThrowsExceptionIfStringDoesNotSplitInto2() {
		HttpBasicAuthorisationHeader.fromAuthorisationHeader("test");
	}
	
	@Test(expected=UnsupportedOperationException.class)
	public void testFromAuthorisationHeaderThrowsExceptionIfStringIsNotBasicAuthorisationString() {
		HttpBasicAuthorisationHeader.fromAuthorisationHeader("NotBasic 123456");
	}
	
	@Test(expected=UnsupportedOperationException.class)
	public void testFromAuthorisationHeaderThrowsExceptionIfDecodedStringIsNotUsernameColonPassword() {
		HttpBasicAuthorisationHeader.fromAuthorisationHeader(BAD_BASE_64_AUTH_TEST);
	}
	
	@Test
	public void testFromAuthoristaionHeaderReturnsHeaderWithCorrectUsernameAndPassword() {
		HttpBasicAuthorisationHeader header = HttpBasicAuthorisationHeader.fromAuthorisationHeader(GOOD_BASE_64_AUTH_TEST);
		
		assertThat(header, is(not(nullValue())));
		assertThat(header.getUsername(), is(equalTo("testUsername")));
		assertThat(header.getPassword(), is(equalTo("testPassword")));
	}
	
	
	// HttpBasicAuthorisationHeader.toString
	
	@Test
	public void testToStringDoesNotIncludePlainTextPassword() {
		HttpBasicAuthorisationHeader header = HttpBasicAuthorisationHeader.fromAuthorisationHeader(GOOD_BASE_64_AUTH_TEST);
		
		assertThat(header, is(not(nullValue())));
		assertThat(header.toString(), not(containsString("testPassword")));
	}
	
	// HttpBasicAuthorisationHeader.toStringWithPassword
	
	@Test
	public void testToStringWithPasswordIncludesPlainTextPassword() {
		HttpBasicAuthorisationHeader header = HttpBasicAuthorisationHeader.fromAuthorisationHeader(GOOD_BASE_64_AUTH_TEST);
		
		assertThat(header, is(not(nullValue())));
		assertThat(header.toStringWithPassword(), containsString("testPassword"));
	}
}

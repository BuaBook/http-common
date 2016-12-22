package com.buabook.http.common.test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.ws.rs.WebApplicationException;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import com.buabook.http.common.jersey.JsonMessageBodyWriter;

public class JsonMessageBodyWriterTest {

	private JsonMessageBodyWriter jsonWriter;
	
	@Before
	public void initialise() {
		this.jsonWriter = new JsonMessageBodyWriter();
	}
	
	// JsonMessageBodyWriter.isWriteable
	
	@Test
	public void testIsWriteableReturnsTrueForJsonObject() {
		assertThat(jsonWriter.isWriteable(JSONObject.class, Object.class, null, null), is(equalTo(true)));
	}
	
	@Test
	public void testIsWriteableReturnsFalseForOtherObjects() {
		assertThat(jsonWriter.isWriteable(JSONArray.class, Object.class, null, null), is(equalTo(false)));
	}
	
	// JsonMessageBodyWriter.getSize
	
	@Test
	public void testGetSizeAlwaysReturnsMinusOne() {
		assertThat(jsonWriter.getSize(null, JSONObject.class, Object.class, null, null), is(equalTo(-1l)));
		assertThat(jsonWriter.getSize(new JSONObject(), JSONObject.class, Object.class, null, null), is(equalTo(-1l)));
	}
	
	// JsonMessageBodyWriter.writeTo
	
	@Test
	public void testWriteToReturnsEmptyJsonStringIfNullJsonObject() throws WebApplicationException, IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		
		jsonWriter.writeTo(null, JSONObject.class, Object.class, null, null, null, baos);
		
		assertThat(baos.toString(), is(equalTo("{}")));
	}
	
	@Test
	public void testWriteToReturnsJsonSerialisationOfJsonObject() throws WebApplicationException, IOException {
		JSONObject objToTest = new JSONObject()
									.put("key1", false)
									.put("key2", 23423)
									.put("key3", "value3");
		
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		
		jsonWriter.writeTo(objToTest, JSONObject.class, Object.class, null, null, null, baos);
		
		assertThat(baos.toString(), is(equalTo(objToTest.toString())));
	}
}

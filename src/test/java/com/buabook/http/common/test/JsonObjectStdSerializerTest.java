package com.buabook.http.common.test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import com.buabook.http.common.jackson.ObjectMapperWithJsonObjectSupport;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonObjectStdSerializerTest {

	private ObjectMapper objectMapper;
	
	
	@Before
	public void initObjectMapper() {
		this.objectMapper = ObjectMapperWithJsonObjectSupport.newMapper();
	}
	

	@Test
	public void testJsonObjectSerializerReturnsNullStringOnNullObject() throws JsonProcessingException {
		assertThat(objectMapper.writeValueAsString(null), is(equalTo("null")));
	}
	
	@Test
	public void testJsonObjectSerializerReturnsEmptyCurlyBracketsOnEmptyObject() throws JsonProcessingException {
		assertThat(objectMapper.writeValueAsString(new JSONObject()), is(equalTo("{}")));
	}
	
	@Test
	public void testJsonObjectSerializerReturnsCorrectForComplexJson() throws JsonProcessingException {
		JSONObject complexJson = new JSONObject()
											.put("val1", true)
											.put("val2", "a string")
											.put("val3", new JSONObject().put("nested", 1.2))
											.put("val4", new JSONArray().put(1));
		
		assertThat(objectMapper.writeValueAsString(complexJson), is(equalTo(complexJson.toString())));
	}

}

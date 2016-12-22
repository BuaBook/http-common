package com.buabook.http.common.jackson;

import org.json.JSONObject;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * <h3>Custom Jackson {@link ObjectMapper} with {@link JSONObject} Serialisation Support</h3>
 * <br/><br/>(c) 2016 Sport Trades Ltd
 * 
 * @author Jas Rajasansir
 * @version 1.0.0
 * @since 28 Nov 2016
 */
public final class ObjectMapperWithJsonObjectSupport {

	private static ObjectMapper mapper;
	
	/** 
	 * <b>NOTE</b>: This method will only instantiate one {@link ObjectMapper}. Multiple calls to this method
	 * will return the same object. 
	 * @return New Jackson object mapper with support to serialise {@link JSONObject}s.
	 */
	public static ObjectMapper newMapper() {
		if(mapper == null) {
			mapper = new ObjectMapper();
			mapper.registerModule(new JsonObjectModule());
		}
		
		return mapper;
	}
}

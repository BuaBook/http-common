package com.buabook.http.common.jackson;

import java.io.IOException;

import org.json.JSONObject;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

/**
 * <h3>Jackson Serializer for {@link JSONObject}</h3>
 * <br/><br/>(c) 2016 Sport Trades Ltd
 * 
 * @author Jas Rajasansir
 * @version 1.0.0
 * @since 28 Nov 2016
 */
public class JsonObjectStdSerializer extends StdSerializer<JSONObject> {
	private static final long serialVersionUID = -1386506229445191527L;
	

	public JsonObjectStdSerializer() {
		super(JSONObject.class);
	}
	
	
	@Override
	public void serialize(JSONObject value, JsonGenerator gen, SerializerProvider provider) throws IOException {		
		gen.writeRawValue(value.toString());
	}

}

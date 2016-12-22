package com.buabook.http.common.jackson;

import org.json.JSONObject;

import com.fasterxml.jackson.core.util.VersionUtil;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.module.SimpleModule;

/**
 * <h3>Jackson {@link Module} for {@link JSONObject} Serialisation</h3>
 * <br/><br/>(c) 2016 Sport Trades Ltd
 * 
 * @author Jas Rajasansir
 * @version 1.0.0
 * @since 28 Nov 2016
 */
public class JsonObjectModule extends SimpleModule {
	private static final long serialVersionUID = 6700709268706395145L;
	
	
	private static final String NAME = "org.json.JSONObject Module";
	
	private static final VersionUtil VERSION_UTIL = new VersionUtil() {};
	

	public JsonObjectModule() {
		super(NAME, VERSION_UTIL.version());
		addSerializer(new JsonObjectStdSerializer());
	}

}

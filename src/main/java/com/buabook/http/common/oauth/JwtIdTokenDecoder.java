package com.buabook.http.common.oauth;

import java.util.List;

import org.json.JSONObject;

import com.google.common.base.Splitter;
import com.google.common.base.Strings;
import com.google.common.io.BaseEncoding;

/**
 * <h3>JWT ID Token Decoder</h3>
 * (c) 2016 Sport Trades Ltd
 * 
 * @author Jas Rajasansir
 * @version 1.0.0
 * @since 8 Jul 2016
 */
public final class JwtIdTokenDecoder {

	/**
	 * Converts an encoded JWT token into a JSON object. <b>NOTE</b>: This method only supports un-encrypted tokens.
	 * @throws IllegalArgumentException If the token string provided is <code>null</code> or empty.
	 * @throws UnsupportedOperationException If the token is not formatted correctly or is encrypted
	 */
	public static JSONObject decodeIdToken(String encodedToken) throws IllegalArgumentException, UnsupportedOperationException {
		if(Strings.isNullOrEmpty(encodedToken))
			throw new IllegalArgumentException("No ID token specified");
		
		List<String> tokenElements = Splitter.on('.').splitToList(encodedToken);
		
		if(tokenElements.size() != 3)
			throw new UnsupportedOperationException("ID token specified does not contain required parts to decode");
		
		String tokenTypeStr = new String(BaseEncoding.base64().decode(tokenElements.get(0)));
		JSONObject tokenType = new JSONObject(tokenTypeStr);
		
		if(! "none".equals(tokenType.getString("alg")))
			throw new UnsupportedOperationException("Function can only decode unencrypted JWT tokens. Supplied token: " + tokenType);
		
		byte[] decoded = BaseEncoding.base64().decode(tokenElements.get(1));
		
		return new JSONObject(new String(decoded));
	}

	private JwtIdTokenDecoder() {} 
}

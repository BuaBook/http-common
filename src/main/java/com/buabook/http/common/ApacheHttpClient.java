package com.buabook.http.common;

import com.buabook.http.common.auth.HttpBearerAuthenticationInitializer;
import com.google.api.client.http.apache.ApacheHttpTransport;

/**
 * <h3>{@link ApacheHttpTransport}-based {@link HttpClient}</h3>
 * <p>Provides a {@link HttpClient} class using Apache's HTTP connection libraries,
 * rather than Java's native ones.</p>
 * <p>This class exists because OneDrive's API doesn't like the standard HTTP connection 
 * interfaces and does like Apache's. Available if there's another API that shows the
 * same behaviour.</p>
 * (c) 2016 Sport Trades Ltd
 * 
 * @author Jas Rajasansir
 * @version 1.0.0
 * @since 6 Jul 2016
 */
public class ApacheHttpClient extends HttpClient {

	public ApacheHttpClient() {
		super(new ApacheHttpTransport().createRequestFactory());
	}
	
	public ApacheHttpClient(String bearerAuthToken) {
		super(new ApacheHttpTransport().createRequestFactory(new HttpBearerAuthenticationInitializer(bearerAuthToken)));
	}

}

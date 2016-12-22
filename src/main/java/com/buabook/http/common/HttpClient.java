package com.buabook.http.common;

import java.io.IOException;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.buabook.http.common.auth.HttpBasicAuthenticationInitializer;
import com.buabook.http.common.auth.HttpBearerAuthenticationInitializer;
import com.buabook.http.common.exceptions.HttpClientRequestFailedException;
import com.google.api.client.http.ByteArrayContent;
import com.google.api.client.http.EmptyContent;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpContent;
import com.google.api.client.http.HttpHeaders;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.HttpResponseException;
import com.google.api.client.http.UrlEncodedContent;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.common.base.Strings;
import com.google.common.net.MediaType;

/**
 * <h3>HTTP Client Access Library</h3>
 * (c) 2015 Sport Trades Ltd
 * 
 * @author Jas Rajasansir
 * @version 1.0.0
 * @since 13 Oct 2015
 */
public class HttpClient {
	private static final Logger log = LoggerFactory.getLogger(HttpClient.class);


	private final HttpRequestFactory requestFactory;
	
	
	public HttpClient() {
		this.requestFactory = new NetHttpTransport().createRequestFactory();
	}
	
	protected HttpClient(HttpRequestFactory customRequestFactory) {
		this.requestFactory = customRequestFactory;
	}
	
	/** 
	 * HTTP client instantiation with Basic Authentication configured. See <a href="https://en.wikipedia.org/wiki/Basic_access_authentication#Client_side">
	 * https://en.wikipedia.org/wiki/Basic_access_authentication#Client_side</a>.
	 * @see HttpBasicAuthenticationInitializer
	 */
	public HttpClient(String username, String password) {
		this.requestFactory = new NetHttpTransport().createRequestFactory(new HttpBasicAuthenticationInitializer(username, password));
	}
	
	/**
	 * HTTP client instantiation with OAuth 2.0 (<code>Bearer</code>) authentication.
	 * @see HttpBearerAuthenticationInitializer
	 */
	public HttpClient(String bearerAuthToken) {
		this.requestFactory = new NetHttpTransport().createRequestFactory(new HttpBearerAuthenticationInitializer(bearerAuthToken));
	}

	
	/**
	 * <p>Standard HTTP GET request to the specified URL.</p>
	 * <p><b>NOTE</b>: You must call {@link HttpResponse#disconnect()} after using the
	 * response. {@link #getResponseAsString(HttpResponse)} and {@link #getResponseAsJson(HttpResponse)} will do this for you when
	 * used.</p>
	 * @param url The full URL to query. {@link HttpHelpers#appendUrlParameters(String, Map)} is useful to append query parameters to
	 * the URL before passing into this function.
	 * @throws HttpClientRequestFailedException If the GET returns any HTTP error code or the request fails due to an {@link IOException}
	 * @see #getResponseAsString(HttpResponse)
	 * @see #getResponseAsJson(HttpResponse)
	 */
	public HttpResponse doGet(String url) throws HttpClientRequestFailedException {
		if(Strings.isNullOrEmpty(url))
			throw new IllegalArgumentException("No URL specified");
		
		GenericUrl target = new GenericUrl(url);
		
		HttpResponse response = null;
		
		try {
			HttpRequest request = requestFactory.buildGetRequest(target);
			response = request.execute();
		} catch(HttpResponseException e) {
			log.error("HTTP client GET failed due to bad HTTP status code [ Status Code: " + e.getStatusCode() + " ]");
			throw new HttpClientRequestFailedException(e);
		} catch (IOException e) {
			log.error("HTTP client GET failed [ URL: " + url + " ]. Error - " + e.getMessage(), e);
			throw new HttpClientRequestFailedException(e);
		}
		
		return response;
	}
	
	/**
	 * <p>Standard HTTP POST equivalent to <code>&lt;form&gt;</code> or Content-Type <code>application/x-www-form-urlencoded</code>.</p>
	 * <p><b>NOTE</b>: You must call {@link HttpResponse#disconnect()} after using the
	 * response. {@link #getResponseAsString(HttpResponse)} and {@link #getResponseAsJson(HttpResponse)} will do this for you when
	 * used.</p>
	 * @param url
	 * @param map
	 * @return
	 * @throws HttpClientRequestFailedException If the POST returns any HTTP error code or the request fails due to an {@link IOException}
	 * @see #doPost(String, HttpContent, HttpHeaders)
	 * @see #getResponseAsString(HttpResponse)
	 * @see #getResponseAsJson(HttpResponse)
	 */
	public HttpResponse doPost(String url, Map<String, Object> map) throws HttpClientRequestFailedException {
		UrlEncodedContent content = new UrlEncodedContent(map);
		return doPost(url, content, null);
	}
	
	/**
	 * <p><b>NOTE</b>: You must call {@link HttpResponse#disconnect()} after using the
	 * response. {@link #getResponseAsString(HttpResponse)} and {@link #getResponseAsJson(HttpResponse)} will do this for you when
	 * used.</p>
	 * @param contentType A standard HTTP Content-Type (e.g. "application/json"). If this is <code>null</code> or empty string,
	 * "text/plain" will be used
	 * @param postContent The content to POST. Pass <code>null</code> or empty string to send no content
	 * @param headers Any custom headers that need to be set. Pass <code>null</code> if not required
	 * @throws HttpClientRequestFailedException If the GET returns any HTTP error code or the request fails due to an {@link IOException}
	 * @see #doPost(String, HttpContent, HttpHeaders)
	 * @see #getResponseAsString(HttpResponse)
	 * @see #getResponseAsJson(HttpResponse)
	 */
	public HttpResponse doPost(String url, MediaType contentType, String postContent, HttpHeaders headers) throws HttpClientRequestFailedException {
		HttpContent content = null;
		
		if(contentType == null)
			contentType = MediaType.PLAIN_TEXT_UTF_8;
		
		if(Strings.isNullOrEmpty(postContent))
			content = new EmptyContent();
		else
			content = ByteArrayContent.fromString(contentType.toString(), postContent);
		
		return doPost(url, content, headers);
	}
	
	/**
	 * <p>Performs a HTTP POST based on the specified content and URL. If you are unsure what content to pass, look at 
	 * the other <code>doPost</code> methods.</p>
	 * <p><b>NOTE</b>: You must call {@link HttpResponse#disconnect()} after using the
	 * response. {@link #getResponseAsString(HttpResponse)} and {@link #getResponseAsJson(HttpResponse)} will do this for you when
	 * used.</p>
	 * @return The response from the server
	 * @throws IllegalArgumentException If either of the parameters are <code>null</code>
	 * @throws HttpClientRequestFailedException If the request fails due to underlying network errors or a bad HTTP server status (e.g. 404, 500)
	 * @see #getResponseAsString(HttpResponse)
	 * @see #getResponseAsJson(HttpResponse)
	 */
	public HttpResponse doPost(String url, HttpContent content, HttpHeaders headers) throws IllegalArgumentException, HttpClientRequestFailedException {
		if(Strings.isNullOrEmpty(url))
			throw new IllegalArgumentException("No URL specified");
		
		if(content == null)
			throw new IllegalArgumentException("Content cannot be null");
		
		GenericUrl target = new GenericUrl(url);
		
		HttpResponse response = null;
		
		try {
			HttpRequest request = requestFactory.buildPostRequest(target, content);
			
			if(headers != null)
				request.setHeaders(headers);
			
			response = request.execute();
		} catch (HttpResponseException e) { 
			log.error("HTTP client POST failed due to bad HTTP status code [ Status Code: " + e.getStatusCode() + " ]");
			throw new HttpClientRequestFailedException(e);
		} catch (IOException e) {
			log.error("HTTP client POST failed [ URL: " + url + " ]. Error - " + e.getMessage(), e);
			throw new HttpClientRequestFailedException(e);
		}
		
		return response;
	}
	
	public HttpResponse doPut(String url, HttpContent content) throws IllegalArgumentException, HttpClientRequestFailedException {
		if(Strings.isNullOrEmpty(url))
			throw new IllegalArgumentException("No URL specified");
		
		if(content == null)
			throw new IllegalArgumentException("Content cannot be null");
		
		GenericUrl target = new GenericUrl(url);
		
		HttpResponse response = null;
		
		try {
			HttpRequest request = requestFactory.buildPutRequest(target, content);
			response = request.execute();
		} catch (HttpResponseException e) {
			log.error("HTTP client POST failed due to bad HTTP status code [ Status Code: " + e.getStatusCode() + " ]");
			throw new HttpClientRequestFailedException(e);
		} catch (IOException e) {
			log.error("HTTP client POST failed [ URL: " + url + " ]. Error - " + e.getMessage(), e);
			throw new HttpClientRequestFailedException(e);
		}
		
		return response;
	}
	
	/**
	 * <b>NOTE</b>: After reading the response, {@link HttpResponse#disconnect()} will be called as required
	 * by the documentation.
	 * @param response
	 * @return The body of the response as a string
	 */
	public static String getResponseAsString(HttpResponse response) {
		if(response == null)
			return "";

		String responseString = "";
		
		try {
			responseString = response.parseAsString();
		} catch (IOException e) {
			log.error("Failed to convert HTTP response to string. Error - " + e.getMessage(), e);
		} finally {
			try {
				response.disconnect();
			} catch (IOException e) {}
		}
		
		return responseString;
	}
	
	/**
	 * @param response
	 * @return The body of the response as a JSON object 
	 * @throws JSONException If the string response does not parse into valid JSON
	 * @see #getResponseAsString(HttpResponse)
	 */
	public static JSONObject getResponseAsJson(HttpResponse response) throws JSONException {
		return new JSONObject(getResponseAsString(response));
	}
	
}

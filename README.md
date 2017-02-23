# HTTP Access and JSON Serialisation Library

This library provides the following features:

* HTTP Interface
    * HTTP GET / POST / PUT (building on the Google HTTP Client library)
    * Support for **Basic** and **Bearer** authorisation schemes
    * Deserialisation to `org.json.JSONObject`

* Jersey Server
    * Serialisation support for `org.json.JSONObject`
    * URL information logging
    * Constraint validation error logging

* Jackson
    * Serialisation support for `org.json.JSONObject`

* OAuth
    * Decoder for unencrypted JWT ID tokens

* Standard JSON Response POJOs
    * Provide standardised JSON responses for all BuaBook APIs

Note that this library marks the Jersey and Jackson dependencies for the features above as `provided` only. Therefore you need to include the dependencies in your application for them to work.

[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.buabook/http-common/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.buabook/http-common)
[![Build Status](https://travis-ci.org/BuaBook/http-common.svg?branch=master)](https://travis-ci.org/BuaBook/http-common)
[![Coverage Status](https://coveralls.io/repos/github/BuaBook/http-common/badge.svg?branch=master)](https://coveralls.io/github/BuaBook/http-common?branch=master)

## HTTP Access

`HttpClient` and `HttpHelpers` provides the ability to perform HTTP requests building on Google's HTTP client.

### `HttpClient`

Provides GET, POST and PUT functionality (see JavaDoc for full details).

Basic and Bearer authentication are supported:

* Basic authentication with `new HttpClient(String, String)`
* Bearer authentication with `new HttpClient(String)`

HTTP responses can be converted to String (`HttpClient.getResponseAsString`) or JSON (`HttpClient.getResponseAsJson`).

A single `HttpClient` can be instantiated for the entire application as a new request is generated each time `doGet`, `doPost` or `doPut` are called.

### `ApacheHttpClient`

This behaves the same as `HttpClient` but uses Apache's HTTP connection libraries instead of the standard `java.net` libraries.

### `HttpHelpers`

The function `HttpHelpers.appendUrlParameters(String, Map<String, Object>)` will take the map of parameters and append them to the end of the specified URL in query parameter form (e.g. ?param1=value). Useful for HTTP GET API calls.

## Jersey Server Features

### Message Body Writer for `org.json.JSONObject`

`JsonMessageBodyWriter` can be registered in a Jersey `ResourceConfig` to support serialisation of `org.json.JSONObject` when returned to the caller:

```java
public class WebServiceResourceConfig extends ResourceConfig {

    public WebServiceResourceConfig() {
        register(new JsonMessageBodyWriter());
    }
}
```

### URL Logger

The `UrlPrinterFilter` class provides logging when any URL within the Jersey application is queried. This can be registered in a Jersey `ResourceConfig` and logs the following information:

* `INFO`: HTTP method, full URL path, content-length and content-type
* `DEBUG`: Query parameters
* `TRACE`: Request headers

Example log output:

```
2017.02.23 15:42:03.597 +0000 INFO  [qtp1005849716-31] com.buabook.http.common.jersey.UrlPrinterFilter : HTTP HEAD: http://127.0.0.1:8090/api/status
```

### Constraint Exception Logger

`ConstraintViolationExceptionConverter.asValidationErrors` provides a way to log any validation errors that occur whilst parsing an inbound API request parameters. 

This can be used with a Jersey `ExceptionMapper` for `ConstraintViolationException`:

```java
public class ParameterConstraintViolationExceptionMapper implements ExceptionMapper<ConstraintViolationException> {
	
	private static final Logger log = LoggerFactory.getLogger(ParameterConstraintViolationExceptionMapper.class);
	
	@Override
	public Response toResponse(ConstraintViolationException e) {
		JSONObject errorResponse = new JSONObject()
						    .put("validationErrors", ConstraintViolationExceptionConverter.asValidationErrors(e));
		
		return Response
			.status(Status.BAD_REQUEST)
			.entity(errorResponse)
			.build();
	}

}
```

## Jackson Features

`ObjectMapperWithJsonObjectSupport` provides a standard Jackson `ObjectMapper` with the added `org.json.JSONObject` serialisation.

This can then be configured to used by Jackson via a Jersey `ResourceConfig`:

```java
public class WebServiceResourceConfig extends ResourceConfig {

    public WebServiceResourceConfig() {
        JacksonJaxbJsonProvider jacksonProvider = new JacksonJaxbJsonProvider();
    	jacksonProvider.setMapper(ObjectMapperWithJsonObjectSupport.newMapper());
    	
    	register(jacksonProvider);
    }
}
```

## OAuth

`JwtIdTokenDecoder.decodeIdToken` will decode an *unencrypted* JWT ID token and return it in JSON form. We use this to decode ID tokens from Microsoft's Azure Active Directory.

## Response POJOs

These POJOs are used to return a standardised JSON interface to clients. They ensure that the JSON returned to clients always take the following form:

```javascript
{
    "success": Boolean,
    "response": Object
}
```
# HTTP Access and JSON Serialisation Library

This library provides the following features:

* HTTP Interface
    * HTTP GET / POST / PUT (building on the Google HTTP Client library)
    * Support for **Basic** and **Bearer** authorisation schemes
    * Deserialisation to `org.json.JSONObject`

* Jersey Server
    * Serialisation support for `org.json.JSONObject`
    * Constraint validation error logging

* Jackson
    * Serialisation support for `org.json.JSONObject`

* OAuth
    * Decoder for unencrypted JWT ID tokens

* Standard JSON Response POJOs
    * Provide standardised JSON responses for all BuaBook APIs


## HTTP Access

--

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

--

## Response POJOs

--
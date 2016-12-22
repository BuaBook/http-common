package com.buabook.http.common.jersey;

import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.glassfish.jersey.server.validation.ValidationError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;

/**
 * <h3>{@link ConstraintViolationException} to {@link ValidationError} Converter</h3>
 * <br/><br/>(c) 2016 Sport Trades Ltd
 * 
 * @author Jas Rajasansir
 * @version 1.0.0
 * @since 22 Dec 2016
 */
public class ConstraintViolationExceptionConverter {
	private static final Logger log = LoggerFactory.getLogger(ConstraintViolationExceptionConverter.class);
	

	public static List<ValidationError> asValidationErrors(ConstraintViolationException e) {
		log.warn("Jersey API input argument validation failed");
		
		List<ValidationError> validationErrors = Lists.newArrayList();
		
		if(e == null || e.getConstraintViolations().isEmpty())
			return validationErrors;
		
		Set<ConstraintViolation<?>> violations = e.getConstraintViolations();

		log.warn("Constraint violation exception detected [ Violations: " + violations.size() + " ]");
		
		for(ConstraintViolation<?> violation : violations) {
			String propertyPath = (violation.getPropertyPath() == null) ? "" : violation.getPropertyPath().toString();
			String invalidValue = (violation.getInvalidValue() == null) ? "" : violation.getInvalidValue().toString();
			
			log.debug(" > Validation failure: " + propertyPath + " | " + violation.getMessage());
			
			validationErrors.add(
				new ValidationError(violation.getMessage(), violation.getMessageTemplate(), propertyPath, invalidValue)
			);
		}
		
		return validationErrors;
	}

}

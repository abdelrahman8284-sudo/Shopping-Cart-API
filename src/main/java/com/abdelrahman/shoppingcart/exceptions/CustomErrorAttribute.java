package com.abdelrahman.shoppingcart.exceptions;

import java.util.Arrays;
import java.util.Map;

import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.DefaultErrorAttributes;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.WebRequest;

@Component
public class CustomErrorAttribute extends DefaultErrorAttributes {

	@Override
	public Map<String, Object> getErrorAttributes(WebRequest webRequest, ErrorAttributeOptions options) {
		// map عشان بشكل افتراضي يجيب القيم ال default
		Map<String,Object> attributes = super.getErrorAttributes(webRequest, options);
		String message = (String) attributes.get("message");
		Object status = attributes.get("status");
		
		attributes.put("exception", attributes.get("error"));
		attributes.put("statusCode", status);
		attributes.put("details", Arrays.asList(message));
		attributes.put("message", message);
		attributes.put("locale", attributes.get(webRequest.getLocale().toString()));
		attributes.put("success", Boolean.FALSE);
		
		attributes.remove("error");
	//	attributes.remove("trace");
		
		return attributes;		
	}
}

package com.mustbusk.backend.app.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

public class FrontEndException extends RuntimeException
{
	private final Logger logger = LoggerFactory.getLogger(FrontEndException.class);

	private HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;

	public FrontEndException(Exception e, HttpStatus httpStatus, String message)
	{
		super(message);
		if (e != null)
		{
			logger.error(e.getMessage(), e);
		}
		this.httpStatus = httpStatus;
	}

	public FrontEndException(HttpStatus httpStatus, String message)
	{
		super(message);
		this.httpStatus = httpStatus;
	}

	public HttpStatus getHttpStatus()
	{
		return httpStatus;
	}
}

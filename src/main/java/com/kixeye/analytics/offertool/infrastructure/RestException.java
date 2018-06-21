package com.kixeye.analytics.offertool.infrastructure;

import org.springframework.http.HttpStatus;

import java.util.HashMap;
import java.util.Map;

public class RestException extends RuntimeException
{
    private HttpStatus httpStatusCode = HttpStatus.BAD_REQUEST;
    private Map<String,String> headers = new HashMap<>();

    public RestException(HttpStatus httpStatus, String message)
    {
        super(message);
        this.httpStatusCode = httpStatus;
    }

    public RestException(Exception e)
    {
        super(e);
        this.httpStatusCode = HttpStatus.INTERNAL_SERVER_ERROR;
    }

    public RestException(String message)
    {
        super(message);
    }


    public HttpStatus getHttpStatusCode()
    {
        return httpStatusCode;
    }

    public Map<String, String> getHeaders()
    {
        return headers;
    }

    public void setHeaders(Map<String, String> headers)
    {
        if (headers != null)
        {
            this.headers = headers;
        }
    }

    public void addHeader(String header, String value)
    {
        this.headers.put(header, value);
    }
}

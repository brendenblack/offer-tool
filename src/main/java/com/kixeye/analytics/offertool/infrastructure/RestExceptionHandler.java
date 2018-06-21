package com.kixeye.analytics.offertool.infrastructure;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler
{
    private static final Logger log = LoggerFactory.getLogger(RestExceptionHandler.class);

    @ExceptionHandler(value = { IllegalArgumentException.class, IllegalStateException.class })
    protected ResponseEntity<Object> handleConflict(RuntimeException ex, WebRequest request) throws JsonProcessingException
    {

        log.error("An exception occurred", ex);
        ObjectMapper mapper = new ObjectMapper();
        Map<String,String> map = new HashMap<>();
        map.put("timestamp", new Date().toString());
        map.put("message", ex.getMessage());
        map.put("path", request.getContextPath());
        String body = mapper.writeValueAsString(map);
        return handleExceptionInternal(ex, body, new HttpHeaders(), HttpStatus.CONFLICT, request);
    }

    @ExceptionHandler(value = RestException.class)
    protected ResponseEntity handleConflict(RestException e, WebRequest request) throws JsonProcessingException
    {
        HttpStatus status = (e.getHttpStatusCode() == null) ? HttpStatus.BAD_REQUEST : e.getHttpStatusCode();
        HttpHeaders headers = new HttpHeaders();
        for (String key : e.getHeaders().keySet())
        {
            headers.add(key, e.getHeaders().get(key));
        }
        ObjectMapper mapper = new ObjectMapper();
        Map<String,String> map = new HashMap<>();
        map.put("message", e.getMessage());
        map.put("timestamp", new Date().toString());
        map.put("path", request.getContextPath());
        String body = mapper.writeValueAsString(map);
        return handleExceptionInternal(e, body, headers, status, request);
    }
}

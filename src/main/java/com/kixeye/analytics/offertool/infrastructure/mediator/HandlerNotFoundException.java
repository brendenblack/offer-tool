package com.kixeye.analytics.offertool.infrastructure.mediator;

public class HandlerNotFoundException extends RuntimeException
{
    public HandlerNotFoundException(String message)
    {
        super(message);
    }

}
package com.kixeye.analytics.offertool.infrastructure.mediator;

public interface RequestHandler<IRequest, TResponse>
{
    TResponse handle(IRequest message);

    Class getRequestType();

    Class getReturnType();
}
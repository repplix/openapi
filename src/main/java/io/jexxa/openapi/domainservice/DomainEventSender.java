package io.jexxa.openapi.domainservice;


import io.jexxa.addend.applicationcore.InfrastructureService;
import io.jexxa.openapi.domain.book.BookSoldOut;

@InfrastructureService
public interface DomainEventSender
{
    void publish(BookSoldOut domainEvent);
}

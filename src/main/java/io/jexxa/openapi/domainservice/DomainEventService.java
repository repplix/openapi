package io.jexxa.openapi.domainservice;


import io.jexxa.addend.applicationcore.DomainEventHandler;
import io.jexxa.addend.applicationcore.DomainService;
import io.jexxa.openapi.domain.DomainEventPublisher;
import io.jexxa.openapi.domain.book.BookSoldOut;

@DomainService
public class DomainEventService
{
    private final DomainEventSender domainEventSender;
    public DomainEventService(DomainEventSender domainEventSender)
    {
        this.domainEventSender = domainEventSender;
        DomainEventPublisher.subscribe(BookSoldOut.class, this::handleEvent);
    }

    @DomainEventHandler
    public void handleEvent(BookSoldOut domainEvent)
    {
        domainEventSender.publish(domainEvent);
    }
}

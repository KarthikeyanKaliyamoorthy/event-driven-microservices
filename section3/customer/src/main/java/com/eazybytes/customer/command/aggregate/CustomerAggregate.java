package com.eazybytes.customer.command.aggregate;

import com.eazybytes.common.event.CustomerDataChangedEvent;
import com.eazybytes.customer.command.CreateCustomerCommand;
import com.eazybytes.customer.command.DeleteCustomerCommand;
import com.eazybytes.customer.command.UpdateCustomerCommand;
import com.eazybytes.customer.command.event.CustomerCreatedEvent;
import com.eazybytes.customer.command.event.CustomerDeletedEvent;
import com.eazybytes.customer.command.event.CustomerUpdatedEvent;
import com.eazybytes.customer.repository.CustomerRepository;
import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.eventsourcing.eventstore.EventStore;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.beans.BeanUtils;

@Aggregate
public class CustomerAggregate {

    @AggregateIdentifier
    private String customerId;
    private String name;
    private String email;
    private String mobileNumber;
    private boolean activeSw;

    public CustomerAggregate() {
    }

    @CommandHandler
    public CustomerAggregate(CreateCustomerCommand createCustomerCommand, CustomerRepository customerRepository) {
        /*Optional<Customer> customerOptional = customerRepository.findByMobileNumberAndActiveSw(createCustomerCommand.getMobileNumber(), true);

        if(customerOptional.isPresent()) {
            throw new CustomerAlreadyExistsException("Customer already registered with given mobileNumber "+ createCustomerCommand.getMobileNumber());
        }*/

        CustomerCreatedEvent customerCreatedEvent = new CustomerCreatedEvent();
        BeanUtils.copyProperties(createCustomerCommand, customerCreatedEvent);

        CustomerDataChangedEvent customerDataChangedEvent = new CustomerDataChangedEvent();
        BeanUtils.copyProperties(createCustomerCommand, customerDataChangedEvent);

        AggregateLifecycle.apply(customerCreatedEvent).andThenApply(() -> customerDataChangedEvent);

    }

    @EventSourcingHandler
    public void on(CustomerCreatedEvent customerCreatedEvent) {
        this.customerId = customerCreatedEvent.getCustomerId();
        this.name = customerCreatedEvent.getName();
        this.email = customerCreatedEvent.getEmail();
        this.mobileNumber = customerCreatedEvent.getMobileNumber();
        this.activeSw = customerCreatedEvent.isActiveSw();

    }

    @CommandHandler
    public void  handle(UpdateCustomerCommand updateCustomerCommand, EventStore eventStore) {

       /* List<? extends DomainEventMessage<?>> domainEventMessages = eventStore.readEvents(updateCustomerCommand.getCustomerId()).asStream().toList();
        if (domainEventMessages.isEmpty()) {
            throw new ResourceNotFoundException("Customer", "Customer Id", updateCustomerCommand.getCustomerId());
        }*/
        CustomerUpdatedEvent customerUpdatedEvent = new CustomerUpdatedEvent();
        BeanUtils.copyProperties(updateCustomerCommand, customerUpdatedEvent);

        CustomerDataChangedEvent customerDataChangedEvent = new CustomerDataChangedEvent();
        BeanUtils.copyProperties(updateCustomerCommand, customerDataChangedEvent);

        AggregateLifecycle.apply(customerUpdatedEvent);
        AggregateLifecycle.apply(customerDataChangedEvent);
    }

    @EventSourcingHandler
    public void on(CustomerUpdatedEvent customerUpdatedEvent) {
        this.name = customerUpdatedEvent.getName();
        this.email = customerUpdatedEvent.getEmail();
    }

    @CommandHandler
    public void handle(DeleteCustomerCommand deleteCustomerCommand) {
        CustomerDeletedEvent customerDeletedEvent = new CustomerDeletedEvent();
        BeanUtils.copyProperties(deleteCustomerCommand, customerDeletedEvent);

        AggregateLifecycle.apply(customerDeletedEvent);

    }

    @EventSourcingHandler
    public void on(CustomerDeletedEvent customerDeletedEvent) {
        this.activeSw = customerDeletedEvent.isActiveSw();
    }

}

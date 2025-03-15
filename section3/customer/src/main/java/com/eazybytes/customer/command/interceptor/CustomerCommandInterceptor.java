package com.eazybytes.customer.command.interceptor;

import com.eazybytes.customer.command.CreateCustomerCommand;
import com.eazybytes.customer.command.DeleteCustomerCommand;
import com.eazybytes.customer.command.UpdateCustomerCommand;
import com.eazybytes.customer.entity.Customer;
import com.eazybytes.customer.exception.CustomerAlreadyExistsException;
import com.eazybytes.customer.exception.ResourceNotFoundException;
import com.eazybytes.customer.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.axonframework.commandhandling.CommandMessage;
import org.axonframework.messaging.MessageDispatchInterceptor;
import org.springframework.stereotype.Component;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;

@Component
@RequiredArgsConstructor
public class CustomerCommandInterceptor implements MessageDispatchInterceptor<CommandMessage<?>> {

    private final CustomerRepository customerRepository;
    /**
     * Apply this interceptor to the given list of {@code messages}. This method returns a function that can be invoked
     * to obtain a modified version of messages at each position in the list.
     *
     * @param messages The Messages to pre-process
     * @return a function that processes messages based on their position in the list
     */
    @Nonnull
    @Override
    public BiFunction<Integer, CommandMessage<?>, CommandMessage<?>> handle(@Nonnull List<? extends CommandMessage<?>> messages) {
        return (index, command) -> {
            if(CreateCustomerCommand.class.isInstance(command.getPayload())){

                CreateCustomerCommand createCustomerCommand = (CreateCustomerCommand) command.getPayload();
                Optional<Customer> customerOptional = customerRepository.findByMobileNumberAndActiveSw(createCustomerCommand.getMobileNumber(), true);

                if(customerOptional.isPresent()) {
                    throw new CustomerAlreadyExistsException("Customer already registered with given mobileNumber "+ createCustomerCommand.getMobileNumber());
                }
            }

            else if(UpdateCustomerCommand.class.isInstance(command.getPayload())){

                UpdateCustomerCommand updateCustomerCommand = (UpdateCustomerCommand) command.getPayload();
               customerRepository.findByMobileNumberAndActiveSw(updateCustomerCommand.getMobileNumber(), true)
                        .orElseThrow(() -> new ResourceNotFoundException("Customer", "mobile number", updateCustomerCommand.getMobileNumber()));
            }

            else if(DeleteCustomerCommand.class.isInstance(command.getPayload())){
                DeleteCustomerCommand deleteCustomerCommand = (DeleteCustomerCommand) command.getPayload();
                customerRepository.findByCustomerIdAndActiveSw(deleteCustomerCommand.getCustomerId(),true)
                        .orElseThrow(() -> new ResourceNotFoundException("Customer", "CustomerId", deleteCustomerCommand.getCustomerId()));
            }
            return command;
        };
    }
}

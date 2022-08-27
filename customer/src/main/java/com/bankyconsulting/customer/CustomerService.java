package com.bankyconsulting.customer;

import com.bankyconsulting.amqp.RabbitMQMessageProducer;
import com.bankyconsulting.clients.fraud.FraudCheckResponse;
import com.bankyconsulting.clients.fraud.FraudClient;
import com.bankyconsulting.clients.notification.NotificationClient;
import com.bankyconsulting.clients.notification.NotificationRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final FraudClient fraudClient;
    private final RabbitMQMessageProducer rabbitMQMessageProducer;

    public void registerCustomer(CustomerRegistrationRequest request) {
        Customer customer = Customer.builder()
                .firstName(request.firstName())
                .lastName(request.lastName())
                .email(request.email())
                .build();

        customerRepository.saveAndFlush(customer);
        FraudCheckResponse fraudCheckResponse = fraudClient.isFraudster(customer.getId());

        if (fraudCheckResponse.isFraudster()) {
            throw new IllegalStateException("Fraudster!");
        }

        NotificationRequest notificationRequest = new NotificationRequest(
                customer.getId(),
                customer.getEmail(),
                String.format("Hi, %s, welcome to AmigosCode!",
                        customer.getFirstName())

        );
        rabbitMQMessageProducer.publish(
                notificationRequest,
                "internal.exchange",
                 "internal.notification.routing-key"
        );
    }
}

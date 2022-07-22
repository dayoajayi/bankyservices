package com.bankyconsulting.customer;

public record CustomerRegistrationRequest(
        String firstName,
        String lastName,
        String email) {
}

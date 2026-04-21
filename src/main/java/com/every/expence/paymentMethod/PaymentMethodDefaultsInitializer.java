package com.every.expence.paymentMethod;

import java.util.List;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Component;

@Component
public class PaymentMethodDefaultsInitializer implements ApplicationRunner {
    private static final List<String> DEFAULT_METHODS = List.of(
            "Cash",
            "Credit Card",
            "Debit Card",
            "Bank Transfer",
            "Check");

    private final PaymentMethodRepository paymentMethodRepository;

    public PaymentMethodDefaultsInitializer(PaymentMethodRepository paymentMethodRepository) {
        this.paymentMethodRepository = paymentMethodRepository;
    }

    @Override
    public void run(ApplicationArguments args) {
        for (String name : DEFAULT_METHODS) {
            if (!paymentMethodRepository.existsByUserIdIsNullAndName(name)) {
                try {
                    paymentMethodRepository.save(new PaymentMethod(null, name));
                } catch (DuplicateKeyException ignored) {
                }
            }
        }
    }
}
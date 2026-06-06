package com.every.expence.paymentMethod;

import java.util.List;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Component;

@Component
public class PaymentMethodDefaultsInitializer implements ApplicationRunner {
    private static final List<PaymentMethod> DEFAULT_METHODS = List.of(
            new PaymentMethod("pm_cash", null, "Cash"),
            new PaymentMethod("pm_credit_card", null, "Credit Card"),
            new PaymentMethod("pm_debit_card", null, "Debit Card"),
            new PaymentMethod("pm_bank_transfer", null, "Bank Transfer"),
            new PaymentMethod("pm_check", null, "Check")
    );

    private final PaymentMethodRepository paymentMethodRepository;

    public PaymentMethodDefaultsInitializer(PaymentMethodRepository paymentMethodRepository) {
        this.paymentMethodRepository = paymentMethodRepository;
    }

    @Override
    public void run(ApplicationArguments args) {
        for (PaymentMethod method : DEFAULT_METHODS) {
            if (!paymentMethodRepository.existsByUserIdIsNullAndName(method.getName())) {
                try {
                    paymentMethodRepository.save(method);
                } catch (DuplicateKeyException ignored) {
                }
            }
        }
    }
}
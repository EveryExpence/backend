package com.every.expence.paymentMethod;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.every.expence.paymentMethod.dto.CreatePaymentMethodRequestDTO;
import com.every.expence.paymentMethod.dto.PaymentMethodResponseDTO;
import com.every.expence.paymentMethod.dto.UpdatePaymentMethodRequestDTO;
import com.every.expence.user.User;

import jakarta.validation.Valid;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/v1/paymentMethod")
public class PaymentMethodController {
    private final PaymentMethodService paymentMethodService;

    public PaymentMethodController(PaymentMethodService paymentMethodService) {
        this.paymentMethodService = paymentMethodService;
    }

    @PostMapping("/create")
    public PaymentMethodResponseDTO addPaymentMethod(
            @AuthenticationPrincipal User user,
            @Valid @RequestBody CreatePaymentMethodRequestDTO createPaymentMethodRequestDTO) {
        PaymentMethod paymentMethod = paymentMethodService.addPaymentMethod(user, createPaymentMethodRequestDTO);
        return new PaymentMethodResponseDTO(paymentMethod.getId(), paymentMethod.getName(), paymentMethod.getIcon());
    }

    @GetMapping("/{id}")
    public PaymentMethodResponseDTO getPaymentMethod(
            @AuthenticationPrincipal User user,
            @PathVariable String id) {
        PaymentMethod paymentMethod = paymentMethodService.getPaymentMethod(user, id);
        return new PaymentMethodResponseDTO(paymentMethod.getId(), paymentMethod.getName(), paymentMethod.getIcon());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePaymentMethod(
            @AuthenticationPrincipal User user,
            @PathVariable String id) {
        paymentMethodService.deletePaymentMethod(user, id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public PaymentMethodResponseDTO updatePaymentMethod(
            @AuthenticationPrincipal User user,
            @PathVariable String id,
            @Valid @RequestBody UpdatePaymentMethodRequestDTO updatePaymentMethodRequestDTO) {
        PaymentMethod paymentMethod = paymentMethodService.updatePaymentMethodById(
                user,
                id,
                updatePaymentMethodRequestDTO);
        return new PaymentMethodResponseDTO(paymentMethod.getId(), paymentMethod.getName(), paymentMethod.getIcon());
    }

    @GetMapping("/getAll")
    public List<PaymentMethodResponseDTO> getMethodName(@AuthenticationPrincipal User user) {
        return paymentMethodService.getAllPaymentMethods(user);
    }

}

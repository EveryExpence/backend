package com.every.expence.paymentMethod;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.every.expence.paymentMethod.dto.PaymentMethodResponseDTO;
import com.every.expence.user.User;

@Service
public class PaymentMethodService {
    private final PaymentMethodRepository paymentMethodRepository;

    public PaymentMethodService(PaymentMethodRepository paymentMethodRepository) {
        this.paymentMethodRepository = paymentMethodRepository;
    }

    public PaymentMethod addPaymentMethod(User user, String name) {
        String normalizedName = name.trim();

        boolean nameTaken = paymentMethodRepository.existsByUserIdAndName(user.getId(), normalizedName);
        if (nameTaken) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Payment method name already in use");
        }

        PaymentMethod paymentMethod = new PaymentMethod(user.getId(), normalizedName);
        return paymentMethodRepository.save(paymentMethod);
    }

    public PaymentMethod getPaymentMethod(User user, String id) {
        return paymentMethodRepository.findByIdAndUserId(id, user.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Payment method not found"));
    }

    public void deletePaymentMethod(User user, String id) {
        long deletedCount = paymentMethodRepository.deleteByIdAndUserId(id, user.getId());

        if (deletedCount == 0) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Payment method not found");
        }
    }

    public PaymentMethod renamePaymentMethodById(User user, String id, String newName) {
        String normalizedNewName = newName.trim();

        PaymentMethod paymentMethod = paymentMethodRepository.findByIdAndUserId(id, user.getId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Payment method not found"));

        if (paymentMethod.getName().equals(normalizedNewName)) {
            return paymentMethod;
        }

        boolean nameTaken = paymentMethodRepository.existsByUserIdAndName(user.getId(), normalizedNewName);
        if (nameTaken) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Payment method name already in use");
        }

        paymentMethod.setName(normalizedNewName);
        return paymentMethodRepository.save(paymentMethod);
    }

    public List<PaymentMethodResponseDTO> getAllPaymentMethods(User user) {
        return paymentMethodRepository
                .findByUserIdOrUserIdIsNullOrderByNameAsc(user.getId())
                .stream()
                .map(paymentMethod -> new PaymentMethodResponseDTO(
                        paymentMethod.getId(),
                        paymentMethod.getName()))
                .toList();
    }
}
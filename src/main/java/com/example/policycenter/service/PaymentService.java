package com.example.policycenter.service;

import com.example.policycenter.model.Policy;
import com.example.policycenter.model.PolicyStatus;
import com.example.policycenter.model.payment.PaymentRequest;
import com.example.policycenter.model.payment.PaymentResponse;
import com.example.policycenter.repository.PolicyRepository;
import org.springframework.stereotype.Service;

@Service
public class PaymentService {

    private final PolicyRepository policyRepo;

    public PaymentService(PolicyRepository policyRepo) {
        this.policyRepo = policyRepo;
    }

    public PaymentResponse processPayment(PaymentRequest req, String username) {

        Policy policy = policyRepo.findById(req.getPolicyId())
                .orElseThrow(() -> new RuntimeException("Policy not found"));

        switch (req.getPaymentType()) {

            case CARD -> {
                // validate card details
                if (req.getCardNumber() == null)
                    throw new RuntimeException("Card details missing");

                // Mark policy as active
                policy.setStatus(PolicyStatus.ACTIVE);
                policyRepo.save(policy);

                return new PaymentResponse("SUCCESS", "Paid using card", policy.getId());
            }

            case DIRECT_ACCOUNT -> {
                if (req.getBankAccountNumber() == null)
                    throw new RuntimeException("Bank details missing");

                policy.setStatus(PolicyStatus.ACTIVE);
                policyRepo.save(policy);

                return new PaymentResponse("SUCCESS", "Paid using direct account", policy.getId());
            }

            case SMALL_PAYMENT_INSTALLMENT -> {
                if (req.getAmountPaid() < policy.getPremium() * 0.20)
                    throw new RuntimeException("Minimum 20% upfront needed");

                policy.setStatus(PolicyStatus.UNDER_REVIEW);
                policyRepo.save(policy);

                return new PaymentResponse("PARTIAL", "Small payment accepted. Installments pending.", policy.getId());
            }

            case FULL_INSTALLMENT -> {
                policy.setStatus(PolicyStatus.UNDER_REVIEW);
                policyRepo.save(policy);

                return new PaymentResponse("PENDING", "Installment cycle started", policy.getId());
            }

            case FULL_PAYMENT -> {
                policy.setStatus(PolicyStatus.ACTIVE);
                policyRepo.save(policy);

                return new PaymentResponse("SUCCESS", "Policy fully paid", policy.getId());
            }

            default -> throw new IllegalStateException("Unknown payment type");
        }
    }
}


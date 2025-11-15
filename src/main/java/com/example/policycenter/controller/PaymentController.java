package com.example.policycenter.controller;


import com.example.policycenter.model.payment.PaymentRequest;
import com.example.policycenter.model.payment.PaymentResponse;
import com.example.policycenter.service.PaymentService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/process")
    public ResponseEntity<?> process(@RequestBody PaymentRequest req, Authentication auth) {
        try {
            PaymentResponse response = paymentService.processPayment(req, auth.getName());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}


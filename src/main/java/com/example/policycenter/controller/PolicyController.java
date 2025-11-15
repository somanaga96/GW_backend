package com.example.policycenter.controller;

import com.example.policycenter.model.User;
import com.example.policycenter.service.PolicyService;
import com.example.policycenter.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/policies")
public class PolicyController {

    private final PolicyService policyService;
    private final UserService userService;

    public PolicyController(PolicyService policyService, UserService userService) {
        this.policyService = policyService;
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<?> listPolicies(Authentication auth) {
        User user = userService.findByUsername(auth.getName());
        return ResponseEntity.ok(policyService.listPoliciesForUser(user));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getPolicy(@PathVariable("id") Long id) {
        return policyService.getPolicy(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/{id}/review")
    public ResponseEntity<?> review(@PathVariable("id") Long id, Authentication auth) {
        try {
            policyService.reviewPolicy(id, auth.getName());
            return ResponseEntity.ok("Policy submitted for review");
        } catch (Exception e) { return ResponseEntity.badRequest().body(e.getMessage()); }
    }

    @PostMapping("/{id}/purchase")
    public ResponseEntity<?> purchase(@PathVariable("id") Long id, Authentication auth) {
        try {
            policyService.purchasePolicy(id, auth.getName());
            return ResponseEntity.ok("Policy purchased successfully");
        } catch (Exception e) { return ResponseEntity.badRequest().body(e.getMessage()); }
    }

    @PostMapping("/{id}/cancel")
    public ResponseEntity<?> cancel(@PathVariable("id") Long id, @RequestBody Map<String,String> body, Authentication auth) {
        try {
            policyService.cancelPolicy(id, body.get("reason"), auth.getName());
            return ResponseEntity.ok("Policy cancelled");
        } catch (Exception e) { return ResponseEntity.badRequest().body(e.getMessage()); }
    }

    @PostMapping("/{id}/reinstate")
    public ResponseEntity<?> reinstate(@PathVariable("id") Long id, Authentication auth) {
        try {
            policyService.reinstatePolicy(id, auth.getName());
            return ResponseEntity.ok("Policy reinstated");
        } catch (Exception e) { return ResponseEntity.badRequest().body(e.getMessage()); }
    }

    @PostMapping("/{id}/amend")
    public ResponseEntity<?> amend(@PathVariable("id") Long id, @RequestBody Map<String, Object> updates, Authentication auth) {
        try {
            policyService.amendPolicy(id, updates, auth.getName());
            return ResponseEntity.ok("Policy amended");
        } catch (Exception e) { return ResponseEntity.badRequest().body(e.getMessage()); }
    }

    @PostMapping("/{id}/renew")
    public ResponseEntity<?> renew(@PathVariable("id") Long id, Authentication auth) {
        try {
            Long newPolicyId = policyService.renewPolicy(id, auth.getName());
            return ResponseEntity.ok("Renewed policy ID: " + newPolicyId);
        } catch (Exception e) { return ResponseEntity.badRequest().body(e.getMessage()); }
    }
}
package com.example.policycenter.service;

import com.example.policycenter.model.*;
import com.example.policycenter.repository.PolicyRepository;
import com.example.policycenter.repository.PolicyHistoryRepository;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class PolicyService {

    private final PolicyRepository policyRepo;
    private final PolicyHistoryRepository historyRepo;

    public PolicyService(PolicyRepository policyRepo, PolicyHistoryRepository historyRepo) {
        this.policyRepo = policyRepo;
        this.historyRepo = historyRepo;
    }

    public Optional<Policy> getPolicy(Long id) {
        return policyRepo.findById(id);
    }

    public List<Policy> listPoliciesForUser(User user) {
        return policyRepo.findByCreatedBy(user);
    }

    private void recordHistory(Long policyId, String action, String by, String note) {
        PolicyHistory h = new PolicyHistory();
        h.setPolicyId(policyId);
        h.setAction(action);
        h.setPerformedBy(by);
        h.setOccurredAt(LocalDateTime.now());
        h.setNote(note);
        historyRepo.save(h);
    }

    // ---------------------------
    // REVIEW POLICY
    // ---------------------------
    public void reviewPolicy(Long id, String username) {
        Policy p = policyRepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        if (p.getStatus() != PolicyStatus.DRAFT)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Only DRAFT policies can be reviewed");

        p.setStatus(PolicyStatus.UNDER_REVIEW);
        policyRepo.save(p);

        recordHistory(p.getId(), "REVIEW", username, "Policy moved to review");
    }

    // ---------------------------
    // PURCHASE POLICY
    // ---------------------------
    public void purchasePolicy(Long id, String username) {
        Policy p = policyRepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        if (p.getStatus() != PolicyStatus.UNDER_REVIEW)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Policy must be UNDER_REVIEW to purchase");

        p.setEffectiveDate(LocalDate.now());
        p.setExpiryDate(LocalDate.now().plusYears(1));
        p.setStatus(PolicyStatus.ACTIVE);

        policyRepo.save(p);
        recordHistory(p.getId(), "PURCHASED", username, "Policy purchased successfully");
    }

    // ---------------------------
    // CANCEL POLICY
    // ---------------------------
    public void cancelPolicy(Long id, String reason, String username) {
        Policy p = policyRepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        if (p.getStatus() != PolicyStatus.ACTIVE && p.getStatus() != PolicyStatus.REINSTATED)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Only ACTIVE or REINSTATED policies can be cancelled");

        p.setStatus(PolicyStatus.CANCELLED);
        policyRepo.save(p);

        recordHistory(p.getId(), "CANCELLED", username, reason);
    }

    // ---------------------------
    // REINSTATE POLICY
    // ---------------------------
    public void reinstatePolicy(Long id, String username) {
        Policy p = policyRepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        if (p.getStatus() != PolicyStatus.CANCELLED)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Only CANCELLED policies can be reinstated");

        p.setStatus(PolicyStatus.REINSTATED);
        policyRepo.save(p);

        recordHistory(p.getId(), "REINSTATED", username, "Policy reinstated");
    }

    // ---------------------------
    // MID-TERM AMENDMENT
    // ---------------------------
    public void amendPolicy(Long id, Map<String, Object> updates, String username) {
        Policy p = policyRepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        if (p.getStatus() != PolicyStatus.ACTIVE && p.getStatus() != PolicyStatus.REINSTATED)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Only ACTIVE policies can be amended");

        if (updates.containsKey("vehicleRegNo"))
            p.setVehicleRegNo((String) updates.get("vehicleRegNo"));

        if (updates.containsKey("vehicleMake"))
            p.setVehicleMake((String) updates.get("vehicleMake"));

        if (updates.containsKey("vehicleModel"))
            p.setVehicleModel((String) updates.get("vehicleModel"));

        if (updates.containsKey("premium"))
            p.setPremium(Double.parseDouble(updates.get("premium").toString()));

        policyRepo.save(p);

        recordHistory(p.getId(), "AMENDMENT", username, "Policy amended");
    }

    // ---------------------------
    // RENEWAL
    // ---------------------------
    public Long renewPolicy(Long id, String username) {
        Policy old = policyRepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        if (old.getStatus() != PolicyStatus.ACTIVE)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Only ACTIVE policies can be renewed");

        Policy renewal = new Policy();
        renewal.setPolicyNumber("P-" + System.currentTimeMillis());
        renewal.setInsuredName(old.getInsuredName());
        renewal.setVehicleRegNo(old.getVehicleRegNo());
        renewal.setVehicleMake(old.getVehicleMake());
        renewal.setVehicleModel(old.getVehicleModel());
        renewal.setVehicleYear(old.getVehicleYear());
        renewal.setPremium(old.getPremium());
        renewal.setProduct("MOTOR");
        renewal.setStatus(PolicyStatus.RENEWAL_PENDING);
        renewal.setCreatedBy(old.getCreatedBy());
        renewal.setQuoteId(old.getQuoteId());

        Policy newPolicy = policyRepo.save(renewal);

        recordHistory(newPolicy.getId(), "RENEWAL_CREATED", username, "Renewal policy initialized");

        return newPolicy.getId();
    }
}
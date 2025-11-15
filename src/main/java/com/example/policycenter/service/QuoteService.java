package com.example.policycenter.service;

import com.example.policycenter.model.Policy;
import com.example.policycenter.model.PolicyStatus;
import com.example.policycenter.model.Quote;
import com.example.policycenter.model.User;
import com.example.policycenter.repository.PolicyRepository;
import com.example.policycenter.repository.QuoteRepository;
import com.example.policycenter.repository.PolicyHistoryRepository;
import com.example.policycenter.model.PolicyHistory;

import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class QuoteService {

    private final QuoteRepository quoteRepo;
    private final PolicyRepository policyRepo;
    private final PolicyHistoryRepository historyRepo;

    public QuoteService(QuoteRepository quoteRepo,
                        PolicyRepository policyRepo,
                        PolicyHistoryRepository historyRepo) {
        this.quoteRepo = quoteRepo;
        this.policyRepo = policyRepo;
        this.historyRepo = historyRepo;
    }

    // Simple premium calculation
    private double calculatePremium(Quote q) {
        int age = LocalDate.now().getYear() - q.getVehicleYear();
        double basePremium = 3000;
        double ageFactor = age * 100;
        return basePremium + ageFactor;
    }

    public Quote createQuote(Quote q, User user) {
        q.setQuoteDate(LocalDate.now());
        q.setStatus("DRAFT");
        q.setPremiumEstimate(calculatePremium(q));
        q.setCreatedBy(user);
        q.setQuoteNumber("Q-" + System.currentTimeMillis());
        return quoteRepo.save(q);
    }

    public List<Quote> listQuotesForUser(User user) {
        return quoteRepo.findByCreatedBy(user)
                .stream()
                .filter(q -> !q.getStatus().equals("ACCEPTED")
                        && !q.getStatus().equals("EXPIRED"))
                .toList();
    }

    public Optional<Quote> getQuote(Long id) {
        return quoteRepo.findById(id);
    }

    public Long acceptQuoteAndCreatePolicy(Long quoteId, String username) {
        Quote quote = quoteRepo.findById(quoteId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Quote not found"));

        if (!quote.getStatus().equals("DRAFT") && !quote.getStatus().equals("READY")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Quote cannot be accepted");
        }

        quote.setStatus("ACCEPTED");
        quoteRepo.save(quote);

        Policy policy = new Policy();
        policy.setPolicyNumber("P-" + System.currentTimeMillis());
        policy.setInsuredName(quote.getInsuredName());
        policy.setProduct("MOTOR");
        policy.setVehicleRegNo(quote.getVehicleRegNo());
        policy.setVehicleMake(quote.getVehicleMake());
        policy.setVehicleModel(quote.getVehicleModel());
        policy.setVehicleYear(quote.getVehicleYear());
        policy.setPremium(quote.getPremiumEstimate());
        policy.setStatus(PolicyStatus.DRAFT);
        policy.setQuoteId(quote.getId());
        policy.setCreatedBy(quote.getCreatedBy());

        Policy saved = policyRepo.save(policy);

        // History
        PolicyHistory h = new PolicyHistory();
        h.setPolicyId(saved.getId());
        h.setAction("CREATED_FROM_QUOTE");
        h.setPerformedBy(username);
        h.setOccurredAt(LocalDateTime.now());
        h.setNote("Policy created from quote " + quote.getQuoteNumber());
        historyRepo.save(h);

        return saved.getId();
    }
}
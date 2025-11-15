package com.example.policycenter.controller;

import com.example.policycenter.model.Quote;
import com.example.policycenter.model.User;
import com.example.policycenter.service.QuoteService;
import com.example.policycenter.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/quotes")
public class QuoteController {

    private final QuoteService quoteService;
    private final UserService userService;

    public QuoteController(QuoteService quoteService, UserService userService) {
        this.quoteService = quoteService;
        this.userService = userService;
    }

    @PostMapping
    public Quote createQuote(@RequestBody Quote q, Authentication auth) {
        User user = userService.findByUsername(auth.getName());
        return quoteService.createQuote(q, user);
    }

    @GetMapping
    public List<Quote> list(Authentication auth) {
        User user = userService.findByUsername(auth.getName());
        return quoteService.listQuotesForUser(user);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getOne(@PathVariable("id") Long id, Authentication auth) {
        return quoteService.getQuote(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/{id}/accept")
    public ResponseEntity<?> acceptQuote(@PathVariable("id") Long id, Authentication auth) {
        try {
            Long newPolicyId = quoteService.acceptQuoteAndCreatePolicy(id, auth.getName());
            return ResponseEntity.ok("Policy created with ID: " + newPolicyId);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
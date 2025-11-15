package com.example.policycenter.repository;

import com.example.policycenter.model.Quote;
import com.example.policycenter.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuoteRepository extends JpaRepository<Quote, Long> {

    List<Quote> findByCreatedBy(User createdBy);
    List<Quote> findByCreatedByAndStatusNot(User createdBy, String status);

}
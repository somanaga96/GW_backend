package com.example.policycenter.repository;

import com.example.policycenter.model.Policy;
import com.example.policycenter.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PolicyRepository extends JpaRepository<Policy, Long> {

    List<Policy> findByCreatedBy(User createdBy);
}

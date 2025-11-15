package com.example.policycenter.repository;

import com.example.policycenter.model.PolicyHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PolicyHistoryRepository extends JpaRepository<PolicyHistory, Long> {

    List<PolicyHistory> findByPolicyId(Long policyId);
}

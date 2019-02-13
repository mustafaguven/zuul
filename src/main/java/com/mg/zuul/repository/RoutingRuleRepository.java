package com.mg.zuul.repository;

import com.mg.zuul.service.RoutingRule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoutingRuleRepository extends JpaRepository<RoutingRule, Long> {
}

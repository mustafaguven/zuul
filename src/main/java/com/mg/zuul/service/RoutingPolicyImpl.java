package com.mg.zuul.service;

import com.mg.zuul.repository.RoutingRuleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoutingPolicyImpl implements RoutingPolicy {

    @Autowired
    private RoutingRuleRepository routingRuleRepository;

    @Override
    public List<RoutingRule> getRouteRules() {
        return routingRuleRepository.findAll();
    }

}

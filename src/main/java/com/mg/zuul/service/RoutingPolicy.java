package com.mg.zuul.service;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RoutingPolicy {

    List<RoutingRule> getRouteRules();

}

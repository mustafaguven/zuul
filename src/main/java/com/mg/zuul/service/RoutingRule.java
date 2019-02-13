package com.mg.zuul.service;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "routingRules")
public class RoutingRule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter
    @Setter
    @Column(name = "uri")
    public String uri;

    @Getter
    @Setter
    @Column(name = "role")
    public String role;
}

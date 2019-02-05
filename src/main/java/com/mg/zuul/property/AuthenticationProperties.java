package com.mg.zuul.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties("authentication")
public class AuthenticationProperties {

    private Boolean shouldAuthenticate;

    private String key;

    private String value;

}

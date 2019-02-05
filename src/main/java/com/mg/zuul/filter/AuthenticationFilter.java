package com.mg.zuul.filter;

import com.mg.zuul.property.AuthenticationProperties;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Component
@Slf4j
public class AuthenticationFilter extends ZuulFilter {

    @Autowired
    private AuthenticationProperties authenticationProperties;

    @Override
    public Object run() throws ZuulException {
        log.info(String.format("Checking authentication is %s", authenticationProperties.getShouldAuthenticate() ? "on" : "off"));
        RequestContext currentContext = RequestContext.getCurrentContext();
        HttpServletRequest request = currentContext.getRequest();
        if (authenticate(request)) {
            log.info("Authentication Failed!");
            currentContext.setSendZuulResponse(false);
            currentContext.setResponseBody("{\"success\": false, \"message\":\"mistaken authentication key\"}");
            currentContext.setResponseStatusCode(200);
        }
        return null;
    }

    private boolean authenticate(HttpServletRequest request) {
        return authenticationProperties.getShouldAuthenticate() &&
                !(request.getHeader(authenticationProperties.getKey()) != null &&
                        request.getHeader(authenticationProperties.getKey()).contentEquals(authenticationProperties.getValue()));
    }

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 1;
    }

    @Override
    public boolean shouldFilter() {
        return true;
    }
}

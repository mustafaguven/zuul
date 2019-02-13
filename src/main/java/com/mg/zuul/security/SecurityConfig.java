package com.mg.zuul.security;

import com.mg.jwtcommon.security.JwtAuthenticationConfig;
import com.mg.jwtcommon.security.JwtTokenAuthenticationFilter;
import com.mg.zuul.service.RoutingRule;
import com.mg.zuul.service.RoutingPolicy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletResponse;


@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private RoutingPolicy routingPolicy;

    @Autowired
    private JwtAuthenticationConfig config;

    @Bean
    public JwtAuthenticationConfig jwtConfig() {
        return new JwtAuthenticationConfig();
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry expressionInterceptUrlRegistry = httpSecurity
                .csrf().disable()
                .logout().disable()
                .formLogin().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .anonymous()
                .and()
                .exceptionHandling().authenticationEntryPoint(
                        (req, rsp, e) -> rsp.sendError(HttpServletResponse.SC_UNAUTHORIZED))
                .and()
                .addFilterAfter(new JwtTokenAuthenticationFilter(config),
                        UsernamePasswordAuthenticationFilter.class)
                .authorizeRequests();

        expressionInterceptUrlRegistry.antMatchers(config.getZuulLoginUrl()).permitAll();
        expressionInterceptUrlRegistry.antMatchers(config.getZuulLogoutUrl()).permitAll();
        routingPolicy.getRouteRules().forEach(it -> {
            expressionInterceptUrlRegistry.antMatchers(it.getUri()).hasRole(it.getRole());
        });
        expressionInterceptUrlRegistry.anyRequest().denyAll();
    }
}


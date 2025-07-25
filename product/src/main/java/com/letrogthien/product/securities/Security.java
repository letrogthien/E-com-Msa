package com.letrogthien.product.securities;

import com.letrogthien.product.common.RoleName;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@RequiredArgsConstructor
public class Security {
    private final CustomJwtDecoder jwtDecoder;
    private final CustomAuthenticatinConverter converter;
    private final CustomAuthenticationEntryPoint entryPoint;
    private final GetTokenResolver getTokenResolver;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity security) throws Exception {
        security.csrf(AbstractHttpConfigurer::disable);
        configureAuthorizationRules(security);
        configureSessionManagement(security);
        configureOAuth2ResourceServer(security);
        return security.build();
    }

    private void configureAuthorizationRules(HttpSecurity security) throws Exception {
        security.authorizeHttpRequests(authorize -> authorize
                .requestMatchers(HttpMethod.GET).permitAll()
                .requestMatchers(HttpMethod.POST).hasAnyAuthority(RoleName.ROLE_SELLER.name(), RoleName.ROLE_ADMIN.name())
                .requestMatchers(HttpMethod.PUT).hasAnyAuthority(RoleName.ROLE_SELLER.name(), RoleName.ROLE_ADMIN.name())
                .requestMatchers(HttpMethod.DELETE).hasAnyAuthority(RoleName.ROLE_SELLER.name(), RoleName.ROLE_ADMIN.name())
                .requestMatchers("/api/admin/**").hasAnyAuthority(RoleName.ROLE_ADMIN.name())
                .anyRequest().authenticated());
    }

    private void configureSessionManagement(HttpSecurity security) throws Exception {
        security.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
    }

    private void configureOAuth2ResourceServer(HttpSecurity security) throws Exception {
        security.oauth2ResourceServer(oauth2 -> oauth2
                .jwt(jwt -> jwt
                        .decoder(jwtDecoder)
                        .jwtAuthenticationConverter(converter))
                .bearerTokenResolver(getTokenResolver)
                .authenticationEntryPoint(entryPoint));
    }

}

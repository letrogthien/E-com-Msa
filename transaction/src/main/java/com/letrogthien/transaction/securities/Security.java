package com.letrogthien.transaction.securities;



import com.letrogthien.transaction.common.RoleName;
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
        security.authorizeHttpRequests(authorize ->
            authorize
                    .requestMatchers(
                            "/swagger-ui/**",
                            "/v3/api-docs/**"
                    ).permitAll()
                    .requestMatchers(HttpMethod.GET).permitAll()
                    .requestMatchers(
                            "api/v1/admin/**"
                    ).hasAuthority(RoleName.ROLE_ADMIN.name())
                    .requestMatchers("/api/orders/**").hasAnyAuthority(
                            RoleName.ROLE_USER.name(),
                            RoleName.ROLE_ADMIN.name(),
                            RoleName.ROLE_MEMBER.name(),
                            RoleName.ROLE_MANAGER.name()
                    )
                    .requestMatchers("/api/cart/**").hasAnyAuthority(
                            RoleName.ROLE_USER.name(),
                            RoleName.ROLE_MEMBER.name()
                    )
                    .anyRequest().authenticated()
        );
    }

    private void configureSessionManagement(HttpSecurity security) throws Exception {
        security.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
    }

    private void configureOAuth2ResourceServer(HttpSecurity security) throws Exception {
        security.oauth2ResourceServer(oauth2 -> oauth2
                .jwt(jwt -> jwt
                        .decoder(jwtDecoder)
                        .jwtAuthenticationConverter(converter)
                )
                .bearerTokenResolver(getTokenResolver)
                .authenticationEntryPoint(entryPoint)
        );
    }

}

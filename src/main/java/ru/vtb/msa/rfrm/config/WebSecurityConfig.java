package ru.vtb.msa.rfrm.config;

import com.fasterxml.jackson.core.type.TypeReference;
import lombok.SneakyThrows;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static ru.vtb.omni.jwt.lib.constant.JwtConstant.TOKEN_DELIMITER;
import static ru.vtb.omni.jwt.lib.util.JwtObjectMapper.OBJECT_MAPPER;



@Order(150)
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private static final String[] AUTH_WHITELIST = {
            // -- Swagger UI v2
            "/v2/api-docs",
            "/swagger-resources",
            "/swagger-resources/**",
            "/configuration/ui",
            "/configuration/security",
            "/swagger-ui.html",
            "/webjars/**",
            // -- Swagger UI v3 (OpenAPI)
            "/v3/api-docs/**",
            "/swagger-ui/**",
            // other public endpoints of your API may be appended to this array
            "/actuator",
            "/actuator/health",
            "/actuator/health/",
            "/actuator/health/liveness",
            "/actuator/health/readiness",
            "/actuator/prometheus",
            "/actuator/prometheus/"
    };

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers(AUTH_WHITELIST);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .httpBasic().disable()
                .formLogin(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .cors().and()
                .authorizeRequests(authorize -> authorize
                        .anyRequest().authenticated()
                )
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(jwt -> jwt
                                .decoder(myCustomDecoder())
                        )
                )
                .sessionManagement(sessionManagement ->
                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
    }

    private String decodeToken(String token) {
        return new String(Base64.getUrlDecoder()
                .decode(token.split(TOKEN_DELIMITER, 0)[1]), StandardCharsets.UTF_8);
    }

    @SneakyThrows
    private <T> T extractTokenPayloadFromAuthHeaderToType(String decodedToken, TypeReference<T> typeReference) {
        return OBJECT_MAPPER.readValue(decodedToken, typeReference);
    }

    @Bean
    public JwtDecoder myCustomDecoder() {
        return token -> {
            final Map<String, Object> claims = extractTokenPayloadFromAuthHeaderToType(
                    decodeToken(token),
                    new TypeReference<HashMap<String, Object>>() {
                    });

            Instant issuedAt = Instant.MIN;
            if (claims.get("iat") != null) issuedAt = Instant.ofEpochSecond(((Integer) claims.get("iat")).longValue());
            Instant expiresAt = Instant.MAX;
            if (claims.get("exp") != null) expiresAt = Instant.ofEpochSecond(((Integer) claims.get("exp")).longValue());

            return Jwt.withTokenValue(token)
                    .claims(c -> c.putAll(claims))
                    .header("alg", "HS256")
                    .header("typ", "JWT")
                    .issuedAt(issuedAt)
                    .expiresAt(expiresAt)
                    .build();
        };
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.applyPermitDefaultValues();
        corsConfiguration.setAllowedHeaders(List.of("*"));
        corsConfiguration.setAllowedOrigins(List.of("*"));
        corsConfiguration.setAllowedMethods(List.of("*"));
        UrlBasedCorsConfigurationSource ccs = new UrlBasedCorsConfigurationSource();
        ccs.registerCorsConfiguration("/**", corsConfiguration);
        return ccs;
    }

    @Bean
    @ConditionalOnProperty(prefix = "app", name="enable-cors", havingValue="false")
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**").allowedMethods("*").allowedOrigins("*");
            }
        };
    }
}

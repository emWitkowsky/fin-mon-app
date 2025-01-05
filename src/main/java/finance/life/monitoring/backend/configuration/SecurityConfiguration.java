package finance.life.monitoring.backend.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Configuration
public class SecurityConfiguration {

//    @Bean
//    @Profile("dev")
//    public SecurityFilterChain securityFilterChainDev(HttpSecurity http) throws Exception {
//        http.cors(cors -> cors.configurationSource(corsConfigurationSource()))
//                .csrf(AbstractHttpConfigurer::disable)
//                .authorizeHttpRequests(
//                        auth ->
//                                auth.requestMatchers("/hello-world")
//                                        .permitAll()
//                                        .requestMatchers("/error")
//                                        .permitAll()
//                                        .anyRequest()
//                                        .permitAll())
//                .oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()));
//        return http.build();
//    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http.csrf(AbstractHttpConfigurer::disable);

        http.cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/v0/**").permitAll()
                        .requestMatchers("/hello-world").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/v0/**").permitAll()
//                        .anyRequest().authenticated()
                        .anyRequest().permitAll() // so far we let that say
                )
                .formLogin(form -> form.disable()) // Wyłącza formularz logowania
                .httpBasic(httpBasic -> httpBasic.disable()); // Wyłącza Basic Auth



        return http.build();
    }


    private CorsConfigurationSource corsConfigurationSource() {
        return request -> {
            CorsConfiguration configuration = new CorsConfiguration();
            configuration.setAllowedOrigins(List.of("*"));
            configuration.setAllowedMethods(List.of("*"));
            configuration.setAllowedHeaders(List.of("*"));
            return configuration;
        };
    }

    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtAuthenticationConverter converter = new JwtAuthenticationConverter();
        converter.setJwtGrantedAuthoritiesConverter(
                jwt -> {
                    @SuppressWarnings("unchecked")
                    var realmAccess = (Map<String, Object>) jwt.getClaims().get("realm_access");
                    @SuppressWarnings("unchecked")
                    var roles = (List<String>) realmAccess.get("roles");

                    // Convert Keycloak roles to Spring Security authorities
                    return roles.stream()
                            .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                            .collect(Collectors.toList());
                });
        return converter;
    }
}

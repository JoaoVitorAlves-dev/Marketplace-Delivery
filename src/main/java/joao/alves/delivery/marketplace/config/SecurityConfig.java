package joao.alves.delivery.marketplace.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, TokenFilter tokenFilter) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/", "/error").permitAll()
                        .requestMatchers(HttpMethod.POST, "/usuarios/auth/cadastrar", "/usuarios/auth/login", "/usuarios/auth/refresh").permitAll()
                        .requestMatchers(HttpMethod.PUT, "/usuarios/auth/alterar/roles/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/usuarios/auth/listar").hasAnyRole("ADMIN", "ENTREGADOR")
                        .requestMatchers(HttpMethod.DELETE, "/usuarios/auth/deletar/**").hasRole("ADMIN")

                        .requestMatchers("/categorias/**").hasRole("DONO_RESTAURANTE")

                        .requestMatchers(HttpMethod.GET, "/pratos/**").authenticated()
                        .requestMatchers("/pratos/**").hasAnyRole("VENDEDOR", "DONO_RESTAURANTE")

                        .requestMatchers(HttpMethod.GET, "/restaurantes/**")
                        .hasAnyRole("DONO_RESTAURANTE", "ADMIN", "CLIENTE")
                        .requestMatchers("/restaurantes/**").hasAnyRole("DONO_RESTAURANTE", "ADMIN")

                        .requestMatchers(HttpMethod.POST, "/pedidos/cadastrar").hasRole("CLIENTE")
                        .requestMatchers(HttpMethod.PUT, "/pedidos/atualizar/**")
                        .hasAnyRole("ENTREGADOR", "DONO_RESTAURANTE", "VENDEDOR", "ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/pedidos/**").hasRole("ADMIN")

                        .requestMatchers("/pagamentos/**").hasRole("CAIXA")

                        .anyRequest().authenticated())
                .addFilterBefore(tokenFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) {
        return configuration.getAuthenticationManager();
    }

}

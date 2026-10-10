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
                        .requestMatchers("/", "/errors").permitAll()
                        .requestMatchers(HttpMethod.POST, "/usuarios/cadastrar", "/usuarios/login").permitAll()
                        .requestMatchers(HttpMethod.PUT, "/usuarios/alterar/roles/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/usuarios/listar").hasAnyRole("ADMIN", "ENTREGADOR")
                        .requestMatchers(HttpMethod.PUT, "/usuarios/atualizar/{id}").authenticated()
                        .requestMatchers(HttpMethod.DELETE, "/usuarios/deletar/{id}").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, "/enderecos/cadastrar").authenticated()
                        .requestMatchers(HttpMethod.PUT, "/enderecos/atualizar/{id}").authenticated()
                        .requestMatchers(HttpMethod.GET, "/enderecos/listar").authenticated()
                        .requestMatchers(HttpMethod.DELETE, "/enderecos/deletar/{id}").authenticated()
                        .requestMatchers(HttpMethod.POST, "/categorias/cadastrar").hasAuthority("DONO_RESTAURANTE")
                        .requestMatchers(HttpMethod.PUT, "/categorias/atualizar/{id}").hasAuthority("DONO_RESTAURANTE")
                        .requestMatchers(HttpMethod.GET, "/categorias/listar").hasAuthority("DONO_RESTAURANTE")
                        .requestMatchers(HttpMethod.DELETE, "/categorias/deletar/{id}").hasAuthority("DONO_RESTAURANTE")
                        .requestMatchers(HttpMethod.POST, "/itempedidos/cadastrar").authenticated()
                        .requestMatchers(HttpMethod.GET, "/itempedidos/listar").authenticated()
                        .requestMatchers(HttpMethod.PUT, "/itempedidos/atualizar/{id}").authenticated()
                        .requestMatchers(HttpMethod.DELETE, "/itempedidos/deletar/{id}").authenticated()
                        .requestMatchers(HttpMethod.POST, "/pedidos/cadastrar").hasRole("ENTREGADOR")
                        .requestMatchers(HttpMethod.GET, "/pedidos/listar").hasRole("ENTREGADOR")
                        .requestMatchers(HttpMethod.PUT, "/pedidos/atualizar/{id}").hasRole("ENTREGADOR")
                        .requestMatchers(HttpMethod.DELETE, "/pedidos/deletar/{id}").hasRole("ENTREGADOR")
                        .requestMatchers(HttpMethod.POST, "/pagamentos/cadastrar").hasRole("CAIXA")
                        .requestMatchers(HttpMethod.GET, "/pagamentos/listar").hasRole("CAIXA")
                        .requestMatchers(HttpMethod.PUT, "/pagamentos/atualizar/{id}").hasRole("CAIXA")
                        .requestMatchers(HttpMethod.DELETE, "/pagamentos/deletar/{id}").hasRole("CAIXA")
                        .anyRequest().authenticated())
                .addFilterBefore(tokenFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) {
        return configuration.getAuthenticationManager();
    }

}

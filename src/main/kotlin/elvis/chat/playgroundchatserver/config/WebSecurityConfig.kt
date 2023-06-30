package elvis.chat.playgroundchatserver.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.Customizer.withDefaults
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.core.userdetails.User
import org.springframework.security.provisioning.InMemoryUserDetailsManager
import org.springframework.security.web.SecurityFilterChain

@Configuration
@EnableWebSecurity
class WebSecurityConfig {
    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .csrf { csrf -> csrf.disable() }
            .headers { headers -> headers.frameOptions { frameOptions -> frameOptions.sameOrigin() } }
            .formLogin(withDefaults())
            .authorizeHttpRequests { authz ->
                authz
                    .requestMatchers("/chat/**").hasRole("USER")
                    .anyRequest().permitAll()
            }
        return http.build()
    }

    @Bean
    fun userDetailService(): InMemoryUserDetailsManager {
        val user1 = User
            .withUsername("happyUser")
            .password("{noop}1234")
            .roles("USER")
            .build()

        val user2 = User
            .withUsername("angryUser")
            .password("{noop}1234")
            .roles("USER")
            .build()

        val user3 = User
            .withUsername("guest")
            .password("{noop}1234")
            .roles("GUEST")
            .build()

        return InMemoryUserDetailsManager(user1, user2, user3)
    }
}

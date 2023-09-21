package com.catoritech.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

	@Bean
	public UserDetailsService userDetailsService() {
		return new UserUserDetailsService();
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		RequestMatcher authenticatedMatchers = new OrRequestMatcher(
			new AntPathRequestMatcher("/user"),
			new AntPathRequestMatcher("/business"),
			new AntPathRequestMatcher("/createContact"),
			new AntPathRequestMatcher("/contact/{id}"),
			new AntPathRequestMatcher("/contact/profile"),
			new AntPathRequestMatcher("/contacts/{id}"),
			new AntPathRequestMatcher("/contact/all"),
			new AntPathRequestMatcher("/contact/update/{id}")

		);
		RequestMatcher permitAllMatchers =  new OrRequestMatcher(
			new AntPathRequestMatcher("/addUser"),
			new AntPathRequestMatcher("/public"),
			new AntPathRequestMatcher("/register"),
		  new AntPathRequestMatcher("/contacts")
		);

		return http.csrf(AbstractHttpConfigurer::disable)
		           .authorizeHttpRequests(auth ->
			                                  auth.requestMatchers(permitAllMatchers).permitAll()
			                                      .requestMatchers(authenticatedMatchers).authenticated()
		           )
		           .httpBasic(Customizer.withDefaults()).build();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public AuthenticationProvider authenticationProvider(){
		DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
		authenticationProvider.setUserDetailsService(userDetailsService());
		authenticationProvider.setPasswordEncoder(passwordEncoder());
		return authenticationProvider;
	}
}

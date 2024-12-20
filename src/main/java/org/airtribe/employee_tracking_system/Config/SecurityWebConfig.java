package org.airtribe.employee_tracking_system.Config;

import java.util.List;
import java.util.stream.Collectors;

import org.airtribe.employee_tracking_system.Service.OAuth2CustomUserService;
import org.airtribe.employee_tracking_system.Service.RoleService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableAspectJAutoProxy(proxyTargetClass = true)
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityWebConfig {

	private final OAuth2CustomUserService fOAuth2CustomUserService;
	private final RoleService roleService;

	public SecurityWebConfig(OAuth2CustomUserService OAuth2CustomUserService, RoleService roleService) {
		this.fOAuth2CustomUserService = OAuth2CustomUserService;
		this.roleService = roleService;
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		System.out.println("Configuring http filterChain");

		http
				.authorizeHttpRequests(authz -> authz
						.requestMatchers("/hello").permitAll()
						.requestMatchers("/api/hello").hasRole("ADMIN")
						.requestMatchers("/departments/**", "/projects/**").hasAnyRole("ADMIN", "MANAGER")
						.requestMatchers("/employees/**").hasAnyRole("ADMIN", "MANAGER", "EMPLOYEE")
						.anyRequest().authenticated()
				)
				.oauth2Login(oauth2 -> oauth2
						.userInfoEndpoint(userInfo -> userInfo
								.userService(fOAuth2CustomUserService)
						)
						.defaultSuccessUrl("/", true)
						.failureUrl("/login?error")
				)
				.oauth2ResourceServer(oauth2 -> oauth2
						.jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthenticationConverter()))
				)
				.csrf(csrf -> csrf.disable()); // Disable CSRF if not needed

		return http.build();
	}

	@Bean
	public JwtAuthenticationConverter jwtAuthenticationConverter() {
		JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();

		// Convert JWT claims to authorities
		jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwt -> {
			// Extract the user's email or subject (sub) from the JWT
			String email = jwt.getClaimAsString("email");
			if (email == null) {
				throw new RuntimeException("Email claim is missing in the JWT.");
			}

			// Fetch roles from the database using the email
			List<String> roles = roleService.getRolesByEmail(email);

			if (roles == null || roles.isEmpty()) {
				throw new RuntimeException("No roles found for user: " + email);
			}

			// Map roles into SimpleGrantedAuthority list, prefixing with ROLE_
			return roles.stream()
					.map(role -> new SimpleGrantedAuthority("ROLE_" + role))
					.collect(Collectors.toList());
		});

		return jwtAuthenticationConverter;
	}

}

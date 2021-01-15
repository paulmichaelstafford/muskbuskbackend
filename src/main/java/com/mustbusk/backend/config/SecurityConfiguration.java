package com.mustbusk.backend.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.google.common.collect.ImmutableList;
import com.mustbusk.backend.app.model.user.UserService;
import com.mustbusk.backend.app.model.user.role.RoleType;
import com.mustbusk.backend.controller.PreAuthorize;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter
{
	@Autowired
	private AppAuthenticationEntryPoint appAuthenticationEntryPoint;

	@Autowired
	private DataSource dataSource;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Autowired
	private UserService userService;

	@Value("${spring.queries.users-query}")
	private String usersQuery;

	@Value("${spring.queries.roles-query}")
	private String rolesQuery;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception
	{
		auth.
			jdbcAuthentication()
			.usersByUsernameQuery(usersQuery)
			.authoritiesByUsernameQuery(rolesQuery)
			.dataSource(dataSource)
			.passwordEncoder(bCryptPasswordEncoder);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception
	{
		http.csrf().disable();
		http.authorizeRequests()
			.antMatchers("/auth/user")
			.permitAll()
			.antMatchers("/admin/**")
			.hasAuthority(RoleType.ADMIN.getValue())
			.antMatchers("/api/v1/user/**")
			.hasAnyAuthority(RoleType.ADMIN.getValue(), RoleType.USER.getValue())
			.antMatchers("/busker/**")
			.hasAnyAuthority(RoleType.ADMIN.getValue(), RoleType.BUSKER.getValue(), RoleType.USER.getValue())
			.and()
			.httpBasic()
			.authenticationEntryPoint(appAuthenticationEntryPoint);
		http.cors();
	}

	@Bean
	public CorsConfigurationSource corsConfigurationSource()
	{
		final CorsConfiguration configuration = new CorsConfiguration();
		configuration.setAllowedOrigins(ImmutableList.of("http://localhost:4200"));
		configuration.setAllowedMethods(ImmutableList.of("HEAD", "GET", "POST", "PUT", "DELETE", "PATCH"));
		configuration.setAllowCredentials(true);
		configuration.setAllowedHeaders(ImmutableList.of("Authorization", "Cache-Control", "Content-Type"));
		final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}

	@Bean
	public PreAuthorize preAuthorize()
	{
		PreAuthorize preAuthorize = new PreAuthorize(userService);
		return preAuthorize;
	}
}

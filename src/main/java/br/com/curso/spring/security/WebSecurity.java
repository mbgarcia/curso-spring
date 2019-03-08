package br.com.curso.spring.security;

import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import br.com.curso.spring.service.UserService;

@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter {
	private final UserService userDetailsService;
	private final BCryptPasswordEncoder bCryptPasswordEncoder;

	public WebSecurity(UserService userDetailsService, BCryptPasswordEncoder bCryptPasswordEncoder) {
		this.userDetailsService = userDetailsService;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable().authorizeRequests()
			.antMatchers(HttpMethod.POST, "/users")
			.permitAll()
			.antMatchers(HttpMethod.GET, "/users/email-verification")
			.permitAll()
			.antMatchers(HttpMethod.POST, "/users/password-reset-request")
			.permitAll()
			.antMatchers(HttpMethod.POST, "/users/password-reset-validation")
			.permitAll()
			.antMatchers(HttpMethod.OPTIONS, "/users/password-reset-validation")
			.permitAll()
			.antMatchers("/v2/api-docs", "/configuration/**", "/swagger*/**", "/webjars/**")
			.permitAll()
			.anyRequest()
			.authenticated()
			.and().addFilter(getAuthenticationFilter())
			.addFilter(new AuthorizationFilter(authenticationManager()))
			.sessionManagement()
			.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
	}
	
    public AuthenticationFilter getAuthenticationFilter() throws Exception {
    	final AuthenticationFilter filter = new AuthenticationFilter(authenticationManager());
    	filter.setFilterProcessesUrl("/users/login");
    	return filter;
    }
}

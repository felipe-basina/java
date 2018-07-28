package com.security.rest.operation.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.security.rest.operation.config.security.ApplicationAuthenticationEntryPoint;
import com.security.rest.operation.service.ApplicationUserDetailsService;

@Configuration
@EnableWebSecurity
public class ApplicationSecurityConfig extends WebSecurityConfigurerAdapter {

	private static final int PASSWORD_ENCODER_LENGTH = 11;
	
	@Value(value = "${rest.endpoint.user}")
	private String uriContext;
	
	@Autowired
	private ApplicationUserDetailsService applicationUserDetailsService;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(this.authenticationProvider());
	}

	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(this.applicationUserDetailsService);
		authProvider.setPasswordEncoder(this.encoder());
		return authProvider;
	}

	@Bean
	public PasswordEncoder encoder() {
		// Base default password = secr3t1, secr3t2...
		return new BCryptPasswordEncoder(PASSWORD_ENCODER_LENGTH);
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
      http.csrf().disable()
        .authorizeRequests()
        .antMatchers(this.uriContext.concat("/**")).hasAnyRole("ADMIN", "SUPPORT")
        .and().httpBasic().realmName(ApplicationAuthenticationEntryPoint.APPLICATION_REALM)
        	.authenticationEntryPoint(this.getAuthenticationEntryPoint())
        .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);// Does not need sessions to be created.		
	}
    
   @Bean
   public ApplicationAuthenticationEntryPoint getAuthenticationEntryPoint(){
       return new ApplicationAuthenticationEntryPoint();
   }
    
   /* To allow Pre-flight [OPTIONS] request from browser */
   @Override
   public void configure(WebSecurity web) throws Exception {
       web.ignoring().antMatchers(HttpMethod.OPTIONS, "/**");
   }

}

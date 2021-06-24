package kr.co.ensmart.frameworkdemo.base.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.authorizeRequests()
				.antMatchers("/x2commerce-system/health/readiness").permitAll()
				.antMatchers("/x2commerce-system/health/liveness").permitAll()
				.antMatchers("/x2commerce-system/**").hasRole("SUPER_USER")
				.anyRequest().permitAll();
	}
				
}

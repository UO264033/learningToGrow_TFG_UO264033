package com.uniovi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.thymeleaf.extras.springsecurity4.dialect.SpringSecurityDialect;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserDetailsService userDetailsService;

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable()
			.authorizeRequests()
				.antMatchers("/css/**", "/img/**", "/script/**", "/", "/signup", "/login/**").permitAll()
				.antMatchers("/homework/add").hasAuthority("ROLE_PROFESSOR")
				.antMatchers("/homework/edit/*").hasAnyAuthority("ROLE_PROFESSOR", "ROLE_ADMIN")
				.antMatchers("/homework/delete/*").hasAuthority("ROLE_PROFESSOR")
				.antMatchers("/homework/do/**").hasAuthority("ROLE_STUDENT")
				.antMatchers("/homework/correct/**").hasAuthority("ROLE_PROFESSOR")
				.antMatchers("/homework/**").hasAnyAuthority("ROLE_STUDENT", "ROLE_PROFESSOR", "ROLE_ADMIN")
				.antMatchers("/user/student/list").hasAuthority("ROLE_PROFESSOR")
				.antMatchers("/user/student/search").hasAuthority("ROLE_PROFESSOR")
				.antMatchers("/user/addGroup").hasAuthority("ROLE_PROFESSOR")
				.antMatchers("/subject/list").hasAnyAuthority("ROLE_STUDENT", "ROLE_PROFESSOR")
				.antMatchers("/subject/details/*").hasAnyAuthority("ROLE_STUDENT", "ROLE_PROFESSOR")
				.antMatchers("/subject/**").hasAuthority("ROLE_PROFESSOR")
				.antMatchers("/exercise/**").hasAuthority("ROLE_PROFESSOR")
				.antMatchers("/user/perfil").hasAnyAuthority("ROLE_STUDENT", "ROLE_PROFESSOR", "ROLE_ADMIN")
				.antMatchers("/user/**").hasAuthority("ROLE_ADMIN")
			.anyRequest()
				.authenticated()
				.and()
				.formLogin().loginPage("/login").permitAll()
				.defaultSuccessUrl("/home")
				.and()
				.logout().permitAll();
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}
	
	@Bean
	public SpringSecurityDialect securityDialect() {
	return new SpringSecurityDialect();
	}

}

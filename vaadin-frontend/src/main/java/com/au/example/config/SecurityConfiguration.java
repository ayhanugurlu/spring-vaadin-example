package com.au.example.config;

import com.au.example.views.LoginView;
import com.vaadin.flow.spring.security.VaadinWebSecurity;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;

@EnableWebSecurity
@Configuration
public class SecurityConfiguration extends VaadinWebSecurity {


  @Override
  protected void configure(HttpSecurity httpSecurity) throws Exception {
    super.configure(httpSecurity);
    setLoginView(httpSecurity, LoginView.class);
  }

  @Bean
  UserDetailsManager userDetailsManager() {
    return new InMemoryUserDetailsManager(
        User.withUsername("ayhan")
            .password("{noop}ayhan")
            .roles("USER").build()
    );
  }
}

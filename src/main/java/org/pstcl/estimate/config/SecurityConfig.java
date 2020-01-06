
package org.pstcl.estimate.config;

import org.pstcl.estimate.util.GlobalProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

//	 @Override
//	    protected void configure(HttpSecurity http) throws Exception{
//	        http.authorizeRequests().antMatchers("/").permitAll();
//	    }
	
	
	@Autowired
	private GlobalProperties globalProperties;
	 
	 @Override
	    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
	    	 PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
	    	auth
	          .inMemoryAuthentication()
	          .withUser(globalProperties.getUserPassword())
	          .password(encoder.encode(globalProperties.getUserPassword()))
	          .roles("USER")
	          .and()
	          .withUser(globalProperties.getAdminUsername())
	          .password(encoder.encode(globalProperties.getAdminPassword()))
	          .roles("USER", "ADMIN")
	          .and()
	          .withUser(globalProperties.getNode1Username())
	          .password(encoder.encode(globalProperties.getNode1Password()))
	          .roles("USER", "ADMIN").and()
	          .withUser(globalProperties.getNode2Username())
	          .password(encoder.encode(globalProperties.getNode2Password()))
	          .roles("USER", "ADMIN").and()
	          .withUser(globalProperties.getNode3Username())
	          .password(encoder.encode(globalProperties.getNode3Password()))
	          .roles("USER", "ADMIN").and()
	          .withUser(globalProperties.getNode4Username())
	          .password(encoder.encode(globalProperties.getNode4Password()))
	          .roles("USER", "ADMIN");
	    	
	    }
	
//    @Override
//    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//    	 PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
//    	auth
//          .inMemoryAuthentication()
//          .withUser("user")
//        //  .password("{noop}password")
//          .password(encoder.encode("password"))
//          .roles("USER")
//          .and()
//          .withUser("admin")
//          .password("admin")
//          .roles("USER", "ADMIN");
//    	
//    }

//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http
//          .authorizeRequests()
//          .anyRequest()
//          .authenticated()
//          .and()
//          .httpBasic();
//      //  http.csrf().disable();
//       
//        
//    }


}

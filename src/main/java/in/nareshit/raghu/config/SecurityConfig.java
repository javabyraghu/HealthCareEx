package in.nareshit.raghu.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import in.nareshit.raghu.constants.UserRoles;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth
		.userDetailsService(userDetailsService)
		.passwordEncoder(passwordEncoder);
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()
		.antMatchers("/patient/register","/patient/save","/user/showForgot","/user/genNewPwd").permitAll()
		.antMatchers("/spec/**").hasAuthority(UserRoles.ADMIN.name())
		.antMatchers("/doctor/**").hasAuthority(UserRoles.ADMIN.name())
		.antMatchers("/doc/**").hasAuthority(UserRoles.ADMIN.name())
		.antMatchers("/appointment/register","/appointment/save","/appointment/all").hasAuthority(UserRoles.ADMIN.name())
		.antMatchers("/appointment/view","/appointment/viewSlot").hasAuthority(UserRoles.PATIENT.name())
		.antMatchers("/slots/book","/slots/cancel").hasAuthority(UserRoles.PATIENT.name())
		.antMatchers("/slots/all","/slots/accept","/slots/reject","/slots/dashboard").hasAuthority(UserRoles.ADMIN.name())
		.antMatchers("/user/login","/login").permitAll()
		
		.anyRequest().authenticated()
		
		.and()
		.formLogin()
		.loginPage("/user/login") //show Login Page
		.loginProcessingUrl("/login") //POST (do login)
		.defaultSuccessUrl("/user/setup",true)
		.failureUrl("/user/login?error=true") //If login is failed
		
		.and()
		.logout()
		.logoutRequestMatcher(new AntPathRequestMatcher("/logout")) //URL for Logout
		.logoutSuccessUrl("/user/login?logout=true") // On logout success
		;
	}
}

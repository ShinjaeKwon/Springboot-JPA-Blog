package com.cos.blog.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

// 빈 등록 : 스프링 컨테이너에서 객체를 관리할 수 있게 하는 것
@Configuration //빈등록 (IoC 관리)
@EnableWebSecurity // 시큐리티 필터가 등록이 등록이 된다.
@EnableGlobalMethodSecurity(prePostEnabled = true) //특정 주소로 접근을 하면 권한 및 인증을 미리 체크하겠다.
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Bean //IoC가 된다.
	public BCryptPasswordEncoder encodePWD() {
		return new BCryptPasswordEncoder(); //리턴값을 Spring이 관리한다.
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.csrf().disable() //csrf 토큰 비활성화 (테스트시 걸어두는 게 좋다)
			.authorizeRequests()
				.antMatchers("/","/auth/**", "/js/**", "css/**","/image/**") 
				.permitAll()  // 이 주소들은 누구나 들어올 수 있다.
				.anyRequest() //위에가 아닌것들은
				.authenticated() //인증이 되야된다
			.and()
				.formLogin()
				.loginPage("/auth/loginForm");
	}
}

// xss : 자바스크립트 공격 
// csrf : 


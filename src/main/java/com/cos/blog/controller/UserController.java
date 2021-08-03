package com.cos.blog.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.cos.blog.config.auth.PrincipalDetail;

//인증이 안된 사용자들이 출입할 수 있는 경로를 /auth/** 만 허용
// 그냥 주소가 / 이면 index.jsp 허용 
// static 이하에 있는 /js/**, /css/**, /image/**  

@Controller
public class UserController {

	@GetMapping("/auth/joinForm")
	public String joinForm() {
		
		return "user/joinForm";
	}

	@GetMapping("/auth/loginForm")
	public String loginForm() {
		
		return "user/loginForm";
	}
	
	@GetMapping("/user/updateForm")
	public String updateForm(@AuthenticationPrincipal PrincipalDetail principal) {
		
		return "user/updateForm";
	}
}
//@AuthenticationPrincipal : SecurityContextHolder에 존재하는객체를 가져온다
//스프링 시큐리티는 세션이란 공간이 있다.
//이 세션에 유저 정보를 저장을 한다.
// 기존의 세션은 user 정보를 session에 직접 집어넣는다.
// 스프링 시큐리티는 세션에 user정보를 직접 넣지 않고, 
//세션안의 시큐리티 컨텍스트라는 공간에  Authentication 객체를 저장해서 관리를 한다.
//Authentication을 만들어 주는 객체는 AuthenticationManager가 만들어준다.
//사용자가 로그인 요청을 하면 username과 password를 날린다.
//그 후에 AuthenticationFilter가 중간에서 가로챈다.  -> 로그인 필터
//AuthenticationFilter는 UsernamePasswordAuthenticationToken이라는 것을 만들어 준다.
//UsernamePasswordAuthenticationToken는 전달된 username과 password 기반으로 만들어진다.
//UsernamePasswordAuthenticationToken를 AuthenticationManager에게 던지면
//AuthenticationManager는 세션을 만들어 준다. 하지만 조건이 있다.
//AuthenticationManager는 username만 UserDetailService에게 던진다. 
//이 프로젝트에서 UserDetailService는 PrincipalDetailService이다.
//UserDetailService는 DB에 접근을 해서 username의 데이터 질의를 한다.
// 만약 있으면 있다고 응답을 해 UserDetailService에게 전달하고,
//UserDetailService은 다시 AuthenticationManager에게 전달을 한다.
//AuthenticationManager는 password를 Bcript로 인코딩을 한다. (암호화)
//다시 암호화된 password를 DB쪽으로 전달하여 password가 맞는지 확인한다.
//여기까지 확인을 하면 AuthenticationManager는 Authentication객체를 만들어준다.
//Authentication객체를 세션안에있는 시큐리티 컨텍스트에 저장을 한다.

//정리
//username과 password를 받아서 필터 낚아채서 토큰을 만든다.
// 이 토큰은 username과 password 기반으로 만들어졌다.
// 토큰을 AuthenticationManager에게 던진다. 던지는 이유는 Authentication객체를 만들기 위해서이다.
// 이 객체를 만들기위해서는 조건이 필요한데 먼저 전달된 username을 체크하고, 인코딩된 password를 체크한다.
// 이제 데이터가 일치하면 Authentication객체를 만들어서 세션에 저장을 한다.
// 세션안에 시큐리티 컨텍스트에는 User객체를 직접 저장하지 못하기때문에 , 
// Authentication 생성해서 저장한다.
// 즉 위과정은 Authentication객체를 저장하기 위한 흐름이다.




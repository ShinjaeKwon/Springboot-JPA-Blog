package com.cos.blog.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cos.blog.dto.ResponseDto;
import com.cos.blog.model.User;
import com.cos.blog.service.UserService;

@RestController
public class UserApiController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private AuthenticationManager authenticationManager;

	@PostMapping("/auth/joinProc")
	public ResponseDto<Integer> save(@RequestBody User user) { // username, password, email
		System.out.println("UserApiController : save 호출됨");
	
		userService.회원가입(user);
		return new ResponseDto<Integer>(HttpStatus.OK.value(), 1); // 자바오브젝트를 JSON으로 변환해서 리턴(Jackson)
	}
	
	@PutMapping("/user")
	public ResponseDto<Integer> update(@RequestBody User user) { 
		userService.회원수정(user);
		//여기서는 트렌잭션이 종료되기 때문에 DB에 값은 변경이 되지만,
		//세션값은 변경이 되지 않은 상태이기 때문에 우리가 직접 세션값을 변경해준다.
		
		//세션 등록
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
				
		
		return new ResponseDto<Integer>(HttpStatus.OK.value(), 1); 
	}

	

}
//@RequestBody : JSON으로 데이터를 받는다.
//만약 위의 어노테이션을 적지 않으면 key=value , x-www-form-urlencoded로 데이터를 받는다.

//강제로 세션값을 바꾸는 부분이다. --실패 (강
//@PutMapping("/user")
//public ResponseDto<Integer> update(@RequestBody User user, 
//		@AuthenticationPrincipal PrincipalDetail principal, 
//		HttpSession session) { 
//	userService.회원수정(user);

//	Authentication authentication =
//			new UsernamePasswordAuthenticationToken(principal, null, principal.getAuthorities());
//	SecurityContext securityContext = SecurityContextHolder.getContext();
//	securityContext.setAuthentication(authentication);
//	session.setAttribute("SPRING_SECURITY_CONTEXT", securityContext);
//	return new ResponseDto<Integer>(HttpStatus.OK.value(), 1); 
//}

/*	//전통적인 로그인 방식
@PostMapping("/api/user/login")
public ResponseDto<Integer> login(@RequestBody User user, HttpSession session) {
	System.out.println("UserApiController : login 호출됨");
	User principal = userService.로그인(user); // principal : 접근 주체

	if (principal != null) {
		session.setAttribute("principal", principal); // 세션 생성 HttpSession session
	}
	return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
}*/

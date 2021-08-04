package com.cos.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;

import com.cos.blog.config.auth.PrincipalDetail;
import com.cos.blog.model.KakaoProfile;
import com.cos.blog.model.OAuthToken;
import com.cos.blog.model.User;
import com.cos.blog.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

//인증이 안된 사용자들이 출입할 수 있는 경로를 /auth/** 만 허용
// 그냥 주소가 / 이면 index.jsp 허용 
// static 이하에 있는 /js/**, /css/**, /image/**  

@Controller
public class UserController {
	
	@Value("${cos.key}")
	private String cosKey;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private UserService userService;

	@GetMapping("/auth/joinForm")
	public String joinForm() {
		
		return "user/joinForm";
	}

	@GetMapping("/auth/loginForm")
	public String loginForm() {
		
		return "user/loginForm";
	}
	
	@GetMapping("/auth/kakao/callback")
	public String kakaoCallback(String code) { //Data를 리턴해주는 컨트롤러 함수
		
		// POST 방식으로 key=value 데이터를 요청(카카오쪽으로)
		//Retorfit2
		//OkHttp
		//RestTemplate
		
		RestTemplate rt = new RestTemplate();
		
		//HttpHeader 오브젝트를 생성
		HttpHeaders headers = new HttpHeaders();
		
		//HttpBody 오브젝트 생성
		headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		
		params.add("grant_type", "authorization_code");
		params.add("client_id", "8583161e742685cda04a453456426b39");
		params.add("redirect_uri", "http://localhost:9090/auth/kakao/callback");
		params.add("code", code);
		
		//HttpHeader와 HttpBody를 하나의 오브젝트에 담기
		HttpEntity<MultiValueMap<String,String>> kakaoTokenRequest = 
				new HttpEntity<>(params, headers);
		
		//Http 요청하기 - POST 방식으로 - 그리고 response 변수의 응답을 받음
		ResponseEntity<String> response = rt.exchange(
				"https://kauth.kakao.com/oauth/token",
				HttpMethod.POST,
				kakaoTokenRequest,
				String.class
		);
		
		// Gson, Json Simple, ObjectMapper
		ObjectMapper objectMapper = new ObjectMapper();
		OAuthToken oauthToken = null;
		try {
			oauthToken = objectMapper.readValue(response.getBody(), OAuthToken.class);
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
		System.out.println("카카오 엑세스 토큰 : "+oauthToken.getAccess_token());
		
		RestTemplate rt2 = new RestTemplate();
		
		//HttpHeader 오브젝트를 생성
		HttpHeaders headers2 = new HttpHeaders();
		headers2.add("Authorization", "Bearer "+oauthToken.getAccess_token());
		headers2.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

		//HttpHeader와 HttpBody를 하나의 오브젝트에 담기
		HttpEntity<MultiValueMap<String,String>> kakaoProfileRequest2 = 
				new HttpEntity<>(headers2);
		
		//Http 요청하기 - POST 방식으로 - 그리고 response 변수의 응답을 받음
		ResponseEntity<String> response2 = rt2.exchange(
				"https://kapi.kakao.com/v2/user/me",
				HttpMethod.POST,
				kakaoProfileRequest2,
				String.class
		);
		
		ObjectMapper objectMapper2 = new ObjectMapper();
		KakaoProfile kakaoProfile = null;
		try {
			kakaoProfile = objectMapper2.readValue(response2.getBody(), KakaoProfile.class);
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
		//User오브젝트 : username, password, email
		System.out.println("카카오 아이디(번호) : "+kakaoProfile.getId());
		System.out.println("카카오 이메일 : "+kakaoProfile.getKakao_account().getEmail());
		
		System.out.println("블로그서버 유저네임 : "+kakaoProfile.getKakao_account().getEmail()+
				"_"+kakaoProfile.getId());
		System.out.println("블로그서버 이메일 : "+kakaoProfile.getKakao_account().getEmail());
		
		//UUID란 -> 중복되지 않는 어떤 특정 값을 만들어내는 알고리즘
		System.out.println("블로그서버 패스워드 : " +cosKey);
		
		User kakaoUser = User.builder()
				.username(kakaoProfile.getKakao_account().getEmail()+
						"_"+kakaoProfile.getId())
				.password(cosKey)
				.email(kakaoProfile.getKakao_account().getEmail())
				.oauth("kakao")
				.build();
		
		//가입자 혹은 비가입자 체크 해서 처리
		User originUser =userService.회원찾기(kakaoUser.getUsername());
		
		if(originUser.getUsername() == null) {
		System.out.println("기존 회원이 아니기에 자동 회원가입을 진행합니다.");
		userService.회원가입(kakaoUser);
		}
		
		System.out.println("자동 로그인을 진행합니다.");
		//로그인 처리
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(kakaoUser.getUsername(), cosKey));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		
		return "redirect:/";
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




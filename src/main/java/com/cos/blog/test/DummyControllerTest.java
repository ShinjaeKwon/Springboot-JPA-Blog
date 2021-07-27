package com.cos.blog.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cos.blog.model.RoleType;
import com.cos.blog.model.User;
import com.cos.blog.repository.UserRepository;

@RestController //문자 자체를 리턴 
public class DummyControllerTest {
	
	@Autowired //DummyControllerTest가 메모리에 적재될때 같이 적재된다. / 의존성 주입(DI)
	private UserRepository userRepository;

	/*@PostMapping("/dummy/join")
	public String join(String username, String password, String email) { // key=value (약속된 규칙), 스프링이 알아서 파라미터에 넣어준다.
		System.out.println("username : "+username);
		System.out.println("password : "+password);
		System.out.println("email : "+email);
		return "회원가입이 완료되었습니다.";
	}*/
	
	@PostMapping("/dummy/join") //파라미터를 오브젝트로 받을 수 도 있다.
	public String join(User user) { 
		System.out.println("id : "+user.getId());
		System.out.println("username : "+user.getUsername());
		System.out.println("password : "+user.getPassword());
		System.out.println("email : "+user.getEmail());
		System.out.println("role : "+user.getRole());
		System.out.println("createDate : "+user.getCreateDate());
		
		user.setRole(RoleType.USER);
		userRepository.save(user);
		return "회원가입이 완료되었습니다.";
	}
	
}

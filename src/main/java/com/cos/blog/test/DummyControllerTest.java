package com.cos.blog.test;

import java.util.List;
import java.util.function.Supplier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cos.blog.model.RoleType;
import com.cos.blog.model.User;
import com.cos.blog.repository.UserRepository;

@RestController //문자 자체를 리턴 
public class DummyControllerTest { //html파일이 아니라 data를 리턴해주는 Controller = RestController
	
	@Autowired //DummyControllerTest가 메모리에 적재될때 같이 적재된다. / 의존성 주입(DI)
	private UserRepository userRepository;

	
	@GetMapping("/dummy/users")
	public List<User> list(){ //전체 회원 리턴
		return userRepository.findAll();
	}
	
	//한 페이지당 2건에 데이터를 리턴받는다.
	@GetMapping("dummy/user")
	public List<User> pageList(@PageableDefault(size=2, sort="id",direction = Direction.DESC)Pageable pageable){
		List<User> users =userRepository.findAll(pageable).getContent();
		return users;
	}
	
	
	/******************************* 상세보기 ******************************/
	//{id}주소로 파라미터를 전달 받을 수 있다.
	//http://localhost:9090/blog/dummy/user/3
	//@PathVariable : 매핑의 URL에 {}로 들어가는 패스 변수를 받는다.
	
//	@GetMapping("/dummy/user/{id}")   // 첫번째 방법 : get() 으로 보내기, 두번째방법 새로운 객체 만들어서 반환
//	public User detail(@PathVariable int id) {
//		//리턴타입이 Optional인 이유 : db에서 못찾으면 user가 null이 되는데, 그럼 return시 null이 리턴되 문제가 생긴다.
//		//때문에 Optional로 User 객체를 감싸서 가져와 사용자가 null인지 판단해서 return 한다.
//		User user = userRepository.findById(id).orElseGet(new Supplier<User>() {  //orElseGet : null일시 작동
//
//			@Override
//			public User get() {
//				return new User(); // 널이아닌 빈객체를 리턴해준다.
//			}
//		});
//		return user;
//	}
	@GetMapping("/dummy/user/{id}") 
	public User detail(@PathVariable int id) { //세번째 방법
		User user = userRepository.findById(id).orElseThrow(new Supplier<IllegalArgumentException>() {
			@Override
			public IllegalArgumentException get() {
				return new IllegalArgumentException("해당 유저는 없습니다. id : "+id) ;
			}
		});
		// 요청 : 웹브라우저 
		// user 객체 = 자바 오브젝트
		// 변환을 해주어야 한다.(웹 브라우저가 이해할 수 있는 데이터로) -> json
		//스프링 부트 = MessageConverter라는 애가 응답시에 자동 작동
		// 만약에 자바 오브젝트를 리턴하게 되면 MessageConverter가 Jackson이라는 라이브러리를 호출해서
		// user오브젝트를 json으로 변환해서 브라우저에게 던져준다.
		return user;
	}
//	//네번째 방법 : 람다식 이용
//	@GetMapping("/dummy/user/{id}") 
//	public User detail(@PathVariable int id) { //세번째 방법
//		User user = userRepository.findById(id).orElseThrow(()->{
//			return new IllegalArgumentException("해당 사용자는 없습니다.");
//		});
//		return user;
//	}
	
	/*@PostMapping("/dummy/join")
	public String join(String username, String password, String email) { // key=value (약속된 규칙), 스프링이 알아서 파라미터에 넣어준다.
		System.out.println("username : "+username);
		System.out.println("password : "+password);
		System.out.println("email : "+email);
		return "회원가입이 완료되었습니다.";
	}*/
	
	/******************************* 회원가입 ******************************/
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

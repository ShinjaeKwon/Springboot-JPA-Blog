package com.cos.blog.test;

import java.util.List;
import java.util.function.Supplier;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cos.blog.model.RoleType;
import com.cos.blog.model.User;
import com.cos.blog.repository.UserRepository;


@RestController //문자 자체를 리턴 
public class DummyControllerTest { //html파일이 아니라 data를 리턴해주는 Controller = RestController
	
	@Autowired //DummyControllerTest가 메모리에 적재될때 같이 적재된다. / 의존성 주입(DI)
	private UserRepository userRepository;

/*******************************  삭제 **********************************************************************************/
	
	@DeleteMapping("/dummy/user/{id}")
	public String delete(@PathVariable int id) {
		try {
			userRepository.deleteById(id);
		}catch (EmptyResultDataAccessException e) {
			return "삭제에 실패하였습니다. 해당 id는 DB에 없습니다.";
		}
		
		return "삭제되었습니다. id : "+id;
	}
	
	
	
	
/*******************************  업데이트 ******************************************************************************/
	//email, Password
	@Transactional //함수 종료시에 자동 commit 
	@PutMapping("/dummy/user/{id}") // json 데이터 요청 => Java Object(MessageConverter의 Jackson 라이브러리가 변환해서 받아준다.
	public User updateUser(@PathVariable int id, @RequestBody User requestUser) { //json 데이터를 받으려면 @RequestBody를 사용해야 한다.
		System.out.println("id : "+id);
		System.out.println("password : "+requestUser.getPassword());
		System.out.println("email : "+requestUser.getEmail());
		
		// orElseThrow : 못찾으면 실행 
		User user = userRepository.findById(id).orElseThrow(()->{ //select를 할때 영속화가 일어난다. 
			return new IllegalArgumentException("수정에 실패하였습니다.");
		});
		user.setPassword(requestUser.getPassword()); // 값 변경후, 함수가 끝나면 @Transactional으로 인해 자동 Commit이 된다.
		user.setEmail(requestUser.getEmail()); // Commit이 되면 영속화 컨테스트랑 기존 DB랑 비교해 변화가 일어나면 자동으로 인식하여, 
		//컨트롤러 종료시에 변화에 따른 자동 업데이트를 해준다.
		//즉 변경감지 -> 더티 체킹이라 한다.
		
//		userRepository.save(user);  // 첫번째 방법
		//save를 사용하지 않고 저장 : 더티 체킹 @Transactional을 사용하면  save를 걸지 않아도 update가 된다. (두번째방법)
		return user;
	}
	//save 함수는 id를 전달하지 않으면 insert를 해주고 
	//save 함수는 id를 전달하면 해당 id에 대한 데이터가 있으면 update를 해주고
	//save 함수는 id를 전달하면 해당 id에 대한 데이터가 없으면 insert를 해준다.
	
/*******************************  페이징기법 *****************************************************************************/

	@GetMapping("/dummy/users")
	public List<User> list(){ //전체 회원 리턴
		return userRepository.findAll();
	}
	
	//한 페이지당 2건에 데이터를 리턴받는다.
	@GetMapping("dummy/user")
	public Page<User> pageList(@PageableDefault(size=2, sort="id",direction = Direction.DESC)Pageable pageable){
		Page<User> pagingUser =userRepository.findAll(pageable);
		
		if(pagingUser.isFirst()) {//첫번째 데이터인가? , isLast() : 마지막 데이터인가?
			
		}
		
		List<User> users = pagingUser.getContent();//getcontent() : Content 타입만 받는다.
		return pagingUser;
	}
	
	
/*******************************  상세보기 ******************************************************************************/	
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
				return new IllegalArgumentException("해당 사용자는 없습니다.") ;
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
	
/*******************************  회원가입 ******************************************************************************/
	@PostMapping("/dummy/join") //파라미터를 오브젝트로 받을 수 도 있다.
	public String join(User user) { 
		System.out.println("id : "+user.getId());
		System.out.println("username : "+user.getUsername());
		System.out.println("password : "+user.getPassword());
		System.out.println("email : "+user.getEmail());
		System.out.println("role : "+user.getRole());
		System.out.println("createDate : "+user.getCreateDate());
		
		user.setRole(RoleType.USER);
		userRepository.save(user); //save는 insert 할때 사용하는 것
		return "회원가입이 완료되었습니다.";
	}
	
}

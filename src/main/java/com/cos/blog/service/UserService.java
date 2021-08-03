package com.cos.blog.service;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.blog.model.RoleType;
import com.cos.blog.model.User;
import com.cos.blog.repository.UserRepository;

// @Service : 스프링이 컴포넌트 스캔을 통해서 Bean에 등록을 해준다. IoC를 해준다.
@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired //DI가 되서 주입된다.
	private BCryptPasswordEncoder encoder;
	
	
	
	@Transactional //하나의 트랜잭션으로 묶어준다. 하나라도 실패하면 롤백을 해야한다.(따로 짜야됨)
	public void 회원가입(User user) {
		String rawPassword = user.getPassword(); //원문
		String encPassword = encoder.encode(rawPassword); //해쉬
		user.setPassword(encPassword); //해쉬로 데이터 삽입
		user.setRole(RoleType.USER);
		userRepository.save(user);
	}

	@Transactional 
	public void 회원수정(User user) {
		//수정시에는 영속성 컨텍스트 User 오브젝트를 영속화를 시키고 ,영속화된 User 오브젝트를 수정
		//Select를 먼저 해서 User 오브젝트를 DB로 부터 가져오는 이유는 영속화를 하기 위해서이다.
		// 영속화된 오브젝트를 변경하면 자동으로 DB에 update문을 날려준다.
		User persistance = userRepository.findById(user.getId()).orElseThrow(()->{
			return new IllegalArgumentException("회원 찾기 실패");
		});
		String rawPassword = user.getPassword();
		String encPassword = encoder.encode(rawPassword);
		persistance.setPassword(encPassword);
		persistance.setEmail(user.getEmail());	
		//회원수정 함수 종료시 = 서비스 종료시 = 트랜잭션이 종료 = commit이 자동으로 된다.
		//영속화된 persistance 객체의 변화가 감지되면 더티체킹이 되어 update문을 날려준다.
		
	}
	
}

//서비스가 필요한 이유
//1. 트랜잭션 관리
//2. 서비스 의미 때문


//@Transactional(readOnly = true) // Select 할 때 트랜잭션이 시작 , 서비스 종료시 트랜잭션 종료 (정합성 유지) 
//public User 로그인(User user) {
//	return userRepository.findByUsernameAndPassword(user.getUsername(), user.getPassword());
//}
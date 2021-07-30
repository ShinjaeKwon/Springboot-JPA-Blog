package com.cos.blog.service;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.blog.model.User;
import com.cos.blog.repository.UserRepository;

// @Service : 스프링이 컴포넌트 스캔을 통해서 Bean에 등록을 해준다. IoC를 해준다.
@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Transactional //하나의 트랜잭션으로 묶어준다. 하나라도 실패하면 롤백을 해야한다.(따로 짜야됨)
	public void 회원가입(User user) {
		userRepository.save(user);
	}
	
	@Transactional(readOnly = true) // Select 할 때 트랜잭션이 시작 , 서비스 종료시 트랜잭션 종료 (정합성 유지) 
	public User 로그인(User user) {
		return userRepository.findByUsernameAndPassword(user.getUsername(), user.getPassword());
	}
	
}

//서비스가 필요한 이유
//1. 트랜잭션 관리
//2. 서비스 의미 때문
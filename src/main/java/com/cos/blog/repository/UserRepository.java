package com.cos.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cos.blog.model.User;

// DAO
// 자동으로 bean등록이 된다.
//@Repository //생략 가능하다.
public interface UserRepository  extends JpaRepository<User, Integer>{ //User테이블을 관리하고, PK는 Integer
	
	//JPA Naming 전략  -> 쿼리 작성
	// SELECT * FROM user WHERE username = ? AND password =? 
	//객체 findBy A And B  = SELECT * FROM 객체(테이블) WHERE A =? AND B = ?
	User findByUsernameAndPassword(String username, String password);
	
	
	//두번째 방법 쿼리를 직접 작성
//	@Query(value="SELECT * FROM user WHERE username =?1AND password = ?2", nativeQuery = true)
//	User login(String username, String password);
}

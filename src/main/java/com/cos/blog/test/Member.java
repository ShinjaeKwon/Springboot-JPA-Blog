package com.cos.blog.test;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

//@Getter : getter 자동 생성
//@Setter : setter 자동 생성
//@Data : getter & setter 자동생성
//@AllArgsConstructor : 생성자 자동 생성
//@RequiredArgsConstructor :final이 있는 변수 생성자 생성 
//@NoArgsConstructor : 빈생성자( 디폴트 생성자)
@Data
//@AllArgsConstructor
@NoArgsConstructor
public class Member {
	private int id;
	private String username;
	private String password;
	private String email;
	
	@Builder
	//Builder의 장점은 생성자의 순서가 상관없다.
	//생성자를 통해 객체를 생성할때는 순서를 반드시 지켜야한다.
	public Member(int id, String username, String password, String email) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.email = email;
	}
	
	

	
	
}

package com.cos.blog.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.CreationTimestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder //빌더 패턴
//ORM -> Java(다른언어) Object -> 테이블로 매핑해주는 기술
@Entity // User 클래스가 MySQL에 테이블이 생성된다. 
//@DynamicInsert // insert할때 null인 필드를 제외한다. 대신 enum 사용
public class User {
	
	@Id //Primary key
	@GeneratedValue(strategy = GenerationType.IDENTITY) //프로젝트에서 연결된 DB의 넘버링 전략을 따라간다.
	private int id; // 시퀀스, auto_increment
	
	@Column(nullable=false, length = 30) // @Column(nullable=false) : 널을 허락하지 않는다, legnth = 길이 설정 
	private String username; //아이디
	
	@Column(nullable=false, length = 100) // password 길이를 100으로 잡는 이유 : 해쉬(비밀번호 암호와)를 위해서
	private String password; 
	
	@Column(nullable=false, length = 50)
	private String email; 
	
	@Enumerated(EnumType.STRING)  //ENUM 타입이 String이라고 알려준다.
	//@ColumnDefault("'user'") // 회원가입시 기본 role은 user이다.
	private RoleType role; // Enum을 쓰는게 좋다. (데이터의 도메인을 만들 수 있다), //admin, user, manager처럼 권한을 준다. Enum전략을 쓸시 오타를 방지할 수 있다
	
	//도메인 : 범위가 정해짐. ex) 성별 : 남/여 등
	
	@CreationTimestamp //시간이 자동으로 입력된다. 
	private Timestamp createDate;
	
	
}

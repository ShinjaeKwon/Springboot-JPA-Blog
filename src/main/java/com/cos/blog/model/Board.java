package com.cos.blog.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

@Entity
public class Board {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) //auto_increment
	private int id;
	
	@Column(nullable = false, length = 100)
	private String title;
	
	@Lob // 대용량 데이터 
	private String content;  //섬머 노트 라이브러리 <html> 태그가 섞여서 디자인이 된다.
	
	@ColumnDefault("0")
	private int count; //조회수
	
	@ManyToOne // Board : many , user : one, 한명의 유저는 여러개의 게시글 작성이 가능하다. (연관관계 설정)
	@JoinColumn(name="userId") // 데이터베이스에 만들어질 때 컬럼이름은 설정한 값으로 만들어진다. 자동으로 FK키를 만들어준다.
	private User user; //DB는 오브젝트를 저장할 수 없다. FK, 자바는 오브젝트를 저장할 수 있다. JPA (ORM)을 사용하면 오브젝트를 저장할 수 있다.
	
	@CreationTimestamp //자동 현재 시간 삽입
	private Timestamp createDate;
}

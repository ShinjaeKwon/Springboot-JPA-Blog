package com.cos.blog.model;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder //빌더 패턴
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
	
	@ManyToOne(fetch = FetchType.EAGER) // Board : many , user : one, 한명의 유저는 여러개의 게시글 작성이 가능하다. (연관관계 설정)
	@JoinColumn(name="userId") // 데이터베이스에 만들어질 때 컬럼이름은 설정한 값으로 만들어진다. 자동으로 FK키를 만들어준다.
	private User user; //DB는 오브젝트를 저장할 수 없다. FK, 자바는 오브젝트를 저장할 수 있다. JPA (ORM)을 사용하면 오브젝트를 저장할 수 있다.
	
	@OneToMany(mappedBy = "board", fetch = FetchType.EAGER) // 하나의 게시글에는 여러개의 답변을 달 수 있다 , mappedBy : 연관관계의 주인이 아니다.(난 FK가 아니다) DB에 컬럼을 만들지 말라.
	private List<Reply> reply;
	
	@CreationTimestamp //자동 현재 시간 삽입
	private Timestamp createDate;
}

// @OneToMany의 default fetch값 : fetch = FetchType.LAZY,  필요할때 땡겨올 경우(댓글 펼치기) LAZY 전략 사용
// @ManyToOne의 default fetch값 : fetch = FetchType.EAGER, Board 테이블을 select하면 user정보가 한개니까 가져오겠다. , 테이블이 select되면 무조건 데이터를 들고와라
// 만약 댓글 펼치기 기능없이 바로 게시글에 댓글이 나타날 경우 같이 땡겨와야하기 때문에 EAGER 전략을 사용한다.


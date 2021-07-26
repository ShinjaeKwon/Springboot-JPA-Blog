package com.cos.blog.test;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

//사용자가 요청 -> 응답(HTML 파일)
//@Controller

//사용자가 요청 -> 응답(data)

@RestController
public class HttpControllerTest {
	
	private static final String TAG = "HttpController Test : ";
	
	@GetMapping("/http/lombok")
	public String lombokTest() {
		Member m1 = Member.builder().username("ssar").password("1234").email("ssar@nate.com").build();
		System.out.println(TAG+"getter : "+m1.getUsername());
		m1.setUsername("cos");
		System.out.println(TAG+"getter : "+m1.getUsername());
		return "lombok test 완료";

	}

	//인터넷 브라우저 요청은 무조건 get 요청밖에 하지 못한다.
	//http://localhost:9090/http/get(select)
	@GetMapping("/http/get")
/*	public String getTest(@RequestParam int id, @RequestParam String username){ //@RequestParam 방식으로 전달할시 한개한개 다 써줘야 한다.
		return "get 요청: "+id+", "+username;
	}*/
	
	//id=1&username=ssar&password=1234&email=ssar@nate.com 값을 Member안에 스프링이 넣어준다.
	public String getTest(Member m){ //(매핑 역할 : MessageConverter(스프링 부트))	
		return "get 요청: "+m.getId()+", "+m.getUsername()+", "+m.getPassword()+", "+m.getEmail();
	}
	
	//http://localhost:9090/http/post(insert)
	@PostMapping("/http/post")
/*	public String postTest(Member m){
		return "post 요청: "+m.getId()+", "+m.getUsername()+", "+m.getPassword()+", "+m.getEmail();
	}*/
	
/*	public String postTest(@RequestBody String text){ //raw : text/plain (평문)
		return "post 요청: "+text;
	}*/
	
	public String postTest(@RequestBody Member m){ // appliaction/json (매핑 역할 : MessageConverter(스프링 부트)) -> @RequsetBody (Body에 들어가는 데이터 매핑 )
		return "post 요청: "+m.getId()+", "+m.getUsername()+", "+m.getPassword()+", "+m.getEmail();
	}
	
	//post방식은 form이나 row 방식으로밖에 데이터를 보내지 못한다. 
	
	
	//http://localhost:9090/http/put(update)
	@PutMapping("/http/put")
	public String putTest(@RequestBody Member m){
		return "put 요청: "+m.getId()+", "+m.getUsername()+", "+m.getPassword()+", "+m.getEmail();
	}
	
	//http://localhost:9090/http/delete(delete)
	@DeleteMapping("/http/delete")
	public String deleteTest(){
		return "delete 요청";
	}
	
}

package com.cos.blog.test;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TempControllerTest {
	
	@GetMapping("/temp/home")
	public String tempHome() {
		System.out.println("tempHome()");
		// 파일리턴 기본경로 : src/main/resources/static
		// 리턴명 : /htom.html
		// 풀경로 : src/main/resources/static/home.html
		
		return "/home.html";
	}
	
	@GetMapping("/temp/jsp")
	public String tempJsp() {
		// application.yml에서 jsp파일 설정 경로 
		//   prefix: /WEB-INF/views/
	    //   suffix: .jsp
		//리턴값 양옆에 붙여준다.
		// 풀네임 : /WEB-INF/views/test.jsp
		
		return "test";
	}
}

//@RestController은 문자 자체를 리턴하지만 
//Controller는 해당 경로 이하에 있는 파일을 리턴해준다.
//기본적으로 Spring은 동적파일인 JSP를 static 폴더에서 인식하지 못한다.

package com.cos.blog;

import com.cos.blog.test.Crawling;

//@SpringBootApplication
public class BlogApplication {

	public static void main(String[] args) {
		
		Crawling crawling = new Crawling();
		crawling.crawling();
	
//		Nike nike = new Nike();
//		nike.crawling("https://www.nike.com/kr/launch/?type=upcoming");
//		nike.crawling("https://www.nike.com/kr/ko_kr/w/new/fw/xc/new-mens-shoes");
//		
		System.out.println(" 끝");
//		SpringApplication.run(BlogApplication.class, args);
	}

}

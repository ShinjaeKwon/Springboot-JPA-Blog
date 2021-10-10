package com.cos.blog.crawler;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Nike implements Crawler {

	public void crawling(String url) {

		// 페이지의 모든 소스
		Document doc = null;
		  
		try {
			
			doc = Jsoup.connect(url)
					.get();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		System.out.println(doc.toString());	

		// 이미지
		// div.product-card ncss-row mr0-sm ml0-sm
//		img.img-component image-component mod-image-component u-full-width
//		product-card ncss-row mr0-sm ml0-sm
		Elements element = doc.select("body"); // 신발리스트

		for (Element element1 : element) {
			System.out.println("이미지 : " + element1.select("img").attr("abs:src"));
			System.out.println("url : " + element1.select("a").attr("abs:href"));
			System.out.println("이름 : " + element1.select("a").attr("title"));
		}

	}

}

package com.cos.blog.test;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class Crawling {
	public static final String WEB_DRIVER_ID = "webdriver.chrome.driver"; //드라이버 ID
	public static final String WEB_DRIVER_PATH = "src/main/resources/static/driver/chromedriver.exe"; //드라이버 경로
	
	public void crawling() {
		//드라이버 설정
		try {
			System.setProperty(WEB_DRIVER_ID, WEB_DRIVER_PATH);
		}catch(Exception e) {
			e.printStackTrace();
		}
		try {Thread.sleep(3000);} catch (InterruptedException e) {}
		
		//크롬 설정을 담은 객체 설정
		ChromeOptions options = new ChromeOptions();
		options.addArguments("headless");
		options.addArguments("--start-maximized"); // 전체화면으로 실행
		options.addArguments("--disable-popup-blocking"); // 팝업 무시
		options.addArguments("--disable-default-apps"); // 기본앱 사용안함
		
		WebDriver driver = new ChromeDriver(options);
		
		//웹페이지 요청
		 String url = "https://www.nike.com/kr/launch/?type=upcoming";
		
		driver.get(url);
		try {Thread.sleep(3000);} catch (InterruptedException e) {}
		//신발 리스트
		System.out.println(driver.getPageSource());
		
		List<WebElement> element = driver.findElements(By.cssSelector(".product-card"));
		
//			.findElement(By.cssSelector(".cta-container"))
//			.findElement(By.cssSelector("div > a"));
		
//		String href = element.getAttribute("href");
		WebElement element1 = driver.findElement(By.cssSelector(".product-card"))
				.findElement(By.cssSelector("div > a"))
				.findElement(By.className("img-component"));
		String src = element1.getAttribute("src");
		System.out.println(src);
		
		for(WebElement e : element) {
			e.findElement(By.cssSelector(".cta-container"))
			.findElement(By.cssSelector("div > a"));
			String href = e.getAttribute("href");
			System.out.println(href);
		}
		
		try {
			//드라이버가 null이 아니라면
			if(driver != null) {
				//드라이버 연결 종료
				driver.close(); //드라이버 연결 해제
				
				//프로세스 종료
				driver.quit();
			}
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}

	}
	
	
}

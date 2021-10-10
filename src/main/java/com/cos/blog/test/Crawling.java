package com.cos.blog.test;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class Crawling {
	public static final String WEB_DRIVER_ID = "webdriver.chrome.driver"; //드라이버 ID
//	public static final String WEB_DRIVER_PATH = "src/main/resources/static/driver/chromedriver.exe"; //드라이버 경로
	public static final String WEB_DRIVER_PATH = "src/main/resources/static/driver/chromedriver.exe"; //드라이버 경로
	
	public void crawling() {
		//드라이버 설정
		try {
			System.setProperty(WEB_DRIVER_ID, WEB_DRIVER_PATH);
		}catch(Exception e) {
			e.printStackTrace();
		}
		
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
		try {Thread.sleep(1000);} catch (InterruptedException e) {}
		//신발 리스트
		System.out.println(driver.getPageSource());
		List<WebElement> element = driver.findElements(By.className("headline-3"));
		
		for(WebElement e : element) {
			System.out.println(e);
		}
		
		
				
				
		/*
		 * //이미지 주소 List<WebElement> el1 = driver.findElements(By.
		 * className("img-component image-component mod-image-component u-full-width"));
		 * List<WebElement> el2 = driver.findElements(By.className("card-link d-sm-b"));
		 * //링크 (이름포함) //출시 시간 List<WebElement> el3 =
		 * driver.findElements(By.className("headline-5"));
		 */
		
		
		
		/*
		 * for(int i=0; i< el2.size(); i++) { System.out.println(el2.get(i).getText());
		 * }
		 */
		
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

package com.cos.blog.crawler;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class Crawling {

	public static final String WEB_DRIVER_ID = "webdriver.chrome.driver"; //드라이버 ID
	public static final String WEB_DRIVER_PATH = "src/main/resources/static/driver/chromedriver.exe"; //드라이버 경로
	
	public String[] crawling() {
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

		
		List<WebElement> el1 = driver.findElements(By.cssSelector(".product-card"));
		String[] src_href = new String[el1.size()];
		
		for(int i=0; i<el1.size(); i++){
			
			WebElement img = el1.get(i)
					.findElement(By.cssSelector("div > a"))
					.findElement(By.className("img-component"));
			String src = img.getAttribute("src");
			
			WebElement a = el1.get(i)
					.findElement(By.cssSelector("div > a"));
			String href = a.getAttribute("href");
			String title = a.getAttribute("title");
			
			src_href[i] = title + "|" + src + "|" + href;
			System.out.println(src_href[i]);
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
		
		return src_href;

	}
}

package com.cos.blog.crawling.test;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.boot.test.context.SpringBootTest;

import com.cos.blog.test.Crawling;

@SpringBootTest
public class CrawlingTest {

	public static final String WEB_DRIVER_ID = "webdriver.chrome.driver"; // 드라이버 ID
	public static final String WEB_DRIVER_PATH = "../static/driver/chromedriver.exe"; // 드라이버 경로

	Crawling crawling = new Crawling();

	@Test
	public void test1() {

		// 드라이버 설정
		try {
			System.setProperty(WEB_DRIVER_ID, WEB_DRIVER_PATH);
		} catch (Exception e) {
			e.printStackTrace();
		}

		// 크롬 설정을 담은 객체 설정
		ChromeOptions options = new ChromeOptions();
		options.addArguments("headless");

		WebDriver driver = new ChromeDriver(options);

		String url = "https://www.nike.com/kr/launch/?type=upcoming";

		driver.get(url);

		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
		}

		// 이미지 주소
		List<WebElement> el1 = driver
			.findElements(By.className("img-component image-component mod-image-component u-full-width"));
		List<WebElement> el2 = driver.findElements(By.className("card-link d-sm-b")); // 링크 (이름포함)
		// 출시 시간
		List<WebElement> el3 = driver.findElements(By.className("headline-5"));

		for (int i = 0; i < el2.size(); i++) {
			System.out.println(el2.get(i).getText());
		}

		try {
			// 드라이버가 null이 아니라면
			if (driver != null) {
				// 드라이버 연결 종료
				driver.close(); // 드라이버 연결 해제

				// 프로세스 종료
				driver.quit();
			}
		} catch (Exception e) {
			throw new RuntimeException(e.getMessage());
		}

	}

}

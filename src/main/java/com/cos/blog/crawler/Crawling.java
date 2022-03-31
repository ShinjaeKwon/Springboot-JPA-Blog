package com.cos.blog.crawler;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.safari.SafariDriver;

public class Crawling {

	public String[] crawling() {
		WebDriver driver = new SafariDriver();
		String url = "https://www.nike.com/kr/launch/?type=upcoming";

		driver.get(url);
		JavascriptExecutor js = (JavascriptExecutor)driver;

		for (int i = 100; i < 1000; i += 100) {
			js.executeScript("window.scrollBy(0,+" + i + ")");
			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		List<WebElement> el1 = driver.findElements(By.cssSelector(".product-card"));
		String[] src_href = new String[el1.size()];

		for (int i = 0; i < el1.size(); i++) {
			WebElement img = el1.get(i)
				.findElement(By.cssSelector("div > a"))
				.findElement(By.className("img-component"));
			String src = img.getAttribute("src");

			WebElement a = el1.get(i)
				.findElement(By.cssSelector("div > a"));
			String href = a.getAttribute("href");
			String title = a.getAttribute("title");

			src_href[i] = title + " | " + src + " | " + href;
			System.out.println(src_href[i]);
		}
		driver.close();

		return src_href;
	}

}

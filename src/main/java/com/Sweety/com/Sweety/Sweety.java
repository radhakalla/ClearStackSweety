package com.Sweety.com.Sweety;

import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.apache.commons.io.FileUtils;
import org.testng.asserts.SoftAssert;

/**
 * Hello world!
 *
 */
public class Sweety {

	static WebDriver driver;
	SoftAssert sassert = new SoftAssert();

	@BeforeClass
	public void openBrowser() {
		System.setProperty("webdriver.chrome.driver", ".//Drivers//chromedriver.exe");
		driver = new ChromeDriver();
		driver.get("http://damp-sands-8561.herokuapp.com/");
		driver.manage().window().maximize();

	}

	@Test(priority = 0)
	public void loginToSweety() {
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

		String verfiy = driver.findElement(By.xpath("//h2[contains(text(),'Welcome')]")).getText();
		driver.findElement(By.id("user_email")).sendKeys("kallaradha.1997@gmail.com");
		driver.findElement(By.id("user_password")).sendKeys("codetheoryio");
		driver.findElement(By.name("commit")).click();
		System.out.println(verfiy);
		// String verifylogin=driver.findElement(By.className("alert alert-info fade
		// in")).getText();
		// System.out.println(verifylogin);
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

	}

	@Test(priority = 1)
	public void enterGlucoseLevels() {

		System.out.println("=====================");
		driver.findElement(By.xpath("//div[@id='sidebar-wrapper']//following::a[text()='Levels']")).click();
		System.out.println("=====================");
		 Random rand = new Random(); //instance of random class
	      int upperbound = 9;
	        //generate random values from 0-9
	      int int_random = rand.nextInt(upperbound); 
	    String number=  Integer.toString(int_random);
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		driver.findElement(By.xpath("//div[@id='page-content-wrapper']//following::a[text()='Add new']")).click();
		driver.findElement(By.id("entry_level")).sendKeys(number);
		driver.findElement(By.xpath("//input[@type='submit']")).click();

		try {
			boolean isPresent = driver.findElement(By.xpath("//div[text()='Maximum entries reached for this date.']"))
					.isDisplayed();

			sassert.assertEquals(isPresent, true, "Entry is created successfully");
		} catch (Exception e) {
			boolean entryCreated = driver.findElement(By.xpath("//div[text()='Entry was successfully created.']"))
					.isDisplayed();

			sassert.assertEquals(entryCreated, true, "Maximum entries reached for this date.");

		}

	}

	@Test(priority = 2)
	public void viewReports() {
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		driver.findElement(By.linkText("Reports")).click();
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

		driver.findElement(By.linkText("Monthly")).click();
		System.out.println("Report===");
		File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);

		try {
			FileUtils.copyFile(screenshot, new File(".//Reports//ReportScreenshot.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// String minValue=
		// driver.findElement(By.xpath("//tbody//tr[1]//td[count(//thead//th[.='Avg']/preceding-sibling::*)+1]")).getText();
		String minValue = driver
				.findElement(By.xpath("//tbody//tr[1]//td[count(//thead//th[.='Min']/preceding-sibling::*)+1]"))
				.getText();
		String maxValue = driver
				.findElement(By.xpath("//tbody//tr[1]//td[count(//thead//th[.='Max']/preceding-sibling::*)+1]"))
				.getText();
		String avgValue = driver
				.findElement(By.xpath("//tbody//tr[1]//td[count(//thead//th[.='Avg']/preceding-sibling::*)+1]"))
				.getText();

		System.out.println("Min value is: " + minValue);
		System.out.println("Max value is: " + maxValue);
		System.out.println("Avg value is: " + avgValue);

	}

	@AfterClass
	public void tearDown() {
		driver.quit();
	}
}

package uiChallenge;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.AfterTest;

public class UITest {

	ChromeDriver driver = null;
	String cName = null;

	@BeforeTest
	public void beforeTest() {

		System.setProperty("webdriver.chrome.driver",
				"/home/arunachalam/eclipse-workspace/learn_automation_online/selenium/drivers/chromedriver");

		driver = new ChromeDriver();

	}

	@Test(priority = 1)
	public void openPage() {
		driver.get("https://petdiseasealerts.org/forecast-map#/");
	}

	@Parameters("regionName")
	@Test(priority = 2, dependsOnMethods = "openPage")

	public void mainRegion(String regionName) {
		driver.switchTo().frame(0);
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		List<WebElement> countryNameElements = driver
				.findElements(By.xpath("//*[local-name()='svg']/*[local-name()='g']/*/*[local-name()='g']"));
		List<String> countryName = new ArrayList<String>();
		for (WebElement elements : countryNameElements) {
			countryName.add(elements.getAttribute("id"));

		}

		Actions action = new Actions(driver);

		cName = regionName;
		for (String elements : countryName) {
			if (elements.equalsIgnoreCase(cName)) {
				System.out.println(elements);
				break;
			} else
				System.out.println(elements);
		}

		WebElement clickableElement = countryNameElements.get(countryName.indexOf(cName));
		action.click(clickableElement).build().perform();
	}

	@Test(dependsOnMethods = "mainRegion")
	public void subRegion() {
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		List<WebElement> subRegionElement = driver.findElements(By.cssSelector(
				"svg[id='map-svg']>g[id='features']>g[id='regions']>g[id=" + cName + "]>g>g[class='subregion']>path"));
		List<String> regionsName = new ArrayList<String>();
		for (WebElement elements : subRegionElement) {
			regionsName.add(elements.getAttribute("name"));
		}
		System.out.println("Number of sub regions in this " + cName + " are " + regionsName.size());

	}

	@AfterTest
	public void afterTest() {
		driver.close();
	}

}

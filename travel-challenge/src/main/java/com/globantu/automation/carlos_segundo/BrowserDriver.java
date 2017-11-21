package com.globantu.automation.carlos_segundo;

import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

public class BrowserDriver {
	
	private WebDriver driver;
	
	public BrowserDriver(Browsers browser, String driverLocation) {
		switch (browser) {
		case FIREFOX_LOCAL:
			try {
				driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), DesiredCapabilities.firefox());
			}catch(MalformedURLException e) {
				e.printStackTrace();
			}
			break;
			
		case FIREFOX:
			System.setProperty(Browsers.FIREFOX.getDriver(), driverLocation);
			driver = new FirefoxDriver();
			break;
			
		case CHROME:
			System.setProperty(Browsers.CHROME.getDriver(), driverLocation);
			driver = new ChromeDriver();
			
			break;
			
		case EDGE:
			System.setProperty(Browsers.EDGE.getDriver(), driverLocation);
			driver = new EdgeDriver();
			
			break;

		case IE:
			System.setProperty(Browsers.IE.getDriver(), driverLocation);
			driver = new InternetExplorerDriver();
			
			break;
			
		default:
			break;
		}
	}

	public WebDriver getDriver() {
		return driver;
	}
	
	public enum Browsers {
		FIREFOX("firefox"),
		FIREFOX_LOCAL("firefoxLocal"),
		CHROME("chrome"),
		EDGE("edge"),
		IE("ie");
		
		private final String FIREFOX_DRIVER = "webdriver.gecko.driver";
		
		private final String CHROME_DRIVER = "webdriver.chrome.driver";
		
		private final String EDGE_DRIVER = "webdriver.edge.driver";
		
		private final String IE_DRIVER = "webdriver.ie.driver";
		
		private String driver = "";
		
		private Browsers(String name) {
			if("firefox".equalsIgnoreCase(name)) {
				this.driver = FIREFOX_DRIVER;
			}else if("chrome".equalsIgnoreCase(name)) {
				this.driver = CHROME_DRIVER;
			}else if("edge".equalsIgnoreCase(name)) {
				this.driver = EDGE_DRIVER;
			}else if("ie".equalsIgnoreCase(name)) {
				this.driver = IE_DRIVER;
			}
		}
		
		public String getDriver() {
			return this.driver;
		}
		
	}
}

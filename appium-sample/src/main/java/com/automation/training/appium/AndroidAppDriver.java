package com.automation.training.appium;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.remote.DesiredCapabilities;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.Activity;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.remote.MobilePlatform;

/**
 * Allows instantiate an {@link AndroidDriver}.
 * @author carlos.segundo
 *
 */
public class AndroidAppDriver {
	private static final Logger LOGGER = Logger.getLogger(AndroidAppDriver.class);

	private AppiumDriver<MobileElement> driver;
	
	private AndroidAppDriver(URL appiumUrl, String deviceName, String appFile, 
			String appPackage, String startActivity) {
		File app = new File(appFile);
		
		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities.setCapability(MobileCapabilityType.PLATFORM_NAME, MobilePlatform.ANDROID);
		capabilities.setCapability(MobileCapabilityType.DEVICE_NAME, deviceName);
		capabilities.setCapability(MobileCapabilityType.APP, app.getAbsolutePath());
		capabilities.setCapability(AndroidMobileCapabilityType.APP_PACKAGE, appPackage);
		capabilities.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY, startActivity);
		
		driver = new AndroidDriver<>(appiumUrl, capabilities);
	}

	/**
	 * Returns the {@link AndroidDriver} instance of the started appium session.
	 * @return
	 */
	public AppiumDriver<MobileElement> getDriver() {
		return driver;
	}
	
	/**
	 * Starts the given activity
	 * @param appPackage
	 * @param activity
	 */
	public void startActivity(String appPackage, String activity) {
		((AndroidDriver<MobileElement>) driver).startActivity(new Activity(appPackage, activity));
	}
	
	/**
	 * Takes a screenshot of the current session.
	 * @return a file of the screenshot taken.
	 */
	public File takeScreenshot() {
		File screenshot = null;
		LOGGER.info("Screenshot requested");
		
		try {
            screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        } catch(Exception e) {
            LOGGER.error("Unable to take screenshot: " + e.getMessage(), e);
        }
		
		return screenshot;
	}
	
	public void dispose() {
		driver.quit();
	}
	
	public static class AndroidDriverBuilder {
		private URL appiumUrl;
		private String device;
		private String appFile;
		private String appPackage;
		private String startActivity;
		
		public AndroidDriverBuilder(String appiumUrl) throws MalformedURLException {
			this.appiumUrl = new URL(appiumUrl);
		}
		
		public AndroidDriverBuilder withDevice(String device) {
			this.device = device;
			return this;
		}
		
		public AndroidDriverBuilder withAppFile(String appFile) {
			this.appFile = appFile;
			return this;
		}
		
		public AndroidDriverBuilder withAppPackage(String appPackage) {
			this.appPackage = appPackage;
			return this;
		}
		
		public AndroidDriverBuilder withStartActivity(String startActivity) {
			this.startActivity = startActivity;
			return this;
		}
		
		public AndroidAppDriver build() throws IllegalArgumentException {
			StringBuffer sb = new StringBuffer("Create new android session with parameters: ");
			sb.append("appiumUrl[").append(appiumUrl).append("], ");
			sb.append("device[").append(device).append("], ");
			sb.append("appFile[").append(appFile).append("], ");
			sb.append("appPackage[").append(appPackage).append("], ");
			sb.append("startActivity[").append(startActivity).append("]");
			
			LOGGER.info(sb.toString());
			
			if(StringUtils.isBlank(appFile) || StringUtils.isBlank(device) || 
					StringUtils.isBlank(appPackage) || StringUtils.isBlank(startActivity)) {
				throw new IllegalArgumentException("Device, AppFile, AppPackage and StartActivity are required");
			}
			
			return new AndroidAppDriver(appiumUrl, device, appFile, appPackage, startActivity);
		}
	}
	
	
}

package com.automation.training.rest_assured.tests;

import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Parameters;

import io.restassured.RestAssured;

public class BaseTest {
	
	@BeforeSuite(alwaysRun=true)
	@Parameters({"baseUrl"})
	public void setup(String baseUrl) {
		RestAssured.baseURI = baseUrl;
	}

	@AfterSuite(alwaysRun=true)
	public void tearDown() {
	}
	
}

package com.automation.training.rest_assured.tests;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static java.net.HttpURLConnection.HTTP_OK;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import org.apache.log4j.Logger;
import org.testng.ITestContext;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.automation.training.rest_assured.Post;

public class PostsTests extends BaseTest {

	private static final Logger LOGGER = Logger.getLogger(PostsTests.class);
	
	@Test(dataProvider="expectedResultsProvider")
	public void testGetPosts(int expectedSize, int testIndex, int expectedId, int expectedUserId, String expectedTitle) {
		int elementIdx = Math.abs(new Random().nextInt() % testIndex);
		
		List<Post> response = 
			when().
				get("/posts").
			then().
				assertThat().statusCode(HTTP_OK).
				and().assertThat().body("size()", equalTo(expectedSize)).
				and().assertThat().body("get(" + elementIdx + ")", notNullValue()).
				and().assertThat().body("get(" + testIndex + ").title", equalTo(expectedTitle)).
				and().assertThat().body("get(" + testIndex + ").id", equalTo(expectedId)).
				and().assertThat().body("get(" + testIndex + ").userId", equalTo(expectedUserId)).
				and().extract().jsonPath().getList("", Post.class);

		LOGGER.info("Extract element with index: " + elementIdx);
		
		Post post = response.get(elementIdx);
		
		LOGGER.info("Get posts by user: " + post.getUserId());
		given()
			.param("userId", post.getUserId()).
		when().
			get("/posts").
		then().
			assertThat().statusCode(HTTP_OK).
			and().assertThat().body("get(0).userId", equalTo(post.getUserId())).
			and().assertThat().body("size()", equalTo(10));

		LOGGER.info("Get detail of post with id: " + post.getId());
		given()
			.basePath("/posts/").
		when().
			get(String.valueOf(post.getId())).
		then().
			assertThat().statusCode(HTTP_OK).
			and().assertThat().body(matchesJsonSchemaInClasspath("post-schema.json")).
			and().assertThat().body("id", equalTo(post.getId())).
			and().assertThat().body("userId", equalTo(post.getUserId())).
			and().assertThat().body("title", equalTo(post.getTitle())).
			and().assertThat().body("body", equalTo(post.getBody()));
		
		LOGGER.info("Delete post with id: " + post.getId());
		given()
			.basePath("/posts/").
		when().
			delete(String.valueOf(post.getId())).
		then().
			assertThat().statusCode(HTTP_OK).
			and().assertThat().body("isEmpty()", equalTo(true));
	}

	@DataProvider(name="expectedResultsProvider")
	public Iterator<Object[]> provideExpectedResults(ITestContext context) throws IOException, URISyntaxException {
	    List<Object[]> list = new ArrayList<>();
	    String configFile = context.getCurrentXmlTest().getParameter("configFile");
	    File file = new File(getClass().getClassLoader().getResource(configFile).getFile());
	
	    Files.readAllLines(file.toPath(), StandardCharsets.UTF_8).forEach(
	    		l -> {
			    	String[] values = l.split(",");
			    	list.add(new Object[] {
		    			Integer.parseInt(values[0]),
		    			Integer.parseInt(values[1]),
		    			Integer.parseInt(values[2]),
		    			Integer.parseInt(values[3]),
		    			values[4]
			    	});
			    });
	
	    return list.iterator();
	}
	
}

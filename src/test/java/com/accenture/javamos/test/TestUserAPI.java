package com.accenture.javamos.test;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;

import org.junit.Test;

import io.restassured.RestAssured;

public class TestUserAPI {
	
	private String url = "http://localhost:8080/user/signup";
	
	@Test
	public void createUserTest() {
		given()
			.contentType("application/json")
			.body("{ \n"
					+ "	\"realName\":\"jon\",\n"
					+ "	\"password\":\"123456\",\n"
					+ "	\"email\":\"john.doe@anon\"\n"
					+ "}")
		.when()
			.post(url)
		.then()
			.statusCode(201)
			.log().all();
	}
	
	@Test
	public void createUserWihOutNameTest() {
		given()
			.contentType("application/json")
			.body("{ \n"
					+ "	\"realName\":\"\",\n"
					+ "	\"password\":\"123456\",\n"
					+ "	\"email\":\"john.doe@anon\"\n"
					+ "}")
		.when()
			.post(url)
		.then()
			.statusCode(400)
			.log().all();
	}
	
	@Test
	public void createUserWihOutPasswordTest() {
		given()
			.contentType("application/json")
			.body("{ \n"
					+ "	\"realName\":\"Jon\",\n"
					+ "	\"password\":\"\",\n"
					+ "	\"email\":\"john.doe@anon\"\n"
					+ "}")
		.when()
			.post(url)
		.then()
			.statusCode(401)
			.log().all();
	}
	
	@Test
	public void createUserTestWithOutEmail() {
		given()
			.contentType("application/json")
			.body("{ \n"
					+ "	\"realName\":\"jon\",\n"
					+ "	\"password\":\"123456\",\n"
					+ "	\"email\":\"\"\n"
					+ "}")
		.when()
			.post(url)
		.then()
			.statusCode(401)
			.log().all();
	}
	
}

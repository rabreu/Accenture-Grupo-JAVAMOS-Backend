package com.accenture.javamos.test;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.accenture.javamos.entity.User;
import com.accenture.javamos.service.UserService;

public class LoginTestUser {
	
	@Autowired
	private UserService userAdd;
	
	@Before
	public void createUser()  {
		/*
		 * User user = new User(); user.setRealName("jon"); user.setPassword("123456");
		 * user.setEmail("john.doe@anon"); user = userAdd.add(user);
		 */
		
	}
	
	@Test
	public void loginTest() throws IOException{
		/*
		 * URL url = new URL("http://localhost:8080/user/signup"); URLConnection con =
		 * url.openConnection(); HttpURLConnection http = (HttpURLConnection)con;
		 * http.setRequestMethod("POST"); // PUT is another valid option
		 * http.setDoOutput(true);
		 * 
		 * byte[] out =
		 * "{\"realName\":\"root\",\"password\":\"password\",\"email\":\"john.doe@anon\"}"
		 * .getBytes(StandardCharsets.UTF_8); int length = out.length;
		 * 
		 * http.setFixedLengthStreamingMode(length);
		 * http.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
		 * http.connect(); try(OutputStream os = http.getOutputStream()) {
		 * os.write(out); }
		 */
	 given()
		  .contentType("application/json") .body("{\n"
		  		+ "	\"email\":\"test@teste.com\",\n"
		  		+ "	\"password\":\"password\"\n"
		  		+ "}")
		  .when() .post("https://accenture-grupo-javamos.herokuapp.com/user/signin") .then() .statusCode(200).log().all();
	}
	
	@Test
	public void tryLoginWithOutEmail() {
		 given()
		  .contentType("application/json") .body("{\n"
		  		+ "	\"email\":\"\",\n"
		  		+ "	\"password\":\"password\"\n"
		  		+ "}")
		  .when() .post("https://accenture-grupo-javamos.herokuapp.com/user/signin") .then() .statusCode(401).log().all();
	}
	
	@Test
	public void tryLoginWithOutPassorwd() {
		 given()
		  .contentType("application/json") .body("{\n"
		  		+ "	\"email\":\"test@teste.com\",\n"
		  		+ "	\"password\":\"\"\n"
		  		+ "}")
		  .when() .post("https://accenture-grupo-javamos.herokuapp.com/user/signin") .then() .statusCode(401).log().all();
	}
	
	@Test
	public void tryLoginWithWrongEmail() {
		 given()
		  .contentType("application/json") .body("{\n"
		  		+ "	\"email\":\"wrongmail@teste.com\",\n"
		  		+ "	\"password\":\"password\"\n"
		  		+ "}")
		  .when() .post("https://accenture-grupo-javamos.herokuapp.com/user/signin") .then() .statusCode(401).log().all();
	}
	
	@Test
	public void tryLoginWithWrongPassword() {
		 given()
		  .contentType("application/json") .body("{\n"
		  		+ "	\"email\":\"test@teste.com\",\n"
		  		+ "	\"password\":\"wrongPassword\"\n"
		  		+ "}")
		  .when() .post("https://accenture-grupo-javamos.herokuapp.com/user/signin") .then() .statusCode(401).log().all();
	}
}

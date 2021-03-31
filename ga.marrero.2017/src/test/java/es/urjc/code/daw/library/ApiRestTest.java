package es.urjc.code.daw.library;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static io.restassured.path.json.JsonPath.from;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;


import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.*;

@SpringBootTest (webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ApiRestTest {
	
	@LocalServerPort
	int port;
	
	@BeforeEach
	public void setUp() {
		RestAssured.port = port;
	 }
	
	@Test
	void whenAddBook_ThenIsRetrievable() {

		
		Response resp = given().
			body("{ \"title\" : \"Eragon\" , \"description\" : \"Sobre dragones...\"}").
				contentType(ContentType.JSON).
				post("/api/books/").
				andReturn();
		
		Integer idBook = from(resp.getBody().asString()).get("id");
		
		when().
			get("/api/books/{idBook}", idBook).
			
			then().
				statusCode(200).
				body("id", equalTo(idBook)).
				body("title",equalTo("Eragon"));	
	}


	@Test
	void whenDeleteBook_ThenIsNotRetrievable() {
		
		//Primero creo un libro para después eliminarlo
		Response resp = given().
				body("{ \"title\" : \"Harry Potter\" , \"description\" : \" Harry Potter debe"
						+ "derrotar al voldemort...\"}").
						contentType(ContentType.JSON).
						post("/api/books/").
						andReturn();
		
		Integer idBook = from(resp.getBody().asString()).get("id");
		
		when().
			delete("/api/books/{idBook}",idBook).
		
		then().
			statusCode(200);
		
		when().
			get("/api/books/{idBook}",idBook).
			then().
				statusCode(404);
	}
}
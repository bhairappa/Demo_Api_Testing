package Demo_Apitesting;

import io.restassured.RestAssured;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import files.Payloads;

public class BasicApi {

//	private static final String RestAssured = "";

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		RestAssured.baseURI="https://rahulshettyacademy.com/";
		given()
		     .log().all()
		     .queryParam("key", "qaclick123")
		     .header("Content-Type", "application/json")
		     .body(Payloads.addplace())
	    .when()
	         .post("maps/api/place/add/json")
	    .then()
	         .log().all()
		     .assertThat().statusCode(200)
		     .body("scope", equalTo("APP"))
		     .header("Server",equalTo("Apache/2.4.52 (Ubuntu)"));

	}

}

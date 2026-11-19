package Demo_Apitesting;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import files.Payloads;

public class BasicApi {

//	private static final String RestAssured = "";

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		RestAssured.baseURI="https://rahulshettyacademy.com/";
		String response =
		given()
		     .log().all()
		     .queryParam("key", "qaclick123")
		     .header("Content-Type", "application/json")
		     .body(Payloads.addplace())
	    .when()
	         .post("maps/api/place/add/json")
	    .then()
		     .assertThat().statusCode(200)
		     .body("scope", equalTo("APP"))
		     .header("Server",equalTo("Apache/2.4.52 (Ubuntu)")).extract().response().asString();

		System.out.println(response);
		
		JsonPath js  = new JsonPath(response);
		String placeId  = js.get("place_id");
		System.out.println(placeId);
		
		//update place
		
		String new_Address = "summer walk, Africa";
		given()
	     .log().all()
	     .queryParam("key", "qaclick123")
	     .header("Content-Type", "application/json")
	     .body(Payloads.updateplace(placeId, new_Address))
   .when()
        .put("maps/api/place/update/json")
   .then()
         .assertThat().statusCode(200)
         .body("msg", equalTo("Address successfully updated"));		
	
		
		//get place
	given()
	    .log().all()
	    .queryParam("key", "qaclick123")
	    .queryParam("place_id",placeId)
    .when()
         .get("maps/api/place/get/json")
    .then()
         .assertThat()
         .log().all()
         .statusCode(200)
         .body("address",equalTo(new_Address));

	}
	
	
}

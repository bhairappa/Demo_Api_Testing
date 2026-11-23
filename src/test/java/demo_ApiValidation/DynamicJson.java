package demo_ApiValidation;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import files.Payloads;
import files.ReusableMethods;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

public class DynamicJson {
	
	@Test(dataProvider="bookdata")
	public void addBook(String isbn,String aisle) {
	
	RestAssured.baseURI="http://216.10.245.166";
	String response =
	given()
	    .log()
	    .all()
	    .header("Content-Type", "application/json")
	    .body(Payloads.addBook(isbn,aisle)).
	when()
	    .post("/Library/Addbook.php").
	then()
	    .log().all()
	    .assertThat()
	    .statusCode(200)
	    .body("Msg", equalTo("successfully added"))
	    .extract()
	    .response()
	    .asString();
	
	JsonPath js1 = ReusableMethods.rawToJson(response);	
	String book_name =js1.get("isbn"); 
    System.out.println(book_name);	
	}
	
	@DataProvider(name="bookdata")
	public  static  Object[][] addDynamicArray() {
		return new Object[][] {{"java","98"},{"python","97"},{"dotnet","96"}};
	}
}
// r  R
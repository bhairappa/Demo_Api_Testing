package tests;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import org.testng.Assert;
import org.testng.annotations.Test;

import base.SpecBuilder;
import payloads.Payloads;
import utilities.ReusableMethods;

public class PlaceApiTest {

    private String placeId;
    private final String newAddress = "Summer Walk, Africa";

    @Test(priority = 1)
    public void addPlaceTest() {
        String response = given()
                .spec(SpecBuilder.getPlaceRequestSpec())
                .body(Payloads.addplace())
        .when()
                .post("/maps/api/place/add/json")
        .then()
                .spec(SpecBuilder.getSuccessResponseSpec(200))
                .body("scope", equalTo("APP"))
                .header("Server", equalTo("Apache/2.4.52 (Ubuntu)"))
                .extract().asString();

        placeId = ReusableMethods.rawToJson(response).getString("place_id");
        Assert.assertNotNull(placeId, "place_id should be generated");
    }

    @Test(priority = 2, dependsOnMethods = "addPlaceTest")
    public void updatePlaceTest() {
        given()
                .spec(SpecBuilder.getPlaceRequestSpec())
                .body(Payloads.updateplace(placeId, newAddress))
        .when()
                .put("/maps/api/place/update/json")
        .then()
                .spec(SpecBuilder.getSuccessResponseSpec(200))
                .body("msg", equalTo("Address successfully updated"));
    }

    @Test(priority = 3, dependsOnMethods = "updatePlaceTest")
    public void getPlaceTest() {
        String response = given()
                .spec(SpecBuilder.getPlaceRequestSpec())
                .queryParam("place_id", placeId)
        .when()
                .get("/maps/api/place/get/json")
        .then()
                .spec(SpecBuilder.getSuccessResponseSpec(200))
                .body("address", equalTo(newAddress))
                .extract().asString();

        String actualAddress = ReusableMethods.rawToJson(response).getString("address");
        Assert.assertEquals(actualAddress, newAddress);
    }
}
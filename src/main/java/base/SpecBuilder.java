package base;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import utilities.ConfigReader;

public class SpecBuilder {

    public static RequestSpecification getPlaceRequestSpec() {
        return new RequestSpecBuilder()
                .setBaseUri(ConfigReader.getProperty("place.baseUri"))
                .addQueryParam("key", ConfigReader.getProperty("place.apiKey"))
                .setContentType(ContentType.JSON)
                .log(LogDetail.ALL)
                .build();
    }

    public static RequestSpecification getLibraryRequestSpec() {
        return new RequestSpecBuilder()
                .setBaseUri(ConfigReader.getProperty("library.baseUri"))
                .setContentType(ContentType.JSON)
                .log(LogDetail.ALL)
                .build();
    }

    public static RequestSpecification getJiraRequestSpec() {
        return new RequestSpecBuilder()
                .setBaseUri(ConfigReader.getProperty("jira.baseUri"))
                .setAuth(RestAssured.preemptive().basic(
                        ConfigReader.getProperty("jira.email"),
                        ConfigReader.getProperty("jira.apiToken")
                ))
                .setContentType(ContentType.JSON)
                .log(LogDetail.ALL)
                .build();
    }

    public static ResponseSpecification getSuccessResponseSpec(int expectedStatusCode) {
        return new ResponseSpecBuilder()
                .expectStatusCode(expectedStatusCode)
                .expectContentType(ContentType.JSON)
                .log(LogDetail.ALL)
                .build();
    }
}
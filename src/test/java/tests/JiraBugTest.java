package tests;

import static io.restassured.RestAssured.given;

import java.io.File;

import org.testng.Assert;
import org.testng.annotations.Test;

import base.SpecBuilder;
import io.restassured.path.json.JsonPath;
import payloads.Payloads;
import utilities.ReusableMethods;

public class JiraBugTest {

    private String issueId;

    @Test(priority = 1)
    public void createBugInJira() {
        String response = given()
                .spec(SpecBuilder.getJiraRequestSpec())
                .body(Payloads.bugTest())
        .when()
                .post("/rest/api/2/issue")
        .then()
                .spec(SpecBuilder.getSuccessResponseSpec(201))
                .extract().asString();

        JsonPath js = ReusableMethods.rawToJson(response);
        issueId = js.getString("id");
        Assert.assertNotNull(issueId, "Jira Issue ID should not be null");
        System.out.println("Created Jira Bug ID: " + issueId);
    }

    @Test(priority = 2, dependsOnMethods = "createBugInJira")
    public void attachFileToJiraBug() {
        File attachment = new File("src/test/resources/sample_bug_log.txt");

        given()
                .baseUri(utilities.ConfigReader.getProperty("jira.baseUri"))
                .auth().preemptive().basic(
                        utilities.ConfigReader.getProperty("jira.email"),
                        utilities.ConfigReader.getProperty("jira.apiToken")
                )
                .header("X-Atlassian-Token", "no-check")
                .pathParam("issueId", issueId)
                .multiPart("file", attachment)
        .when()
                .post("/rest/api/2/issue/{issueId}/attachments")
        .then()
                .log().all()
                .assertThat()
                .statusCode(200);
    }
}
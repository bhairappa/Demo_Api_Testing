package tests;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import base.SpecBuilder;
import payloads.Payloads;
import utilities.ReusableMethods;

public class LibraryApiTest {

    @Test(dataProvider = "bookdata")
    public void addAndDeleteBookTest(String isbn, String aisle) {
        // 1. Add Book
        String addResponse = given()
                .spec(SpecBuilder.getLibraryRequestSpec())
                .body(Payloads.addBook(isbn, aisle))
        .when()
                .post("/Library/Addbook.php")
        .then()
                .spec(SpecBuilder.getSuccessResponseSpec(200))
                .body("Msg", equalTo("successfully added"))
                .extract().asString();

        String bookId = ReusableMethods.rawToJson(addResponse).getString("ID");
        Assert.assertEquals(bookId, isbn + aisle);

        // 2. Delete Book (Clean up test data for repeatable runs)
        given()
                .spec(SpecBuilder.getLibraryRequestSpec())
                .body("{\"ID\": \"" + bookId + "\"}")
        .when()
                .post("/Library/DeleteBook.php")
        .then()
                .spec(SpecBuilder.getSuccessResponseSpec(200))
                .body("msg", equalTo("book is successfully deleted"));
    }

    @DataProvider(name = "bookdata")
    public Object[][] addDynamicArray() {
        return new Object[][] {
            {"java", "9801"},
            {"python", "9701"},
            {"dotnet", "9601"}
        };
    }
}
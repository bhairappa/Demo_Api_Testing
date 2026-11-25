package tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.path.json.JsonPath;
import payloads.Payloads;
import utilities.ReusableMethods;

public class CourseValidationTest {

    @Test
    public void verifySumOfCoursesMatchesPurchaseAmount() {
        JsonPath js = ReusableMethods.rawToJson(Payloads.courses());
        int count = js.getInt("courses.size()");
        int dashboardAmount = js.getInt("dashboard.purchaseAmount");

        int sumOfCopiesPrice = 0;
        for (int i = 0; i < count; i++) {
            int price = js.getInt("courses[" + i + "].price");
            int copies = js.getInt("courses[" + i + "].copies");
            sumOfCopiesPrice += (copies * price);
        }

        Assert.assertEquals(sumOfCopiesPrice, dashboardAmount, "Total course revenue must match dashboard purchaseAmount");
    }

    @Test
    public void verifyRpaCourseCopies() {
        JsonPath js = ReusableMethods.rawToJson(Payloads.courses());
        int count = js.getInt("courses.size()");
        int rpaCopies = 0;

        for (int i = 0; i < count; i++) {
            String title = js.getString("courses[" + i + "].title");
            if (title.equalsIgnoreCase("RPA")) {
                rpaCopies = js.getInt("courses[" + i + "].copies");
                break;
            }
        }

        Assert.assertEquals(rpaCopies, 10, "RPA course copies should be 10");
    }
}
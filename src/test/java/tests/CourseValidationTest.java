package tests;

import java.util.List;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.fasterxml.jackson.databind.ObjectMapper;
import payloads.Payloads;
import pojos.courses.Course;
import pojos.courses.GetCoursesResponse;

public class CourseValidationTest {

    @Test
    public void verifySumOfCoursesMatchesPurchaseAmount() throws Exception {
        // Deserialize JSON string directly into GetCoursesResponse POJO
        ObjectMapper mapper = new ObjectMapper();
        GetCoursesResponse coursesResponse = mapper.readValue(Payloads.courses(), GetCoursesResponse.class);

        int dashboardAmount = coursesResponse.getDashboard().getPurchaseAmount();
        List<Course> courses = coursesResponse.getCourses();

        int sumOfCopiesPrice = 0;
        for (Course course : courses) {
            sumOfCopiesPrice += (course.getPrice() * course.getCopies());
        }

        Assert.assertEquals(sumOfCopiesPrice, dashboardAmount, "Total course revenue must match dashboard purchaseAmount");
    }

    @Test
    public void verifyRpaCourseCopies() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        GetCoursesResponse coursesResponse = mapper.readValue(Payloads.courses(), GetCoursesResponse.class);

        int rpaCopies = 0;
        for (Course course : coursesResponse.getCourses()) {
            if (course.getTitle().equalsIgnoreCase("RPA")) {
                rpaCopies = course.getCopies();
                break;
            }
        }

        Assert.assertEquals(rpaCopies, 10, "RPA course copies should be 10");
    }
}
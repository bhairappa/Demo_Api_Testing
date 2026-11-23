package demo_ApiValidation;

import org.testng.Assert;
import org.testng.annotations.Test;

import files.Payloads;
import io.restassured.path.json.JsonPath;
public class CompleteJsonParse {

	JsonPath js  = new JsonPath(Payloads.courses());
	//get the size of the courses
			int count = js.getInt("courses.size()");
	@Test
	public  void sum_of_courses() {
		// TODO Auto-generated method stub
	
		System.out.println(count);		
		int dashboard_amount = js.getInt ("dashboard.purchaseAmount");
//		String title=js.get("courses[0].title");
//		System.out.println(title);
		System.out.println(dashboard_amount);
		int sumOfcopiesprice = 0;
		
		//varify sum of courses price amount is exactly same dashboard amount
		for(int i=0;i<count;i++) {			
				int price=js.getInt("courses["+i+"].price");
			     int copies= js.getInt("courses["+i+"].copies");
			     sumOfcopiesprice+= (copies*price);
		} Assert.assertEquals(dashboard_amount,sumOfcopiesprice);
	}
		
	@Test
		public void get_copiesprice() {
		//get the price amount where the course title is rpa
		for(int i=0;i<count;i++) {
			String title1 = js.get("courses["+i+"].title");
			if(title1.equalsIgnoreCase("rpa")) {
				System.out.println(" "+js.get("courses["+i+"].price"));
				break;
			}
		}
	}
	
}

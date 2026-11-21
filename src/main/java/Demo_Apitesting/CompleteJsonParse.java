package Demo_Apitesting;

import files.Payloads;
import io.restassured.path.json.JsonPath;

public class CompleteJsonParse {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		JsonPath js  = new JsonPath(Payloads.courses());
		int count = js.getInt("courses.size()");
		System.out.println(count);

	}

}

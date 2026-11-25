package utilities;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class ReusableMethods {

    public static JsonPath rawToJson(String response) {
        return new JsonPath(response);
    }

    public static String getJsonPathValue(Response response, String keyPath) {
        JsonPath js = new JsonPath(response.asString());
        return js.getString(keyPath);
    }

    public static String generateRandomAisle() {
        return String.valueOf((int) (Math.random() * 9000) + 1000);
    }
}
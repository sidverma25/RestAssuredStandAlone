import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import files.*;
public class AddPlace {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		// Validate if the getplace API is working as expected or not.
		
		RestAssured.baseURI= "https://rahulshettyacademy.com";
		
		//Add Place
		
		String response = given().log().all().queryParam("key", "qaclick123").header("Content-Type", "application/json")
		.body(payload.body())
		.when().post("maps/api/place/add/json")
		.then().log().all().assertThat().statusCode(200).body("scope", equalTo("APP")).header("Server", "Apache/2.4.52 (Ubuntu)").extract().response().asString();
		//System.out.println(response);
		//JsonPath jp = new JsonPath(response);
		String placeId = ReusableCode.getParameterFromResponse(response,"place_id");
		System.out.println(placeId);
		
		
		//Update Address
		
		String newAddress = "30 Winter Garden, UK";
		given().log().all().queryParam("key", "qaclick123").header("Content-Type", "application/json")
		.body("{\r\n"
				+ "\"place_id\":\""+placeId+"\",\r\n"
				+ "\"address\":\""+newAddress+"\",\r\n"
				+ "\"key\":\"qaclick123\"\r\n"
				+ "}\r\n"
				+ "")
		.when().put("maps/api/place/update/json")
		.then().log().all().assertThat().statusCode(200).body("msg", equalTo("Address successfully updated"));
		
		//Get Place and Verify Address
		String response2 = given().log().all().queryParam("key", "qaclick123").queryParam("place_id", placeId)
		.when().get("maps/api/place/get/json")
		.then().log().all().assertThat().statusCode(200).body("address", equalTo(newAddress)).extract().response().asString();
		
		String currentAddress = ReusableCode.getParameterFromResponse(response2,"address");
		Assert.assertEquals(newAddress,currentAddress);
	}

}

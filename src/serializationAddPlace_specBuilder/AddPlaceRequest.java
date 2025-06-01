package serializationAddPlace_specBuilder;

import files.*;
import static io.restassured.RestAssured.*;
import org.testng.Assert;

import java.util.ArrayList;
import java.util.List;

import org.testng.annotations.Test;

public class AddPlaceRequest {
	
	@Test(enabled = true)
	public void addPlace_ConstructedPayload() {
		AddPlacePayload ap = new AddPlacePayload();
		Location location = new Location();
		location.setLat(-31.135256);
		location.setLng(35.134616);
		ap.setLocation(location);
		ap.setAccuracy(50);
		ap.setName("Backline forest");
		ap.setPhone_number("(+91) 981 193 2816");
		ap.setAddress("31, front layout, sohen 11");
		List<String> types = new ArrayList<String>();
		types.add("garden");
		types.add("park");
		ap.setTypes(types);
		ap.setWebsite("http://green-forest.com");
		ap.setLanguage("English-IN");
		
		given().log().all().baseUri("https://rahulshettyacademy.com")
		.queryParam("key", "qaclick123")
		.header("Content-Type", "application/json")
		.body(ap)
		.when().post("maps/api/place/add/json")
		.then().log().all().assertThat().statusCode(200);
	}

}

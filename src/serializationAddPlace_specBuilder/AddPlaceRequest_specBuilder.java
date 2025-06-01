package serializationAddPlace_specBuilder;

import static io.restassured.RestAssured.given;

import java.util.ArrayList;
import java.util.List;

import org.testng.annotations.Test;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class AddPlaceRequest_specBuilder {
	@Test(enabled = true)
	public void addPlace_ConstructedPayload_specBuilder() {
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
		
		RequestSpecification req = new RequestSpecBuilder()
				.setBaseUri("https://rahulshettyacademy.com")
				.addQueryParam("key", "qaclick123")
				.setContentType(ContentType.JSON).build();
		
		ResponseSpecification res = new ResponseSpecBuilder()
				.expectStatusCode(200)
				.expectContentType(ContentType.JSON).build();
		
		String response = given().spec(req)
		.body(ap)
		.when().post("maps/api/place/add/json")
		.then().spec(res).extract().response().asString();
		
		System.out.println(response);
	}
}

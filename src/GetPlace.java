import io.restassured.RestAssured;
import static io.restassured.RestAssured.*;
public class GetPlace {

	public static void main(String[] args) {
		RestAssured.baseURI= "https://rahulshettyacademy.com";
		given().log().all().queryParam("key","qaclick123").queryParam("place_id","945b85cd1c246108c0098b12e9a6d74a")
		.when().get("maps/api/place/get/json")
		.then().log().all().assertThat();
		

	}

}

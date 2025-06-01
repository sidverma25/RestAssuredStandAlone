package OAuth;

import files.*;
import static io.restassured.RestAssured.*;
import org.testng.annotations.Test;

public class AccessTokenGetCourseDetails {
	String accessToken;
	@Test(enabled = true)
	public void getAcessToken() {
		String oAuthResponse = given().log().all()
		.formParam("client_id", "692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
		.formParam("client_secret", "erZOWM9g3UtwNRj340YYaK_W")
		.formParam("grant_type", "client_credentials")
		.formParam("scope", "trust")
		.when().post("https://rahulshettyacademy.com/oauthapi/oauth2/resourceOwner/token")
		.then().log().all().assertThat().statusCode(200).extract().response().asString();
		
		accessToken = Utilities.getParameterFromResponse(oAuthResponse, "access_token");
	}
	
	@Test(enabled = true, dependsOnMethods = {"getAcessToken"})
	public void getCourseDetails() {
		given().log().all()
		.queryParam("access_token", accessToken)
		.when().get("https://rahulshettyacademy.com/oauthapi/getCourseDetails")
		.then().log().all();
	}
}

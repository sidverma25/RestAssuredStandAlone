package deserializationGetCourse;

import files.*;
import static io.restassured.RestAssured.*;
import org.testng.Assert;
import java.util.List;

import org.testng.annotations.Test;

public class GetCourseDetails {
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
		getCourse gc = given().log().all()
		.queryParam("access_token", accessToken)
		.when().get("https://rahulshettyacademy.com/oauthapi/getCourseDetails")
		.then().log().all().extract().response().as(getCourse.class);
		String linkedin = gc.getLinkedIn();
		//print the name of the instructor
		System.out.println("LinkedIn url is: "+gc.getLinkedIn());
		Assert.assertEquals(linkedin, "https://www.linkedin.com/in/rahul-shetty-trainer/");
		
		//print the price of the course with courseTitle Cypress under WebAutomation.
		List<webAutomation> wa = gc.getCourses().getWebAutomation();
		String coursePrice="";
		for(int i=0; i<wa.size(); i++) {
			if(wa.get(i).getCourseTitle().equalsIgnoreCase("Cypress")) {
				coursePrice = wa.get(i).getPrice();
				System.out.println("Cypress course price is: "+coursePrice);
			}
		}
		Assert.assertEquals(coursePrice, "40");
		
	}
}

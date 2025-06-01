package JiraAPI;

import files.*;
import static io.restassured.RestAssured.*;
import java.io.File;
import org.testng.annotations.Test;

public class CreateIssueWithAttachment {
	String key;
	@Test(enabled = true)
	public void createIssue() {
		String createIssueResponse = given().log().all()
		.baseUri("https://bugtrackingdemo.atlassian.net")
		.header("Content-Type", "application/json")
		.header("Authorization", "Basic dmVybWEuc2lkZGhhcnRoMjVAZ21haWwuY29tOkFUQVRUM3hGZkdGMEdIbjNvel9VQzdzRjdaX2RyWG5YVWhnN19zMjM1SVRNaTlzdTI0b3Z3TzVIXzd5X2tOTlo0ZGNKM19SX0lDcjQtaXhMakc1T2w1LUFNOGFOaUs3ZTNPWkdDZXJ3bU5ZN25UYm5lZjFZeV9tNEJTSEVrN0FYMXBuVVh6Z2tZbVVCc2RUam0xWjE0VkZFLW9Ebkk3XzdrbjBrTFd0Y0xqRlVyenhJa2V0ME5UQT1ENDQ5MEQ4Rg==")
		//.queryParam("Cookie", "atlassian.xsrf.token=6492cc64b5e4586fb302d7f504502ffbe455a438_lin")
		.body(Payload.createIssue("Dropdown is not working"))
		.post("rest/api/3/issue")
		
		.then().log().all().assertThat().statusCode(201).extract().response().asString();
		
		key= Utilities.getParameterFromResponse(createIssueResponse, "key");
	}
	
	@Test(enabled=true,dependsOnMethods = {"createIssue"} )
	public void addAttachment() {
		given().log().all()
		.baseUri("https://bugtrackingdemo.atlassian.net").pathParam("key", key)
		.header("Authorization", "Basic dmVybWEuc2lkZGhhcnRoMjVAZ21haWwuY29tOkFUQVRUM3hGZkdGMEdIbjNvel9VQzdzRjdaX2RyWG5YVWhnN19zMjM1SVRNaTlzdTI0b3Z3TzVIXzd5X2tOTlo0ZGNKM19SX0lDcjQtaXhMakc1T2w1LUFNOGFOaUs3ZTNPWkdDZXJ3bU5ZN25UYm5lZjFZeV9tNEJTSEVrN0FYMXBuVVh6Z2tZbVVCc2RUam0xWjE0VkZFLW9Ebkk3XzdrbjBrTFd0Y0xqRlVyenhJa2V0ME5UQT1ENDQ5MEQ4Rg==")
		.header("X-Atlassian-Token", "no-check")
		.multiPart("file", new File(".\\src\\image_2.jpg"))
		.when().post("rest/api/3/issue/{key}/attachments")
		.then().log().all().assertThat().statusCode(200);
	}
	
	
	@Test(enabled = true,dependsOnMethods = {"addAttachment"})
	public void getIssue() {
		given().log().all()
		.baseUri("https://bugtrackingdemo.atlassian.net").pathParam("key", key)
		.header("Authorization", "Basic dmVybWEuc2lkZGhhcnRoMjVAZ21haWwuY29tOkFUQVRUM3hGZkdGMEdIbjNvel9VQzdzRjdaX2RyWG5YVWhnN19zMjM1SVRNaTlzdTI0b3Z3TzVIXzd5X2tOTlo0ZGNKM19SX0lDcjQtaXhMakc1T2w1LUFNOGFOaUs3ZTNPWkdDZXJ3bU5ZN25UYm5lZjFZeV9tNEJTSEVrN0FYMXBuVVh6Z2tZbVVCc2RUam0xWjE0VkZFLW9Ebkk3XzdrbjBrTFd0Y0xqRlVyenhJa2V0ME5UQT1ENDQ5MEQ4Rg==")
		.when().get("rest/api/3/issue/{key}")
		.then().log().all().assertThat().statusCode(200);
	}

}

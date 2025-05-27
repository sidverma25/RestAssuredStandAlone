package files;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;
import files.*;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.testng.annotations.Test;
import org.testng.annotations.DataProvider;

public class LibrabyAPI {
	List <String> ids = new ArrayList<String>();
	@Test(enabled = true,dataProvider = "getData")
	public void addBook(String isbninput, String aisleinput) {
	String id;
	String bodyjson = payload.addBookPayload(isbninput, aisleinput);
	String addBookResponse = given().log().all().baseUri("http://216.10.245.166").queryParam("Content-Type", "application/json").body(bodyjson)
	.when().post("Library/Addbook.php")
	.then().log().all().assertThat().statusCode(200).extract().response().asString();
	
	String msg = ReusableCode.getParameterFromResponse(addBookResponse, "Msg");
	Assert.assertEquals(msg, "successfully added");
	
	String isbn = ReusableCode.getParameterFromResponse(bodyjson, "isbn");
	String aisle = ReusableCode.getParameterFromResponse(bodyjson, "aisle");
	id = ReusableCode.getParameterFromResponse(addBookResponse, "ID");
	ids.add(id);
	Assert.assertEquals(id, (isbn+aisle));
	
	}
	
	
	/*@Test
	public void getBookByAuthor() {
		
	}
	
	
	@Test
	public void getBookById() {	
	}*/
	
	
	@Test(dependsOnMethods = {"addBook"},dataProvider = "getIds")
	public void deleteBook(String id) {
		String bodyjson = payload.deleteBook(id);
		String response = given().log().all().baseUri("http://216.10.245.166").queryParam("Content-Type", "application/json").body(bodyjson)
		.when().post("Library/DeleteBook.php")
		.then().log().all().assertThat().statusCode(200).extract().response().asString();
		
		String msg = ReusableCode.getParameterFromResponse(response, "msg");
		
		Assert.assertEquals(msg, "book is successfully deleted");
		
	}
	
	@DataProvider
	public String[][] getData(){
		String data[][] = {{"test1", "932"},{"test2","672"}};
		return data;
	}
	@DataProvider
	public String[] getIds() {
		String id[] = ids.toArray(new String[0]);
		return id;
	}

}

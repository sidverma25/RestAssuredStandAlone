package files;
import org.testng.Assert;

import static io.restassured.RestAssured.*;

import java.util.ArrayList;
import java.util.List;

import org.testng.annotations.Test;
import org.testng.annotations.DataProvider;

public class LibrabyAPI {
	List <String> ids = new ArrayList<String>();
	@Test(enabled = true,dataProvider = "getData")
	public void addBook(String isbninput, String aisleinput) {
	String id;
	String bodyjson = Payload.addBookPayload(isbninput, aisleinput);
	String addBookResponse = given().log().all().baseUri("http://216.10.245.166").queryParam("Content-Type", "application/json").body(bodyjson)
	.when().post("Library/Addbook.php")
	.then().log().all().assertThat().statusCode(200).extract().response().asString();
	
	String msg = Utilities.getParameterFromResponse(addBookResponse, "Msg");
	Assert.assertEquals(msg, "successfully added");
	
	String isbn = Utilities.getParameterFromResponse(bodyjson, "isbn");
	String aisle = Utilities.getParameterFromResponse(bodyjson, "aisle");
	id = Utilities.getParameterFromResponse(addBookResponse, "ID");
	ids.add(id);
	Assert.assertEquals(id, (isbn+aisle));
	
	}
	
	
	/*@Test
	public void getBookByAuthor() {
		
	}
	
	
	@Test
	public void getBookById() {	
	}*/
	
	
	@Test(dependsOnMethods = {"addBook"},dataProvider = "getData")
	public void deleteBook(String isbninput, String aisleinput) {
		String id = isbninput+aisleinput;
		String bodyjson = Payload.deleteBook(id);
		String response = given().log().all().baseUri("http://216.10.245.166").queryParam("Content-Type", "application/json").body(bodyjson)
		.when().post("Library/DeleteBook.php")
		.then().log().all().assertThat().statusCode(200).extract().response().asString();
		
		String msg = Utilities.getParameterFromResponse(response, "msg");
		
		Assert.assertEquals(msg, "book is successfully deleted");
		
	}
	
	@DataProvider
	public String[][] getData(){
		String data[][] = {{"test1", "932"},{"test2","672"}};
		return data;
	}

}

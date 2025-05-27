package files;

import io.restassured.path.json.JsonPath;

public class JsonParse {

	public static void main(String[] args) {
		
		JsonPath jp = new JsonPath(payload.complexJson());
		//1. Print No of courses returned by API
		System.out.println(jp.getInt("courses.size()"));
		
		//Print Purchase Amount
		System.out.println(jp.getInt("dashboard.purchaseAmount"));
		
		//Print Title of the first course
		System.out.println(jp.getString("courses[0].title"));
		
		//Print All course titles and their respective Prices
		int size = jp.getInt("courses.size()");
		for(int i=0; i<size; i++) {
			System.out.println(jp.getString("courses["+i+"].title")+" - "+jp.getInt("courses["+i+"].price"));
		}
		//Print no of copies sold by RPA Course
		for(int j=0; j<size; j++) {
				if(jp.getString("courses["+j+"].title").equals("RPA")) {
					System.out.println(jp.getInt("courses["+j+"].copies"));
				}
		}
		//Verify if Sum of all Course prices matches with Purchase Amount
		int total = 0;
		for(int j=0; j<size; j++) {
			total = total + (jp.getInt("courses["+j+"].price") * jp.getInt("courses["+j+"].copies"));
		}
		if(total == jp.getInt("dashboard.purchaseAmount")) {
			System.out.println("total -"+total+" match");
		}
		else {
			System.out.println("total -"+total+" don't match");
		}
	}

}

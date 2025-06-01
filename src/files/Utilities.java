package files;

import io.restassured.path.json.JsonPath;

public class Utilities {
	
	public static String getParameterFromResponse(String reponse,String key)
	{
		JsonPath jp = new JsonPath(reponse);
		return jp.getString(key);
	}

}

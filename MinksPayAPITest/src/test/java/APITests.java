

import static io.restassured.matcher.RestAssuredMatchers.*;
import static org.hamcrest.Matchers.*;
import org.json.simple.JSONObject;
import org.testng.annotations.Test;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import static io.restassured.RestAssured.*;

public class APITests {
	
	@Test(priority=1)
	public void RegAUserPOSTOppSuccessfull() {
	
		JSONObject request_body = new JSONObject();
		request_body.put("email", "eve.holt@reqres.in");
		request_body.put("password", "pistol");
		System.out.println("the user details is : " + request_body.toJSONString());
		
		given()
			.header("Content-Type","application/json")
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
			.body(request_body.toJSONString())
		.when()
			.post("https://reqres.in/api/register")
		.then()
			.statusCode(200)
		.log().all();
	}
	
	@Test(priority=2)
	public void RegAUserPOSTOppUnSuccessfull() {
		
		JSONObject request_body = new JSONObject();
		request_body.put("email", "maali@gmail.com");
		System.out.println("the user details is : " + request_body.toJSONString());
		
		given()
			.header("Content-Type","application/json")
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
			.body(request_body.toJSONString())
		.when()
			.post("https://reqres.in/api/register")
		.then()
			.statusCode(400)
		.log().all()
			.body("error", equalTo("Missing password"));
	}
	
	@Test(priority=3)
	public void LoginAUser_POSTOpp_Successfull() {
		
		JSONObject request_body = new JSONObject();
		request_body.put("email", "eve.holt@reqres.in");
		request_body.put("password", "cityslicka");
		System.out.println("the user details is : " + request_body.toJSONString());
		
		given()
			.header("Content-Type","application/json")
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
			.body(request_body.toJSONString())
		.when()
			.post("https://reqres.in/api/login")
		.then()
			.statusCode(200)
		.log().all();
	}
	
	@Test(priority=4)
	public void LoginAUser_POSTOpp_UnSuccessfull() {
		
		JSONObject request_body = new JSONObject();
		request_body.put("email", "peter@klaven");
		System.out.println("the user details is : " + request_body.toJSONString());
		
		given()
			.header("Content-Type","application/json")
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
			.body(request_body.toJSONString())
		.when()
			.post("https://reqres.in/api/login")
		.then()
			.statusCode(400)
		.log().all().body("error", equalTo("Missing password"));
	}
	
	@Test(priority=5)
	public void List_GETOpp_Successful() {
		given()
		.get("https://reqres.in/api/users?page=2").
	then()
		.statusCode(200).body("total", equalTo(12));
	}
	
	@Test(priority=6)
	public void DelayResponce_GETOpp_Successful() {
		given()
		.get("https://reqres.in/api/users?delay=3").
	then()
		.statusCode(200).time(lessThan(4000L));
	}
	
	@Test(priority=7)
	public void SingleUserSearch_GETOpp_Successful() {
		given()
		.get("https://reqres.in/api/users/2").
	then()
		.statusCode(200).body("data.first_name", equalTo("Janet"));
	}
	
	@Test(priority=8)
	public void SingleUserNotFound_GETOpp_Successful() {
		given()
		.get("https://reqres.in/api/users/23").
	then()
		.statusCode(404);
	}
	
	@Test(priority=9)
	public void UpdateUser_PATCHOpp_Successful() {
		JSONObject request_body = new JSONObject();
		request_body.put("name", "Ramaiah");
		request_body.put("job", "Commission agent");
		System.out.println("the request to update is : " + request_body.toJSONString());
		
		given()
			.header("Content-Type","application/json")
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
			.body(request_body.toJSONString())
		.when()
			.put("https://reqres.in/api/users/2")
		.then()
			.statusCode(200)
		.log().all()
		.body("job", equalTo("Commission agent"));
	}
	
	@Test(priority=10)
	public void DeleteUser_DELETEOpp_Successful() {
		given()
		.when()
	.delete("https://reqres.in/api/users/2")
		.then()
	.statusCode(204);
	}
	
}

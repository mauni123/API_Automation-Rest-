package stepDefinitions;

import static io.restassured.RestAssured.given;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.io.IOException;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import Resources.APIResources;
import Resources.TestDataBuild;
import Resources.Utils;

public class StepDefination extends Utils {
	
	ResponseSpecification responseSpec;
	RequestSpecification res;
	Response response;
	static String placeId;
	
	TestDataBuild data = new TestDataBuild();
	
	@Given("Add Place Payload with {string} {string} {string}")
	public void add_place_payload_with(String name, String language, String address) throws IOException {

		
		
		
		//RestAssured.baseURI = "https://rahulshettyacademy.com";
		
		 res = given().log().all().spec(requestSpecification())
		.body(data.addPlacePayload(name, language, address));
	}
	
	@When("user calls {string} with {string} http request")
	public void user_calls_with_http_request(String resource, String httpMethod) {
	    // Write code here that turns the phrase above into concrete actions
		
		APIResources resourceAPI = APIResources.valueOf(resource);
		System.out.println(resourceAPI.getResource());
		//Response Spec Builder
		 responseSpec = new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();
		 
		 if(httpMethod.equalsIgnoreCase("Post"))
		 {
		 response = res.when().post(resourceAPI.getResource());
				
		 }
		 else if(httpMethod.equalsIgnoreCase("Get"))
			 response = res.when().get(resourceAPI.getResource());
		 
	}
	@Then("the API call is success with status code {int}")
	public void the_api_call_is_success_with_status_code(Integer int1) {
	    // Write code here that turns the phrase above into concrete actions
	    assertEquals(response.getStatusCode(),200);
	}
	@Then("{string} in response body is {string}")
	public void in_response_body_is(String keyValue, String ExpectedValue) {
	    // Write code here that turns the phrase above into concrete actions
	   
	    
	    assertEquals(getJsonPath(response,keyValue),ExpectedValue);
	}
	
	@Then("verify place_Id created maps to {string} using {string}")
	public void verify_place_id_created_maps_to_using(String expextedName, String resource) throws IOException {
	    
		//requestSpec
		placeId = getJsonPath(response,"place_id");
		res = given().spec(requestSpecification()).queryParam("place_id", placeId);
		user_calls_with_http_request(resource,"GET");
		
		//Match the name
		String actualName = getJsonPath(response,"name");
		assertEquals(actualName,expextedName);
		
		
	}
	
	@Given("DeletePlace Payload")
	public void delete_place_payload() throws IOException {
	    // Write code here that turns the phrase above into concrete actions
	    
		res = given().spec(requestSpecification()).body(data.deletePlacePayload(placeId));
	}



}

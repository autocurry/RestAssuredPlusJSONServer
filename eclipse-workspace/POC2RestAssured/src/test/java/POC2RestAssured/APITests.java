package POC2RestAssured;

import org.testng.annotations.Test;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import java.io.File;

import org.json.JSONException;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;

import io.restassured.RestAssured;
import io.restassured.RestAssured.*;
import io.restassured.http.ContentType;
import io.restassured.matcher.RestAssuredMatchers.*;
import io.restassured.response.Response;



public class APITests {
	static ExtentReports _extent;
	static ExtentTest _test;
	ProcessBuilder pb;
	Process p ;
	int idElement = 7;
	
  @Test(groups="smoke")
  public void createUser() throws JSONException {
	  System.out.println("Inside test createUser");
	  	  
	
	  JSONObject json1 = new JSONObject(); 
	    json1.put("id",idElement);
	    json1.put("lookupid","lookup"+idElement); 
	    json1.put("name","some company");
	    json1.put("AUM",963963); 
	    
	    
	    Response body =   RestAssured.given()
	    
	    .accept(ContentType.JSON) 
	    .contentType(ContentType.JSON)
	    .body(json1.toString())
	    .post("http://localhost:3000/lookup") 
	    .then()
	    .statusCode(201)
	    .extract().response();  
	
	 
	 String stringbody = body.asString();
	 System.out.println(stringbody);
	  
	 _extent.startTest("MyFirstTest")
	  .log(LogStatus.PASS, stringbody);
	 
	  
  }
  
  
  @Test(groups="smoke")
  public void getAnotherUser() {
	  
	  System.out.println("Inside test getUser");
	  String fullurl = "http://localhost:3000/lookup/"+idElement;
	  	  
	  Assert.assertEquals(true, true);
	  
	  
		 Response body =  RestAssured.given()
		  .when().get(fullurl).then().extract().response();
		 
		 String stringbody = body.asString();
		 System.out.println(stringbody);
		  
	  
	  _extent.startTest("my second testcase")
	  .log(LogStatus.FAIL, stringbody);
  }
	
	
  	@BeforeClass 
  	public static void beforeClass() {
	  String reportFileName = "Test-Automaton-Report"+".html"; //// String
	  String reportFilepath = System.getProperty("user.dir") +"\\"+ "TestReport";	 
	  _extent = new ExtentReports(reportFilepath+"\\"+reportFileName);
	   
	  //Set environment details 
	  
	    }
  	
  	@AfterClass
  	public static void afterClass() {
  		_extent.flush();
  	}
  	
  	@BeforeTest
  	public void beforeTest() {
  		System.out.println("inside before test");
  		
  		try {
  			File dir = new File("G:\\jsonserver\\");
  	         pb = new ProcessBuilder("cmd.exe", "/C", "Start","launchjsonserver.bat");
  	        pb.directory(dir);
  	        p= pb.start();
  	        Thread.sleep(10000);
  		}
  		catch(Exception e) {
  			System.out.println(e.getMessage());
  		}
  	}
  	@AfterTest
  	public void afterTest() {
  		System.out.println("inside after test");
  		
  			p.destroy();

  	  		if(p!=null) {
  	  			System.out.println("p is not null");
  	  		}
  			p.destroyForcibly();
  		
  	}
	  
}

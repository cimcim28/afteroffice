package stepdefinitions;

import java.util.Map;

import org.testng.Assert;

import com.afterofficeprisma.model.ResponseItem;
import com.afterofficeprisma.model.request.RequestItem;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import static io.restassured.RestAssured.given;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import resources.DataRequest;

public class StepDefinitions {

    /*
     *  Given A list of products are available
        When I add new products to etalase
        Then The product is available
     */

    static ResponseItem responseItem;
    DataRequest dataRequest;
    RequestItem requestItem;
    String json;
    String idProduct;

    @Given("A list of products are available")
    public void getAllProducts(){

        System.out.println("Get all products");
        RestAssured.baseURI = "https://api.restful-api.dev";
        
        Response AddResponse = given()
                                .log().all().get("objects");
    
        System.out.println("Hasilnya adalah " + AddResponse.asPrettyString());

    }

    @When("I add new product to etalase")
    public void addNewProduct(){

        System.out.println("Add new products");

        String json = """
            {
                "name": "Macbook Pro",
                "data": {
                    "year": 2025,
                    "price": 10000000,
                    "CPU model": "Intel Core",
                    "Hard disk size": "500GB"
                }
            }
            """;

        Response AddResponse =  given()
                                .log()
                                .all()
                                .contentType("application/json")
                                .body(json)
                                .when()
                                .post("/objects");

        System.out.println("Add product " + AddResponse.asPrettyString());

        JsonPath jsonPath = AddResponse.jsonPath();
        responseItem = jsonPath.getObject("$", ResponseItem.class);

        Assert.assertEquals(AddResponse.statusCode(), 200);
        Assert.assertEquals(responseItem.name, "Macbook Pro");
        Assert.assertNotNull(responseItem.createdAt);
        Assert.assertEquals(responseItem.dataItem.year, 2025);
        Assert.assertEquals(responseItem.dataItem.price, 10000000);
        Assert.assertEquals(responseItem.dataItem.cpuModel, "Intel Core");
        Assert.assertEquals(responseItem.dataItem.hardDiskSize, "500GB");

        /*
         * Simulate kalau idproduct nya kita dapat dari responseItem.id,
         * Tapi karena id nya akan selalu sama bakanya kita modify manual
         *  idProduct = responseItem.id;
         */
        // idProduct = 1;

        // String idObject = responseItem.id;
        System.out.println("Product added with ID: " + idProduct);

        idProduct = jsonPath.getString("id"); // Mengambil id sebagai String
        System.out.println("Product added with ID: " + idProduct);
        Assert.assertNotNull(idProduct, "ID product is null!");

        System.out.println("JSON: " + json);

    }

    @When("I add new {string} to etalase")
    public void addNewProducts(String payload) throws JsonMappingException, JsonProcessingException{

        System.out.println("Add new products");

        dataRequest = new DataRequest();

        RestAssured.baseURI = "https://api.restful-api.dev";  
        
        for(Map.Entry<String, String> entry : dataRequest.addItemCollection().entrySet()){
            if (entry.getKey().equals(payload)) {
                json = entry.getValue();
                break;
            }
        }

        Response AddResponse =  given()
                                .log()
                                .all()
                                .contentType("application/json")
                                .body(json)
                                .when()
                                .post("/objects");

        System.out.println("Add products more than 1 " + AddResponse.asPrettyString());

        //Object mapper
        /*
         * Convert JSON to POJO
         */
        ObjectMapper requestAddItem = new ObjectMapper();
        requestItem = requestAddItem.readValue(json, RequestItem.class);

        JsonPath jsonPath = AddResponse.jsonPath();
        responseItem = jsonPath.getObject("$", ResponseItem.class);

        Assert.assertEquals(AddResponse.statusCode(), 200);
        Assert.assertEquals(responseItem.name,requestItem.name);
        Assert.assertNotNull(responseItem.createdAt);
        Assert.assertEquals(responseItem.dataItem.year, requestItem.dataItem.year);
        Assert.assertEquals(responseItem.dataItem.price, requestItem.dataItem.price);
        Assert.assertEquals(responseItem.dataItem.cpuModel, requestItem.dataItem.cpuModel);
        Assert.assertEquals(responseItem.dataItem.hardDiskSize, requestItem.dataItem.hardDiskSize);
    
        System.out.println("Payload: " + payload);
        System.out.println("JSON: " + json);

        }

    @Then("The product is available")
        public void getSingleProduct(){

        System.out.println("Get Single Product is available");

        RestAssured.baseURI = "https://api.restful-api.dev";  

        Response singleResponse = given()
                                .when()
                                .log()
                                .all()
                                .get("objects/{idProduct}", 7);

        System.out.println("Hasilnya GET " + singleResponse.asPrettyString()); 

        Assert.assertEquals(singleResponse.statusCode(), 200);
        Assert.assertNotNull(singleResponse.jsonPath().getString("id"));
        // Assert.assertNotNull(idProduct);
        Assert.assertEquals(responseItem.name, "Macbook Pro");
        Assert.assertEquals(responseItem.dataItem.year, 2025);
        Assert.assertEquals(responseItem.dataItem.price, 10000000);
        Assert.assertEquals(responseItem.dataItem.cpuModel, "Intel Core");
        Assert.assertEquals(responseItem.dataItem.hardDiskSize, "500GB");

    }
}
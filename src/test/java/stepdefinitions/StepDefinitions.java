package stepdefinitions;

import java.util.Map;

import org.testng.Assert;

import com.afterofficeprisma.model.ResponseItem;
import com.afterofficeprisma.model.request.RequestItem;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import apiengine.Assertion;
import apiengine.Endpoints;
import io.cucumber.java.BeforeStep;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import resources.DataRequest;

public class StepDefinitions {

    /*
     *  Given A list of products are available
        When I add new products to etalase
        Then The product is available
     */

    ResponseItem responseItem;
    DataRequest dataRequest;
    RequestItem requestItem;
    String json;
    String idProduct;
    Endpoints endpoints;
    Response response;
    Assertion assertion;

    @BeforeStep
    public void setUp(){
        endpoints = new Endpoints();
        assertion = new Assertion();
    }

    @Given("A list of products are available")
    public void getAllProducts(){
        response = endpoints.getAllProducts("products");
        System.out.println("Hasilnya adalah " + response.asPrettyString());

    }
    @When("I add new product to etalase")
    public void addNewProduct() throws JsonMappingException, JsonProcessingException {
        // JSON payload untuk produk baru
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
    
        // Mengirimkan request untuk menambah produk
        Response addResponse = endpoints.addProductData("/objects", json);
    
        // Menampilkan response ke console
        System.out.println("Add product response: " + addResponse.asPrettyString());
    
        // Mengambil data dari response
        JsonPath jsonPath = addResponse.jsonPath();
        responseItem = jsonPath.getObject("$", ResponseItem.class);
    
        // Mengambil ID produk
        idProduct = jsonPath.getString("id");
        System.out.println("Product added with ID: " + idProduct);
    
        // Validasi ID produk tidak null
        Assert.assertNotNull(idProduct, "ID product is null!");
    
        // Konversi JSON request ke POJO (RequestItem)
        ObjectMapper objectMapper = new ObjectMapper();
        requestItem = objectMapper.readValue(json, RequestItem.class);
    
        // Menampilkan JSON request
        System.out.println("Request JSON: " + json);
    
        // Melakukan assert untuk memverifikasi data produk
        if (requestItem != null) {
            assertion.assertAddProduct(responseItem, requestItem);
        } else {
            System.out.println("requestItem is null. Skipping assertion.");
        }
    }
    
    @When("I add new {string} to etalase")
    public void addNewProducts(String payload) throws JsonMappingException, JsonProcessingException {
        dataRequest = new DataRequest();
    
        for (Map.Entry<String, String> entry : dataRequest.addItemCollection().entrySet()) {
            if (entry.getKey().equals(payload)) {
                json = entry.getValue();
                break;
            }
        }
    
        // Mengirimkan request untuk menambah produk
        Response addResponse = endpoints.addProductData("/objects", json);
    
        // Menampilkan response ke console
        System.out.println("Add products response: " + addResponse.asPrettyString());
    
        // Konversi JSON ke POJO (RequestItem)
        ObjectMapper objectMapper = new ObjectMapper();
        requestItem = objectMapper.readValue(json, RequestItem.class);
    
        // Mengambil data dari response dan konversi ke POJO (ResponseItem)
        JsonPath jsonPath = addResponse.jsonPath();
        responseItem = jsonPath.getObject("$", ResponseItem.class);
    
        // Menampilkan payload dan JSON request
        System.out.println("Payload: " + payload);
        System.out.println("Request JSON: " + json);
    
        // Melakukan assert untuk memverifikasi data produk
        if (requestItem != null) {
            assertion.assertAddProduct(responseItem, requestItem);
        } else {
            System.out.println("requestItem is null. Skipping assertion.");
        }
    }
    

    @Then("The product is available")
    public void getSingleProduct(){
        Response singleResponse = endpoints.getProductById("objects", 7);

        // Menampilkan hasil response
        System.out.println("Hasil GET: " + singleResponse.asPrettyString());

    }
}
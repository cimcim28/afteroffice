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
        System.out.println("Hasilnya get All Product " + response.asPrettyString());

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
    
       // Ambil idProduct
        JsonPath jsonPath = addResponse.jsonPath();
        idProduct = jsonPath.getString("id");
        System.out.println("Product added with ID: " + idProduct);
        Assert.assertNotNull(idProduct, "ID product is null!");

        // Konversi JSON ke RequestItem
        ObjectMapper objectMapper = new ObjectMapper();
        requestItem = objectMapper.readValue(json, RequestItem.class);

        // Konversi response ke ResponseItem
        responseItem = jsonPath.getObject("$", ResponseItem.class);

        // Validasi produk
        assertion.assertAddProduct(responseItem, requestItem);
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
    
        // Ambil idProduct
        JsonPath jsonPath = addResponse.jsonPath();
        idProduct = jsonPath.getString("id");
        System.out.println("Product added with ID: " + idProduct);
        Assert.assertNotNull(idProduct, "ID product is null!");

        // Konversi JSON request ke RequestItem
        ObjectMapper objectMapper = new ObjectMapper();
        requestItem = objectMapper.readValue(json, RequestItem.class);

        // Konversi response ke ResponseItem
        responseItem = jsonPath.getObject("$", ResponseItem.class);

        // Validasi produk
        assertion.assertAddProduct(responseItem, requestItem);
    }
    
    @Then("The product is available")
    public void getSingleProduct(){

        if (idProduct == null) {
            throw new IllegalStateException("idProduct is null! Make sure to add a product first.");
        }
        Response singleResponse = endpoints.getProductById("objects", idProduct);

        // Menampilkan hasil response
        System.out.println("Hasil GET: " + singleResponse.asPrettyString());
    }

    @Then("I can update item {string}")
    public void updateSingleProduct(String payload){

        for(Map.Entry<String, String> entry : dataRequest.addItemCollection().entrySet()){
            if (entry.getKey().equals(payload)) {
                json = entry.getValue();
                break;
            }
        }
        // Validasi idProduct
        if (idProduct == null) {
            throw new IllegalStateException("idProduct is null! Cannot update product.");
        }
        response = endpoints.updateProductById("objects", idProduct, json);

        // Tampilkan response
        System.out.println("Update product response: " + response.asPrettyString());
    }
}
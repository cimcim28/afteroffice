package apiengine;

import com.afterofficeprisma.constants.Constants;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class Endpoints {

    private final RequestSpecification requestSpecification;

    public Endpoints() {
        RestAssured.baseURI = Constants.BASE_URL;
        this.requestSpecification = RestAssured.given()
                                               .contentType("application/json")
                                               .log().all();
    }

    public RequestSpecification getRequestSpecification() {
        return requestSpecification;
    }
    
    public Response getAllProducts(String path){
        return RestAssured.given()
                .when()
                .get(path);
    }

    public Response addProductData(String path, String json) {
        return RestAssured.given()
                .contentType("application/json")
                .body(json)
                .when()
                .post(path);
    }

    public Response getProductById(String path, String idProduct) {
        return RestAssured.given()
                .pathParam("path", path)
                .pathParam("idProduct", idProduct)
                .when()
                .get("{path}/{idProduct}");
    }

    public Response updateProductById(String path, String idProduct, String json) {
        return RestAssured.given()
                .body(json)
                .pathParam("path", path)
                .pathParam("idProduct", idProduct)
                .when()
                .put("{path}/{idProduct}");
    }
}
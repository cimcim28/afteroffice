package restapi;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.afterofficeprisma.model.ResponseItem;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class RestApiTest {

    static ResponseItem responseItem;
    
        public static void main(String[] args) {
            // listAllOfObject();
            // listOfObjectByIds();
            // singleObject();
            // addObject();
            // updateObject();
            // partiallyUpdateObject();
            // deleteObject();
        }
    
        @Test
        public static void listAllOfObject(){
            //Define baseURI
           /*
            *  "https://api.restful-api.dev/objects"
            baseURI = https://api.restful-api.dev
            path = objects
    
                    [
                {
                    "id": "1",
                    "name": "Google Pixel 6 Pro",
                    "data": {
                        "color": "Cloudy White",
                        "capacity": "128 GB"
                    }
                },
            */
    
            RestAssured.baseURI = "https://api.restful-api.dev";
            RequestSpecification requestSpecification = RestAssured
                                                        .given();
    
            Response response = requestSpecification.log().all().get("objects");
    
            System.out.println("Hasilnya adalah " + response.asPrettyString());
            
            JsonPath jsonPath = response.jsonPath();
    
            List<ResponseItem> responseItems = jsonPath.getList(".", ResponseItem.class);
            
            ResponseItem firstItem = responseItems.get(0);

            Assert.assertEquals(response.statusCode(), 200);
            Assert.assertEquals(firstItem.id, "1");
            Assert.assertEquals(firstItem.name, "Google Pixel 6 Pro");
            Assert.assertEquals(firstItem.dataItem.color, "Cloudy White");
            Assert.assertEquals(firstItem.dataItem.capacity, "128 GB");   
    }

    @Test
    public static void listOfObjectByIds(){

        RestAssured.baseURI = "https://api.restful-api.dev";
        RequestSpecification requestSpecification = RestAssured
                                                    .given();

        Response response = requestSpecification
                            .log()
                            .all()
                            .pathParam("path", "objects")
                            .queryParam("idProduct", "3,5,10")
                            .when()
                                .get("{path}");

        System.out.println("Hasilnya adalah " + response.asPrettyString());
    }

    @Test
    public static void singleObject(){

        RestAssured.baseURI = "https://api.restful-api.dev";
        RequestSpecification requestSpecification = RestAssured
                                                    .given();

        Response response = requestSpecification
                            .log()
                            .all()
                            .pathParam("path", "objects")
                            .pathParam("idProduct", "7")
                            .when()
                                .get("{path}/{idProduct}");

        System.out.println("Hasilnya adalah " + response.asPrettyString());

        JsonPath jsonPath = response.jsonPath();

        responseItem = jsonPath.getObject(".", ResponseItem.class);

        Assert.assertEquals(response.statusCode(), 200);
        Assert.assertEquals(responseItem.id, "7");
        Assert.assertEquals(responseItem.name, "Apple MacBook Pro 16");
        Assert.assertEquals(responseItem.dataItem.year, 2019);
        Assert.assertEquals(responseItem.dataItem.price, 1849.99);
        Assert.assertEquals(responseItem.dataItem.cpuModel, "Intel Core i9");
        Assert.assertEquals(responseItem.dataItem.hardDiskSize, "1 TB");

    }

    @Test
    public static void addObject(){

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

        RestAssured.baseURI = "https://api.restful-api.dev";
        RequestSpecification requestSpecification = RestAssured
                                                    .given();

        Response response = requestSpecification
                            .log()
                            .all()
                            .contentType("application/json")
                            .body(json)
                            .when()
                                .post("/objects");

        System.out.println("Hasilnya adalah " + response.asPrettyString());

        JsonPath jsonPath = response.jsonPath();

        responseItem = jsonPath.getObject(".", ResponseItem.class);

        Assert.assertEquals(response.statusCode(), 200);
        Assert.assertEquals(responseItem.name, "Macbook Pro");
        Assert.assertNotNull(responseItem.createdAt);
        Assert.assertEquals(responseItem.dataItem.year, 2025);
        Assert.assertEquals(responseItem.dataItem.price, 10000000);
        Assert.assertEquals(responseItem.dataItem.cpuModel, "Intel Core");
        Assert.assertEquals(responseItem.dataItem.hardDiskSize, "500GB");
    }

    @Test
    public static void updateObject(){
        // ff808181932badb601952bcb48177ecf

        String json;
        json = """
               {
                  "name": "Macbook Pro Update",
                  "data": {
                     "year": 2026,
                     "price": 12000000,
                     "CPU model": "Intel Core Update",
                     "Hard disk size": "500GB Update"
                  }
               }""";

        RestAssured.baseURI = "https://api.restful-api.dev";
        RequestSpecification requestSpecification = RestAssured
                                                    .given();

        Response response = requestSpecification
                            .log()
                            .all()
                            .pathParam("path", "objects")
                            .pathParam("idProduct", "ff808181932badb601952bcb48177ecf")
                            .contentType("application/json")
                            .body(json)
                            .when()
                                .put("{path}/{idProduct}");

        System.out.println("Hasilnya adalah " + response.asPrettyString());

        JsonPath jsonPath = response.jsonPath();

        responseItem = jsonPath.getObject(".", ResponseItem.class);

        Assert.assertEquals(response.statusCode(), 200);
        Assert.assertEquals(responseItem.name, "Macbook Pro Update");
        Assert.assertNotNull(responseItem.updatedAt);
        Assert.assertEquals(responseItem.dataItem.year, 2026);
        Assert.assertEquals(responseItem.dataItem.price, 12000000);
        Assert.assertEquals(responseItem.dataItem.cpuModel, "Intel Core Update");
        Assert.assertEquals(responseItem.dataItem.hardDiskSize, "500GB Update");

    }

    @Test
    public static void partiallyUpdateObject(){

        String json = """
                      {
                         "name": "Apple MacBook Pro 16 (Updated Name)"
                      }"""
                        ;

        RestAssured.baseURI = "https://api.restful-api.dev";
        RequestSpecification requestSpecification = RestAssured
                                                    .given();

        Response response = requestSpecification
                            .log()
                            .all()
                            .pathParam("path", "objects")
                            .pathParam("idProduct", "ff808181932badb601952bcb48177ecf")
                            .contentType("application/json")
                            .body(json)
                            .when()
                                .patch("{path}/{idProduct}");

        System.out.println("Hasilnya adalah " + response.asPrettyString());

        JsonPath jsonPath = response.jsonPath();

        responseItem = jsonPath.getObject(".", ResponseItem.class);

        Assert.assertEquals(response.statusCode(), 200);
        Assert.assertEquals(responseItem.name, "Apple MacBook Pro 16 (Updated Name)");
        Assert.assertNotNull(responseItem.updatedAt);

    }

    @Test
    public static void deleteObject(){

        // ff808181932badb601952bcb48177ecf
        RestAssured.baseURI = "https://api.restful-api.dev";
        RequestSpecification requestSpecification = RestAssured
                                                    .given();

        Response response = requestSpecification
                            .log()
                            .all()
                            .pathParam("path", "objects")
                            .pathParam("idProduct", "ff808181932badb601952bcb48177ecf")
                            .when()
                                .delete("{path}/{idProduct}");
        System.out.println("delete product" + response.asPrettyString());

        Assert.assertEquals(response.statusCode(), 200);
    }
}

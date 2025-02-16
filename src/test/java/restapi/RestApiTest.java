package restapi;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class RestApiTest {

    public static void main(String[] args) {
        // listAllOfObject();
        // listOfObjectByIds();
        singleObject();
        // addObject();
        // updateObject();
        // partiallyUpdateObject();
        // deleteObject();
    }

    public static void listAllOfObject(){
        //Define baseURI
       /*
        *  "https://api.restful-api.dev/objects"
        baseURI = https://api.restful-api.dev
        path = objects
        */

        RestAssured.baseURI = "https://api.restful-api.dev";
        RequestSpecification requestSpecification = RestAssured
                                                    .given();

        Response response = requestSpecification.log().all().get("objects");

        System.out.println("Hasilnya adalah " + response.asPrettyString());
    }

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
    }

    public static void addObject(){

        String json = """
            {
                "name": "Macbook Pro",
                "data": {
                    "year": 2025,
                    "price": 2025.99,
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

    }

    public static void updateObject(){
        // ff808181932badb601950897c7fd2db5

        String json;
        json = """
               {
                  "name": "Macbook Pro Update",
                  "data": {
                     "year": 2026,
                     "price": 2026.99,
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
                            .pathParam("idProduct", "ff808181932badb601950897c7fd2db5")
                            .contentType("application/json")
                            .body(json)
                            .when()
                                .put("{path}/{idProduct}");

        System.out.println("Hasilnya adalah " + response.asPrettyString());

    }

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
                            .pathParam("idProduct", "ff808181932badb6019508a1d3992dbd")
                            .contentType("application/json")
                            .body(json)
                            .when()
                                .patch("{path}/{idProduct}");

        System.out.println("Hasilnya adalah " + response.asPrettyString());

    }

    public static void deleteObject(){

        // ff808181932badb6019508a1d3992dbd
        RestAssured.baseURI = "https://api.restful-api.dev";
        RequestSpecification requestSpecification = RestAssured
                                                    .given();

        Response response = requestSpecification
                            .log()
                            .all()
                            .pathParam("path", "objects")
                            .pathParam("idProduct", "ff808181932badb601950897c7fd2db5")
                            .when()
                                .delete("{path}/{idProduct}");
        System.out.println("delete product" + response.asPrettyString());
    }
}

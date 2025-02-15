package restapi;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class RestApiTest {

    public static void main(String[] args) {

        // listAllOfObject();
        // listOfObjectByIds();
        // singleObject();
        // addObject();
        // updateObject();
        partiallyUpdateObject();
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

        String json = "{\n" + //
                        "   \"name\": \"Macbook Pro\",\n" + //
                        "   \"data\": {\n" + //
                        "      \"year\": 2025,\n" + //
                        "      \"price\": 2025.99,\n" + //
                        "      \"CPU model\": \"Intel Core\",\n" + //
                        "      \"Hard disk size\": \"500GB\"\n" + //
                        "   }\n" + //
                        "}";

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

        String json = "{\n" + //
                        "   \"name\": \"Macbook Pro Update\",\n" + //
                        "   \"data\": {\n" + //
                        "      \"year\": 2026,\n" + //
                        "      \"price\": 2026.99,\n" + //
                        "      \"CPU model\": \"Intel Core Update\",\n" + //
                        "      \"Hard disk size\": \"500GB Update\"\n" + //
                        "   }\n" + //
                        "}";

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

        String json = "{\n" + //
                        "   \"name\": \"Apple MacBook Pro 16 (Updated Name)\"\n" + //
                        "}";

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

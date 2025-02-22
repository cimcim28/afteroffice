package restapi.scenario;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.afterofficeprisma.model.ResponseItem;

import io.restassured.RestAssured;
import static io.restassured.RestAssured.given;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class E2eTest {

    static ResponseItem responseItem;

    @Test
    public void scenarioE2eTest() {

        /*
         * Scenario Add Product
            Create new object (hit API add_object)
            Verify new object is added (hit API single_object)
            Delete product (hit API delete_object)
            Verify new object is deleted (hit API single_object)
                Note:
                1. Untuk E2E test buat dalam satu function test
                2. Submission buat dalam satu branch jangan di merge ke master
         */


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

        // Add product
        RestAssured.baseURI = "https://api.restful-api.dev";    

        Response AddResponse =  given()
                                .log()
                                .all()
                                .contentType("application/json")
                                .body(json)
                                .when()
                                    .post("/objects");

        System.out.println("Hasilnya adalah " + AddResponse.asPrettyString());

        JsonPath jsonPath = AddResponse.jsonPath();

        // validation pojo
        responseItem = jsonPath.getObject(".", ResponseItem.class);

        Assert.assertEquals(AddResponse.statusCode(), 200);
        Assert.assertEquals(responseItem.name, "Macbook Pro");
        Assert.assertNotNull(responseItem.createdAt);
        Assert.assertEquals(responseItem.dataItem.year, 2025);
        Assert.assertEquals(responseItem.dataItem.price, 10000000);
        Assert.assertEquals(responseItem.dataItem.cpuModel, "Intel Core");
        Assert.assertEquals(responseItem.dataItem.hardDiskSize, "500GB");

        String idObject = responseItem.id;

        // Get single product
        Response singleResponse = given()
                                .when()
                                .log()
                                .all()
                                .get("objects/{idProduct}", idObject);

        System.out.println("Hasilnya adalah " + singleResponse.asPrettyString()); 

        JsonPath jsonPath2 = singleResponse.jsonPath();

        responseItem = jsonPath2.getObject(".", ResponseItem.class);

        Assert.assertEquals(singleResponse.statusCode(), 200);
        Assert.assertNotNull(idObject);
        Assert.assertEquals(responseItem.name, "Macbook Pro");
        Assert.assertEquals(responseItem.dataItem.year, 2025);
        Assert.assertEquals(responseItem.dataItem.price, 10000000);
        Assert.assertEquals(responseItem.dataItem.cpuModel, "Intel Core");
        Assert.assertEquals(responseItem.dataItem.hardDiskSize, "500GB");


        // Delete product
        Response deleteResponse = given()
                                .when()
                                .log()
                                .all()
                                .delete("objects/{idProduct}", idObject);

        System.out.println("hasil delete" + deleteResponse.asPrettyString());
        Assert.assertEquals(deleteResponse.statusCode(), 200);

        // Verifikasi bahwa objek sudah dihapus
        Response getResponse = given()
                        .when()
                        .pathParam("path", "objects")
                        .pathParam("idProduct", idObject)
                        .log().all()
                        .get("{path}/{idProduct}");

        System.out.println("Hasil verifikasi: " + getResponse.asPrettyString());

        Assert.assertEquals(getResponse.statusCode(), 404);

        JsonPath jsonPath3 = getResponse.jsonPath();
        String message = jsonPath3.getString("message");

        System.out.println("Pesan yang diterima: " + message);

            // Verifikasi pesan atau jika kosong, pastikan sesuai ekspektasi
            if (message != null) {
                String expectedMessage = "Object with id = " + idObject + " has been deleted.";
                Assert.assertEquals(message, expectedMessage);
            } else {
                // Pastikan status code menunjukkan data memang sudah dihapus (404 misalnya)
                Assert.assertEquals(getResponse.statusCode(), 404);
            }
    }
}

package org.loonycorn.restassuredtests;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.response.ValidatableResponse;
import org.testng.Assert;
import org.testng.annotations.Test;

public class RestAssuredTests03 {

    private static final String URL = "https://httpbin.org/get";

    @Test
    void basicResponseTest() {
        Response response = RestAssured.get(URL);

        Assert.assertEquals(response.getHeader("Access-Control-Allow-Origin"),"*");
        Assert.assertEquals(response.getHeader("Server"),"gunicorn/19.9.0");
        Assert.assertEquals(response.getHeader("Content-Length"),"322");
    }

    @Test
    void validateResponseTest() {
        // Pour valider différentes parties de la réponse reçue, il faut utiliser le validatableResponse "then()"
        RestAssured.get(URL)
                .then()
                .statusCode(200)
                .statusLine("HTTP/1.1 200 OK")
                .contentType("application/json");
    }
    @Test
    void validateResponseTest2() {
        // j'appel RestAssured.get() que je passe à la fonction then()
        ValidatableResponse vResponse = RestAssured.get(URL)
                        .then();

        vResponse = vResponse.statusCode(200);
        vResponse = vResponse.statusLine("HTTP/1.1 200 OK");
        vResponse = vResponse.contentType("application/json");
        vResponse = vResponse.header("Server", "gunicorn/19.9.0");
        vResponse = vResponse.header("Access-Control-Allow-Origin", "*");
    }
    @Test
    void validateResponseTest3() {
        // j'appel RestAssured.get() que je passe à la fonction then()
        ValidatableResponse vResponse = RestAssured.get(URL)
                        .then()
                .statusCode(200)
                .statusLine("HTTP/1.1 200 OK")
                .contentType(ContentType.JSON)
                .header("Server", "gunicorn/19.9.0")
                .header("Access-Control-Allow-Origin", "*")
                .header("Content-Length", "322");
    }
}

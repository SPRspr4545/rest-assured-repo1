package org.loonycorn.restassuredtests;

import io.restassured.RestAssured;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import org.apache.http.Header;
import org.testng.Assert;
import org.testng.annotations.Test;

public class RestAssuredTests02 {

    private static final String URL = "https://httpbin.org/get";

    @Test
    void basicResponseTest() {
        Response response = RestAssured.get(URL);

        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertEquals(response.getStatusLine(), "HTTP/1.1 200 OK");
        Assert.assertEquals(response.getContentType(), "application/json");

        Assert.assertEquals(response.statusCode(), 200);
        Assert.assertEquals(response.statusLine(), "HTTP/1.1 200 OK");
        Assert.assertEquals(response.contentType(), "application/json");

        // Accéder au Body
        ResponseBody responseBody = response.getBody();
        responseBody.peek();
        // asString converti la réponse en chaîne pour vérifier qu'elle contient bien "httpbin.org"
        Assert.assertTrue(responseBody.asString().contains("httpbin.org"));
        Assert.assertTrue(responseBody.asString().contains("gzip,deflate"));

        // Accéder aux entêtes
        Headers headers = response.getHeaders();
        // vérifie la présence de certaines entêtes
        Assert.assertTrue(headers.hasHeaderWithName("Content-Length"));
        Assert.assertTrue(headers.hasHeaderWithName("Content-Type"));

        // spécifier les entêtes par leur nom pour en vérifier la valeur
        Assert.assertEquals(headers.get("Access-Control-Allow-Origin").getValue(), "*");
        Assert.assertEquals(headers.get("Server").getValue(), "gunicorn/19.9.0");

        Assert.assertEquals(response.getHeader("Access-Control-Allow-Origin"),"*");
        Assert.assertEquals(response.getHeader("Server"),"gunicorn/19.9.0");
        Assert.assertEquals(response.getHeader("Content-Length"),"322");
    }
}

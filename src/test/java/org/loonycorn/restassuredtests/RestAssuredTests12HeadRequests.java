package org.loonycorn.restassuredtests;

import io.restassured.RestAssured;
import org.testng.annotations.Test;

import static org.hamcrest.Matchers.*;

public class RestAssuredTests12HeadRequests {

    //REST Assured API Testing: Testing Different Types of HTTP Endpoints
    // Making HEAD and OPTIONS Requests

    private static final String URL = "https://httpbin.org";

    @Test
    public void testHEADEmptyBody() {
        // la méthode HTTP HEAD est similaires à la méthode GET
        // sauf qu'elle demande au serveur de répondre uniquement les entêtes sans body
        RestAssured
                .head(URL)
                .prettyPeek()
                .then()
                    .statusCode(200)
                    .body(emptyOrNullString());
    }

    @Test
    public void testOPTIONEmptyBody() {
        // la méthode HTTP OPTION est utilisée pour décrire le options de communication pour la ressource cible
        // le serveur renvoie les méthodes HTTP disponibles pour l'URL spécifiée
        RestAssured
                .options(URL)
                .peek()
                .then()
                    .statusCode(200)
                    .header("Allow", allOf(
                            containsString("OPTIONS"),
                            containsString("GET"),
                            containsString("HEAD")
                    ))
                .header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, PATCH, OPTIONS")
                .body(emptyOrNullString());
    }

}

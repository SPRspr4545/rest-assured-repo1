package org.loonycorn.restassuredtests;

import io.restassured.RestAssured;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

import static org.hamcrest.Matchers.*;

public class RestAssuredTests04 {

    private static final String POSTS_URL = "https://jsonplaceholder.typicode.com/posts";

    @Test
    void validateHeaders1() {
        RestAssured
                .get(POSTS_URL)
                .peek(); // permet de voir les entêtes et le corps de la réponse
    }

    @Test
    void validateHeaders2() {
        RestAssured
                .get(POSTS_URL)
                .then()
                .statusCode(200)
                .statusCode(greaterThanOrEqualTo(200)); // transmettre une interface de type Matcher à statusCode()
                // pour vérifier sir le statusCode() renvoyé est >= à 200
    }

    @Test
    void validateHeaders3() {
        RestAssured
                .get(POSTS_URL)
                .then()
                .statusCode(200)
                .statusCode(allOf(greaterThanOrEqualTo(200), lessThanOrEqualTo(300))); // vérifier avec allOf() si le statusCode() se trouve dans une plage

    }

    @Test
    void validateHeaders4() {
        RestAssured
                .get(POSTS_URL)
                .then()
                .statusCode(200)
                .statusCode(allOf(greaterThanOrEqualTo(200), lessThanOrEqualTo(300)))
                .time(lessThan(5L), TimeUnit.SECONDS); // mesurer le temps de réponse de l'API dansun délai de 5 secondes

    }

    @Test
    void validateHeaders5() {
        RestAssured
                .get(POSTS_URL)
                .then()
                .statusCode(200)
                .statusCode(allOf(greaterThanOrEqualTo(200), lessThanOrEqualTo(300)))
                .time(lessThan(5L), TimeUnit.SECONDS)
                .header("Content-Type", notNullValue()) // vérifier que le header "Content-Type" correspond à l'attendu
                .header("Content-Type", equalTo("application/json; charset=utf-8"))
                .header("Connection", notNullValue()) // vérifier que le header "Connection" correspond à l'attendu
                .header("Connection", equalTo("keep-alive"))
                .header("Etag", notNullValue())
                .header("Cache-Control", containsStringIgnoringCase("max-age=43200"))
                .header("Expires", equalTo("-1"))
                .header("X-Ratelimit-Limit", equalTo("1000"));
        // même les entêtes contenant des valeurs numériques sont comparées sous forme de chaînes "String"
    }

    @Test
    void validateHeaders6() {
        // il existe d'autres options, pour une correspondance avancée avec le bon type de données
        RestAssured
                .get(POSTS_URL)
                .then()


                .statusCode(200)
                .statusCode(allOf(greaterThanOrEqualTo(200), lessThanOrEqualTo(300)))
                .time(lessThan(5L), TimeUnit.SECONDS)
                .header("Content-Type", notNullValue()) // vérifier que le header "Content-Type" correspond à l'attendu
                .header("Content-Type", equalTo("application/json; charset=utf-8"))
                .header("Connection", notNullValue()) // vérifier que le header "Connection" correspond à l'attendu
                .header("Connection", equalTo("keep-alive"))
                .header("Etag", notNullValue())
                .header("Cache-Control", containsStringIgnoringCase("max-age=43200"))
                .header("Expires", equalTo("-1"))
                .header("X-Ratelimit-Limit", equalTo("1000"));

    }

}

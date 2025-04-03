package org.loonycorn.restassuredtests;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.restassured.RestAssured;
import org.testng.annotations.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
                .header("Expires", Integer::parseInt, equalTo(-1))
                .header("X-Ratelimit-Limit", Integer::parseInt, equalTo(1000))
                .header("X-Ratelimit-Remaining", Integer::parseInt, equalTo(999))
                .header("X-Ratelimit-Reset", Integer::parseInt, equalTo(1743544555));
        // Integer::parseInt est la méthode de référence appliquée à l'entête pour obtenir le bon format
    }

    @Test
    void validateHeaders7() {
        // la bibliothèque fasterxml.jackson.core permet d'analyser et sérialiser JSON
        ObjectMapper objectMapper = new ObjectMapper();

        RestAssured
                .get(POSTS_URL)
                .then()
                .header("report-to", (s) -> {
                    try {
                        return objectMapper.readTree(s).get("max_age").asInt();
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException(e);
                    }
                }, equalTo(3600));
    // la fonction "objectMapper.readTree(s)" permet de lire la chaîne sous forme d'objet JSON
    // une fois que j'ai l'objet JSON, j'obtiens la valeur "max_age" sous forme d'entier
    // on peut ainsi utiliser le matcherHamcrest "equalTo()" pour comparer l'entier 3600
    // si le JSON n'est pas bien formaté, ça génère un "JsonProcessingException"
    // qui lance un "new RuntimeException()" pour indiquer que l'analyse a échoué
    }

    @Test
    void validateHeaders8() {
        // les DATES sont renvoyées sous forme de chaîne et nous la validons comme Date réelle
        RestAssured.get(POSTS_URL)
                .then()
                .header("Date", (dateString) -> {
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE, dd MMM yyyy HH:mm:ss zzz");
                    LocalDateTime dateTime = LocalDateTime.parse(dateString, formatter);

                    // Obtenir l'année à partir de l'objet LocalDateTime
                    return  dateTime.getYear();
                }, equalTo(2025));


    }

    @Test
    void validateHeaders9() {
        // les DATES sont renvoyées sous forme de chaîne et nous la validons comme Date réelle
        RestAssured.get(POSTS_URL)
                .then()
                .header(
                        "Date",
                        (dateString) ->
                                LocalDate.parse(dateString, DateTimeFormatter.RFC_1123_DATE_TIME),
                        equalTo(LocalDate.now())
                );
        // La date est renvoyée dans l'entête de réponse au format RFC_1123_DATE_TIME
        // on vérifie ensuite si la
    }

}

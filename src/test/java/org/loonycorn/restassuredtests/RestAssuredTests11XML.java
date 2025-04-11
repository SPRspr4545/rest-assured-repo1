package org.loonycorn.restassuredtests;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.testng.annotations.Test;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.*;

public class RestAssuredTests11XML {

    private static final String AUTHOR_URL = "http://thetestrequest.com/authors/{id}";

    // Validating XML Responses
    // de manière générale, l'indexation dans un tableau dans les réponses XML est la même que pour les réponses JSON

    @Test
    public void testXMLResponse() {
        RestAssured
                .given()
                    .accept(ContentType.XML) // j'indique que j'accepte (ContentType.XML)
                    .pathParam("id", 1)
                .get(AUTHOR_URL)
                .then()
                    .statusCode(200)
                    .body("hash", notNullValue()) // "hash" correspond à la balise XML la plus extérieure
                    .body("hash.name", equalTo("Karl Zboncak")) // on peut accéder aux propriétés imbriquées "hash.name"
                    .body("hash.email", equalTo("viva@keebler.biz"));

    }

    @Test
    public void testXMLResponse2() {
        // on peut utiliser .rootPath() pour spécifier les balises XML de 1er niveau et ainsi éviter les répétitions
        RestAssured
                .given()
                    .accept(ContentType.XML) // j'indique que j'accepte (ContentType.XML)
                    .pathParam("id", 1)
                .get(AUTHOR_URL)
                .then()
                    .statusCode(200)
                    .rootPath("hash")
                        .body("", notNullValue()) // le code XML imbriqué sous la balise "hash" est un notNullValue()
                        .body("name", equalTo("Karl Zboncak"))
                        .body("email", equalTo("viva@keebler.biz"));

    }


    private static final String AUTHORS_URL = "http://thetestrequest.com/authors.xml";

    // qu'en est-il des collections?
    // qu'en est-il des tableaux avec la balise de 1er niveau <objects type="array">

    @Test
    public void testXMLResponse3() {
        RestAssured
                .given()
                    .accept(ContentType.XML)
                    .get(AUTHORS_URL)
                .then()
                    .statusCode(200)
                    .body("objects.object[0]", notNullValue()) // le code XML imbriqué sous la balise "objects..." de type "array"
                    .body("objects.object.id[0]", equalTo("1"))
                    .body("objects.object.name[0]", equalTo("Karl Zboncak"))
                    .body("objects.object.email[0]", equalTo("viva@keebler.biz"));

    }

    @Test
    public void testXMLResponse4() {
        // on peut utiliser .rootPath() pour spécifier les balises XML de 1er niveau et ainsi éviter les répétitions
        RestAssured
                .given()
                    .accept(ContentType.XML)
                    .get(AUTHORS_URL)
                .then()
                    .statusCode(200)
                    .rootPath("objects.object[0]") // identifie le 1er objet de la réponse
                        .body("", notNullValue())
                            .body("id", equalTo("1"))
                            .body("name", equalTo("Karl Zboncak"))
                            .body("email", equalTo("viva@keebler.biz"))
                    .rootPath("objects.object[1]") // identifie le 2ème objet de la réponse
                        .body("", notNullValue())
                            .body("id", equalTo("2"))
                            .body("name", equalTo("Jeffie Wolf I"))
                            .body("email", equalTo("mike@keebler.biz"));

    }

    @Test
    public void testXMLResponse5() {
        // on peut accéder à tous les éléments de la même balise XML
        RestAssured
                .given()
                    .accept(ContentType.XML)
                    .get(AUTHORS_URL)
                .then()
                    .statusCode(200)
                    // j'accède à la liste des noms de tous les auteurs du tableau de réponse
                    .body("objects.object.name", hasItem("Dede Tillman")) // comme cela renvoit un tableau ou une liste, j'utilise hashItem()
                    .body("objects.object.id", containsInAnyOrder("5", "3", "2", "1", "4"))
                    .body("objects.object.avatar", everyItem(endsWith("set1")));

    }

    // il exite d'autres techniques plus complexes avec le XML, comme les techniques avancées de correspondance de chemins XML

}

package org.loonycorn.restassuredtests;

import io.restassured.RestAssured;
import org.testng.annotations.Test;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class RestAssuredTests10JSON {

    // REST Assured API Testing: Validating JSON Responses & Schemas
        // Generating JSON Schema for Validation
        // Validating JSON Schema

    private static final String TODO_URL = "https://jsonplaceholder.typicode.com/todos/{id}";

    @Test
    public void testSchemaValidation() {
        RestAssured
                .given()
                    .pathParam("id", 1)
                .when()
                    .get(TODO_URL)
                .then() // quand la réponse est disponible:
                    .body(matchesJsonSchemaInClasspath("todo_schema.json")); // vérifie si le body correspond au schéma spécifié
            // "todo_schema.json" se trouve directement sous "resources", je n'ai pas besoin de spécifier le chemin absolue (complet)
    }

    // Validating Response Type and Response Properties
    // Configuring Required Properties and Additional Properties
    // Specifying Range Constraints for Response Values
    // Validating Nested JSON Fields and JSON Arrays

    private static final String USERS_URL = "https://reqres.in/api/users/{id}";

    @Test
    public void testSchemaValidation2() {
        RestAssured
                .given()
                    .pathParam("id", 5)
                .when()
                    .get(USERS_URL)
                .then()
                    .body(matchesJsonSchemaInClasspath("user_schema.json"));

    }

    private static final String TODOS_URL = "https://jsonplaceholder.typicode.com/todos/";

    @Test
    public void testSchemaValidation3() {
        RestAssured
                .get(TODOS_URL) // renvoi 200 objets
                .then()
                    .body(matchesJsonSchemaInClasspath("todos_schema.json"));
                    // le schema n'en contient qu'un identique pour tous les objets

    }

    // Validating XML Responses

}

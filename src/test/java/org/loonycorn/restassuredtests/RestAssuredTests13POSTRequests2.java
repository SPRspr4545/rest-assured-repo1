package org.loonycorn.restassuredtests;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.testng.annotations.Test;

import static org.hamcrest.Matchers.*;

public class RestAssuredTests13POSTRequests2 {

    //REST Assured API Testing: Testing Different Types of HTTP Endpoints
        // Specifying a Request Body Using JSON Objects

    private static final String BUGS_URL = "http://localhost:8090/bugs";

    @Test
    public void testPOSTCreateBugOne() throws JsonProcessingException {
        // la méthode lancera une "JsonProcessingException" s'il n'est pas capable de sérialiser le bug au format JSON sous forme de chaîne

        // j'instancie un ObjectMapper() "jackson-databind"
        ObjectMapper objectMapper = new ObjectMapper(); // c'est la classe qui permet de créer du JSON et de le sérialiser ou d'analyser du JSON
        ObjectNode bug = objectMapper.createObjectNode(); // à l'aide de l'ObjectMapper je crée un noeud JSON
        // c'est l'objet JSON qui représentera notre bug

        // je précise les champs de mon objet de bug
        bug.put("createdBy", "Joseph Wang 1");
        bug.put("priority", 3);
        bug.put("severity", "Higth");
        bug.put("title", "Cannot filter by category 1");
        bug.put("completed", false);

        // j'appel objectMapper.writeValueAsString() et je lui transmets cet objet de bug...
        String bugBodyJson = objectMapper.writeValueAsString(bug); // ...qui sérialisera ce bug sous forme de chaîne String
        // cette méthode est bien plus simple que d'utiliser des chaînes JSON brutes

        String bugId = RestAssured // je crée la variable "bugId" pour récupérer et stocker la/les propriétés de mon choix
                .given()
                    .contentType(ContentType.JSON)
                    .body(bugBodyJson) // cette chaine "bugBodyJson" est transmise en tant que corps de la requête
                .when()
                    .post(BUGS_URL) // requête POST à l'adresse BUGS_URL
                .then()
                    .statusCode(201)
                    .body("createdBy", equalTo("Joseph Wang 1"), // vérifie que le corps de la réponse possède les propriétés que j'ai précédement créé
                            "priority", equalTo(3),
                            "severity", equalTo("Higth"),
                            "title", equalTo("Cannot filter by category 1"),
                            "completed", equalTo(false)
                    )
                    .extract().path("bugId"); // .extract() méthode me permet d'accéder aux champs de ma réponse
                    // .path() méthode me permet d'extraire le champs ID que je stocke dans la variable "bugId"

        System.out.println("Bug ID: " + bugId);

        // ensuite je passe un autre appel RestAssured pour vérifier le bug avec l'ID que je possède
        RestAssured
                .given()
                    .contentType(ContentType.JSON)
                    .baseUri(BUGS_URL) // je spécifie l'URL de base dans la méthode .baseUri() plutôt que dans la méthode GET ou POST
                    .pathParam("bug_id", bugId) // puis je précise un pathParam()
                    // cependant le baseUri() ne contient pas d'espace réservé pour le pathParam()
                .when()
                    .get("/{bug_id}") // le "bug_id" pathParam() est spécifié dans la requête GET
                .then()
                    .statusCode(200)
                    .body("createdBy", equalTo(bug.get("createdBy").asText()))
                    .body("priority", equalTo(bug.get("priority").asInt()))
                    .body("severity", equalTo(bug.get("severity").asText()))
                    .body("title", equalToIgnoringCase(bug.get("title").asText()))
                    .body("completed", equalTo(bug.get("completed").asBoolean()));
    }

    @Test
    public void testPOSTCreateBugTwo() throws JsonProcessingException {

        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode bug = objectMapper.createObjectNode();

        bug.put("createdBy", "Nora Jones 2");
        bug.put("priority", 0);
        bug.put("severity", "Critical");
        bug.put("title", "Home page does not load 2");
        bug.put("completed", false);

        String bugBodyJson = objectMapper.writeValueAsString(bug);

        String bugId = RestAssured
                .given()
                    .contentType(ContentType.JSON)
                    .body(bugBodyJson)
                .when()
                    .post(BUGS_URL)
                .then()
                    .statusCode(201)
                    .body("createdBy", equalTo("Nora Jones 2"),
                            "priority", equalTo(0),
                            "severity", equalTo("Critical"),
                            "title", equalTo("Home page does not load 2"),
                            "completed", equalTo(false)
                    )
                    .extract().path("bugId");

        System.out.println("Bug ID: " + bugId);

        RestAssured
                .given()
                    .contentType(ContentType.JSON)
                    .baseUri(BUGS_URL)
                    .pathParam("bug_id", bugId)
                .when()
                    .get("/{bug_id}")
                .then()
                    .statusCode(200)
                    .body("createdBy", equalTo(bug.get("createdBy").asText()))
                    .body("priority", equalTo(bug.get("priority").asInt()))
                    .body("severity", equalTo(bug.get("severity").asText()))
                    .body("title", equalToIgnoringCase(bug.get("title").asText()))
                    .body("completed", equalTo(bug.get("completed").asBoolean()));
    }

    @Test(dependsOnMethods = {"testPOSTCreateBugOne", "testPOSTCreateBugTwo"})
    public void testGETTwoBugsPresent() {
        RestAssured
                .given()
                    .contentType(ContentType.JSON)
                .when()
                    .get(BUGS_URL)
                .then()
                    .statusCode(200)
                    .body("size()", equalTo(2));
    }

    @Test(dependsOnMethods = {"testGETTwoBugsPresent"})
    public void testPUTUpdateBugOne() throws JsonProcessingException {
        // les requêtes PUT sont destinées à des mises à jour complètes
        String bugIdOne = RestAssured // j'attibue l'ID du bug à la variable "bugIdOne"
                .given()
                    .contentType(ContentType.JSON)
                .when()
                    .get(BUGS_URL)
                .then()
                    .statusCode(200)
                    .extract().path("bugId[0]"); // extraire l'ID du bug en position d'index 0

        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode bug = objectMapper.createObjectNode();

        bug.put("createdBy", "Joseph Wang 1");
        bug.put("priority", 2);
        bug.put("severity", "Low");
        bug.put("title", "Cannot filter by category 1");
        bug.put("completed", true);

        String bugBodyJson = objectMapper.writeValueAsString(bug);

        RestAssured
                .given()
                    .contentType(ContentType.JSON)
                    .baseUri(BUGS_URL)
                    .body(bugBodyJson)
                    .pathParam("bug_id", bugIdOne)
                .when()
                    .put("/{bug_id}")
                .then()
                    .statusCode(200)
                    .body("createdBy", equalTo(bug.get("createdBy").asText()),
                            "priority", equalTo(bug.get("priority").asInt()),
                            "severity", equalTo(bug.get("severity").asText()),
                            "title", equalTo(bug.get("title").asText()),
                            "completed", equalTo(bug.get("completed").asBoolean()));
    }

    @Test(dependsOnMethods = {"testPUTUpdateBugOne"})
    public void testPATCHUpdateBugTwo() throws JsonProcessingException {
        // les requêtes PATCH sont destinées à des mises à jour ciblées
        String bugIdTwo = RestAssured // j'attibue l'ID du bug à la variable "bugIdTwo"
                .given()
                    .contentType(ContentType.JSON)
                .when()
                    .get(BUGS_URL)
                .then()
                    .statusCode(200)
                    .extract().path("bugId[1]"); // extraire l'ID du bug en position d'index 1

        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode bug = objectMapper.createObjectNode();

        // PATCH me permet de ne spécifier que l'attribut que je souhaite mettre à jour
        bug.put("title", "HOME PAGE DOES NOT LOAD 2");

        String bugBodyJson = objectMapper.writeValueAsString(bug); // nous sérialisons ce JSON en une chaîne

        RestAssured
                .given()
                    .contentType(ContentType.JSON)
                    .baseUri(BUGS_URL)
                    .body(bugBodyJson)
                    .pathParam("bug_id", bugIdTwo)
                .when()
                    .patch("/{bug_id}")
                .then()
                    .statusCode(200)
                .body("createdBy", equalTo("Nora Jones 2"),
                        "priority", equalTo(0),
                        "severity", equalTo("Critical"),
                        "title", equalTo(bug.get("title").asText()),
                        "completed", equalTo(false)
                );
    }

}
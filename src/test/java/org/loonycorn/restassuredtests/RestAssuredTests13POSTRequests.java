package org.loonycorn.restassuredtests;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.testng.annotations.Test;

import static org.hamcrest.Matchers.*;

public class RestAssuredTests13POSTRequests {

    //REST Assured API Testing: Testing Different Types of HTTP Endpoints
        // Making and Testing POST Requests

    private static final String BUGS_URL = "http://localhost:8090/bugs";

    @Test
    public void testPOSTCreateBug() {
        // le corps de la requête est du JSON au format String, sérialisé et concaténé à l'aide de différentes chaînes
        String bugBodyJson = "{\n" +
                "    \"createdBy\": \"Kim Doe\",\n" +
                "    \"priority\": 2 ,\n" +
                "    \"severity\": \"Critical\",\n" +
                "    \"title\": \"Database Connection Failure\",\n" +
                "    \"completed\": false\n" +
                "}";

        RestAssured
                .given()
                    .contentType(ContentType.JSON) // le type de contenu est défini sur JSON
                    .body(bugBodyJson) // j'utilise la méthode .body() pour spécifier le le corps de la requête
                .when()
                    .post(BUGS_URL) // requête POST à l'adresse BUGS_URL
                .then()
                    .statusCode(201)
                    .body("createdBy", equalTo("Kim Doe"), // un seul appel à la méthode .body() surchargée avec plusieurs arguments d'entrée pour valider
                            "priority", equalTo(2),
                            "severity", equalTo("Critical"),
                            "title", equalTo("Database Connection Failure"),
                            "completed", equalTo(false)
                    );
    }

    // l'annotation spécifie à TestNG que cette méthode dépend de l'exécution réussie de la méthode précédente "testPOSTCreateBug()"
    @Test(dependsOnMethods = {"testPOSTCreateBug"}) // TestNG va s'assurer que les tests sont exécutés dans le bon ordre
    public void testGETCreatedBug() {
        // un moyen de valider la création du bug en récupérant tous les bugs du serveur (ce qui n'est pas un bon moyen)
        RestAssured
                .given()
                    .contentType(ContentType.JSON)
                .when()
                    .get(BUGS_URL) // requête GET au BUGS_URL renvoie une liste de bugs et dans cette liste...
                .then() // je vérifie si un des bugs possède les attributs que nous venons de créer
                    .statusCode(200)
                    .body("createdBy", hasItem("Kim Doe")) // vérifie que la liste contient "Kim Doe"
                    .body("priority", hasItem(2))
                    .body("severity", hasItem("Critical"))
                    .body("title", hasItem(equalToIgnoringCase("Database Connection Failure")));
    }

    //REST Assured API Testing: Testing Different Types of HTTP Endpoints
        //Specifying a Request Body Using JSON Objects

    // Cette fois, au lieu de récupérer tous les bugs pour vérifier si certains possèdent les attributs attendus,
    // on vérifie si l'ID du bug renvoyé dans la réponse de création est bien présent et ses attributs sont conformes

    @Test
    public void testPOSTCreateBug2() {
        String bugBodyJson = "{\n" +
                "    \"createdBy\": \"Kim Doe\",\n" +
                "    \"priority\": 2 ,\n" +
                "    \"severity\": \"Critical\",\n" +
                "    \"title\": \"Database Connection Failure\",\n" +
                "    \"completed\": false\n" +
                "}";

        String bugId = RestAssured // je crée la variable "bugId" pour récupérer et stocker la/les propriétés de mon choix
                .given()
                    .contentType(ContentType.JSON)
                    .body(bugBodyJson)
                .when()
                    .post(BUGS_URL) // requête POST à l'adresse BUGS_URL
                .then()
                    .statusCode(201)
                    .body("createdBy", equalTo("Kim Doe"), // vérifie que le corps de la réponse possède les propriétés que j'ai précédement créé
                            "priority", equalTo(2),
                            "severity", equalTo("Critical"),
                            "title", equalTo("Database Connection Failure"),
                            "completed", equalTo(false)
                    )
                    .extract().path("bugId"); // .extract() méthode me permet d'accéder aux champs de ma réponse
                    // .path() méthode me permet d'extraire le champs ID que je stocke dans la variable "bugId"

        System.out.println("Bug ID: " + bugId); // j'imprime l'ID

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
                    .body("createdBy", equalTo("Kim Doe"))
                    .body("priority", equalTo(2))
                    .body("severity", equalTo("Critical"))
                    .body("title", equalToIgnoringCase("Database Connection Failure"))
                    .body("completed", equalTo(false));
    }

    // pour quoi ne pas utiliser un "mappeur" d'objets JSON pour créer un objet bug "bugBodyJson" ?
    // nous pouvons utiliser la dépendance <>jackson-databind</> présente dans le pom.xml, pour sérialiser les objets JSON

    @Test
    public void testPOSTCreateBug3() {
        // plutôt que de spécifier le JSON  du corps de la requêt sous forme de chaîne sérialisée qui est difficile à lire,
        // j'utilise un "mappeur d'objets"

        String bugBodyJson = "null"; // la chaîne "bugBodyJson" est initialisée à 0 pour commencer

        // j'instancie un ObjectMapper() "jackson-databind"
        ObjectMapper objectMapper = new ObjectMapper(); // c'est la classe qui permet de créer du JSON et de le sérialiser ou d'analyser du JSON
        ObjectNode bug  = objectMapper.createObjectNode(); // à l'aide de l'ObjectMapper je crée un noeud JSON
        // c'est l'objet JSON qui représentera notre bug

        // je précise les champs de mon objet de bug
        bug.put("createdBy", "Joseph Wang 2");
        bug.put("priority", 3);
        bug.put("severity", "Higth");
        bug.put("title", "Cannot filter by category 2");
        bug.put("completed", false);

        // ne pas oublier que le body de notre requête doit être spécifié au format JSON sérialisé sous forme de chaîne sérialisée
        try {
            // j'appel objectMapper.writeValueAsString() et je lui transmets cet objet de bug...
            bugBodyJson = objectMapper.writeValueAsString(bug); // ...qui sérialisera ce bug sous forme de chaîne String
            // cette méthode est bien plus simple que d'utiliser des chaînes JSON brutes
        } catch (Exception e) {
            e.printStackTrace();
        }

        String bugId = RestAssured // je crée la variable "bugId" pour récupérer et stocker la/les propriétés de mon choix
                .given()
                    .contentType(ContentType.JSON)
                    .body(bugBodyJson) // cette chaine "bugBodyJson" est transmise en tant que corps de la requête
                .when()
                    .post(BUGS_URL) // requête POST à l'adresse BUGS_URL
                .then()
                    .statusCode(201)
                    .body("createdBy", equalTo("Joseph Wang 2"), // vérifie que le corps de la réponse possède les propriétés que j'ai précédement créé
                            "priority", equalTo(3),
                            "severity", equalTo("Higth"),
                            "title", equalTo("Cannot filter by category 2"),
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

}
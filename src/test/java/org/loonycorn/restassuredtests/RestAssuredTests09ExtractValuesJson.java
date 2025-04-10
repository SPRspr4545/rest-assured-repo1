package org.loonycorn.restassuredtests;

import io.restassured.RestAssured;
import org.testng.annotations.Test;

import static org.hamcrest.Matchers.*;

public class RestAssuredTests09ExtractValuesJson {

    private static final String USERS_URL = "https://reqres.in/api/users?page=1";

    // REST Assured API Testing: Validating JSON Responses & Schemas
        // Extracting Values from Responses

    @Test
    public void testBody() {
        RestAssured
                .get(USERS_URL)
                .prettyPeek()
                .then()
                .rootPath("data[0]") // je précise le rootPath() index [0] pour accès au 1er objet JSON de la collection "data"
                    .body("id", equalTo(1))
                    .body("first_name", equalTo("George"))
                .noRootPath() // pour valider des propriétés de 1er niveau
                    .body("data.first_name[1]", equalTo("Janet")) // accède à l'objet JSON index [1]
                    .body("data.email[1]", equalTo("janet.weaver@reqres.in"))
                    .body("data.last_name", hasItems("Holt", "Morris")) // vérifier la présence de 2 "last_name" dans la liste
                    .body("data.last_name", hasItem(startsWith("Ram"))); // vérifie si dans la liste un élément commence par "Ram"
    }

    // jusqu'à présent nous avons validé les réponses en comparant les valeurs de la réponse à une valeur codée en dur
    // Que se passe t-il si vous souhaitez comparer la valeur d'une réponse à une valeur dynamique qui fait partie de la réponse elle même ?

    @Test
    public void testBody2() {
        // le matcher ResponseAwareMatcher prend l'objet de réponse comme argument d'entrée
        RestAssured
                .get(USERS_URL)
                .then()
                    .body("data.email[0]", response -> // fonction lambda
                            // j'accède au body de la response, puis au jsonPath puis la propriété "first_name[0]"
                            containsStringIgnoringCase(response.body().jsonPath().get("data.first_name[0]"))) // vérifie que le prénom est inclus dans l'email
                            // ...IgnoringCase() car le "first_name" contient une majuscule
                    .body("data.email[0]", response ->
                            containsStringIgnoringCase(response.body().jsonPath().get("data.last_name[0]"))); // je fais la même chose pour le "last_name"
    }

    // affiche la liste des publications
    private static final String POST_URL = "https://jsonplaceholder.typicode.com/posts";
    // permet de récupérer les commentaires d'une publication avec un {id}
    private static final String COMMENT_URL = "https://jsonplaceholder.typicode.com/posts/{id}/comments";

    @Test
    public void testExtract() {
        // extraire une partie de la réponse et utiliser
        Integer postId = RestAssured // stocke l'id qu'on extrait de la réponse dans la variable "postId"
                .get(POST_URL)
                //.prettyPeek()
                .then()
                    .body("size()", equalTo(100)) // vérifie que le nombre de publication = 100
                    .extract() // la méthode extract() donne accès à l'objet de réponse...
                    .path("id[2]"); // ...spécifié par le chemin jsonPath de la valeur que je souhaite extraire

        RestAssured
                .given()
                    .pathParam("id", postId) // spécifie le postId sous la forme .pathParam()
                // j'utilise cet "id" que j'ai extrait de la réponse précédente pour compléter {id} dans l'URL
                // et  récupérer les commentaires
                .when()
                    .get(COMMENT_URL)
                    .then()
                    .body("size()", equalTo(5)); // j'effectue une validation de base (taille des commentaire = 5)
    }

    //Generating JSON Schema for Validation



}

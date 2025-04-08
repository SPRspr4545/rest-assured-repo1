package org.loonycorn.restassuredtests;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.ResponseBody;
import org.testng.annotations.Test;

import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class RestAssuredTests07Json {

    private static final String STORE_USER_URL = "https://fakestoreapi.com/users/{id}";

    @Test
    public void testBodyAsString() {
        // la requête .get() renvoie un objet de réponse
        // Elle implémente l'interface ResponseBody et la stocke dans la variable "responseBody"
        ResponseBody<?> responseBody = RestAssured
                .given()
                    .pathParam("id", 1)
                .when()
                    .get(STORE_USER_URL);

        // pour comparer rapidement convertir la réponse au format asString()
        // n'est pas recommandé car on perd toute la structure de la réponse
        // ce qui empêche de faire des contrôles complexes
        String responseBodyString = responseBody.asString();

        // si la chaîne contient les champs qui m'intéressent, .contains() renvoit "true"
        assertThat(responseBodyString.contains("address"), equalTo(true));
        assertThat(responseBodyString.contains("geolocation"), equalTo(true));
        assertThat(responseBodyString.contains("id"), equalTo(true));
        assertThat(responseBodyString.contains("email"), equalTo(true));
    }

    @Test
    public void testBodyUsingJsonPath() {
        ResponseBody<?> responseBody = RestAssured
                .given()
                    .pathParam("id", 1)
                .when()
                    .get(STORE_USER_URL)
                    .prettyPeek(); // jetons un oeil sur la réponse

        System.out.println("--------------");

        // RestAssured propose un outil puissant appelé JsonPath qui permet d'extraire et manipuler les données JSON
        JsonPath jsonPath = responseBody.jsonPath();

        // jsonPath.get() me donne la structure JSON complète sous forme de MAP<> contenant des valeurs de n'importe quel type
        Map<String, ?> bodyJson = jsonPath.get();
        System.out.println(bodyJson);

        System.out.println("--------------");

        // accéder aux différentes parties de la réponse
        Map<String, ?> addressJson = jsonPath.get("address"); // "address" est un objet JSON complexe
        System.out.println(addressJson);

        System.out.println("--------------");

        // pour accéder à la valeur de la propriété "geolocation" qui est imbriquée dans "address"
        // il faut indiquer le chemin
        Map<String, ?> geolocationJson = jsonPath.get("address.geolocation");
        System.out.println(geolocationJson);

        System.out.println("--------------");

        String lat = jsonPath.get("address.geolocation.lat");
        System.out.println(lat);
    }

    @Test
    public void testBody1() {
        ResponseBody<?> responseBody = RestAssured
                .given()
                    .pathParam("id", 1)
                .when()
                    .get(STORE_USER_URL);

        // Nous accédons à l'Objet jsonPath() depuis responseBody, puis nous utilisons la méthode get() pour accéder aux différentes parties du responseBody
        JsonPath jsonPath = responseBody.jsonPath();

        // Propriétés de 1er niveau
        assertThat(jsonPath.get("email"), equalTo("john@gmail.com"));
        assertThat(jsonPath.get("username"), equalTo("johnd"));

        // Propriétés imbriquées dans la propriété "name"
        assertThat(jsonPath.get("name.firstname"), equalTo("john"));
        assertThat(jsonPath.get("name.lastname"), equalTo("doe"));

        // jsonPath.get("address.number") me donne cette valeur sous forme d'un entier
        assertThat(jsonPath.get("address.city"), equalTo("kilcoole"));
        assertThat(jsonPath.get("address.street"), equalTo("new road"));
        assertThat(jsonPath.get("address.number"), equalTo(7682));
    }

    // REST Assured API Testing: Validating JSON Responses & Schemas
    //Validating JSON Response with Nested Structures

    @Test
    public void testBody2() {
        // Maintenant je n'ai aucune instruction assertThat() dans mon code
        // je n'ai que l'interface fluide RestAssured pour valider le corps de la réponse sans accéder à l'objet jsonPath
        RestAssured
                .given()
                    .pathParam("id", 1)
                .when()
                    .get(STORE_USER_URL)
                .then() // j'utilise la méthode then() pour obtenir un réponse valide
                    .body("id", equalTo(1)) // j'invoque simplement la méthode body() pour vérifier le corps de la réponse
                    .body("email", equalTo("john@gmail.com"))
                    .body("address.city", equalTo("kilcoole"))
                    .body("address.number", equalTo(7682))
                    .body("name.firstname", equalTo("john"))
                    .body("name.lastname", equalTo("doe"))
                    .body (containsStringIgnoringCase("zipcode"))
                    .body(allOf(containsString("name"), containsString("address")));

    }

    @Test
    public void testBody3() {
        // RootPath() permet de préciser le chemin JSON des données imbriquées
        RestAssured
                .given()
                    .pathParam("id", 1)
                .when()
                    .get(STORE_USER_URL)
                    .prettyPeek()
                .then()
                .rootPath("address") // le rootPath() permet de préciser le chemin JSON des données imbriquées
                    .body("city", equalTo("kilcoole"))
                    .body("number", equalTo(7682))
                .rootPath("address.geolocation") // le rootPath() permet de préciser le chemin des données imbriquées
                    .body("lat", equalTo("-37.3159"))
                    .body("long", equalTo("81.1496"))
                .rootPath("name") // il faut le préciser à chaque fois car il s'applique à tous les invocations .body() qui suivent
                    .body("firstname", equalTo("john"))
                    .body("lastname", equalTo("doe"))
                .noRootPath() // il faut désactiver le rootPath() pour les données de 1er niveau
                    .body("id", equalTo(1))
                    .body("email", equalTo("john@gmail.com"))
                    .body("phone", equalTo("1-570-236-7033"));


    }

}

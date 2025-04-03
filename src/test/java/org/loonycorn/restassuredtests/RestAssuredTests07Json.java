package org.loonycorn.restassuredtests;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.ResponseBody;
import org.testng.Assert;
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
    public void testBody() {
        ResponseBody<?> responseBody = RestAssured
                .given()
                    .pathParam("id", 1)
                .when()
                    .get(STORE_USER_URL);

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

}

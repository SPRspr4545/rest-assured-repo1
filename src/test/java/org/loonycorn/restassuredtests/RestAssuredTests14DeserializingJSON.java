package org.loonycorn.restassuredtests;

// -----> pour testBody3()
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.response.Response;
import org.loonycorn.restassuredtests.model.User;
// ---------------------<

import io.restassured.RestAssured;
import io.restassured.common.mapper.TypeRef;
import org.testng.annotations.Test;

import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class RestAssuredTests14DeserializingJSON {

    // REST Assured API Testing: Testing Different Types of HTTP Endpoints
        // Deserializing JSON Responses

    private static final String STORE_USER_URL = "https://fakestoreapi.com/users/{id}";

    @Test
    public void testBody() {
        // RestAssured permet de désérialiser la réponse à un type générique, pour effectuer des validations
        // la réponse, un objet JSON, peut être désérialisé dans un conteneur de type une MAP<>
        // Si la réponse était une liste d'objet JSON, on désérialiserait pour passer à un type de liste générique
        RestAssured
                .given()
                    .pathParam("id", 3)
                .when()
                    .get(STORE_USER_URL)
                .then()
                    .body("id", equalTo(3))
                    .body("email", equalTo("kevin@gmail.com"))
                    .body("username", equalTo("kevinryan"))
                    .body("address.city", equalTo("Cullman"))
                    .body("address.number", equalTo(86))
                    .body("address.geolocation.lat", equalTo("40.3467"))
                    .body("address.geolocation.long", equalTo("-30.1310"))
                    .body("name.firstname", equalTo("kevin"))
                    .body("name.lastname", equalTo("ryan"));

    }

    @Test
    public void testBody2() {
        // maintenant, plutôt que de valider le body en spécifiant le chemin JSON pour spécifier les différents champs
        // RestAssured permet de désérialiser la réponse à un type générique, utilisable pour valider
        // un objet JSON peut être désérialisé sur une MAP<>
        // si la réponse était une lIST<> d'objets JSON, on ferait la désérialisation avec un type de LIST<> générique
        Map<String, Object> user = RestAssured // je stocke la réponse sous forme de MAP
                .given()
                    .pathParam("id", 3)
                .when()
                    .get(STORE_USER_URL).as(new TypeRef<>() {});
                    // j'utilise la méthode as() pour désérialiser la réponse en fonction d'un type générique
                    // je spécifie "new TypeRef<>() {}" pour effectuer cette désérialisation

        // je peux utiliser la MAP en utilisant la méthode "user.get()" pour accéder à des propriétés spécifiques
        assertThat((Integer) user.get("id"), equalTo(3)); // je convertis "id" en entier pour effectuer une comparaîson d'entiers
        assertThat(user.get("email"), equalTo("kevin@gmail.com"));
        assertThat(user.get("username"), equalTo("kevinryan"));
        assertThat(user.get("phone"), equalTo("1-567-094-1345"));

        // nous savons que l'adresse est un objet imbriqué
        // j'appel user.get("address") et transforme sa valeur en MAP composée de Chaînes d'Objets
        Map<String, Object> address = (Map<String, Object>) user.get("address"); // cela me donnera le plan d'adresses

        assertThat(address.get("city"), equalTo("Cullman"));
        assertThat(address.get("street"), equalTo("Frances Ct"));
        assertThat((Integer) address.get("number"), equalTo(86));
        assertThat(address.get("zipcode"), equalTo("29567-1452"));

    }

    @Test
    public void testBody3() throws JsonProcessingException {
        // Désérialiser la réponse reçue du serveur au format JSON vers POJO (Simple Object Java)
            // .../model/User.java
        Response response = RestAssured
                .given()
                    .pathParam("id", 3)
                .when()
                    .get(STORE_USER_URL)
                .then()
                    .extract().response();
// j'utilise la méthode .extract() & .response() pour extraire la réponse et la stocker dans la variable "response"

        // créer un ObjectMapper et...
        ObjectMapper objectMapper = new ObjectMapper();
        // j'utilise objectMapper.readValue() pour désérialiser la réponse en tant qu'objet de la Classe User
        User user = objectMapper.readValue(response.asString(), User.class);

        assertThat(user.getId(), equalTo(3));
        assertThat(user.getEmailAddress(), equalTo("kevin@gmail.com"));
        assertThat(user.getUsername(), equalTo("kevinryan"));
        assertThat(user.getPhoneNumber(), equalTo("1-567-094-1345"));

    }

    // REST Assured API Testing: Testing Different Types of HTTP Endpoints
        // Deserializing Nested Objects

    @Test
    public void testBody4() {
        // il existe une meilleur façon de procéder lorsqu'on utilise REST Assured
        // on se débarasse complètement du "ObjectMapper"
        // au lieu de cela j'adresse une requête .get() au "STORE_USER_URL" ...
        // ...pour obtenir une réponse que je désérialise à un objet "User.class" en utilisant la méthode .as()
        User user = RestAssured
                .given()
                    .pathParam("id", 3)
                .when()
                    .get(STORE_USER_URL).as(User.class); // la méthode .as() va effectuer la désérialisation...
                    // ...et on obtient un objet "User" que je stocke dans la variable "user"

        assertThat(user.getId(), equalTo(3));
        assertThat(user.getEmailAddress(), equalTo("kevin@gmail.com"));
        assertThat(user.getUsername(), equalTo("kevinryan"));
        assertThat(user.getPhoneNumber(), equalTo("1-567-094-1345"));

    }

}

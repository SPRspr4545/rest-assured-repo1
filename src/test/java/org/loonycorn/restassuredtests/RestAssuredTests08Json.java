package org.loonycorn.restassuredtests;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.ResponseBody;
import org.testng.annotations.Test;

import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class RestAssuredTests08Json {

    private static final String STORE_CATEGORIES_URL = "https://fakestoreapi.com/products/categories";

    // Validating Collections in the JSON Response

    @Test
    public void testBody() {
        // mettre en place un test pour valider l'ojet racine JSON anonyme, le chemin JSON indiqué est juste "$"
        // le "$" du jsonPath indique que je valide l'objet JSON racine que sont les catégories
        RestAssured
                .get(STORE_CATEGORIES_URL)
                .then()
                    .body("$", hasItem("electronics"));
        // le comparateur hasItem() pour vérifier si une catégorie est présente dans la collection et return TRUE
    }

    @Test
    public void testBody2() {
        // le plus important dans ce cas de test est d'utiliser le "$" pour valider la racine JSON dans son ensemble
        // on peut également utiliser une chaîne vide "" pour désigner la racine JSON
        RestAssured
                .get(STORE_CATEGORIES_URL)
                .then()
                    .body("$", hasItem("electronics"))
                    .body("$", hasItems("electronics", "jewelery", "men's clothing", "women's clothing"))
                    .body("", contains("electronics", "jewelery", "men's clothing", "women's clothing")) // le Hamcrest Matcher contains() vérifie s'il contient les articles
                    .body("", containsInAnyOrder("jewelery", "men's clothing", "electronics", "women's clothing")); // dans un ordre précis ou quelconque
    }

    private static final String STORE_ELECTRONICS_URL = "https://fakestoreapi.com/products/category/electronics";

    @Test
    public void testBody3() {
        // en utilisant les recherches d'Index entre crochets [] pour la spécification du chemin JSON
        // je fournis l'index de l'élément que je souhaite valider entre crochets
        RestAssured
                .get(STORE_ELECTRONICS_URL)
                .then()
                    .body("[0]", notNullValue()) // j'accède au 1er élément de la collection
                    .body("[2]", notNullValue()); // et au 3ème élément de la liste
        // il s'agit d'une spécification jsonPath valide qui permet de rechercher des éléments sur des index spécifiques d'une liste
    }

    @Test
    public void testListElementContents() {
        RestAssured
                .get(STORE_ELECTRONICS_URL)
                .prettyPeek()
                .then()
                    .body("[0].id", equalTo(9))
                    .body("[0].title", containsStringIgnoringCase("hard drive"))
                    .body("[0].price", equalTo(64))
                    .body("[0].rating.rate", lessThanOrEqualTo(5f)) // objet JSON imbriqué
                    .body("[0].rating.count", greaterThan(200))
                // En utilisant une structure légèrement différente de spécifications de chemins JSON
                    .body("id[2]", equalTo(11))
                    .body("title[2]", containsStringIgnoringCase("256GB SSD"))
                    .body("price[2]", equalTo(109))
                    .body("rating.rate[2]", lessThanOrEqualTo(5f))
                    .body("rating.count[2]", greaterThan(300));
    }

    @Test
    public void testListElementContents2() {
        // éliminer les répétitions avec la méthode rootPath()
        RestAssured
                .get(STORE_ELECTRONICS_URL)
                .prettyPeek()
                .then()
                .rootPath("[0]")
                    .body("id", equalTo(9))
                    .body("title", containsStringIgnoringCase("hard drive"))
                    .body("price", equalTo(64))
                    .body("rating.rate", lessThanOrEqualTo(5f)) // objet JSON imbriqué
                    .body("rating.count", greaterThan(200))
                .rootPath("[2]")
                    .body("id", equalTo(11))
                    .body("title", containsStringIgnoringCase("256GB SSD"))
                    .body("price", equalTo(109))
                    .body("rating.rate", lessThanOrEqualTo(5f))
                    .body("rating.count", greaterThan(300));
    }

    @Test
    public void testFieldContentsInLists() {
        // pour accéder à une propriété de la réponse JSON, sans avoir toutes la collection avec cette même propriété
        // la possibilité d'accéder aux propriétés d'une collection d'objets est très utile pour certains types de contrôles
        RestAssured
                .get(STORE_ELECTRONICS_URL)
                .then()
                    //.body("title", equalTo("Some Title"));
                    .body("id", hasItems(9, 12, 13, 14)) // vérifier si la collection contient certains "id"
                    .body("id", containsInAnyOrder(9, 12, 13, 14, 11, 10)) // vérifie tous les "id" InAnyOrder
                    .body("title", hasItem(containsString("SSD"))) // au moins l'un des titres contient la chaîne SSD
                    .body("image", everyItem(endsWith(".jpg"))) // la propriété "image" de chaque produits se terminent par JPG
                    .body("category", everyItem(equalTo("electronics"))) // tous sont de la "category" "electronics"
                    .body("rating.rate", everyItem(allOf(greaterThan(0f), lessThan(5f)))); // vérifie la note de tous les produits
                    // avec des Match imbriqués pour vérifier que les "rate" sont supérieures à 0 et inférieures à 5

    }

}

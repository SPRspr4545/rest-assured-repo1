package org.loonycorn.restassuredtests;

import io.restassured.RestAssured;
import org.testng.annotations.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class RestAssuredTests06Url {

    // RestAssured permet de spécifier des variables de chemin "pathParams" dans l'URL comme paramètre d'entrée pour la requête GET
    private static final String COMPONENT_ID_URL = "https://fakestoreapi.com/{component}/{id}";
    @Test
    public void testProductsWithPathParams1() {
        RestAssured.get(COMPONENT_ID_URL, "products", 1)
                .then()
                .statusCode(200)
                .body("id", equalTo(1));

        RestAssured.get(COMPONENT_ID_URL, "products", 11)
                .then()
                .statusCode(200)
                .body("id", equalTo(11));

        RestAssured.get(COMPONENT_ID_URL, "carts", 3)
                .then()
                .statusCode(200)
                .body("id", equalTo(3));

    }

    // il existe une autre méthode tel que la syntaxe inspirée du FWK CUCUMBER
    // pour spécifier plusieurs variables de chemin "pathParams"
    // cette syntaxe utilisée par RestAssured est dans le cadre du dév piloté par le comportement
    @Test
    public void testProductsWithPathParams2() {
        // avec les 3 méthodes .given .when et .then:
        RestAssured
                .given()
                    .pathParams("component", "products")
                    .pathParams("id", 1)
                .when()
                    .get(COMPONENT_ID_URL)
                .then()
                    .statusCode(200)
                    .body("id", equalTo(1));

        RestAssured
                .given()
                    .pathParams("component", "products")
                    .pathParams("id", 4)
                .when()
                    .get(COMPONENT_ID_URL)
                .then()
                    .statusCode(200)
                    .body("id", equalTo(4));

        RestAssured
                .given()
                    .pathParams("component", "carts")
                    .pathParams("id", 2)
                .when()
                    .get(COMPONENT_ID_URL)
                .then()
                    .statusCode(200)
                    .body("id", equalTo(2));

    }


    private static final String COMPONENT_CATEGORY_URL = "https://fakestoreapi.com/{component}/{categories}/{category}";

    @Test
    public void testCategoriesWithPathParams() {
        RestAssured
                .given()
                    .pathParams("component", "products")
                    .pathParams("categories", "category")
                    .pathParams("category", "jewelery")
                .when()
                    .get(COMPONENT_CATEGORY_URL)
                .then()
                    .statusCode(200);

        RestAssured
                .given()
                    .pathParams("component", "products")
                    .pathParams("categories", "category")
                    .pathParams("category", "electronics")
                .when()
                    .get(COMPONENT_CATEGORY_URL)
                .then()
                    .statusCode(200);

    }

    private static final String CART_USER_LIMIT_URL = "https://fakestoreapi.com/{component}?{user}&{limit}";

    @Test
    public void testCartUserAndLimit() {
        RestAssured
                .given()
                    .pathParams("component", "carts")
                    .pathParams("user", "userId=1")
                    .pathParams("limit", "limit=5")
                .when()
                    .get(CART_USER_LIMIT_URL)
                .then()
                    .statusCode(200)
                    .body("size()", is(5));

        RestAssured
                .given()
                .pathParams("component", "carts")
                .pathParams("user", "userId=2")
                .pathParams("limit", "limit=3")
                .when()
                    .get(CART_USER_LIMIT_URL)
                .then()
                    .statusCode(200)
                    .body("size()", is(3));

    }

    // Comment spécifier les paramètres de requête dans RestAssured avec "QueryParam"
    private static final String STORE_CART_URL = "https://fakestoreapi.com/carts";

    @Test
    public void testComponentsWithPathParams() {
        // les fonctionnalités des méthodes .queryParam() et .param() sont identiques
        RestAssured
                .given()
                    .queryParam("userId", 1) // QueryParam pour avoir "?userId=1"
                    .queryParam("limit", 5) // QueryParam supplémentaire pour avoir "&limit=5"
                .when()
                    .get(STORE_CART_URL) // les QueryParam seront ajoutés à l'URL .get()
                    .prettyPeek() // On jète un oeil à la réponse qui nous est renvoyée
                .then()
                    .statusCode(200)
                    .body("size()", equalTo(5));

        RestAssured
                .given()
                    .param("userId", 2) // QueryParam pour avoir "?userId=2"
                    .param("limit", 2) // QueryParam supplémentaire pour avoir "&limit=2"
                .when()
                    .get(STORE_CART_URL) // les QueryParam seront ajoutés à l'URL .get()
                    .peek()
                .then()
                    .statusCode(200)
                    .body("size()", equalTo(2));

    }

}

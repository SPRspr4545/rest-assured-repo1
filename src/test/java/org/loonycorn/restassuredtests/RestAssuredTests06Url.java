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


    private static final String CATEGORY_JEWELLERY_PATH = "/products/category/jewelery";
    private static final String CATEGORY_ELECTRONICS_PATH = "/products/category/electronics";

    @Test
    public void testCategories() {
        RestAssured.get(COMPONENT_ID_URL + CATEGORY_JEWELLERY_PATH)
                .then()
                .statusCode(200);

        RestAssured.get(COMPONENT_ID_URL + CATEGORY_ELECTRONICS_PATH)
                .then()
                .statusCode(200);

    }

    private static final String USER_ONE_LIMIT_FIVE_CART_PARAMS = "/carts?userId=1&limit=5";
    private static final String USER_ONE_LIMIT_TWO_CART_PARAMS = "/carts?userId=1&limit=2";

    @Test
    public void testCartUserAndLimit() {
        RestAssured.get(COMPONENT_ID_URL + USER_ONE_LIMIT_FIVE_CART_PARAMS)
                .then()
                .statusCode(200)
                .body("size()", is(5));

        RestAssured.get(COMPONENT_ID_URL + USER_ONE_LIMIT_TWO_CART_PARAMS)
                .then()
                .statusCode(200)
                .body("size()", is(2));

    }

}

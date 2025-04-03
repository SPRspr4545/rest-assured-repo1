package org.loonycorn.restassuredtests;

import io.restassured.RestAssured;
import org.testng.annotations.Test;

import static org.hamcrest.Matchers.*;

public class RestAssuredTests05Url {
    // Spécifier les paramètres de chemin et les paramètres de requête dans nos requêtes

    private static final String STORE_URL = "https://fakestoreapi.com";
    private static final String PRODUCT_ONE_PATH = "/products/1";
    private static final String PRODUCT_TWO_PATH = "/products/2";
    private static final String PRODUCT_FIVE_PATH = "/products/5";

    @Test
    public void testProducts() {
        RestAssured.get(STORE_URL + PRODUCT_ONE_PATH)
                .then()
                .statusCode(200)
                .body("id", equalTo(1));

        RestAssured.get(STORE_URL + PRODUCT_TWO_PATH)
                .then()
                .statusCode(200)
                .body("id", equalTo(2));

        RestAssured.get(STORE_URL + PRODUCT_FIVE_PATH)
                .then()
                .statusCode(200)
                .body("id", equalTo(5));
    }

    private static final String CATEGORY_JEWELLERY_PATH = "/products/category/jewelery";
    private static final String CATEGORY_ELECTRONICS_PATH = "/products/category/electronics";

    @Test
    public void testCategories() {
        RestAssured.get(STORE_URL + CATEGORY_JEWELLERY_PATH)
                .then()
                .statusCode(200);

        RestAssured.get(STORE_URL + CATEGORY_ELECTRONICS_PATH)
                .then()
                .statusCode(200);

    }

    private static final String USER_ONE_LIMIT_FIVE_CART_PARAMS = "/carts?userId=1&limit=5";
    private static final String USER_ONE_LIMIT_TWO_CART_PARAMS = "/carts?userId=1&limit=2";

    @Test
    public void testCartUserAndLimit() {
        RestAssured.get(STORE_URL + USER_ONE_LIMIT_FIVE_CART_PARAMS)
                .then()
                .statusCode(200)
                .body("size()", is(5));

        RestAssured.get(STORE_URL + USER_ONE_LIMIT_TWO_CART_PARAMS)
                .then()
                .statusCode(200)
                .body("size()", is(2));

    }

}

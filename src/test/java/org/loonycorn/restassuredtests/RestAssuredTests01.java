package org.loonycorn.restassuredtests;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

public class RestAssuredTests01 {

    @Test
    public void firstTest() {
        RestAssured
                .get("https://www.Saucedemo.com/") // envoit une requête GET sur le site skillsoft.com
                .then() // une fois que j'ai reçu la réponse de skillsoft.com, "Alors":
                .statusCode(200); // je vérifie le statusCode

    }

    @Test
    void peekTest() {
        Response response = RestAssured.get("https://httpbin.org/get"); // adresse une requête GET pour obtenir un objet de réponse

        Assert.assertTrue(response.peek() instanceof Response); // vérifie que la conditions est True
        // cet objet de réponse contient une fonction "peek" qui est un outil de débogage sur le entêtes et les corps de réponse
        //response.peek();

    }
    @Test
    void prettyPeekTest() {
        Response response = RestAssured.get("https://httpbin.org/get"); // adresse une requête GET pour obtenir un objet de réponse

        Assert.assertTrue(response.prettyPeek() instanceof Response); // vérifie que la conditions est True
        // cet objet de réponse contient une fonction "prettyPeek" pour une réponse plus structurée et plus lisible
        //response.prettyPeek();

    }
    @Test
    void printTest() {
        Response response = RestAssured.get("https://httpbin.org/get"); // adresse une requête GET pour obtenir un objet de réponse

        Assert.assertTrue(response.print() instanceof String); // vérifie que la conditions est True
        // "print" débogage uniquement le corps de réponse
        //response.print();

    }
    @Test
    void prettyPrintTest() {
        Response response = RestAssured.get("https://httpbin.org/get"); // adresse une requête GET pour obtenir un objet de réponse

        Assert.assertTrue(response.prettyPrint() instanceof String); // vérifie que la conditions est True
        // "peekPrint" débogage uniquement le corps de réponse
        //response.prettyPrint();

    }
}

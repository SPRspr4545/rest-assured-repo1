package org.loonycorn.restassuredtests;

import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class HamcrestMatcherTests01 {

    @Test
    public void testEqualTo() {
        // assertThat() prend 2 arguments d'entrée: la valeur réelle et celle qu'on attend
        // la méthode static equalTo() est un comparateur déclaratif fourni par Hamcrest
        assertThat(200, equalTo(200));
    }

    @Test
    public void testGreaterThan200() {
        // la méthode static greaterThan() vérifie si une valeur est supérieure à une autre
        assertThat(201, greaterThan(200));
    }

    @Test
    public void testLessThan300() {
        assertThat(200, lessThan(300));
    }

    @Test
    public void testLessThanOrEqualTo300() {
        assertThat(300, lessThanOrEqualTo(300));
    }

    @Test
    public void testGreaterThanEqualito200() {
        assertThat(300, greaterThanOrEqualTo(300));
    }

    @Test
    public void testCloseTo() {
        // closeTo() ne test pas la correspondance exacte, mais si la valeur numérique est proche
        // vérifie si closeTo(10) avec une erreur de 0,5
        assertThat(10.5, closeTo(10,0.5));
        assertThat(9.6, closeTo(10,0.5));
    }

    @Test
    public void testBetween() {
        // j'utilise la méthode allOf() pour indiquer 2 conditions
        //  pour vérifie que la valeur numérique se situe entre 200 et 300
        // pour celà j'utilise les 2 MATCHERs (greaterThanOrEqualTo() et lessThanOrEqualTo())
        assertThat(201, allOf(greaterThanOrEqualTo(200), lessThanOrEqualTo(300)));
    }

    @Test
    public void testEither() {
        // either().or() permet d'indiquer que l'une ou l'autre des conditions doit être remplie
        assertThat(200, either(equalTo(200)).or(equalTo(201)));
    }

    @Test
    public void testIfStringEqual() {
        String testString = "Test";
        assertThat(testString, equalTo("Test"));
    }

    @Test
    public void testIfStringEqualIgnoreCase() {
        // test si la chaîne est égale, ignorer la casse > sans distinction majuscules/minuscules
        String testString = "Test";
        assertThat(testString, equalToIgnoringCase("test"));
    }

    @Test
    public void testIfStringContainsSubstring() {
        // Vérifier si une chaîne en contient une autre
        String testString = "Hello Hamcrest";
        assertThat(testString, containsString("Hamcrest"));
    }

    @Test
    public void testIfStringContainsSubstringIgnoringCase() {
        // Vérifier si une chaîne en contient une autre > sans distinction majuscules/minuscules
        String testString = "Hello Hamcrest";
        assertThat(testString, containsStringIgnoringCase("hamcrest"));
    }

    @Test
    public void testIfStringStartsWithPrefix() {
        // Vérifier si une chaîne commence par un préfixe
        String testString = "Hello Hamcrest";
        assertThat(testString, startsWith("Hello"));
    }

    @Test
    public void testIfStringEndsWithSuffix() {
        // Vérifier si une chaîne finie par un suffixe
        String testString = "Hello Hamcrest";
        assertThat(testString, endsWith("Hamcrest"));
    }

    @Test
    public void testIfStringStartsWithPrefixIgnoringCase() {
        // Vérifier si une chaîne commence par un préfixe > sans distinction majuscules/minuscules
        String testString = "Hello Hamcrest";
        assertThat(testString, startsWithIgnoringCase("hello"));
    }

    @Test
    public void testIfStringEndsWithSuffixIgnoringCase() {
        // Vérifier si une chaîne finie par un suffixe > sans distinction majuscules/minuscules
        String testString = "Hello Hamcrest";
        assertThat(testString, endsWithIgnoringCase("hamcrest"));
    }

    @Test
    public void testIfStringMatchesPattern() {
        // correspondance de chaînes complexes avec Regex
        String testString = "test@example.com";
        assertThat(testString, matchesPattern("[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}"));
    }

    @Test
    public void testIfStringIsEmpty() {
        // vérifier si la chaîne est vide
        String testString = "";
        assertThat(testString, is(emptyString()));
    }

    @Test
    public void testIfStringIsBlank() {
        // vérifier si la chaîne ne contient que des caractères vides
        String testString = "    ";
        assertThat(testString, is(blankString()));
    }

    @Test
    public void testIfStringEqualToCompressingWhiteSpace() {
        // outil qui supprime tous les espaces blancs supplémentaires puis effectue la comparaison
        String testString = "   Hello Hamcrest  ";
        assertThat(testString, equalToCompressingWhiteSpace("Hello Hamcrest"));

        testString = "Hello           Hamcrest";
        assertThat(testString, equalToCompressingWhiteSpace("Hello Hamcrest"));
    }

}

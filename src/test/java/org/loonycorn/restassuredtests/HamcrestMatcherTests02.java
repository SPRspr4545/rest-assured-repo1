package org.loonycorn.restassuredtests;

import org.testng.annotations.Test;

import java.util.*;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class HamcrestMatcherTests02 {

    @Test
    public void testIfCollectionEmpty() {
        List<String> namesList = new ArrayList<>();

        assertThat(namesList, empty());
    }

    @Test
    public void testIfCollectionOfCertainSize() {
        List<String> namesList = Arrays.asList("John", "Elise", "Nora", "Peter", "Charles");

        // vérifier la taille de la collection
        assertThat(namesList, hasSize(5));
    }

    @Test
    public void testIfCollectionContainsItem() {
        List<String> namesList = Arrays.asList("John", "Elise", "Nora", "Peter", "Charles");

        // vérifier si une collection contient certains objets
        assertThat(namesList, hasItem("Nora"));
    }

    @Test
    public void testIfCollectionContainsItems() {
        List<String> namesList = Arrays.asList("John", "Elise", "Nora", "Peter", "Charles");

        // vérifier si une collection contient certains objets
        assertThat(namesList, hasItems("Nora", "Charles"));
    }

    @Test
    public void testIfCollectionContainsInOrder() {
        List<String> namesList = Arrays.asList("John", "Elise", "Nora", "Peter", "Charles");

        assertThat(namesList, contains("John", "Elise", "Nora", "Peter", "Charles"));
    }

    @Test
    public void testIfCollectionContainsInAnyOrder() {
        List<String> namesList = Arrays.asList("John", "Elise", "Nora", "Peter", "Charles");

        assertThat(namesList, containsInAnyOrder("Nora", "Peter", "John", "Elise", "Charles"));
    }

    @Test
    public void testIfEveryItemMatches() {
        List<String> namesList = Arrays.asList("John", "Julie", "Jackson", "Jane", "Joseph");

        assertThat(namesList, everyItem(startsWith("J")));
    }

    @Test
    public void testIfMapHaskey() {
        // si l'API renvoit une MAP<> vérifier si elle possède une association clés/valeurs
        Map<String, Integer> agesMap = new HashMap<>();
        agesMap.put("John", 30);
        agesMap.put("Elise", 25);
        agesMap.put("Nora", 20);

        // hasKey() permet de vérifier la clé
        assertThat(agesMap, hasKey("John"));
    }

    @Test
    public void testIfMapHasValue() {
        Map<String, Integer> agesMap = new HashMap<>();
        agesMap.put("John", 30);
        agesMap.put("Elise", 25);
        agesMap.put("Nora", 20);

        // hasValue() permet voir si la MAP<> possède une certaine valeur
        assertThat(agesMap, hasValue(25));
    }

    @Test
    public void testIfMapHasEntry() {
        Map<String, Integer> agesMap = new HashMap<>();
        agesMap.put("John", 30);
        agesMap.put("Elise", 25);
        agesMap.put("Nora", 20);

        // hasEntry() permet voir une paire
        assertThat(agesMap, hasEntry("John", 30));
    }

    @Test
    public void testIfArrayOfCertainSize() {
        String[] namesArray = {"John", "Elise", "Nora", "Peter", "Charles"};

        // vérifier la longueur du tableau
        assertThat(namesArray, arrayWithSize(5));
    }

    @Test
    public void testIfArrayHasItem() {
        String[] namesArray = {"John", "Elise", "Nora", "Peter", "Charles"};

        // vérifier un élément du tableau
        assertThat(namesArray, hasItemInArray("Charles"));
    }

    @Test
    public void testIfArrayHasItemsInOrder() {
        String[] namesArray = {"John", "Elise", "Nora", "Peter", "Charles"};

        // comparaison ordonnée du tableau
        assertThat(namesArray, arrayContaining("John", "Elise", "Nora", "Peter", "Charles"));
    }

    @Test
    public void testIfArrayHasItemsInAnyOrder() {
        String[] namesArray = {"John", "Elise", "Nora", "Peter", "Charles"};

        // outil de correspondance du tableau
        assertThat(namesArray, arrayContainingInAnyOrder("Charles", "Elise", "John", "Peter", "Nora"));
    }

}

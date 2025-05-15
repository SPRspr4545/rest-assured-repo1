package org.loonycorn.restassuredtests.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

// indiquer au désérialiseur JSON qu'il faut ignorer les autres propriétés du flux
@JsonIgnoreProperties(ignoreUnknown = true)
public class User {
    private int id;
    private String username;

    // *********************************
        // REST Assured API Testing: Testing Different Types of HTTP Endpoints
            // Deserializing Nested Objects
    // la variable membre "address" de l'objet "Address.java" doit être référencé
    // le désérialisateur RestAssured l'identifiera automatiquement comme un objet imbriqué complexe et le désérialisera en conséquence
    private Address address;
    // **********************************

    // pour que les variables membres de la classe Java aient leur propre convention de dénomination, qui soit différente de celle de la réponse JSON
    // en utilisant l'annotation @JsonProperty() pour spécifier le mappage avec le champ que représente cette variable membre
    // @JsonProperty("email") indiquera au désérialiseur que la valeur du champ "email" dans la réponse JSON est mappé à la variable membre "emailAddress"
    // ce mappage permet à la variable membre de porter un nom différent du champ
    @JsonProperty("email")
    private String emailAddress;
    // idem
    @JsonProperty("phone")
    private String phoneNumber;

    // une fois les variables déclarées, pour générer automatiquement les Getters et les Setters :
        // clic droit : Generate
        // [Getter & Setter]


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    // *************************
        // REST Assured API Testing: Testing Different Types of HTTP Endpoints
            // Deserializing Nested Objects
    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }
    // **********************

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}

package org.loonycorn.restassuredtests.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

// REST Assured API Testing: Testing Different Types of HTTP Endpoints
    // Deserializing Nested Objects

// je demande au désérialisateur d'ignorer la propriété de géolocalisation dansl'objet "Address"
@JsonIgnoreProperties({"geolocation"})
public class Address {

    private String city;
    private String street;
    private int number;
    private String zipcode;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }
}

package org.loonycorn.restassuredtests.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

// indiquer au désérialiseur JSON qu'il faut ignorer les autres propriétés du flux
@JsonIgnoreProperties(ignoreUnknown = true)
public class User {
    private int id;
    private String email;
    private String username;
    private String phone;

    // pour générer automatiquement les Getters et les Setters :
        // clic droit : Generate
        // [Getter & Setter]


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}

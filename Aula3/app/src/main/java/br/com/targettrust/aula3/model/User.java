package br.com.targettrust.aula3.model;

import java.io.Serializable;

/**
 * Created by sala01 on 07/10/2016.
 */

public class User implements Serializable {

    private String email;
    private String password;

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}

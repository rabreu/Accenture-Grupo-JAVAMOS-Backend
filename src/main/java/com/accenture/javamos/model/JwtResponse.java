package com.accenture.javamos.model;

public class JwtResponse {
    private final String jwttoken;
    private final String authenticatedUserName;

    public JwtResponse(String jwttoken, String authenticatedUserName) {
        this.jwttoken = jwttoken;
        this.authenticatedUserName = authenticatedUserName;
    }

    public String getToken() {return this.jwttoken;}
    public String getAuthenticatedUserName() {return this.authenticatedUserName;}

}
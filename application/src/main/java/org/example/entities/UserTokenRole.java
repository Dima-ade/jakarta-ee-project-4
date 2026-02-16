package org.example.entities;

import java.security.Principal;
import java.util.Set;

public class UserTokenRole implements Principal {

    private String username;

    private String token;

    private Set<String> allowedRoles;

    public UserTokenRole(String username, String token, Set<String> allowedRoles) {
        this.username = username;
        this.token = token;
        this.allowedRoles = allowedRoles;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Set<String> getAllowedRoles() {
        return allowedRoles;
    }

    public void setAllowedRoles(Set<String> allowedRoles) {
        this.allowedRoles = allowedRoles;
    }

    @Override
    public String getName() {
        return username;
    }
}

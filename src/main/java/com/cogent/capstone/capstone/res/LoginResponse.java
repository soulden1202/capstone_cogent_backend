package com.cogent.capstone.capstone.res;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;

public class LoginResponse {
    private String token;

    private Collection<? extends GrantedAuthority> role;

    private String email;

    private long id;

    private boolean isActive;
    private long expiresIn;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public long getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(long expiresIn) {
        this.expiresIn = expiresIn;
    }

    public LoginResponse(String token, long expiresIn, Collection<? extends GrantedAuthority> role, String email,
            boolean isActive, long id) {
        this.token = token;
        this.expiresIn = expiresIn;
        this.role = role;
        this.email = email;
        this.isActive = isActive;
        this.id = id;
    }

    public Collection<? extends GrantedAuthority> getRole() {
        return role;
    }

    public void setRole(Collection<? extends GrantedAuthority> role) {
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    // Getters and setters...
}
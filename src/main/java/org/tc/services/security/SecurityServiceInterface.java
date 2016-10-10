package org.tc.services.security;

public interface SecurityServiceInterface {
    String findLoggedInUserName();

    void autologin(String username, String password);
}

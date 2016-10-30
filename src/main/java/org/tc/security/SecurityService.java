package org.tc.security;

public interface SecurityService {
    String findLoggedInUserName();

    void autologin(String username, String password);
}

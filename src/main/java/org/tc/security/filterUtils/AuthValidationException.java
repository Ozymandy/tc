package org.tc.security.filterUtils;

import org.springframework.security.core.AuthenticationException;

import java.util.List;


public class AuthValidationException extends AuthenticationException {

    private static final long serialVersionUID = -3613393016881542212L;
    private List<String> errors;

    public AuthValidationException(String msg) {
        super(msg);
    }

    public AuthValidationException(String msg, List<String> errors) {
        super(msg);
        this.errors = errors;
    }

    public List<String> getErrors() {
        return this.errors;
    }
}

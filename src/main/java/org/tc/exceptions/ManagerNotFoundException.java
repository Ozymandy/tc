package org.tc.exceptions;

public class ManagerNotFoundException extends RuntimeException {
    public ManagerNotFoundException(String msg){
        super(msg);
    }
}

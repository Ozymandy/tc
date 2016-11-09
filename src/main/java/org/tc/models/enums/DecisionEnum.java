package org.tc.models.enums;

public enum DecisionEnum {
    APPROVE("Approve"), REJECT("Reject");
    private String value;

    DecisionEnum(String stringValue) {
        this.value=stringValue;
    }

    public String getValue() {
        return value;
    }
}

package org.tc.models.forms;

import org.hibernate.validator.constraints.NotEmpty;
import org.tc.models.Course;
import org.tc.models.enums.DecisionEnum;

import javax.validation.constraints.NotNull;

public class DecisionForm {
    @NotNull
    private DecisionEnum decision;

    @NotEmpty
    private String reason;

    public DecisionForm() {
    }

    public DecisionEnum getDecision() {
        return decision;
    }

    public void setDecision(DecisionEnum decision) {
        this.decision = decision;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}

package org.tc.models;


import org.tc.models.enums.DecisionEnum;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Decision {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int decisionId;
    @Enumerated(EnumType.STRING)
    private DecisionEnum decision;

    @ManyToOne
    @JoinColumn(name = "CourseId")
    private Course courseForReview;

    @ManyToOne
    @JoinColumn(name = "UserId")
    private User manager;
    private String reason;

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Course getCourseForReview() {
        return courseForReview;
    }

    public void setCourseForReview(Course courseForReview) {
        this.courseForReview = courseForReview;
    }

    public User getManager() {
        return manager;
    }

    public void setManager(User manager) {
        this.manager = manager;
    }

    public DecisionEnum getDecision() {
        return decision;
    }

    public void setDecision(DecisionEnum decision) {
        this.decision = decision;
    }

    public int getDecisionId() {
        return decisionId;
    }

    public void setDecisionId(int decisionId) {
        this.decisionId = decisionId;
    }

}

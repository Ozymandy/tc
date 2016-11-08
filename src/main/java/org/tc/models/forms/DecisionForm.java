package org.tc.models.forms;

public class DecisionForm {
    private String manager;
    private String decision;
    private String reason;
    private int CourseId;
    private boolean isCurrentUserKM;
    private boolean isCurrentUserDM;

    public boolean isCurrentUserKM() {
        return isCurrentUserKM;
    }

    public void setCurrentUserKM(boolean currentUserKM) {
        isCurrentUserKM = currentUserKM;
    }

    public boolean isCurrentUserDM() {
        return isCurrentUserDM;
    }

    public void setCurrentUserDM(boolean currentUserDM) {
        isCurrentUserDM = currentUserDM;
    }

    public int getCourseId() {
        return CourseId;
    }

    public void setCourseId(int courseId) {
        CourseId = courseId;
    }

    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }

    public String getDecision() {
        return decision;
    }

    public void setDecision(String decision) {
        this.decision = decision;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}

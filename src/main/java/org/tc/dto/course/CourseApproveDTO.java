package org.tc.dto.course;

import org.tc.models.Decision;

public class CourseApproveDTO extends AbstractCourseDTO {
    private String Description;
    private String links;
    private String ownerEmail;
    private String knowledgeManager;
    private String departmentManager;
    private boolean isDepartmentManager;
    private boolean isKnowledgeManager;
    private boolean isVoted;
    private String knowledgeManagerDecision;
    private String departmentManagerDecision;

    public String getKnowledgeManagerDecision() {
        return knowledgeManagerDecision;
    }

    public void setKnowledgeManagerDecision(String knowledgeManagerDecision) {
        this.knowledgeManagerDecision = knowledgeManagerDecision;
    }

    public String getDepartmentManagerDecision() {
        return departmentManagerDecision;
    }

    public void setDepartmentManagerDecision(String departmentManagerDecision) {
        this.departmentManagerDecision = departmentManagerDecision;
    }

    public boolean isVoted() {
        return isVoted;
    }

    public void setVoted(boolean voted) {

        isVoted = voted;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getLinks() {
        return links;
    }

    public void setLinks(String links) {
        this.links = links;
    }

    public String getOwnerEmail() {
        return ownerEmail;
    }

    public void setOwnerEmail(String ownerEmail) {
        this.ownerEmail = ownerEmail;
    }

    public String getKnowledgeManager() {
        return knowledgeManager;
    }

    public void setKnowledgeManager(String knowledgeManager) {
        this.knowledgeManager = knowledgeManager;
    }

    public String getDepartmentManager() {
        return departmentManager;
    }

    public void setDepartmentManager(String departmentManager) {
        this.departmentManager = departmentManager;
    }

    public boolean isDepartmentManager() {
        return isDepartmentManager;
    }

    public void setDepartmentManager(boolean departmentManager) {
        isDepartmentManager = departmentManager;
    }

    public boolean isKnowledgeManager() {
        return isKnowledgeManager;
    }

    public void setKnowledgeManager(boolean knowledgeManager) {
        isKnowledgeManager = knowledgeManager;
    }
}

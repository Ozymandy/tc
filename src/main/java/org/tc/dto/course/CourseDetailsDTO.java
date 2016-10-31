package org.tc.dto.course;

import java.util.List;

public class CourseDetailsDTO extends AbstractCourseDTO {
    private String Description;
    private String links;
    private String ownerEmail;
    private List<String> subscribersCourse;
    private List<String> attendeeCourse;

    public List<String> getSubscribersCourse() {
        return subscribersCourse;
    }

    public void setSubscribersCourse(List<String> subscribersCourse) {
        this.subscribersCourse = subscribersCourse;
    }

    public List<String> getAttendeeCourse() {
        return attendeeCourse;
    }

    public void setAttendeeCourse(List<String> attendeeCourse) {
        this.attendeeCourse = attendeeCourse;
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
}

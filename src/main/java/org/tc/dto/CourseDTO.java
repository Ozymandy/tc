package org.tc.dto;

public class CourseDTO {
    private int id;
    private String courseName;
    private String Description;
    private String links;
    private boolean isOwner;
    private boolean isSubscribed;
    private boolean isAttendee;
    private boolean isEvaluated;
    private String attendeeSubscriber;

    public boolean isEvaluated() {
        return isEvaluated;
    }

    public void setEvaluated(boolean evaluated) {
        isEvaluated = evaluated;
    }

    public String getAttendeeSubscriber() {
        return attendeeSubscriber;
    }

    public void setAttendeeSubscriber(String attendeeSubscriber) {
        this.attendeeSubscriber = attendeeSubscriber;
    }

    public boolean isAttendee() {
        return isAttendee;
    }

    public void setAttendee(boolean attendee) {
        isAttendee = attendee;
    }

    public boolean isOwner() {
        return isOwner;
    }

    public void setOwner(boolean owner) {
        isOwner = owner;
    }

    public boolean isSubscribed() {
        return isSubscribed;
    }

    public void setSubscribed(boolean subscribed) {
        isSubscribed = subscribed;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
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

    public boolean getIsOwner() {
        return isOwner;
    }

    public void setIsOwner(boolean isOwner) {
        this.isOwner = isOwner;
    }
}

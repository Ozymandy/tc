package org.tc.dto.course;

public class CourseDTO extends AbstractCourseDTO {
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
        return getSubscribersCount() +
                (getAttendeeCount() > 0 ? "\\" +
                        getAttendeeCount() : "");
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

    public boolean getIsOwner() {
        return isOwner;
    }

    public void setIsOwner(boolean isOwner) {
        this.isOwner = isOwner;
    }
}
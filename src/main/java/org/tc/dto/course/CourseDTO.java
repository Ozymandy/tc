package org.tc.dto.course;

public class CourseDTO extends AbstractCourseDTO {
    private boolean isOwner;
    private boolean isEvaluated;
    private boolean isDrafted;
    private boolean isProposal;
    private boolean isRejected;
    private boolean isNew;
    private boolean isOpen;
    private boolean canSubscribe;
    private boolean canAttend;
    private String attendeeSubscriber;

    public boolean canAttend() {
        return canAttend;
    }

    public void setCanAttend(boolean canAttend) {
        this.canAttend = canAttend;
    }

    public boolean isNew() {
        return isNew;
    }

    public void setNew(boolean aNew) {
        isNew = aNew;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void setOpen(boolean open) {
        isOpen = open;
    }

    public boolean canSubscribe() {
        return canSubscribe;
    }

    public void setCanSubscribe(boolean canSubscribe) {
        this.canSubscribe = canSubscribe;
    }

    public boolean isRejected() {
        return isRejected;
    }

    public void setRejected(boolean rejected) {
        isRejected = rejected;
    }

    public boolean isDrafted() {
        return isDrafted;
    }

    public void setDrafted(boolean drafted) {
        isDrafted = drafted;
    }

    public boolean isProposal() {
        return isProposal;
    }

    public void setProposal(boolean proposal) {
        isProposal = proposal;
    }

    public boolean isEvaluated() {
        return isEvaluated;
    }

    public void setEvaluated(boolean evaluated) {
        isEvaluated = evaluated;
    }

    public boolean canBeDeleted() {
        return isOwner && (isDrafted || isRejected);
    }

    public String getAttendeeSubscriber() {
        return getSubscribersCount() +
                (getAttendeeCount() > 0 ? "\\" +
                        getAttendeeCount() : "");
    }

    public void setAttendeeSubscriber(String attendeeSubscriber) {
        this.attendeeSubscriber = attendeeSubscriber;
    }


    public boolean isOwner() {
        return isOwner;
    }

    public void setOwner(boolean owner) {
        isOwner = owner;
    }

    public boolean getIsOwner() {
        return isOwner;
    }

    public void setIsOwner(boolean isOwner) {
        this.isOwner = isOwner;
    }
}

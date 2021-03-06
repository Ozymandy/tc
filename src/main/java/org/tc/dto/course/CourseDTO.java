package org.tc.dto.course;

public class CourseDTO extends AbstractCourseDTO {
    private boolean isOwner;
    private boolean isDrafted;
    private boolean isProposal;
    private boolean isRejected;
    private boolean isNew;
    private boolean isOpen;
    private boolean isReady;
    private boolean isInProgress;
    private boolean canSubscribe;
    private boolean canAttend;
    private boolean isFinished;
    private boolean canEvaluate;
    private boolean canUpdate;
    private String attendeeSubscriber;

    public boolean canUpdate() {
        return canUpdate;
    }

    public void setCanUpdate(boolean canUpdate) {
        this.canUpdate = canUpdate;
    }

    public boolean canEvaluate() {
        return canEvaluate;
    }

    public void setCanEvaluate(boolean canEvaluate) {
        this.canEvaluate = canEvaluate;
    }

    public boolean isFinished() {
        return isFinished;
    }

    public void setFinished(boolean finished) {
        isFinished = finished;
    }

    public boolean isInProgress() {
        return isInProgress;
    }

    public void setInProgress(boolean inProgress) {
        isInProgress = inProgress;
    }

    public boolean isReady() {
        return isReady;
    }

    public void setReady(boolean ready) {
        isReady = ready;
    }

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

    public boolean canBeDeleted() {
        return isOwner && (isDrafted || isRejected);
    }

    public String getAttendeeSubscriber() {
        return getSubscribersCount() +
                (getAttendeeCount() > 0 ? "\\" +
                        getAttendeeCount() : "");
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

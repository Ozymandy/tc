package org.tc.models.forms;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class CourseUpdateForm extends CourseForm {

    @Min(1)
    @Max(10)
    @NotNull
    private Integer minSubscribers;

    @Min(1)
    @Max(10)
    @NotNull
    private Integer minAttendees;

    public Integer getMinAttendees() {
        return minAttendees;
    }

    public void setMinAttendees(Integer minAttendees) {
        this.minAttendees = minAttendees;
    }

    public Integer getMinSubscribers() {
        return minSubscribers;
    }

    public void setMinSubscribers(Integer minSubscribers) {
        this.minSubscribers = minSubscribers;
    }
}

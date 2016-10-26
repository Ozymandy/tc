package org.tc.models.usercourse;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "usercourse")
@DiscriminatorValue("attendee")
public class Attendee extends UserCourse {
}

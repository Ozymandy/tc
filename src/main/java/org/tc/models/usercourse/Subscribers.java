package org.tc.models.usercourse;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "usercourse")
@DiscriminatorValue("subscriber")
public class Subscribers extends UserCourse {
}

package org.tc.models.usercourse;

import org.hibernate.annotations.DiscriminatorOptions;
import org.tc.models.Course;
import org.tc.models.User;

import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@IdClass(UserCourseId.class)
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "relationtype",
        discriminatorType = DiscriminatorType.STRING, length = 15)
@DiscriminatorOptions(force = true)
public abstract class UserCourse {

    @ManyToOne
    @Id
    @JoinColumn(name = "userid")
    private User user;
    @ManyToOne
    @Id
    @JoinColumn(name = "courseid")
    private Course course;

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}

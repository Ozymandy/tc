package org.tc.models;

import org.hibernate.validator.constraints.NotEmpty;
import org.tc.models.usercourse.AttendeeCourse;
import org.tc.models.usercourse.SubscribersCourse;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "User")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "UserId")
    private int id;
    @Column
    @NotEmpty
    private String password;
    @Column
    @NotEmpty
    private String username;
    @Column
    private String email;
    @ManyToOne
    @JoinColumn(name = "RoleId")
    private Role role;
    @OneToMany
    @JoinColumn(name = "ownerid")
    private List<Course> ownedCourses;
    @OneToMany
    @JoinColumn(name = "participantid")
    private List<SubscribersCourse> coursesSubscribe;
    @OneToMany
    @JoinColumn(name = "participantid")
    private List<AttendeeCourse> coursesAttend;
    @OneToMany
    @JoinColumn(name = "UserId")
    private List<Decision> madeDecisions;

    public User(int id, String password, String username, String email,
                Role role) {
        this.id = id;
        this.password = password;
        this.username = username;
        this.email = email;
        this.role = role;
    }

    public User(int id) {
        this.id = id;
    }

    public User() {
    }

    public List<Decision> getMadeDecisions() {
        return madeDecisions;
    }

    public void setMadeDecisions(List<Decision> madeDecisions) {
        this.madeDecisions = madeDecisions;
    }

    public List<Course> getOwnedCourses() {
        return ownedCourses;
    }

    public void setOwnedCourses(List<Course> ownedCourses) {
        this.ownedCourses = ownedCourses;
    }

    public List<AttendeeCourse> getCoursesAttend() {
        return coursesAttend;
    }

    public void setCoursesAttend(List<AttendeeCourse> coursesAttend) {
        this.coursesAttend = coursesAttend;
    }

    public List<SubscribersCourse> getCoursesSubscribe() {
        return coursesSubscribe;
    }

    public void setCoursesSubscribe(List<SubscribersCourse> coursesSubscribe) {
        this.coursesSubscribe = coursesSubscribe;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {

        return username;
    }

    public void setUsername(String userName) {
        this.username = userName;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Role getRole() {
        return this.role;
    }

    public void setRole(Role role) {
        this.role = role;
    }


}

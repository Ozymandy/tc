package org.tc.models;

import org.tc.models.usercourse.Attendee;
import org.tc.models.usercourse.Subscribers;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.sql.Date;
import java.util.List;

@Entity
@Table(name = "Course")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "CourseId")
    private int id;
    private String name;
    private String description;
    private String links;
    private Date date;
    @ManyToOne
    @JoinColumn(name = "UserId")
    private User user;
    @OneToMany
    @JoinColumn(name = "courseid")
    private List<Subscribers> subscribers;

    @OneToMany
    @JoinColumn(name = "courseid")
    private List<Attendee> attendee;

    public Course(int id, String name, String description, String links,
                  Date date) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.links = links;
        this.date = date;
    }

    public Course() {
    }

    public Course(int id) {
        this.id = id;
    }

    public List<Attendee> getAttendee() {
        return attendee;
    }

    public void setAttendee(List<Attendee> attendee) {
        this.attendee = attendee;
    }

    public List<Subscribers> getSubscribers() {
        return subscribers;
    }

    public void setSubscribers(List<Subscribers> subscribers) {
        this.subscribers = subscribers;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLinks() {
        return this.links;
    }

    public void setLinks(String links) {
        this.links = links;
    }

    public Date getDate() {
        return this.date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}

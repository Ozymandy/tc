package org.tc.models;

import org.tc.models.enums.StateEnum;
import org.tc.models.usercourse.AttendeeCourse;
import org.tc.models.usercourse.SubscribersCourse;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.sql.Date;
import java.util.List;
import java.util.Optional;

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
    @JoinColumn(name = "OwnerId")
    private User owner;

    @ManyToOne
    @JoinColumn(name = "CategoryId")
    private Category category;

    @OneToMany(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "courseid")
    private List<SubscribersCourse> subscribers;

    @OneToMany(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "courseid")
    private List<AttendeeCourse> attendeeCourse;

    @OneToMany(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "courseid")
    private List<Evaluation> evaluations;

    @Column(nullable = true)
    private Integer minSubscribers;

    @Column(nullable = true)
    private Integer minAttendees;

    @Column(name = "State")
    @Enumerated(EnumType.STRING)
    private StateEnum state;
    @OneToMany(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "courseid")
    private List<Decision> decisions;

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

    public List<Decision> getDecisions() {
        return decisions;
    }

    public void setDecisions(List<Decision> decisions) {
        this.decisions = decisions;
    }

    public StateEnum getState() {
        return state;
    }

    public void setState(StateEnum state) {
        this.state = state;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public List<Evaluation> getEvaluations() {
        return evaluations;
    }

    public void setEvaluations(List<Evaluation> evaluations) {
        this.evaluations = evaluations;
    }

    public List<AttendeeCourse> getAttendeeCourse() {
        return attendeeCourse;
    }

    public void setAttendeeCourse(List<AttendeeCourse> attendeeCourse) {
        this.attendeeCourse = attendeeCourse;
    }

    public List<SubscribersCourse> getSubscribers() {
        return subscribers;
    }

    public void setSubscribers(List<SubscribersCourse> subscribers) {
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

    public User getOwner() {
        return owner;
    }

    public void setOwner(User user) {
        this.owner = user;
    }
    public boolean isDraft(){
        return this.state.equals(StateEnum.DRAFT);
    }
    public boolean isProposal(){
        return this.state.equals(StateEnum.PROPOSAL);
    }
    public boolean isRejected(){
        return this.state.equals(StateEnum.REJECTED);
    }
    public boolean isNew(){
        return this.state.equals(StateEnum.NEW);
    }
    public boolean isOpen(){
        return this.state.equals(StateEnum.OPEN);
    }
    public boolean isReady(){
        return this.state.equals(StateEnum.READY);
    }
    public boolean isInProgress(){
        return this.state.equals(StateEnum.IN_PROGRESS);
    }
    public boolean isFinished(){
        return this.state.equals(StateEnum.FINISHED);
    }
}

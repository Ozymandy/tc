package org.tc.models;

import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.sql.Date;
import java.util.List;

@Entity
@Table(name="Course")
public class Course {
    @Id
    @GeneratedValue
    @Column(name = "CourseId")
    private int id;
    private String name;
    private String description;
    private String links;
    private Date date;
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

}

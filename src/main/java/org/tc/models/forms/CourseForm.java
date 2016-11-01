package org.tc.models.forms;

import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Size;

public class CourseForm {

    @Size(
            min = 2,
            max = 50,
            message = "Size.Course.Name"
    )
    @NotEmpty(message = "Blank.Course.Name")
    private String name;
    @NotEmpty(message = "Blank.Course.Desc")
    private String description;
    @NotEmpty(message = "Blank.Course.Link")
    private String links;
    @NotEmpty(message = "Blank.Course.Category")
    private String category;
    private String user;
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLinks() {
        return links;
    }

    public void setLinks(String links) {
        this.links = links;
    }

    public String getUser() {

        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

}

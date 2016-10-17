package org.tc.models;

import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.BitSet;

@Entity
@Table(name = "User")
public class User {
    @Id
    @GeneratedValue
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

    public void setUsername(String userName) {
        this.username = userName;
    }

    public String getUsername() {

        return username;
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

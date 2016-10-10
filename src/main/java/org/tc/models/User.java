package org.tc.models;

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
    private String password;
    @Column
    private String userName;
    @Column
    private String email;
    @ManyToOne
    @JoinColumn(name = "RoleId")
    private Role role;

    public User(int id, String password, String userName, String email,
                Role role) {
        this.id = id;
        this.password = password;
        this.userName = userName;
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

    public String getUserName() {
        return this.userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

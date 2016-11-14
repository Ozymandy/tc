package org.tc.models;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "Role")
public class Role implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RoleId")
    private int id;

    @OneToMany
    @JoinColumn(name = "RoleId")
    private List<User> userListByRole;
    private String name;
    public Role(int id, String name) {
        this.id = id;
        this.name = name;
    }
    public Role() {
    }

    public List<User> getUserListByRole() {
        return userListByRole;
    }

    public void setUserListByRole(List<User> userListByRole) {
        this.userListByRole = userListByRole;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getAuthority() {
        return this.getName();
    }
}

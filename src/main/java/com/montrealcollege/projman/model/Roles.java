package com.montrealcollege.projman.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "ROLES", uniqueConstraints = {@UniqueConstraint(name = "ROLES_UK", columnNames = "ROLE_NAME")})
public class Roles {

    @Id
//    @GeneratedValue
    @Column(name = "ROLE_ID", nullable = false)
    private Long roleId;

    @Column(name = "ROLE_NAME", length = 30, nullable = false)
    private String roleName;

    @OneToMany(mappedBy = "role")
//    @ManyToMany(mappedBy = "roles", cascade = CascadeType.PERSIST)
    private Set<Users> users;

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public Set<Users> getUsers() {
        return users;
    }

    public void setUsers(Set<Users> users) {
        this.users = users;
    }
}

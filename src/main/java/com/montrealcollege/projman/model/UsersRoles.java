package com.montrealcollege.projman.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
@Table(name = "USERS_ROLES",
        uniqueConstraints = {@UniqueConstraint(name = "USERS_ROLES_UK", columnNames = {"USER_ID", "ROLE_ID"})}
)
public class UsersRoles {

    @Id
    @GenericGenerator(name="ur_seq", strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {@org.hibernate.annotations.Parameter(name = "USERS_ROLES_SEQ", value = "SEQUENCE")}
    )
    @GeneratedValue(generator = "USERS_ROLES_SEQ")
    @Column(name = "ID", nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID", nullable = false)
    private Users user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ROLE_ID", nullable = false)
    private Roles role;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    public Roles getRole() {
        return role;
    }

    public void setRole(Roles role) {
        this.role = role;
    }

}

package com.montrealcollege.projman.model;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "USERS")
public class Users {

    @Id
    @GenericGenerator(name="usrs_seq", strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {@Parameter(name = "USERS_SEQ", value = "SEQUENCE")}
    )
    @GeneratedValue(generator = "USERS_SEQ")
    @Column(name = "ID")
    private Long id;

    @Column(name = "USER_NAME", length = 36, nullable = false)
    @NotBlank(message="Please enter a user name.")
    private String userName;

    @Column(name = "ENCRYPTED_PASSWORD", length = 128, nullable = false)
    @NotBlank(message="Please enter a password.")
    private String encryptedPassword;

    @Column(name = "ENABLED", length = 1, nullable = false)
    private boolean enabled;

    @Column(name = "FIRST_NAME")
    @NotBlank(message="Please enter a first name.")
    private String firstName;

    @Column(name = "LAST_NAME")
    @NotBlank(message="Please enter a last name.")
    private String lastName;

    @Column(name = "EMAIL")
    @Email(message = "Please enter a valid email address.")
    private String email;

    @Column(name = "PHONE")
    @Pattern(regexp= "\\(\\d{3}\\) \\d{3}-\\d{4}|",
            message="Please enter a valid phone number. (999) 999-9999")
    private String phone;

    @Column(name = "PROJECTS")
    private Integer projects;

    @Column(name = "TASKS")
    private Integer tasks;

    @ManyToMany()
    @JoinTable(name = "USERS_ROLES",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id")}
    )
    private Set<Roles> roles = new HashSet<>();

    @OneToOne(mappedBy = "leader")
    private Projects project;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEncryptedPassword() {
        return encryptedPassword;
    }

    public void setEncryptedPassword(String encryptedPassword) {
        this.encryptedPassword = encryptedPassword;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getProjects() {
        return projects;
    }

    public void setProjects(Integer projects) {
        this.projects = projects;
    }

    public Integer getTasks() {
        return tasks;
    }

    public void setTasks(Integer tasks) {
        this.tasks = tasks;
    }

    public Set<Roles> getRoles() {
        return roles;
    }

    public void setRoles(Set<Roles> roles) {
        this.roles = roles;
    }

    public Projects getProject() {
        return project;
    }

    public void setProject(Projects project) {
        this.project = project;
    }
}

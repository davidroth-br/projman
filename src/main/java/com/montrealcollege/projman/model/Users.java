package com.montrealcollege.projman.model;

import com.montrealcollege.projman.utils.Constants;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
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
    @NotBlank(message = Constants.REQUIRED)
    @Size(min = 5, message = "5 characters minimum")
    @Size(max = 36, message = Constants.CHAR_MAX_36)
    private String userName;

    @Column(name = "ENCRYPTED_PASSWORD", length = 128, nullable = false)
    @NotBlank(message = Constants.REQUIRED)
    private String encryptedPassword;

    @Column(name = "ENABLED", length = 1, nullable = false)
    private boolean enabled;

    @Column(name = "FIRST_NAME")
    @NotBlank(message = Constants.REQUIRED)
    @Size(max = 20, message = Constants.CHAR_MAX_20)
    private String firstName;

    @Column(name = "LAST_NAME")
    @NotBlank(message = Constants.REQUIRED)
    @Size(max = 20, message = Constants.CHAR_MAX_20)
    private String lastName;

    @Column(name = "EMAIL")
    @Email(message = "Please enter a valid email address")
    @Size(max = 40, message = Constants.CHAR_MAX_40)
    private String email;

    @Column(name = "PHONE")
    @Pattern(regexp= "\\(\\d{3}\\) \\d{3}-\\d{4}|", message = "Please enter a valid phone number. (999) 999-9999")
    @Size(max = 20, message = Constants.CHAR_MAX_15)
    private String phone;

    @ManyToMany(mappedBy = "users")
    private Set<Projects> projects = new HashSet<>();

    @ManyToMany(mappedBy = "users")
    @OrderBy(value = "project asc, name asc")
    private Set<Tasks> tasks = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "ROLE_ID")
    private Roles role;

    @OneToMany(mappedBy = "leader")
    @OrderBy(value = "name")
    private Set<Projects> projectsLead;

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

    public Set<Projects> getProjects() {
        return projects;
    }

    public void setProjects(Set<Projects> projects) {
        this.projects = projects;
    }

    public Set<Tasks> getTasks() {
        return tasks;
    }

    public void setTasks(Set<Tasks> tasks) {
        this.tasks = tasks;
    }

    public Roles getRole() {
        return role;
    }

    public void setRole(Roles role) {
        this.role = role;
    }

    public Set<Projects> getProjectsLead() {
        return projectsLead;
    }

    public void setProjectsLead(Set<Projects> project) {
        this.projectsLead = project;
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }

    public boolean isLeader() {
        return projectsLead.size() != 0;
    }

    public boolean isAdmin() {
        return role.getRoleId() == 1;
    }
}

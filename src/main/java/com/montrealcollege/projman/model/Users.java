package com.montrealcollege.projman.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Entity
@Table(name = "USERS")
public class Users {

    @Id
    @Column(name = "ID")
    private Long id;
//    @Column(name = "USER_NAME")
//    @NotNull(message="Please enter a user name.")
//    private String userName;
//    @Column(name = "PASSWORD")
//    @NotNull(message="Please enter a password.")
//    private String password;
    @Column(name = "FIRST_NAME")
    @NotNull(message="Please enter a first name.")
    private String firstName;
    @Column(name = "LAST_NAME")
    @NotNull(message="Please enter a last name.")
    private String lastName;
    @Column(name = "EMAIL")
    @Email(message = "Please enter a valid email address.")
    private String email;
    @Column(name = "PHONE")
    @Pattern(regexp= "\\(\\d{3} \\)\\d{3}-\\d{4}", message="Please enter a valid phone number.")
    private String phone;
    @Column(name = "ROLE")
    @NotNull(message="Please enter a role")
    private String role;
    @Column(name = "PROJECTS")
    private Integer projects;
    @Column(name = "TASKS")
    private Integer tasks;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

//    public String getUserName() {
//        return userName;
//    }
//
//    public void setUserName(String userName) {
//        this.userName = userName;
//    }
//
//    public String getPassword() {
//        return password;
//    }
//
//    public void setPassword(String password) {
//        this.password = password;
//    }

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

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
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
}

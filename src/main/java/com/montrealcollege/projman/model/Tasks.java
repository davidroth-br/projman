package com.montrealcollege.projman.model;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "TASKS")
public class Tasks {
    @Id
    @GenericGenerator(name="tasks_seq", strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {@org.hibernate.annotations.Parameter(name = "TASKS_SEQ", value = "SEQUENCE")}
    )
    @GeneratedValue(generator = "TASKS_SEQ")
    @Column(name = "ID")
    private Integer id;

    @Column(name = "NAME")
    @NotBlank(message = "Please enter a task name")
    private String name;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "DEADLINE")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "Please enter a deadline")
    @Future(message="Deadline must be in the future.")
    private Date deadline;

    @Column(name = "PRIORITY")
    @NotNull(message = "Please select a priority")
    private Integer priority;

    @Column(name = "STATE")
    @NotNull(message = "Please select a state")
    private Integer state;

    @Column(name = "COMPLETION_DATE")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date completionDate;

    @ManyToOne
    @JoinColumn(name = "PROJECT_ID")
    @NotNull(message = "Please select a project")
    private Projects project;

    @ManyToMany
    @JoinTable(name = "USERS_TASKS",
            joinColumns = {@JoinColumn(name = "TASK_ID")},
            inverseJoinColumns = {@JoinColumn(name = "USER_ID")})
    private Set<Users> users = new HashSet<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public Date getCompletionDate() {
        return completionDate;
    }

    public void setCompletionDate(Date completionDate) {
        this.completionDate = completionDate;
    }

    public Projects getProject() {
        return project;
    }

    public void setProject(Projects project) {
        this.project = project;
    }

    public Set<Users> getUsers() {
        return users;
    }

    public void setUsers(Set<Users> users) {
        this.users = users;
    }
}

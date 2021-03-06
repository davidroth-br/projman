package com.montrealcollege.projman.model;

import com.montrealcollege.projman.utils.Constants;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "PROJECTS")
public class Projects {

    @Id
    @GenericGenerator(name="proj_seq", strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
            parameters = {@Parameter(name = "PROJECTS_SEQ", value = "SEQUENCE")}
    )
    @GeneratedValue(generator = "PROJECTS_SEQ")
    @Column(name = "ID")
    private Long id;

    @Column(name = "NAME")
    @NotBlank(message = Constants.REQUIRED)
    @Size(max = 40, message = Constants.CHAR_MAX_40)
    private String name;

    @Column(name = "DESCRIPTION")
    @Size(max = 400, message = Constants.CHAR_MAX_400)
    private String description;

    @Column(name = "START_DATE")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = Constants.REQUIRED)
    private Date startDate;

    @Column(name = "END_DATE")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = Constants.REQUIRED)
    private Date endDate;

    @ManyToOne
    @JoinColumn(name = "LEADER_ID")
    private Users leader;

    @OneToMany(mappedBy = "project")
    @OrderBy(value = "name ASC")
    private Set<Tasks> tasks;

    @ManyToMany
    @JoinTable(name = "USERS_PROJECTS",
            joinColumns = {@JoinColumn(name = "PROJECT_ID")},
            inverseJoinColumns = {@JoinColumn(name = "USER_ID")})
    @OrderBy(value = "firstName ASC, lastName ASC")
    private Set<Users> users = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Users getLeader() {
        return leader;
    }

    public void setLeader(Users leader) {
        this.leader = leader;
    }

    public Set<Tasks> getTasks() {
        return tasks;
    }

    public void setTasks(Set<Tasks> tasks) {
        this.tasks = tasks;
    }

    public Set<Users> getUsers() {
        return users;
    }

    public void setUsers(Set<Users> users) {
        this.users = users;
    }
}

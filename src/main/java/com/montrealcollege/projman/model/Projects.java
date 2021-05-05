package com.montrealcollege.projman.model;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;
import javax.validation.constraints.Future;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

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
    @NotBlank(message="Please enter a project name.")
    private String name;

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "START_DATE")
    @NotNull(message="Please enter a start date.")
    private Date startDate;

    @Column(name = "END_DATE")
    @NotNull(message="Please enter an end date.")
    @Future(message="End date must be in the future.")
    private Date endDate;

    @OneToOne
    @JoinColumn(name = "LEADER_ID", referencedColumnName = "ID")
    private Users leader;

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

    @Override
    public String toString() {
        return "Projects{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
//                ", leaderId=" + leaderId +
                '}';
    }
}

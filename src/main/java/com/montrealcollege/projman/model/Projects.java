package com.montrealcollege.projman.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "PROJECTS")
public class Projects {

    @Id
    @Column(name = "ID")
    private Integer id;
    @Column(name = "NAME")
    private String name;
    @Column(name = "DESCRIPTION")
    private String description;
    @Column(name = "START_DATE")
    private Date startDate;
    @Column(name = "END_DATE")
    private Date endDate;
//    @Column(name = "LEADER_ID")
//    private Long leaderId;

    @OneToOne //(cascade = CascadeType.ALL)
    @JoinColumn(name = "LEADER_ID", referencedColumnName = "ID")
    private Users leader;

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

//    public Long getLeaderId() {
//        return leaderId;
//    }
//
//    public void setLeaderId(Long leaderId) {
//        this.leaderId = leaderId;
//    }

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

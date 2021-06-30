package com.montrealcollege.projman.dto;

import java.util.List;

public class ProjectStats {
    private String projectName;
    private List<MemberStats> memberStats;
    private int tasksInProject;

    public ProjectStats(String projectName, List<MemberStats> memberStats, int tasksInProject) {
        this.projectName = projectName;
        this.memberStats = memberStats;
        this.tasksInProject = tasksInProject;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public List<MemberStats> getMemberStats() {
        return memberStats;
    }

    public void setMemberStats(List<MemberStats> memberStats) {
        this.memberStats = memberStats;
    }

    public int getTasksInProject() {
        return tasksInProject;
    }

    public void setTasksInProject(int tasksInProject) {
        this.tasksInProject = tasksInProject;
    }
}

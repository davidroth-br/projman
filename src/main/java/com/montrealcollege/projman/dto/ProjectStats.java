package com.montrealcollege.projman.dto;

import java.util.List;

public class ProjectStats {
    private String projectName;
    private List<MemberStats> memberStats;

    public ProjectStats(String projectName, List<MemberStats> memberStats) {
        this.projectName = projectName;
        this.memberStats = memberStats;
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
}

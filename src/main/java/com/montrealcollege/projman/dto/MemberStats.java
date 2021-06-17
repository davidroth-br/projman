package com.montrealcollege.projman.dto;

public class MemberStats {
    private String memberName;
    private int pendingTasksOnTime;
    private int pendingTasksOverdue;
    private int completedTasksOnTime;
    private int completedTasksLate;

    public MemberStats(String memberName, int pendingTasksOnTime, int pendingTasksOverdue, int completedTasksOnTime, int completedTasksLate) {
        this.memberName = memberName;
        this.pendingTasksOnTime = pendingTasksOnTime;
        this.pendingTasksOverdue = pendingTasksOverdue;
        this.completedTasksOnTime = completedTasksOnTime;
        this.completedTasksLate = completedTasksLate;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public int getPendingTasksOnTime() {
        return pendingTasksOnTime;
    }

    public void setPendingTasksOnTime(int pendingTasksOnTime) {
        this.pendingTasksOnTime = pendingTasksOnTime;
    }

    public int getPendingTasksOverdue() {
        return pendingTasksOverdue;
    }

    public void setPendingTasksOverdue(int pendingTasksOverdue) {
        this.pendingTasksOverdue = pendingTasksOverdue;
    }

    public int getCompletedTasksOnTime() {
        return completedTasksOnTime;
    }

    public void setCompletedTasksOnTime(int completedTasksOnTime) {
        this.completedTasksOnTime = completedTasksOnTime;
    }

    public int getCompletedTasksLate() {
        return completedTasksLate;
    }

    public void setCompletedTasksLate(int completedTasksLate) {
        this.completedTasksLate = completedTasksLate;
    }

    public int getTotalTasks(){
        return this.completedTasksLate + this.completedTasksOnTime + this.pendingTasksOverdue + this.pendingTasksOnTime;
    }
}

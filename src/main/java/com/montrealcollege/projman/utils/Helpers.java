package com.montrealcollege.projman.utils;

import com.montrealcollege.projman.dto.MemberStats;
import com.montrealcollege.projman.dto.ProjectStats;
import com.montrealcollege.projman.model.Projects;
import com.montrealcollege.projman.model.Tasks;
import com.montrealcollege.projman.model.Users;

import java.util.*;

public class Helpers {
    public static Date today() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        return calendar.getTime();
    }

    public static String capitalizeFirstLetter(String str) {
        return str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase();
    }

    public static List<ProjectStats> getAllStats(Users currentUser, Collection<Projects> projectsList) {
        List<ProjectStats> projectsStatsList = projectsList == null ? new ArrayList<>() : getProjectStatList(projectsList);
        projectsStatsList.add(new ProjectStats(null, getCurrentUserStats(currentUser), 0));
        return projectsStatsList;
    }

    private static List<MemberStats> getCurrentUserStats(Users currentUser) {
        Set<Tasks> tasks = currentUser.getTasks();
        int totalTasks = tasks.size();
        int completed = 0;
        int onTime = 0;
        int overdue = 0;
        for (Tasks task : tasks) {
            if (task.getState() == 4) {
                completed++;
                if (!task.getCompletionDate().after(task.getDeadline())) onTime++;
            } else {
                if (task.getDeadline().before(Helpers.today())) overdue++;
            }
        }
        int pendingOnTime = totalTasks - completed - overdue;
        int completedLate = completed - onTime;
        List<MemberStats> memberStats = new ArrayList<>();
        memberStats.add(new MemberStats("Current User", pendingOnTime, overdue, onTime, completedLate));
        return memberStats;
    }

    private static List<ProjectStats> getProjectStatList(Collection<Projects> projectsList) {
        List<ProjectStats> projectStatsList = new ArrayList<>();
        for (Projects project : projectsList) {
            int tasksInProject = 0;
            List<MemberStats> memberStats = new ArrayList<>();
            for (Users member : project.getUsers()) {
                int pendingTasksOnTime = 0;
                int pendingTasksOverdue = 0;
                int completedTasksOnTime = 0;
                int completedTasksLate = 0;
                for (Tasks memberTask : member.getTasks()) {
                    if (memberTask.getProject().getId().equals(project.getId())) {
                        if (memberTask.getState() == 4) {
                            if (!memberTask.getCompletionDate().after(memberTask.getDeadline()))
                                completedTasksOnTime++;
                            else completedTasksLate++;
                        } else {
                            if (memberTask.getDeadline().before(Helpers.today())) pendingTasksOverdue++;
                            else pendingTasksOnTime++;
                        }
                        tasksInProject++;
                    }
                }
                memberStats.add(new MemberStats(member.getFullName(), pendingTasksOnTime, pendingTasksOverdue, completedTasksOnTime, completedTasksLate));
            }
            projectStatsList.add(new ProjectStats(project.getName(), memberStats, tasksInProject));
        }
        return projectStatsList;
    }
}

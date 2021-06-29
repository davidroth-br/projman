package com.montrealcollege.projman.utils;

import java.util.LinkedHashMap;
import java.util.Map;

public class Constants {

    public static final Map<Integer, String> priorityList = new LinkedHashMap<Integer, String>() {{
        put(0, "Low");
        put(1, "Medium");
        put(2, "High");
    }};
    public static final Map<Integer, String> stateList = new LinkedHashMap<Integer, String>() {{
        put(0, "Not Assigned");
        put(1, "To Do");
        put(2, "In progress");
        put(3, "Quality Assurance");
        put(4, "Completed");
    }};

    public static final String red = "color: red";
    public static final String blue = "color: blue";

    public static final String startInPast = "Start date must not be in the past.";
    public static final String endInPast = "End date must be in the future.";
    public static final String endBeforeStart = "End date must be after start date.";

    public static final String newSuccess = " was successfully edited!";
    public static final String editSuccess = " was successfully edited!";
    public static final String deleteSuccess = " was successfully removed!";

    public static final String validateNewTask = "/tasks/leader/validateNew";
    public static final String validateEditTask = "/tasks/leader/validateEdit";
    public static final String leaderChangeState = "/tasks/leader/changeState";
    public static final String userChangeState = "/tasks/user/changeState";
}

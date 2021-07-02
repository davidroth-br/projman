package com.montrealcollege.projman.utils;

import java.util.LinkedHashMap;
import java.util.Map;

public class Constants {

    public static final Map<Integer, String> PRIORITY_LIST = new LinkedHashMap<Integer, String>() {{
        put(0, "Low");
        put(1, "Medium");
        put(2, "High");
    }};
    public static final Map<Integer, String> STATE_LIST = new LinkedHashMap<Integer, String>() {{
        put(0, "Pending");
        put(1, "To Do");
        put(2, "In progress");
        put(3, "QA");
        put(4, "Completed");
    }};

    public static final String RED = "color: red";
    public static final String GREEN = "color: green";

    public static final String START_IN_PAST = "Start date must not be in the past.";
    public static final String END_IN_PAST = "End date must be in the future.";
    public static final String END_BEFORE_START = "End date must be after start date.";

    public static final String NEW_SUCCESS = " was successfully created!";
    public static final String EDIT_SUCCESS = " was successfully edited!";
    public static final String DELETE_SUCCESS = " was successfully removed!";

    public static final String VALIDATE_NEW_TASK = "/tasks/leader/validateNew";
    public static final String VALIDATE_EDIT_TASK = "/tasks/leader/validateEdit";
    public static final String LEADER_CHANGE_STATE = "/tasks/leader/changeState";
    public static final String USER_CHANGE_STATE = "/tasks/user/changeState";
}

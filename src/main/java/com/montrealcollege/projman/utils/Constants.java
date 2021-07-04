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
        put(0, "To Do");
        put(1, "In progress");
        put(2, "Pending");
        put(3, "QA");
        put(4, "Completed");
    }};

    public static final String RED = "color: red";
    public static final String GREEN = "color: green";

    public static final String NOT_IN_PAST = "Must not be in the past.";
    public static final String IN_FUTURE = "Must be in the future.";
    public static final String END_BEFORE_START = "End date must be after start date.";
    public static final String PASSWORD_MISMATCH = "Passwords did not match.";
    public static final String DELETE_ERROR = "Unable to delete. ";
    public static final String REMOVE_ERROR = "Unable to remove. ";

    public static final String NEW_SUCCESS = " was successfully created!";
    public static final String EDIT_SUCCESS = " was successfully edited!";
    public static final String DELETE_SUCCESS = " was successfully removed!";

    public static final String VALIDATE_NEW_TASK = "/tasks/leader/validateNew";
    public static final String VALIDATE_EDIT_TASK = "/tasks/leader/validateEdit";
    public static final String LEADER_CHANGE_STATE = "/tasks/leader/changeState";
    public static final String USER_CHANGE_STATE = "/tasks/user/changeState";
    public static final String VALIDATE_EDIT_PROJECT = "/projects/admin/validateEdit";
    public static final String VALIDATE_NEW_PROJECT = "/projects/admin/validateNew";

    public static final String CHAR_MAX_15 = "15 characters maximum";
    public static final String CHAR_MAX_20 = "20 characters maximum";
    public static final String CHAR_MAX_36 = "36 characters maximum";
    public static final String CHAR_MAX_40 = "40 characters maximum";
    public static final String CHAR_MAX_400 = "400 characters maximum";
    public static final String REQUIRED = "Required";
}

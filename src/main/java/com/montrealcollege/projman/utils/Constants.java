package com.montrealcollege.projman.utils;

import java.util.LinkedHashMap;
import java.util.Map;

public class Constants {

    public Map<Integer, String> priorityList = new LinkedHashMap<>();
    public Map<Integer, String> stateList = new LinkedHashMap<>();

    public Constants() {
        priorityList.put(0, "Low");
        priorityList.put(1, "Medium");
        priorityList.put(2, "High");

        stateList.put(0, "Not Assigned");
        stateList.put(1, "To Do");
        stateList.put(2, "In progress");
        stateList.put(3, "Quality Assurance");
        stateList.put(4, "Completed");
    }
}

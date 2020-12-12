package com.titianvalero.todolist.Utils;

public class Task {

    private final String name;
    private final String date;
    private final int priority;

    public Task(String name, String date, int priority) {
        this.name = name;
        this.date = date;
        this.priority = priority;
    }

    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }

    public int getPriority() {
        return priority;
    }

}

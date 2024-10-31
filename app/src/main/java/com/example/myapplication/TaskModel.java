package com.example.myapplication;

import androidx.annotation.NonNull;

public class TaskModel {
    private String name;

    public TaskModel(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @NonNull
    @Override
    public String toString() {
        return "Name: " + name;
    }
}

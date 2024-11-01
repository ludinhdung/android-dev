package com.example.myapplication;

import androidx.annotation.NonNull;

public class TaskModel {
    private int id;  // New ID field
    private String name;

    public TaskModel(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
        return "ID: " + id + ", Name: " + name;
    }
}

package com.example.myapplication;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class TaskAdapter extends BaseAdapter {

    Activity context;
    List<TaskModel> taskModels;

    public TaskAdapter(Activity context, List<TaskModel> taskModels) {
        this.context = context;
        this.taskModels = taskModels;
    }

    @Override
    public int getCount() {
        return taskModels.size();
    }

    @Override
    public Object getItem(int i) {
        return taskModels;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @SuppressLint("ViewHolder")
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater layoutInflater = context.getLayoutInflater();

        view = layoutInflater.inflate(R.layout.activity_item_task, null);

        TextView textViewTaskName = view.findViewById(R.id.textViewTaskName);
        textViewTaskName.setText(taskModels.get(i).getName());

        return view;
    }
}

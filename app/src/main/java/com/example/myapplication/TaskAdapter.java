package com.example.myapplication;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class TaskAdapter extends BaseAdapter {

    Activity context;
    List<TaskModel> taskModels;
    private final TaskActionListener taskActionListener;

    public TaskAdapter(Activity context, List<TaskModel> taskModels, TaskActionListener taskActionListener) {
        this.context = context;
        this.taskModels = taskModels;
        this.taskActionListener = taskActionListener;
    }

    @Override
    public int getCount() {
        return taskModels.size();
    }

    @Override
    public Object getItem(int i) {
        return taskModels.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater layoutInflater = context.getLayoutInflater();
        view = layoutInflater.inflate(R.layout.activity_item_task, null);

        TaskModel task = taskModels.get(i);
        TextView textViewTaskName = view.findViewById(R.id.textViewTaskName);
        textViewTaskName.setText("ID: " + task.getId() + " - " + task.getName());

        Button deleteButton = view.findViewById(R.id.buttonDelete);
        Button updateButton = view.findViewById(R.id.buttonUpdateItem);

        deleteButton.setOnClickListener(v -> {
            if (taskActionListener != null) {
                taskActionListener.onDeleteTask(task);
                taskModels.remove(i);
                notifyDataSetChanged();
            }
        });

        updateButton.setOnClickListener(v -> {
            if (taskActionListener != null) {
                taskActionListener.onUpdateTask(task);
            }
        });

        return view;
    }


    public interface TaskActionListener {
        void onDeleteTask(TaskModel task);
        void onUpdateTask(TaskModel task);
    }
}

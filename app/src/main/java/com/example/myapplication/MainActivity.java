package com.example.myapplication;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class MainActivity extends AppCompatActivity implements TaskAdapter.TaskActionListener {

    private EditText editTextTask, editTextUpdate;
    private ListView listViewTasks;
    private TaskAdapter adapter;
    private TaskModel selectedTask;

    private final DatabaseHelper databaseHelper = new DatabaseHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextTask = findViewById(R.id.editTextTask);
        editTextUpdate = findViewById(R.id.editTextUpdate);
        Button buttonAdd = findViewById(R.id.buttonAdd);
        Button buttonUpdate = findViewById(R.id.buttonUpdate);
        listViewTasks = findViewById(R.id.listViewTasks);

        setAdapter();

        buttonAdd.setOnClickListener(v -> toggleAddTask());

        buttonUpdate.setOnClickListener(v -> {
            if (selectedTask != null) {
                updateTask();
            }
        });
    }

    private void toggleAddTask() {
        String taskName = editTextTask.getText().toString().trim();
        if (TextUtils.isEmpty(taskName)) return;

        boolean addTask = databaseHelper.addTask(taskName);

        if (addTask) {
            adapter.notifyDataSetChanged();
            editTextTask.setText("");
            Toast.makeText(this, "Task added successfully", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Failed to add task", Toast.LENGTH_SHORT).show();
        }
        setAdapter();
    }

    private void updateTask() {
        String updatedText = editTextUpdate.getText().toString().trim();
        if (TextUtils.isEmpty(updatedText)) return;

        selectedTask.setName(updatedText);
        boolean updateSuccess = databaseHelper.updateTask(selectedTask);

        if (updateSuccess) {
            Toast.makeText(this, "Task updated successfully", Toast.LENGTH_SHORT).show();
            editTextUpdate.setText("");
            editTextUpdate.setVisibility(View.GONE);
            findViewById(R.id.buttonUpdate).setVisibility(View.GONE);
            setAdapter();
        } else {
            Toast.makeText(this, "Failed to update task", Toast.LENGTH_SHORT).show();
        }
    }

    private void setAdapter() {
        List<TaskModel> taskModels = databaseHelper.getTasks();
        adapter = new TaskAdapter(this, taskModels, this);
        listViewTasks.setAdapter(adapter);
    }

    @Override
    public void onDeleteTask(TaskModel task) {
        boolean deleteSuccess = databaseHelper.deleteTask(task);
        if (deleteSuccess) {
            setAdapter();
            Toast.makeText(this, "Task deleted successfully", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Failed to delete task", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onUpdateTask(TaskModel task) {
        selectedTask = task;
        editTextUpdate.setText(task.getName());
        editTextUpdate.setVisibility(View.VISIBLE);
        findViewById(R.id.buttonUpdate).setVisibility(View.VISIBLE);
    }
}

package com.example.myapplication;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private EditText editTextTask;
    private ListView listViewTasks;

    private final DatabaseHelper databaseHelper = new DatabaseHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextTask = findViewById(R.id.editTextTask);
        Button buttonAdd = findViewById(R.id.buttonAdd);
        listViewTasks = findViewById(R.id.listViewTasks);

        showListTask();

        buttonAdd.setOnClickListener(v -> {
            if (TextUtils.isEmpty(editTextTask.getText().toString())) return;

            TaskModel taskModel = new TaskModel(editTextTask.getText().toString());

            boolean addTask = databaseHelper.addTask(taskModel.getName());

            if (addTask) {
                showListTask();
                editTextTask.setText("");
                editTextTask.requestFocus();
                Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show();
            }
        });

        listViewTasks.setOnItemClickListener((adapterView, view, i, l) -> {
            Toast.makeText(this, adapterView.getItemAtPosition(i).toString(), Toast.LENGTH_SHORT).show();
        });
    }


    private void showListTask() {
        List<TaskModel> taskModels = databaseHelper.getTasks();
        TaskAdapter adapter = new TaskAdapter(this, taskModels);
        listViewTasks.setAdapter(adapter);
    }

}
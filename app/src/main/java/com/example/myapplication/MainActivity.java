package com.example.myapplication;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private EditText editTextTask;
    private Button buttonAdd;
    private ListView listViewTasks;
    private DatabaseHelper databaseHelper = new DatabaseHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextTask = findViewById(R.id.editTextTask);
        buttonAdd = findViewById(R.id.buttonAdd);
        listViewTasks = findViewById(R.id.listViewTasks);

        showListTask();

        buttonAdd.setOnClickListener(v -> {
            TaskModel taskModel = new TaskModel(editTextTask.getText().toString());

            if (databaseHelper.addTask(taskModel.getName())) {
                Toast.makeText(this, taskModel.toString(), Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Failed", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void showListTask() {
        List<TaskModel> taskModels = databaseHelper.getTasks();
        ArrayAdapter<TaskModel> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, taskModels);
        listViewTasks.setAdapter(adapter);
    }

}
package com.example.myapplication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    public DatabaseHelper(@Nullable Context context) {
        super(context, "task.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String tasks = "CREATE TABLE task(name TEXT)";
        String users = "CREATE TABLE user(name TEXT, password TEXT)";

        sqLiteDatabase.execSQL(tasks);
        sqLiteDatabase.execSQL(users);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
    }

    public boolean addTask(String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("name", name);

        return db.insert("task", null, contentValues) != -1;
    }

    public List<TaskModel> getTasks() {
        List<TaskModel> taskModels = new ArrayList<>();

        String query = "SELECT * FROM task";

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()){
            do {
                String name = cursor.getString(0);
                TaskModel taskModel = new TaskModel(name);
                taskModels.add(taskModel);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return taskModels;


    }
}

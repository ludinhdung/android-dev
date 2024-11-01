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
        String tasks = "CREATE TABLE task(id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT)";
        String users = "CREATE TABLE user(username TEXT PRIMARY KEY, password TEXT)";

        sqLiteDatabase.execSQL(tasks);
        sqLiteDatabase.execSQL(users);

        sqLiteDatabase.execSQL("INSERT INTO user (username, password) VALUES ('user1', 'password1')");
        sqLiteDatabase.execSQL("INSERT INTO user (username, password) VALUES ('user2', 'password2')");
        sqLiteDatabase.execSQL("INSERT INTO user (username, password) VALUES ('admin', 'adminpass')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS task");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS user");
        onCreate(sqLiteDatabase);
    }

    public boolean addUser(String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("username", username);
        contentValues.put("password", password);

        long result = db.insert("user", null, contentValues);
        db.close();
        return result != -1;
    }

    public boolean checkUser(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM user WHERE username = ? AND password = ?",
                new String[]{username, password});
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        db.close();
        return exists;
    }

    public boolean addTask(String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);

        long result = db.insert("task", null, contentValues);
        db.close();
        return result != -1;
    }

    public List<TaskModel> getTasks() {
        List<TaskModel> taskModels = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT id, name FROM task", null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                taskModels.add(new TaskModel(id, name));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return taskModels;
    }

    public boolean deleteTask(TaskModel taskModel) {
        SQLiteDatabase db = this.getWritableDatabase();
        int rows = db.delete("task", "id = ?", new String[]{String.valueOf(taskModel.getId())});
        db.close();
        return rows > 0;
    }

    public boolean updateTask(TaskModel taskModel) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", taskModel.getName());
        int rows = db.update("task", values, "id = ?", new String[]{String.valueOf(taskModel.getId())});
        db.close();
        return rows > 0;
    }
}

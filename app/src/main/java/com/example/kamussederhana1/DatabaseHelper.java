package com.example.kamussederhana1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "KamusDB";
    private static final int DATABASE_VERSION = 2; // Tingkatkan versi database
    private static final String TABLE_USERS = "users";
    private static final String TABLE_DICTIONARY = "dictionary";
    private static final String TABLE_HISTORY = "history";
    private static final String TABLE_FAVORITES = "favorites";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_USERS + " (id INTEGER PRIMARY KEY AUTOINCREMENT, username TEXT, password TEXT)");
        db.execSQL("CREATE TABLE " + TABLE_DICTIONARY + " (id INTEGER PRIMARY KEY AUTOINCREMENT, word TEXT, meaning TEXT)");
        db.execSQL("CREATE TABLE " + TABLE_HISTORY + " (id INTEGER PRIMARY KEY AUTOINCREMENT, word TEXT, timestamp TEXT)");
        db.execSQL("CREATE TABLE " + TABLE_FAVORITES + " (id INTEGER PRIMARY KEY AUTOINCREMENT, word TEXT, meaning TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 2) {
            db.execSQL("CREATE TABLE " + TABLE_FAVORITES + " (id INTEGER PRIMARY KEY AUTOINCREMENT, word TEXT, meaning TEXT)");
        }
    }

    public boolean registerUser(String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("username", username);
        values.put("password", password);
        long result = db.insert(TABLE_USERS, null, values);
        return result != -1;
    }

    public boolean checkUser(String username, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_USERS + " WHERE username = ? AND password = ?", new String[]{username, password});
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }

    public boolean updatePassword(String username, String newPassword) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("password", newPassword);
        long result = db.update(TABLE_USERS, values, "username = ?", new String[]{username});
        return result != -1;
    }

    public boolean addWord(String word, String meaning) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("word", word);
        values.put("meaning", meaning);
        long result = db.insert(TABLE_DICTIONARY, null, values);
        return result != -1;
    }

    public Cursor searchWord(String word) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_DICTIONARY + " WHERE word LIKE ?", new String[]{"%" + word + "%"});
    }

    public boolean addHistory(String word) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("word", word);
        values.put("timestamp", String.valueOf(System.currentTimeMillis()));
        long result = db.insert(TABLE_HISTORY, null, values);
        return result != -1;
    }

    public Cursor getHistory() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_HISTORY + " ORDER BY timestamp DESC", null);
    }

    public boolean addFavorite(String word, String meaning) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("word", word);
        values.put("meaning", meaning);
        long result = db.insert(TABLE_FAVORITES, null, values);
        return result != -1;
    }

    public Cursor getFavorites() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_FAVORITES, null);
    }
}
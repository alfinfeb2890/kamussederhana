package com.example.kamussederhana1;

import android.database.Cursor;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textview.MaterialTextView;

public class FavoriteWordsActivity extends AppCompatActivity {
    private MaterialTextView tvFavorites;
    private DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite_words);

        db = new DatabaseHelper(this);
        tvFavorites = findViewById(R.id.tvFavorites);

        Cursor cursor = db.getFavorites();
        StringBuilder favorites = new StringBuilder();
        if (cursor.moveToFirst()) {
            do {
                favorites.append("Word: ").append(cursor.getString(1)).append("\n");
                favorites.append("Meaning: ").append(cursor.getString(2)).append("\n\n");
            } while (cursor.moveToNext());
        } else {
            favorites.append("No favorite words found.");
        }
        cursor.close();
        tvFavorites.setText(favorites.toString());
    }
}
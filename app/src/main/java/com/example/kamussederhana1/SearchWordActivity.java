package com.example.kamussederhana1;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textview.MaterialTextView;

public class SearchWordActivity extends AppCompatActivity {
    private TextInputEditText etWord;
    private MaterialTextView tvResult;
    private DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_word);

        db = new DatabaseHelper(this);
        etWord = findViewById(R.id.etWord);
        tvResult = findViewById(R.id.tvResult);
        MaterialButton btnSearch = findViewById(R.id.btnSearch);
        MaterialButton btnAddFavorite = findViewById(R.id.btnAddFavorite);

        btnSearch.setOnClickListener(v -> {
            String word = etWord.getText().toString();
            if (!word.isEmpty()) {
                Cursor cursor = db.searchWord(word);
                StringBuilder result = new StringBuilder();
                if (cursor.moveToFirst()) {
                    do {
                        result.append("Word: ").append(cursor.getString(1)).append("\n");
                        result.append("Meaning: ").append(cursor.getString(2)).append("\n\n");
                    } while (cursor.moveToNext());
                    db.addHistory(word);
                } else {
                    result.append("No results found.");
                }
                cursor.close();
                tvResult.setText(result.toString());
                btnAddFavorite.setEnabled(!result.toString().equals("No results found."));
            } else {
                Toast.makeText(SearchWordActivity.this, "Enter a word", Toast.LENGTH_SHORT).show();
            }
        });

        btnAddFavorite.setOnClickListener(v -> {
            String word = etWord.getText().toString();
            Cursor cursor = db.searchWord(word);
            if (cursor.moveToFirst()) {
                String meaning = cursor.getString(2);
                if (db.addFavorite(word, meaning)) {
                    Toast.makeText(SearchWordActivity.this, "Added to favorites", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(SearchWordActivity.this, "Failed to add to favorites", Toast.LENGTH_SHORT).show();
                }
            }
            cursor.close();
        });
    }
}
package com.example.kamussederhana1;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AddWordActivity extends AppCompatActivity {
    private EditText etWord, etMeaning;
    private DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_word);

        db = new DatabaseHelper(this);
        etWord = findViewById(R.id.etWord);
        etMeaning = findViewById(R.id.etMeaning);
        Button btnAdd = findViewById(R.id.btnAdd);

        btnAdd.setOnClickListener(v -> {
            String word = etWord.getText().toString();
            String meaning = etMeaning.getText().toString();
            if (!word.isEmpty() && !meaning.isEmpty()) {
                if (db.addWord(word, meaning)) {
                    Toast.makeText(AddWordActivity.this, "Word added", Toast.LENGTH_SHORT).show();
                    etWord.setText("");
                    etMeaning.setText("");
                } else {
                    Toast.makeText(AddWordActivity.this, "Failed to add word", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(AddWordActivity.this, "Fill all fields", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
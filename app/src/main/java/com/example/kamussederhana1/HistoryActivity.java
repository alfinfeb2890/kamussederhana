package com.example.kamussederhana1;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class HistoryActivity extends AppCompatActivity {
    private TextView tvHistory;
    private DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        db = new DatabaseHelper(this);
        tvHistory = findViewById(R.id.tvHistory);

        Cursor cursor = db.getHistory();
        StringBuilder history = new StringBuilder();
        if (cursor.moveToFirst()) {
            do {
                history.append("Word: ").append(cursor.getString(1)).append("\n");
                history.append("Time: ").append(cursor.getString(2)).append("\n\n");
            } while (cursor.moveToNext());
        } else {
            history.append("No history found.");
        }
        cursor.close();
        tvHistory.setText(history.toString());
    }
}
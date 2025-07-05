package com.example.kamussederhana1;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

public class ProfileActivity extends AppCompatActivity {
    private TextInputEditText etUsername, etNewPassword;
    private DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        db = new DatabaseHelper(this);
        etUsername = findViewById(R.id.etUsername);
        etNewPassword = findViewById(R.id.etNewPassword);
        MaterialButton btnUpdate = findViewById(R.id.btnUpdate);

        // Assume username is passed from LoginActivity or stored in SharedPreferences
        etUsername.setText("user"); // Ganti dengan mekanisme pengambilan username
        etUsername.setEnabled(false); // Username tidak dapat diedit

        btnUpdate.setOnClickListener(v -> {
            String newPassword = etNewPassword.getText().toString();
            if (!newPassword.isEmpty()) {
                if (db.updatePassword(etUsername.getText().toString(), newPassword)) {
                    Toast.makeText(ProfileActivity.this, "Password updated", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(ProfileActivity.this, LoginActivity.class));
                    finish();
                } else {
                    Toast.makeText(ProfileActivity.this, "Failed to update password", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(ProfileActivity.this, "Enter new password", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
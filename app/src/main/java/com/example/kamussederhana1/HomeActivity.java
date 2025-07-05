package com.example.kamussederhana1;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationView;

public class HomeActivity extends AppCompatActivity {
    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navView = findViewById(R.id.nav_view);
        MaterialToolbar toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(v -> drawerLayout.openDrawer(GravityCompat.START));

        navView.setNavigationItemSelectedListener(item -> {
            int id = item.getItemId();
            Intent intent;
            if (id == R.id.nav_search) {
                intent = new Intent(HomeActivity.this, SearchWordActivity.class);
            } else if (id == R.id.nav_add_word) {
                intent = new Intent(HomeActivity.this, AddWordActivity.class);
            } else if (id == R.id.nav_history) {
                intent = new Intent(HomeActivity.this, HistoryActivity.class);
            } else if (id == R.id.nav_favorites) {
                intent = new Intent(HomeActivity.this, FavoriteWordsActivity.class);
            } else if (id == R.id.nav_profile) {
                intent = new Intent(HomeActivity.this, ProfileActivity.class);
            } else {
                return false;
            }
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            drawerLayout.closeDrawer(GravityCompat.START);
            return true;
        });
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}
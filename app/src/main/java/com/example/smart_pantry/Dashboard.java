package com.example.smart_pantry;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;

import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.smart_pantry.ui.main.SectionsPagerAdapter;

public class Dashboard extends AppCompatActivity {

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);
        FloatingActionButton fab = findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Toast.makeText(this, "Selected Item: " + item.getTitle(), Toast.LENGTH_SHORT).show();
        switch (item.getItemId()) {
            case R.id.i_search:
                Toast.makeText(this, "Search", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.i_share:
                Toast.makeText(this, "Share", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.i_settings:
                Toast.makeText(this, "Settings", Toast.LENGTH_SHORT).show();
                Intent setup = new Intent(getApplicationContext(), Settings_activity.class);
                startActivity(setup);
                finish();
                return true;
            case R.id.i_location:
                Toast.makeText(this, "Location", Toast.LENGTH_SHORT).show();
                Intent loc = new Intent(getApplicationContext(), Location_activity.class);
                startActivity(loc);
                finish();
                return true;
            case R.id.i_dash:
                Toast.makeText(this, "Dashboard", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.i_logout:
                Toast.makeText(this, "Loging out", Toast.LENGTH_SHORT).show();
                Intent log = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(log);
                finish();
                return true;
            case R.id.profile:
                Toast.makeText(this, "Profile", Toast.LENGTH_SHORT).show();
                Intent prof = new Intent(getApplicationContext(), Profile.class);
                startActivity(prof);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
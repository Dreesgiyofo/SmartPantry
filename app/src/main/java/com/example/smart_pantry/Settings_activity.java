package com.example.smart_pantry;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class Settings_activity extends AppCompatActivity {

    private ImageView item_image;
    private EditText item_name;
//    private

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_activity);
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
                return true;
            case R.id.i_location:
                Toast.makeText(this, "Location", Toast.LENGTH_SHORT).show();
                Intent loc=new Intent(getApplicationContext(),Location_activity.class);
                startActivity(loc);
                finish();
                return true;
            case R.id.i_dash:
                Toast.makeText(this, "Dashboard", Toast.LENGTH_SHORT).show();
                Intent das = new Intent(getApplicationContext(), Dashboard.class);
                startActivity(das);
                finish();
                return true;
            case R.id.i_logout:
                Toast.makeText(this, "Loging out", Toast.LENGTH_SHORT).show();
                Intent log = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(log);
                finish();
                return true;
            case R.id.profile:
                Toast.makeText(this,"Profile",Toast.LENGTH_SHORT).show();
                Intent prof=new Intent(getApplicationContext(),Profile.class);
                startActivity(prof);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}

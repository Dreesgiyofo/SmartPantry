package com.example.smart_pantry;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.UUID;

import javax.annotation.Nullable;

public class Profile extends AppCompatActivity {

    private TextView name;
    private TextView email;
    private TextView phone;
    private Uri filepath;

    private final int PICK_IMAGE_REQUEST = 71;

    FirebaseAuth firebaseAuth;
    FirebaseFirestore firebaseFirestore;
    String user_id;

    FirebaseStorage storage;
    StorageReference storageReference;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        name = (TextView) findViewById(R.id.text_username);
        email = (TextView) findViewById(R.id.text_email);
        phone = (TextView) findViewById(R.id.text_phone);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        user_id = firebaseAuth.getCurrentUser().getUid();

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference("images");
        databaseReference = FirebaseDatabase.getInstance().getReference("images");

        DocumentReference documentReference = firebaseFirestore.collection("users").document(user_id);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                phone.setText(documentSnapshot.getString("phone"));
                name.setText(documentSnapshot.getString("fullname"));
                email.setText(documentSnapshot.getString("email"));
                //Glide.with(Profile.this).load(FirebaseAuth.getInstance().getCurrentUser().getPhotoUrl()).into(profilepic);
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
                Intent loc = new Intent(getApplicationContext(), Settings_activity.class);
                startActivity(loc);
                finish();
                return true;
            case R.id.i_location:
                Toast.makeText(this, "Location", Toast.LENGTH_SHORT).show();
                Intent location = new Intent(getApplicationContext(), Location_activity.class);
                startActivity(location);
                finish();
                return true;
            case R.id.i_dash:
                Toast.makeText(this, "Dashboard", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.i_logout:
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(this, "Loging out", Toast.LENGTH_SHORT).show();
                Intent log = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(log);
                finish();
                return true;
            case R.id.profile:
                Toast.makeText(this, "Profile", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}

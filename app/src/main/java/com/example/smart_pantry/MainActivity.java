package com.example.smart_pantry;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

public class MainActivity extends AppCompatActivity {

    private Button login;
    private EditText username;
    private EditText password;
    private TextView signup;

    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        username = (EditText) findViewById(R.id.user_name);
        password = (EditText) findViewById(R.id.user_password);
        firebaseAuth = FirebaseAuth.getInstance();

        login = (Button) findViewById(R.id.button_login);
        signup=(TextView)findViewById(R.id.signup);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signup=new Intent(getApplicationContext(),Registration.class);
                startActivity(signup);
                finish();
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Loging in", Toast.LENGTH_SHORT).show();
                String i_username = username.getText().toString();
                String i_password = password.getText().toString();

                if (TextUtils.isEmpty(i_username)) {
                    username.setError("Email is required.");
                    return;
                }

                if (TextUtils.isEmpty(i_password)) {
                    password.setError("Password is reduired.");
                    return;
                }

                if (i_password.length() < 6) {
                    password.setError("Password should be greater than 6 characters");
                    return;
                }

                //authenticate the user
                firebaseAuth.signInWithEmailAndPassword(i_username, i_password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                                Intent das = new Intent(getApplicationContext(), Dashboard.class);
                                startActivity(das);
                                finish();
                            } else {
                                Toast.makeText(MainActivity.this, "Set Location Permission", Toast.LENGTH_SHORT).show();
                                Dexter.withActivity(MainActivity.this)
                                        .withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                                        .withListener(new PermissionListener() {
                                            @Override
                                            public void onPermissionGranted(PermissionGrantedResponse response) {
                                                Intent das = new Intent(getApplicationContext(), Dashboard.class);
                                                startActivity(das);
                                                finish();
                                            }

                                            @Override
                                            public void onPermissionDenied(PermissionDeniedResponse response) {
                                                if (response.isPermanentlyDenied()) {
                                                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                                                    builder.setTitle("Permission Denied")
                                                            .setMessage("Required permisssion to access device location")
                                                            .setNegativeButton("Cancel", null)
                                                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                                @Override
                                                                public void onClick(DialogInterface dialog, int which) {
                                                                    Intent intent = new Intent();
                                                                    intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                                                    intent.setData(Uri.fromParts("package", getPackageName(), null));
                                                                }
                                                            })
                                                            .show();
                                                } else {
                                                    Toast.makeText(MainActivity.this, "Permissoin Denied", Toast.LENGTH_SHORT).show();
                                                }
                                            }

                                            @Override
                                            public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                                                token.continuePermissionRequest();
                                            }
                                        }).check();
                            }
                        } else {
                            Toast.makeText(MainActivity.this,"Error ! "+task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });

        login.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                username.setText("alwinjosephkaduppil@gmail.com");
                password.setText("123456");

                return true;
            }
        });
    }
}

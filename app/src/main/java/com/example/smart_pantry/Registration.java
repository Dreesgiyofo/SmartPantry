package com.example.smart_pantry;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Registration extends AppCompatActivity {

    private TextView login;
    private FirebaseAuth mAuth;
    private FirebaseFirestore firestore;
    private String user_id;

    private EditText name;
    private EditText mail;
    private EditText password;
    private EditText phone;

    private Button register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        mAuth = FirebaseAuth.getInstance();
        firestore=FirebaseFirestore.getInstance();

        login=(TextView)findViewById(R.id.login);
        name=(EditText)findViewById(R.id.r_user_full_name);
        mail=(EditText)findViewById(R.id.r_user_name);
        password=(EditText)findViewById(R.id.r_user_password);
        phone=(EditText)findViewById(R.id.r_user_phone);

        if(mAuth.getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
            finish();
        }

        register=(Button)findViewById(R.id.button_register);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent login=new Intent(getApplicationContext(),MainActivity.class);
                startActivity(login);
                finish();
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String fname=name.getText().toString();
                final String email=mail.getText().toString();
                final String pwd=password.getText().toString();
                final String ph=phone.getText().toString();

                if(TextUtils.isEmpty(email)){
                    mail.setError("Email is required.");
                    return;
                }

                if(TextUtils.isEmpty(pwd)){
                    password.setError("Password is required.");
                    return;
                }

                if(password.length() < 6){
                    password.setError("Password should be >= 6 characters.");
                    return;
                }

                //register the user in firebase
                mAuth.createUserWithEmailAndPassword(email,pwd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(Registration.this,"User created.",Toast.LENGTH_SHORT).show();
                            user_id=mAuth.getCurrentUser().getUid();
                            DocumentReference documentReference = firestore.collection("users").document(user_id);
                            Map<String,Object> user = new HashMap<>();
                            user.put("fullname",fname);
                            user.put("email",email);
                            user.put("phone",ph);
                            documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d("TAG","OnSuccess: user profile is created for "+user_id);
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.d("TAG","OnSuccess: user profile is not created for "+user_id+" Error : "+e.toString());
                                }
                            });
                            FirebaseAuth.getInstance().signOut();
                            startActivity(new Intent(getApplicationContext(),MainActivity.class));
                            finish();
                        }else {
                            Toast.makeText(Registration.this,"Error ! "+task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });
    }
}

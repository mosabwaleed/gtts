package com.zu.gtts;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class student_login extends AppCompatActivity {
    EditText id,pass;
    Button login;
    ProgressBar progressBar;
    TextView login_type;

    FirebaseFirestore firebaseFirestore;

    SharedPreference sharedPreference;
    Info info;

    SharedPreference_doctor sharedPreference_doctor;
    doctor_info doctor_info;

    String key;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_login);

        id = findViewById(R.id.id);
        pass = findViewById(R.id.pass);
        login = findViewById(R.id.login);
        progressBar = findViewById(R.id.progressBar);
        login_type = findViewById(R.id.login_type);

        sharedPreference = new SharedPreference();
        sharedPreference_doctor = new SharedPreference_doctor();

        key = getIntent().getStringExtra("key");

        login_type.setText(key.toUpperCase());

        firebaseFirestore = FirebaseFirestore.getInstance();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println(key);
                progressBar.setVisibility(View.VISIBLE);
                if (key.equals("student")){
                    System.out.println(key);
                firebaseFirestore.collection(key)
                        .document(id.getText().toString())
                        .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()){
                            DocumentSnapshot document = task.getResult();
                            if(document.exists()){
                                if(document.get("password").equals(pass.getText().toString())){
                                    info = new Info(document.get("id").toString(),
                                            document.get("email").toString(),
                                            document.get("company").toString(),
                                            document.get("career").toString(),
                                            document.get("major").toString(),
                                            document.get("name").toString(),
                                            document.get("phone").toString());
                                    sharedPreference_doctor.getFavorites(student_login.this).clear();
                                    sharedPreference.addFavorite(student_login.this,info);

                                    startActivity(new Intent(student_login.this,student.class));
                                }

                                else {
                                    progressBar.setVisibility(View.GONE);
                                    Toast.makeText(student_login.this, "please check your email or password ", Toast.LENGTH_LONG).show();
                                }
                            }
                            else {
                                progressBar.setVisibility(View.GONE);
                                Toast.makeText(student_login.this, "please check your email or password ", Toast.LENGTH_LONG).show();
                            }
                        }
                        else {
                            progressBar.setVisibility(View.GONE);
                            Toast.makeText(student_login.this, "please check your email or password ", Toast.LENGTH_LONG).show();
                        }

                    }
                });
                }

                else if (key.equals("doctors")){
                    System.out.println(key);
                    firebaseFirestore.collection(key)
                            .document(id.getText().toString())
                            .get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                            if (task.isSuccessful()){
                                DocumentSnapshot document = task.getResult();
                                if(document.exists()){
                                    if(document.get("password").equals(pass.getText().toString())){
                                        doctor_info = new doctor_info(document.get("name").toString(),
                                                document.get("major").toString());
                                        sharedPreference_doctor.getFavorites(student_login.this).clear();
                                        sharedPreference_doctor.addFavorite(student_login.this,doctor_info);

                                        startActivity(new Intent(student_login.this,doctor.class));
                                    }

                                    else {
                                        System.out.println("mosab1");
                                        progressBar.setVisibility(View.GONE);
                                        Toast.makeText(student_login.this, "please check your id or password ", Toast.LENGTH_LONG).show();
                                    }
                                }
                                else {
                                    System.out.println("mosab1");
                                    progressBar.setVisibility(View.GONE);
                                    Toast.makeText(student_login.this, "please check your id or password ", Toast.LENGTH_LONG).show();
                                }
                            }
                            else {
                                System.out.println(task.getException()+"");
                                System.out.println("mosab3");
                                progressBar.setVisibility(View.GONE);
                                Toast.makeText(student_login.this, "please check your id or password ", Toast.LENGTH_LONG).show();
                            }

                        }
                    });
                }

            }
        });





    }
}

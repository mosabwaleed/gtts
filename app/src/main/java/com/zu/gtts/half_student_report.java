package com.zu.gtts;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class half_student_report extends AppCompatActivity {
    TextView name,id,place;
    EditText learn;
    Button submit;

    SharedPreference sharedPreference;
    ArrayList<Info>arrayList;

    DatabaseReference databaseReference;
    public static int reportcount;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_half_student_report);

        name = findViewById(R.id.name);
        id = findViewById(R.id.id);
        place = findViewById(R.id.place);
        learn = findViewById(R.id.learn);
        submit = findViewById(R.id.submit);

        sharedPreference = new SharedPreference();
        arrayList = new ArrayList<>();
        arrayList = sharedPreference.getFavorites(half_student_report.this);

        name.setText("اسم الطالب : "+arrayList.get(0).getName());
        id.setText("الرقم الجامعي : "+arrayList.get(0).getId());
        place.setText("مكان التدريب : "+arrayList.get(0).getCompany());

        databaseReference = FirebaseDatabase.getInstance().getReference("student").child(arrayList.get(0).getId());
        reportcount = -1;


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseReference.child("lat").setValue("");
                databaseReference.child("lng").setValue("");
                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        reportcount = -1;
                        for (DataSnapshot d : dataSnapshot.getChildren()){
                            reportcount++;
                        }
                        databaseReference.child("report"+reportcount).setValue(learn.getText().toString());
                        startActivity(new Intent(half_student_report.this,student.class));
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });
            }
        });
    }
}
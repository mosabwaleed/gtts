package com.zu.gtts;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class show_reports extends AppCompatActivity {
    TextView name,id,place,showlearn;

    String count;

    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_reports);

        name = findViewById(R.id.name);
        id = findViewById(R.id.id);
        place = findViewById(R.id.place);
        showlearn = findViewById(R.id.show_learn);

        Intent intent = (Intent) getIntent().getParcelableExtra("key");

        name.setText("اسم الطالب : "+intent.getStringExtra("name"));
        id.setText("الرقم الجامعي : "+intent.getStringExtra("id"));
        place.setText("مكان التدريب : "+intent.getStringExtra("company"));

        count = getIntent().getStringExtra("count");

        databaseReference = FirebaseDatabase.getInstance().getReference().child("student").child(intent.getStringExtra("id"));

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                showlearn.setText(dataSnapshot.child("report"+count).getValue(String.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
}

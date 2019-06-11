package com.zu.gtts;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class doctor extends AppCompatActivity {

    TextView name,major;
    SharedPreference_doctor sharedPreference;
    ArrayList<doctor_info> arrayList;
    Button show;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor);

        name = findViewById(R.id.name);
        major = findViewById(R.id.major);
        show = findViewById(R.id.show);

        sharedPreference = new SharedPreference_doctor();
        arrayList = new ArrayList<>();
        arrayList = sharedPreference.getFavorites(doctor.this);
        name.setText("Name : "+arrayList.get(0).getName());
        major.setText("Major : "+arrayList.get(0).getMajor());

        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(doctor.this,student_list.class));
            }
        });
    }
}

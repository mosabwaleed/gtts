package com.zu.gtts;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;

public class student extends AppCompatActivity {
    TextView name,id,company,career,major;
    SharedPreference sharedPreference;
    ArrayList<Info> arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);
        name = findViewById(R.id.name);
        id = findViewById(R.id.id);
        company = findViewById(R.id.company);
        career = findViewById(R.id.career);
        major = findViewById(R.id.major);
        sharedPreference = new SharedPreference();
        arrayList = new ArrayList<>();
        arrayList = sharedPreference.getFavorites(student.this);
        name.setText(arrayList.get(0).getName());
        id.setText(arrayList.get(0).getId());
        company.setText(arrayList.get(0).getCompany());
        career.setText(arrayList.get(0).getCareer());
        major.setText(arrayList.get(0).getMajor());
    }
}

package com.zu.gtts;

import android.content.DialogInterface;
import android.content.Intent;
import android.icu.lang.UCharacter;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class student extends AppCompatActivity {
    TextView name,id,company,career,major,phone;
    SharedPreference sharedPreference;
    ArrayList<Info> arrayList;
    Button report,location;
    public static boolean flag;

    public int i;

    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);
        name = findViewById(R.id.name);
        id = findViewById(R.id.id);
        company = findViewById(R.id.company);
        career = findViewById(R.id.career);
        major = findViewById(R.id.major);
        phone = findViewById(R.id.phone);
        report = findViewById(R.id.report);
        location = findViewById(R.id.location);

        i =0;
        sharedPreference = new SharedPreference();
        arrayList = new ArrayList<>();
        System.out.println(sharedPreference.getFavorites(student.this).size());
        if (sharedPreference.getFavorites(student.this).size()>0
                &&sharedPreference.getFavorites(student.this).get(0).getPhone()!=null){
        arrayList = sharedPreference.getFavorites(student.this);
        name.setText("Name : "+arrayList.get(0).getName());
        id.setText("ID : "+arrayList.get(0).getId());
        company.setText("Company : "+arrayList.get(0).getCompany());
        career.setText("Career : "+arrayList.get(0).getCareer());
        major.setText("Major : "+arrayList.get(0).getMajor());
        phone.setText("Phone Number : "+arrayList.get(0).getPhone());
        flag = true;
        }

        else {
            name.setText("Name : "+getIntent().getStringExtra("name"));
            id.setText("ID : "+getIntent().getStringExtra("id"));
            company.setText("Company : "+getIntent().getStringExtra("company"));
            career.setText("Career : "+getIntent().getStringExtra("career"));
            major.setText("Major : "+getIntent().getStringExtra("major"));
            phone.setText("Phone Number : "+getIntent().getStringExtra("phone"));
            report.setText("Show Reports");
            location.setText("Show Current Location");
            flag = false;
        }

        location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flag){
                    Intent intent = new Intent(student.this,location.class);
                    intent.putExtra("id",arrayList.get(0).getId());
                    startActivity(intent);
                }
                else {
                    databaseReference = FirebaseDatabase.getInstance().getReference("student").child(getIntent().getStringExtra("id"));
                    databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            String lat = dataSnapshot.child("lat").getValue(String.class);
                            String lng = dataSnapshot.child("lng").getValue(String.class);

                           startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com/maps/@" + lat + "," + lng+",20z")));
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
            }
        });


        report.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(flag){
                startActivity(new Intent(student.this,half_student_report.class));
                }
                else {
                    databaseReference = FirebaseDatabase.getInstance().getReference("student")
                            .child(getIntent().getStringExtra("id"));

                    databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            i=0;

                            for(DataSnapshot d : dataSnapshot.getChildren()){
                                i++;
                            }
                            if(i==3){
                                Intent intent = new Intent(student.this,show_reports.class);
                                intent.putExtra("key",getIntent());
                                intent.putExtra("count","1");
                                startActivity(intent);
                            }
                            else if (i<=2) {
                                Toast.makeText(student.this,"No Reports Yet",Toast.LENGTH_LONG).show();
                            }
                            else if (i>4){
                                AlertDialog.Builder alertDialog = new AlertDialog.Builder(student.this);
                                alertDialog.setTitle("Number of Report");
                                int number = i-2;
                                alertDialog.setMessage("Student Have "+ number+" Number of Report please select number you want to show");

                                final EditText input = new EditText(student.this);
                                input.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);;
                                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                                        LinearLayout.LayoutParams.MATCH_PARENT,
                                        LinearLayout.LayoutParams.MATCH_PARENT);
                                input.setLayoutParams(lp);
                                alertDialog.setView(input);

                                alertDialog.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        int int_input  = Integer.parseInt(input.getText().toString());
                                        System.out.println(int_input + " int input  "+i +"  i  " + input.getText().toString() + "  actual input");
                                        if (int_input<=i-2){
                                            Intent intent = new Intent(student.this,show_reports.class);
                                            intent.putExtra("key",getIntent());
                                            intent.putExtra("count",int_input+"");
                                            startActivity(intent);
                                        }
                                    }
                                }).setNeutralButton("Cancle", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                });

                                alertDialog.show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        if(flag){Intent a = new Intent(Intent.ACTION_MAIN);
            a.addCategory(Intent.CATEGORY_HOME);
            a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(a);}
        else {
        super.onBackPressed();}
    }
}

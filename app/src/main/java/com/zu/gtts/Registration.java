package com.zu.gtts;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.facebook.accountkit.Account;
import com.facebook.accountkit.AccountKit;
import com.facebook.accountkit.AccountKitCallback;
import com.facebook.accountkit.AccountKitError;
import com.facebook.accountkit.PhoneNumber;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Registration extends AppCompatActivity {
    EditText Name,Id,Pass,Repass,Phone,Company,Career,Major,Email,pass,repass;
    Button sign_up;
    FirebaseAuth mAuth;
    SharedPreference sharedPreference;
    FirebaseFirestore db ;
    static String phoneNumberString;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        sharedPreference = new SharedPreference();
        Name=findViewById(R.id.name);
        Id=findViewById(R.id.id);
        Email=findViewById(R.id.email);
        Company=findViewById(R.id.company);
        Career=findViewById(R.id.career);
        Major=findViewById(R.id.major);
        pass = findViewById(R.id.password);
        repass = findViewById(R.id.passwordre);
        db = FirebaseFirestore.getInstance();
        sign_up=findViewById(R.id.button);
        sign_up.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                final String name =Name.getText().toString();
                final String id=Id.getText().toString();
                final String company=Company.getText().toString();
                final String career=Career.getText().toString();
                final String major=Major.getText().toString();
                final String email=Email.getText().toString();
                final String password = pass.getText().toString();
                phoneNumberString = getIntent().getStringExtra("phone");
                if(pass.getText().toString().equals(repass.getText().toString())){
                if( !(email.isEmpty()||name.isEmpty()|| id.isEmpty()||career.isEmpty()||company.isEmpty()||major.isEmpty()||password.isEmpty()))
                { sharedPreference.addFavorite(Registration.this,new Info(id,email,company,career,major,name,phoneNumberString));

                    Map<String, Object> student = new HashMap<>();
                    student.put ("id",id);
                    student.put("email" , email);
                    student.put("company",company);
                    student.put("career",career);
                    student.put("major",major);
                    student.put("name",name);
                    student.put("password",password);
                    student.put("phone",phoneNumberString);
                    db.collection("student")
                            .document(id)
                            .set(student);
                startActivity(new Intent(Registration.this,post_login.class));
                }
                 else {
                     Toast.makeText(Registration.this,"Check Your Entry",Toast.LENGTH_LONG).show();
                 }
                }
                else {
                    Toast.makeText(Registration.this,"password and re-password must match",Toast.LENGTH_LONG).show();
                }

                }
            }
        );
}
//    @Override
//    protected void onStart() {
//        super.onStart();
//        if(sharedPreference.getFavorites(Registration.this).size()==1
//                &&sharedPreference.getFavorites(Registration.this)
//                .get(1).getPhone().equals(phoneNumberString)){
//            startActivity(new Intent(Registration.this,student.class));
//        }
//
//    }
}
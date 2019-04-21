package com.zu.gtts;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Registration extends AppCompatActivity {
    EditText Name,Id,Pass,Repass,Phone,Company,Career,Major,Email;
    Button sign_up;
    FirebaseAuth mAuth;
    SharedPreference sharedPreference;
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
                if( !(email.isEmpty()||name.isEmpty()  || id.isEmpty()||career.isEmpty()||company.isEmpty()||major.isEmpty()))
                { sharedPreference.addFavorite(Registration.this,new Info(id,email,company,career,major,name));
                startActivity(new Intent(Registration.this,post_login.class));}
                 else {
                     Toast.makeText(Registration.this,"check your entry",Toast.LENGTH_LONG).show();
                 }
                }
            }
        );
}
    @Override
    protected void onStart() {
        super.onStart();
        if(sharedPreference.getFavorites(Registration.this).size()==1){
            startActivity(new Intent(Registration.this,post_login.class));
        }
    }
}
package com.zu.gtts;

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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        Name=findViewById(R.id.name);
        Id=findViewById(R.id.id);
        Email=findViewById(R.id.email);
        Pass=findViewById(R.id.pass);
        Repass=findViewById(R.id.repass);
        Phone=findViewById(R.id.phone);
        Company=findViewById(R.id.company);
        Career=findViewById(R.id.career);
        Major=findViewById(R.id.major);
        sign_up=findViewById(R.id.button);
        sign_up.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                sign_up.setVisibility(View.INVISIBLE);
                final String name =Name.getText().toString();
                final String pass=Pass.getText().toString();
                final String id=Id.getText().toString();
                final String repass=Repass.getText().toString();
                final String  phone= Phone.getText().toString();
                final String company=Company.getText().toString();
                final String career=Career.getText().toString();
                final String major=Major.getText().toString();
                final String email=Email.getText().toString();

                if( email.isEmpty()||name.isEmpty() || pass.isEmpty() || id.isEmpty()||repass.isEmpty()||phone.isEmpty()||career.isEmpty()||company.isEmpty()||major.isEmpty())
                {
                    ShowMessage("please verify all filed");
                    sign_up.setVisibility(View.VISIBLE); }
                 else {
                CreateuserAccount(email,pass);
                 }
                }

            }
        );
}

    private void CreateuserAccount(String email, String pass) {

        mAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    ShowMessage("Account create");

                }
                else {

                    ShowMessage("Account creation field"+task.getException());
                    sign_up.setVisibility(View.VISIBLE);
                }
            }
        }) ;
    }

    private void ShowMessage(String Message) {
        Toast.makeText(getApplicationContext(),Message,Toast.LENGTH_LONG).show();
    }
}

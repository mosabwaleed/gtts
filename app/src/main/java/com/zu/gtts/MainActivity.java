package com.zu.gtts;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    EditText id,pass;
    Button login,Registration;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        id =findViewById(R.id.id);
        pass=findViewById(R.id.pass);
        login=findViewById(R.id.login);
        Registration = findViewById(R.id.Registration);

    }
}

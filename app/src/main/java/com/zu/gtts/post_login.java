package com.zu.gtts;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.Selection;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.facebook.FacebookSdk;
import com.facebook.accountkit.Account;
import com.facebook.accountkit.AccountKitCallback;
import com.facebook.accountkit.AccountKitError;
import com.facebook.accountkit.AccountKitLoginResult;
import com.facebook.accountkit.PhoneNumber;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.facebook.accountkit.AccountKit;
import com.facebook.accountkit.AccessToken;
import com.facebook.accountkit.AccountKit;
import com.facebook.accountkit.ui.AccountKitActivity;
import com.facebook.accountkit.ui.AccountKitConfiguration;
import com.facebook.accountkit.ui.LoginType;
import java.util.concurrent.TimeUnit;

public class post_login extends AppCompatActivity {

    Button login,register;
    public static final String TAG = "mosab";
    public String mVerificationId;
    public FirebaseAuth mAuth;
    public PhoneAuthProvider.ForceResendingToken mResendToken;
    public AccessToken accessToken;
    public static int APP_REQUEST_CODE = 99;
    String phoneNumberString ;
    SharedPreference sharedPreference;
    SharedPreference_doctor sharedPreference_doctor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_login);
        accessToken = AccountKit.getCurrentAccessToken();

        login = findViewById(R.id.login);
        register = findViewById(R.id.register);
        mAuth = FirebaseAuth.getInstance();

        sharedPreference = new SharedPreference();
        sharedPreference_doctor = new SharedPreference_doctor();





        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(post_login.this)
                        .setTitle("Choose please")
                .setMessage("what do U want login AS ?")
                        .setNegativeButton("Student", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(post_login.this,student_login.class);
                                intent.putExtra("key","student");
                                startActivity(intent);

                            }
                        })
                        .setNeutralButton("doctor", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(post_login.this,student_login.class);
                                intent.putExtra("key","doctors");
                                startActivity(intent);

                            }
                        }).show();
            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Intent intent = new Intent(post_login.this, AccountKitActivity.class);
                AccountKitConfiguration.AccountKitConfigurationBuilder configurationBuilder =
                        new AccountKitConfiguration.AccountKitConfigurationBuilder(
                                LoginType.PHONE,
                                AccountKitActivity.ResponseType.TOKEN); // or .ResponseType.TOKEN
                // ... perform additional configuration ...
                intent.putExtra(
                        AccountKitActivity.ACCOUNT_KIT_ACTIVITY_CONFIGURATION,
                        configurationBuilder.build());
                startActivityForResult(intent, APP_REQUEST_CODE);
                //startActivity(new Intent(post_login.this,Registration.class));
            }
        });

    }

    @Override
    protected void onActivityResult(
        final int requestCode,
        final int resultCode,
        final Intent data) {
            super.onActivityResult(requestCode, resultCode, data);
            if (requestCode == APP_REQUEST_CODE) { // confirm that this response matches your request
                AccountKitLoginResult loginResult = data.getParcelableExtra(AccountKitLoginResult.RESULT_KEY);
                String toastMessage;
                if (loginResult.getError() != null) {
                    toastMessage = loginResult.getError().getErrorType().getMessage();
                    //showErrorActivity(loginResult.getError());
                } else if (loginResult.wasCancelled()) {
                    toastMessage = "Login Cancelled";
                } else {
                    if (loginResult.getAccessToken() != null) {
                        toastMessage = "Success:" + loginResult.getAccessToken().getAccountId();
                        AccountKit.getCurrentAccount(new AccountKitCallback<Account>() {
                            @Override
                            public void onSuccess(Account account) {
                                PhoneNumber phoneNumber = account.getPhoneNumber();
                                if (phoneNumber != null) {
                                    phoneNumberString = phoneNumber.toString();
                                    System.out.println(phoneNumberString+"mosab2");
                                    System.out.println(phoneNumberString);
                                    Intent intent = new Intent(post_login.this,Registration.class);
                                    intent.putExtra("phone",phoneNumberString);
                                    startActivity(intent);
                                }
                            }

                            @Override
                            public void onError(AccountKitError accountKitError) {
                                System.out.println(accountKitError.toString());

                            }
                        });

                    }
                }


                // Surface the result to your user in an appropriate way.
//                Toast.makeText(this, toastMessage, Toast.LENGTH_LONG).show();
//                System.out.println(phoneNumberString);
//                Intent intent = new Intent(post_login.this,Registration.class);
//                intent.putExtra("phone",phoneNumberString);
//                startActivity(intent);


            }
        }

        @Override
    protected void onStart() {
        super.onStart();

            System.out.println(sharedPreference.getFavorites(post_login.this).size());
            System.out.println(sharedPreference_doctor.getFavorites(post_login.this).size());

        if(sharedPreference.getFavorites(post_login.this).size()>0
                &&sharedPreference.getFavorites(post_login.this).get(0).getPhone()!=null){
            startActivity(new Intent(post_login.this,student.class));
        }

            else if(sharedPreference_doctor.getFavorites(post_login.this).size()>0
                         &&sharedPreference_doctor.getFavorites(post_login.this).get(0).getMajor()!=null ){
                startActivity(new Intent(post_login.this,doctor.class));
            }


    }
}

package com.zu.gtts;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_login);
        accessToken = AccountKit.getCurrentAccessToken();

        login = findViewById(R.id.login);
        register = findViewById(R.id.register);
        mAuth = FirebaseAuth.getInstance();

        final Intent intent = new Intent(post_login.this, AccountKitActivity.class);
        AccountKitConfiguration.AccountKitConfigurationBuilder configurationBuilder =
                new AccountKitConfiguration.AccountKitConfigurationBuilder(
                        LoginType.PHONE,
                        AccountKitActivity.ResponseType.CODE); // or .ResponseType.TOKEN
        // ... perform additional configuration ...
        intent.putExtra(
                AccountKitActivity.ACCOUNT_KIT_ACTIVITY_CONFIGURATION,
                configurationBuilder.build());
        startActivityForResult(intent, APP_REQUEST_CODE);



        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //phoneauth(logphone.getText().toString().trim());
            }
        });

        AccountKit.getCurrentAccount(new AccountKitCallback<Account>() {
            @Override
            public void onSuccess(final Account account) {
                // Get Account Kit ID
                String accountKitId = account.getId();

                // Get phone number
                PhoneNumber phoneNumber = account.getPhoneNumber();
                if (phoneNumber != null) {
                    String phoneNumberString = phoneNumber.toString();
                }

                // Get email
                String email = account.getEmail();
            }

            @Override
            public void onError(final AccountKitError error) {
                // Handle Error
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
                    } else {
                        toastMessage = String.format(
                                "Success:%s...",
                                loginResult.getAuthorizationCode().substring(0,10));
                    }
                    startActivity(new Intent(post_login.this,student.class));
                }

                // Surface the result to your user in an appropriate way.
                Toast.makeText(
                        this,
                        toastMessage,
                        Toast.LENGTH_LONG)
                        .show();
            }
        }
    //    private void phoneauth(String phoneNumber) {
//        if (phoneNumber.equals("")) {
//            Toast.makeText(post_login.this,"Phone Number Can't Be Empty",Toast.LENGTH_SHORT).show();
//            logphone.setError("phone number can't be empty");
//        } else if (phoneNumber.length() < 13 || phoneNumber.length() > 13) {
//            logphone.setError("Mobile Must and just 13 Digit");
//            Toast.makeText(post_login.this,"Mobile just 13 Digit",Toast.LENGTH_SHORT).show();
//        } else {
//            PhoneAuthProvider.getInstance().verifyPhoneNumber(phoneNumber, 60, TimeUnit.SECONDS, this,
//                    new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
//                @Override
//                public void onVerificationCompleted(PhoneAuthCredential credential) {
//                    Log.d(TAG, "onVerificationCompleted:" + credential);
//                    signInWithPhoneAuthCredential(credential);
//                }
//                @Override
//                public void onVerificationFailed(FirebaseException e) {
//                    // This callback is invoked in an invalid request for verification is made,
//                    // for instance if the the phone number format is not valid.
//                    Log.w(TAG, "onVerificationFailed", e);
//                    Toast.makeText(post_login.this, "Check Your Phone Number", Toast.LENGTH_SHORT).show();
//
//                    if (e instanceof FirebaseAuthInvalidCredentialsException) {
//                        // Invalid request
//                        // ...
//                    } else if (e instanceof FirebaseTooManyRequestsException) {
//                        // The SMS quota for the project has been exceeded
//                        // ...
//                    }
//                    // Show a message and update the UI
//                    // ...
//                }
//
//                @Override
//                public void onCodeSent(String verificationId, PhoneAuthProvider.ForceResendingToken token) {
//                    // The SMS verification code has been sent to the provided phone number, we
//                    // now need to ask the user to enter the code and then construct a credential
//                    // by combining the code with a verification ID.
//                    Log.d(TAG, "onCodeSent:" + verificationId);
//                    // Save verification ID and resending token so we can use them later
//                    mVerificationId = verificationId;
//                    mResendToken = token;
//                }
//            });
//        }
//    }
//    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
//        mAuth.signInWithCredential(credential)
//                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        if (task.isSuccessful()) {
//                            // Sign in success, update UI with the signed-in user's information
//                            Log.d(TAG, "signInWithCredential:success");
//                            FirebaseUser user = task.getResult().getUser();
//                        } else {
//                            // Sign in failed, display a message and update the UI
//                            Log.w(TAG, "signInWithCredential:failure", task.getException());
//                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
//                                // The verification code entered was invalid
//                            }
//                        }
//                    }
//                });
//    }
}

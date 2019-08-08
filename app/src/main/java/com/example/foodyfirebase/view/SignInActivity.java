package com.example.foodyfirebase.view;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.foodyfirebase.R;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import java.util.Arrays;
import java.util.List;

public class SignInActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener,View.OnClickListener , FirebaseAuth.AuthStateListener {

    public static final int REQUEST_CODE_SIGN_IN_GOOGLE = 1;
    private int CHECK_SIGN_IN_TYPE = 0;
    private final int SIGN_IN_WITH_GOOGLE = 1;
    private final int SIGN_IN_WITH_FACEBOOK = 2;

    private FirebaseAuth firebaseAuth;
    private GoogleApiClient googleApiClient;
    private Button btnSignInGG;
    private Button btnSignInFB;
    private TextView txtSignUp,txtForgotPass;
    private EditText edtEmail,edtPassword;
    private Button btnSignIn;
    private CallbackManager callbackManager;
    private LoginManager loginManager;
    private List< String > permissionNeeds = Arrays.asList("user_photos", "email", "user_birthday", "public_profile");
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.signin_layout);

        callbackManager = CallbackManager.Factory.create();

        loginManager = LoginManager.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();

        progressDialog = new ProgressDialog(this);

        firebaseAuth.signOut();
        connectView();
        createClientGoogle();
    }

    @Override
    protected void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        firebaseAuth.removeAuthStateListener(this);
    }

    private void connectView(){

        edtEmail = findViewById(R.id.edtEmail);
        edtPassword = findViewById(R.id.edtPassword);

        btnSignInGG = findViewById(R.id.btnSignInGG);
        btnSignInFB = findViewById(R.id.btnSignInFB);
        btnSignIn = findViewById(R.id.btnSignIn);

        txtSignUp = findViewById(R.id.txtSignUp);
        txtForgotPass = findViewById(R.id.txtForgotPass);

        btnSignInGG.setOnClickListener(this);
        btnSignInFB.setOnClickListener(this);
        btnSignIn.setOnClickListener(this);

        txtSignUp.setOnClickListener(this);
        txtForgotPass.setOnClickListener(this);
    }

    private void createClientGoogle(){

        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder()
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        googleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this,this)
                .addApi(Auth.GOOGLE_SIGN_IN_API,googleSignInOptions)
                .build();
    }

    private boolean validateUserInput(String email, String pass){

        if(email.trim().length() == 0){
            Toast.makeText(this,getString(R.string.request_input_email),Toast.LENGTH_LONG).show();
        }else if(!validateEmail(email)){
            Toast.makeText(this,getString(R.string.request_input_valid_email),Toast.LENGTH_LONG).show();
        }else if(pass.trim().length() == 0){
            Toast.makeText(this,getString(R.string.request_input_Password),Toast.LENGTH_LONG).show();
        }else if(pass.trim().length() < 6){
            Toast.makeText(this,getString(R.string.input_pass_too_weak),Toast.LENGTH_LONG).show();
        }else{
            return true;
        }
        return false;
    }
    private boolean validateEmail(String email){
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private void signInWithEmail(){

        progressDialog.setMessage(getString(R.string.handling));
        String email = edtEmail.getText().toString();
        String pass = edtPassword.getText().toString();

        if(validateUserInput(email,pass)){
            progressDialog.show();
            firebaseAuth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(!task.isSuccessful()){
                        progressDialog.dismiss();
                        Toast.makeText(SignInActivity.this,getString(R.string.signin_email_failed),Toast.LENGTH_LONG).show();
                        edtEmail.setText(null);
                        edtPassword.setText(null);
                    }
                }
            });
        }

    }

    private void signInWithGoogle( GoogleApiClient apiClient){
        progressDialog.setMessage(getString(R.string.handling));
        CHECK_SIGN_IN_TYPE = SIGN_IN_WITH_GOOGLE;
        Intent iSignInGG = Auth.GoogleSignInApi.getSignInIntent(apiClient);
        startActivityForResult(iSignInGG,REQUEST_CODE_SIGN_IN_GOOGLE);
    }

    private void signInWithFacebook() {
        progressDialog.setMessage(getString(R.string.handling));
        loginManager.logInWithReadPermissions(this,permissionNeeds);
        loginManager.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                CHECK_SIGN_IN_TYPE = SIGN_IN_WITH_FACEBOOK;
                String tokenID = loginResult.getAccessToken().getToken();
                signInFirebaseWithAuthCredential(tokenID);
            }

            @Override
            public void onCancel() {
                progressDialog.dismiss();
            }

            @Override
            public void onError(FacebookException error) {
                progressDialog.dismiss();
                Toast.makeText(SignInActivity.this,getString(R.string.sign_in_fb_failed),Toast.LENGTH_LONG).show();
            }
        });
    }

    private void signInFirebaseWithAuthCredential( String tokenID )
    {
        progressDialog.show();
        if( CHECK_SIGN_IN_TYPE == SIGN_IN_WITH_GOOGLE )
        {
            AuthCredential authCredential = GoogleAuthProvider.getCredential(tokenID,null);
            firebaseAuth.signInWithCredential(authCredential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(!task.isSuccessful()){
                        progressDialog.dismiss();
                        Toast.makeText(SignInActivity.this,getString(R.string.sign_in_gg_failed),Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
        else if ( CHECK_SIGN_IN_TYPE == SIGN_IN_WITH_FACEBOOK ){
            AuthCredential authCredential = FacebookAuthProvider.getCredential(tokenID);
            firebaseAuth.signInWithCredential(authCredential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(!task.isSuccessful()){
                        progressDialog.dismiss();
                        Toast.makeText(SignInActivity.this,getString(R.string.sign_in_fb_failed_credential),Toast.LENGTH_LONG).show();
                    }
                }
            });
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE_SIGN_IN_GOOGLE){
            if(resultCode == RESULT_OK) {
                GoogleSignInResult signInResult = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
                GoogleSignInAccount googleSignInAccount = signInResult.getSignInAccount();
                String tokenID = googleSignInAccount.getIdToken();
                signInFirebaseWithAuthCredential(tokenID);
            }
        } else {
            callbackManager.onActivityResult(requestCode,resultCode,data);
        }
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id){
            case R.id.btnSignInGG:
                signInWithGoogle(googleApiClient);
                break;
            case R.id.btnSignInFB:
                signInWithFacebook();
                break;
            case R.id.txtSignUp:
                Intent iRegister = new Intent(SignInActivity.this,RegisterActivity.class);
                startActivity(iRegister);
                break;
            case R.id.btnSignIn:
                signInWithEmail();
                break;
            case R.id.txtForgotPass:
                Intent iRestorePass = new Intent(SignInActivity.this,RestorePasswordActivity.class);
                startActivity(iRestorePass);
                break;

        }
    }


    @Override
    public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if (user != null){
            progressDialog.dismiss();
            Intent iHome = new Intent(SignInActivity.this,HomeActivity.class);
            startActivity(iHome);
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}

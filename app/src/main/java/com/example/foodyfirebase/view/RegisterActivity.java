package com.example.foodyfirebase.view;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.foodyfirebase.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText edtEmail,edtPass,edtConfirmPass;
    private Button btnRegister;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_layout);

        firebaseAuth = FirebaseAuth.getInstance();

        progressDialog = new ProgressDialog(this);

        edtEmail = findViewById(R.id.edtEmailRegister);
        edtPass = findViewById(R.id.edtPassRegister);
        edtConfirmPass = findViewById(R.id.edtConfirmPass);
        btnRegister = findViewById(R.id.btnRegister);

        btnRegister.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {

       progressDialog.setMessage(getString(R.string.progress_create_user_email));
       final String email = edtEmail.getText().toString();
       final String pass = edtPass.getText().toString();
       final String confirmPass = edtConfirmPass.getText().toString();

       if(validateUserInput(email,pass,confirmPass)){
           progressDialog.show();
           firebaseAuth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
               @Override
               public void onComplete(@NonNull Task<AuthResult> task) {
                    edtEmail.setText(null);
                    edtPass.setText(null);
                    edtConfirmPass.setText(null);
                    if(task.isSuccessful()){
                        progressDialog.dismiss();
                        Toast.makeText(RegisterActivity.this,getString(R.string.announce_register_success),Toast.LENGTH_LONG).show();
                        Intent iSignIn = new Intent(RegisterActivity.this,SignInActivity.class);
                        startActivity(iSignIn);
                    }else{
                        progressDialog.dismiss();
                        Toast.makeText(RegisterActivity.this,getString(R.string.announce_register_failed),Toast.LENGTH_LONG).show();
                    }

               }
           });
       }

    }

    private boolean validateUserInput(String email, String pass, String confirmPass){

        if(email.trim().length() == 0){
            Toast.makeText(this,getString(R.string.request_input_email),Toast.LENGTH_LONG).show();
        }else if(!validateEmail(email)){
            Toast.makeText(this,getString(R.string.request_input_valid_email),Toast.LENGTH_LONG).show();
        }else if(pass.trim().length() == 0){
            Toast.makeText(this,getString(R.string.request_input_Password),Toast.LENGTH_LONG).show();
        }else if(pass.trim().length() < 6){
            Toast.makeText(this,getString(R.string.input_pass_too_weak),Toast.LENGTH_LONG).show();
        }else if(!pass.trim().equals(confirmPass.trim())){
            Toast.makeText(this,getString(R.string.request_confirm_Password),Toast.LENGTH_LONG).show();
        }else{
            return true;
        }
        return false;
    }
    private boolean validateEmail(String email){
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
}

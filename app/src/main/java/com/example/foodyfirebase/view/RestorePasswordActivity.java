package com.example.foodyfirebase.view;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class RestorePasswordActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText edtEmailRestore;
    private TextView txtRegisterNew;
    private Button btnRestorePass;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.restore_password_layout);

        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);

        edtEmailRestore = findViewById(R.id.edtEmailResotre);
        txtRegisterNew = findViewById(R.id.txtRegisterNow);
        btnRestorePass = findViewById(R.id.btnRestorePass);

        txtRegisterNew.setOnClickListener(this);
        btnRestorePass.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.txtRegisterNow:
                Intent iRegister = new Intent(RestorePasswordActivity.this,RegisterActivity.class);
                startActivity(iRegister);
                break;
            case R.id.btnRestorePass:
                progressDialog.setMessage(getString(R.string.handling));
                restorePassword();
                break;
        }
    }

    private void restorePassword(){
        String email = edtEmailRestore.getText().toString();
        if(email.trim().length() == 0){
            Toast.makeText(this,getString(R.string.request_input_email),Toast.LENGTH_LONG).show();
        }else if(!validateEmail(email)){
            Toast.makeText(this,getString(R.string.request_input_valid_email),Toast.LENGTH_LONG).show();
        }else{
            progressDialog.show();
            firebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if(task.isSuccessful()){
                        progressDialog.dismiss();
                        Toast.makeText(RestorePasswordActivity.this,getString(R.string.email_restore_pass_sent),Toast.LENGTH_LONG).show();
                    }else{
                        progressDialog.dismiss();
                        Toast.makeText(RestorePasswordActivity.this,getString(R.string.restore_pass_failed),Toast.LENGTH_LONG).show();
                        edtEmailRestore.setText(null);
                    }
                }
            });
        }
    }

    private boolean validateEmail(String email){
        return Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
}

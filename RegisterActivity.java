package com.example.usermanagement;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity {
    private EditText email,password1,password2;
    private Button Register;
    private TextView Login;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        firebaseAuth=FirebaseAuth.getInstance();
        email=findViewById(R.id.txtEmail);
        password1=findViewById(R.id.txtPassword1);
        password2=findViewById(R.id.txtPassword2);
        Register=findViewById(R.id.btnRegister);
        Login=findViewById(R.id.tvLogin);
        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Register();
            }
        });
        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
    private void Register(){
        String Email =email.getText().toString();
        String Password1 =password1.getText().toString();
        String Password2 =password2.getText().toString();
        if(TextUtils.isEmpty(Email)){
            email.setError("Enter your Email");
            return;
        }
        else if(TextUtils.isEmpty(Password1)){
            password1.setError("Enter your Password");
            return;
        }
        else if(TextUtils.isEmpty(Password2)){
            password2.setError("Confirm your Password");
            return;
        }
        else if(!Password1.equals(Password2)){
            password2.setError("Passwords don't match");
            return;
        }
        else if(Password1.length()<4){
            password1.setError("Length should be > 4");
            return;
        }
        else if(!isValidEmail(Email)){
            email.setError("Email is not valid");
            return;
        }
        firebaseAuth.createUserWithEmailAndPassword(Email,Password1).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(RegisterActivity.this,"Successfully Registered",Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(RegisterActivity.this,DashboardActivity.class);
                    startActivity(intent);
                    finish();
                }
                else{
                    Toast.makeText(RegisterActivity.this,"Registration failed",Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    private boolean isValidEmail(CharSequence target){
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }
}

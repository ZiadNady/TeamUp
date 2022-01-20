package com.aboelnour.teamup.ui.login;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.aboelnour.teamup.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;



public class forgotPasswordActivity extends AppCompatActivity {

    EditText emailVerfi;
    Button send;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        emailVerfi = findViewById(R.id.emailVerfi);
        send = findViewById(R.id.send);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onViewClicked();
            }
        });
    }

    public void onViewClicked() {
        FirebaseAuth.getInstance().sendPasswordResetEmail(emailVerfi.getText().toString())
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(forgotPasswordActivity.this, "تم إرسال رسالة لبريدك الالكتروني لتغيير كلمة السر", Toast.LENGTH_SHORT).show();
                            finish();
                            Log.d("TAG", "Email sent.");
                        }
                        else{
                            Toast.makeText(forgotPasswordActivity.this, "حدث خطا", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}

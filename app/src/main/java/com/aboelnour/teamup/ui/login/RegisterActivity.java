package com.aboelnour.teamup.ui.login;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.aboelnour.teamup.R;
import com.aboelnour.teamup.module.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity {

    private static final String TAG = "RegisterActivity";
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    EditText Email,FirstName,LastName,Password,ConfirmPassword;
    Button Registerbtn;
    FirebaseUser firebaseUser;
    private CheckBox checkBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mDatabase = FirebaseDatabase.getInstance().getReference().child("users");
        mAuth = FirebaseAuth.getInstance();
        Email = findViewById(R.id.RegisterEmail);
        FirstName = findViewById(R.id.FirstName);
        LastName = findViewById(R.id.LastName);
        Password = findViewById(R.id.RegisterPassword);
        ConfirmPassword = findViewById(R.id.RegisterConfirmPassword);
        Registerbtn = findViewById(R.id.Registerbtn);
        checkBox = findViewById(R.id.checkLicence);

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }

    public void toLogin(View view) {
        startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
    }

    public void Registerbtn(View view) {
        final String email,firstname,lastname,password,confirmpassword;
        email = Email.getText().toString();
        firstname = FirstName.getText().toString();
        lastname = LastName.getText().toString();
        password = Password.getText().toString();
        confirmpassword = ConfirmPassword.getText().toString();
        if(TextUtils.isEmpty(email)){
            Toast.makeText(RegisterActivity.this, "Please enter your email.", Toast.LENGTH_SHORT).show();
//            GradientDrawable drawable = (GradientDrawable)Email.getBackground();
//            drawable.setStroke(3, Color.RED);
        }
        else if(TextUtils.isEmpty(firstname)){
            Toast.makeText(RegisterActivity.this, "Please enter your full name", Toast.LENGTH_SHORT).show();
//            GradientDrawable drawable = (GradientDrawable)Email.getBackground();
//            drawable.setStroke(3, Color.GREEN);
//            drawable = (GradientDrawable)FirstName.getBackground();
//            drawable.setStroke(3, Color.RED);

        }
        else if(TextUtils.isEmpty(lastname)){
            Toast.makeText(RegisterActivity.this, "Please enter your full name", Toast.LENGTH_SHORT).show();
            GradientDrawable drawable = (GradientDrawable)Email.getBackground();
//            drawable.setStroke(3, Color.GREEN);
//            drawable = (GradientDrawable)FirstName.getBackground();
//            drawable.setStroke(3, Color.GREEN);
//            drawable = (GradientDrawable)LastName.getBackground();
//            drawable.setStroke(3, Color.RED);

        }
        else if(TextUtils.isEmpty(password)){
            Toast.makeText(RegisterActivity.this, "Please enter your password", Toast.LENGTH_SHORT).show();
//            GradientDrawable drawable = (GradientDrawable)Email.getBackground();
//            drawable.setStroke(3, Color.GREEN);
//            drawable = (GradientDrawable)FirstName.getBackground();
//            drawable.setStroke(3, Color.GREEN);
//            drawable = (GradientDrawable)LastName.getBackground();
//            drawable.setStroke(3, Color.GREEN);
//            drawable = (GradientDrawable)Password.getBackground();
//            drawable.setStroke(3, Color.RED);
        }
        else if(TextUtils.isEmpty(confirmpassword)){
            Toast.makeText(RegisterActivity.this, "please confirm your password", Toast.LENGTH_SHORT).show();
//            GradientDrawable drawable = (GradientDrawable)Email.getBackground();
//            drawable.setStroke(3, Color.GREEN);
//            drawable = (GradientDrawable)FirstName.getBackground();
//            drawable.setStroke(3, Color.GREEN);
//            drawable = (GradientDrawable)LastName.getBackground();
//            drawable.setStroke(3, Color.GREEN);
//            drawable = (GradientDrawable)Password.getBackground();
//            drawable.setStroke(3, Color.GREEN);
//            drawable = (GradientDrawable)ConfirmPassword.getBackground();
//            drawable.setStroke(3, Color.RED);
        }
        else if(email.contains("]")||email.contains("[")||email.contains("?")||email.contains("/")
                ||email.contains("<")||email.contains("~")||email.contains("#")||email.contains("`")
                ||email.contains("!")||email.contains("$")||email.contains("%")
                ||email.contains("^")||email.contains("&")||email.contains("*")||email.contains("(")
                ||email.contains(")")||email.contains("+")||email.contains("-")||email.contains("=")
                ||email.contains("}")||email.contains("|")||email.contains(":")||email.contains("\"")
                ||email.contains(";")||email.contains("'")||email.contains(",")||email.contains(">")
                ||email.contains("{")||email.contains(" ")){
            Toast.makeText(RegisterActivity.this,"email can't contain : []?/<`#~!$%^&*()-=+{}|:\";',> and space ", Toast.LENGTH_SHORT).show();
        }
        else if(firstname.contains("]")||firstname.contains("[")||firstname.contains("?")||firstname.contains("/")
                ||firstname.contains("<")||firstname.contains("~")||firstname.contains("#")||firstname.contains("`")
                ||firstname.contains("!")||firstname.contains("$")||firstname.contains("%")
                ||firstname.contains("^")||firstname.contains("&")||firstname.contains("*")||firstname.contains("(")
                ||firstname.contains(")")||firstname.contains("+")||firstname.contains("-")||firstname.contains("=")
                ||firstname.contains("}")||firstname.contains("|")||firstname.contains(":")||firstname.contains("\"")
                ||firstname.contains(";")||firstname.contains("'")||firstname.contains(",")||firstname.contains(">")
                ||firstname.contains("{")||firstname.contains(" ")){
            Toast.makeText(RegisterActivity.this,"First name can't contain : []?/<`#~!$%^&*()-=+{}|:\";',> and space ", Toast.LENGTH_SHORT).show();
        }
        else if(lastname.contains("]")||lastname.contains("[")||lastname.contains("?")||lastname.contains("/")
                ||lastname.contains("<")||lastname.contains("~")||lastname.contains("#")||lastname.contains("`")
                ||lastname.contains("!")||lastname.contains("$")||lastname.contains("%")
                ||lastname.contains("^")||lastname.contains("&")||lastname.contains("*")||lastname.contains("(")
                ||lastname.contains(")")||lastname.contains("+")||lastname.contains("-")||lastname.contains("=")
                ||lastname.contains("}")||lastname.contains("|")||lastname.contains(":")||lastname.contains("\"")
                ||lastname.contains(";")||lastname.contains("'")||lastname.contains(",")||lastname.contains(">")
                ||lastname.contains("{")||lastname.contains(" ")){
            Toast.makeText(RegisterActivity.this,"last name can't contain : []?/<`#~!$%^&*()-=+{}|:\";',> and space ", Toast.LENGTH_SHORT).show();
        }
        else if(password.contains("]")||password.contains("[")||password.contains("?")||password.contains("/")
                ||password.contains("<")||password.contains("~")||password.contains("#")||password.contains("`")
                ||password.contains("!")||password.contains("$")||password.contains("%")
                ||password.contains("^")||password.contains("&")||password.contains("*")||password.contains("(")
                ||password.contains(")")||password.contains("+")||password.contains("-")||password.contains("=")
                ||password.contains("}")||password.contains("|")||password.contains(":")||password.contains("\"")
                ||password.contains(";")||password.contains("'")||password.contains(",")||password.contains(">")
                ||password.contains("{")||password.contains(" ")){
            Toast.makeText(RegisterActivity.this,"password can't contain : []?/<`#~!$%^&*()-=+{}|:\";',> and space ", Toast.LENGTH_SHORT).show();
        }
        else if(!password.equals(confirmpassword)){
            Toast.makeText(RegisterActivity.this, "password don't match", Toast.LENGTH_SHORT).show();
            GradientDrawable drawable = (GradientDrawable)Email.getBackground();
            drawable.setStroke(3, Color.GREEN);
            drawable = (GradientDrawable)FirstName.getBackground();
            drawable.setStroke(3, Color.GREEN);
            drawable = (GradientDrawable)LastName.getBackground();
            drawable.setStroke(3, Color.GREEN);
            drawable = (GradientDrawable)Password.getBackground();
            drawable.setStroke(2, Color.WHITE);
            drawable = (GradientDrawable)ConfirmPassword.getBackground();
            drawable.setStroke(3, Color.RED);
        }
        else if (!checkBox.isChecked()){
            Toast.makeText(this, "برجاء اموافقة علي شروط الاستخدام.", Toast.LENGTH_SHORT).show();
        }
        else{
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                                writeNewUser(firebaseUser.getUid(),email,firstname,lastname);
                                startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
                                finish();
                            } else {
                                // If sign in fails, display a message to the user.
                                    Toast.makeText(RegisterActivity.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                                Log.d(TAG, "onComplete: "+ task.toString());
                            }
                        }
                    });
        }
    }

        private void writeNewUser(String userId, String email, String firstname, String lastname) {

            Uri uri = Uri.parse("android.resource://com.aboelnour.teamup/drawable/image_name");
            User user = new User(firstname, lastname, "","", "", email,"", "","","","");

            Drawable drawable = this.getResources().getDrawable(R.drawable.photo);
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.photo);



            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                    .setDisplayName(firstname+" "+lastname)
                    .setPhotoUri(uri)
                    .build();

            firebaseUser.updateProfile(profileUpdates)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Log.d(TAG, "onComplete: update success");
                            }
                        }
                    });

            mDatabase.child(userId).setValue(user);

        }

    public void ToLaw(View view) {
        startActivity(new Intent(RegisterActivity.this, LawActivity.class));
    }
}

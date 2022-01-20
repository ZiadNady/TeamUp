package com.aboelnour.teamup.ui.home;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.aboelnour.teamup.HomeActivity;
import com.aboelnour.teamup.R;
import com.aboelnour.teamup.module.DataPostModel;
import com.aboelnour.teamup.module.PartnersForm;
import com.aboelnour.teamup.module.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class PartnerFormActivity extends AppCompatActivity {

    private static final String TAG = "PartnerFormActivity";
    private DatabaseReference mDatabase;
    FirebaseAuth mAuth;
    FirebaseUser firebaseUser;
    RadioGroup ProjectPlaceAvailable, PlaceState;
    LinearLayout IHaveAPlace, Place, partnerTechnicalExperienceLayout, partnerFinancingLayout,mainLayout;
    EditText MonthlyRent, ProjectPlaceDescription, FinancialFinancing, YearsOfExperience, ExperienceDescription;
    TextView PlaceText, financingText, experienceText, noParticipation;
    Bundle bundle;
    Button Finished;
    DatabaseReference mReference;
    boolean IHaveAPlaceState = false, RentState = false, ProjectneedPlace1 =false ,visible = false;
    User user;
    ProgressDialog progressDialog ;
    DataPostModel PData;
    PartnersForm NData;
    Spinner fields;
    String[] categorys = {"صناعي", "سياحي", "نقل و مواصلات", "حيواني", "طبي", "هندسي", "تعليمي", "تجاري", "زراعي","إداري","استيراد","تصدير"};



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_partner_form);

        intialize();
        userdata();
        projectPlaceAvailable();
        placeState();
        finished();


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void intialize() {

        progressDialog = new ProgressDialog(this);
        bundle = getIntent().getExtras();
        PData = bundle.getParcelable("data");

        fields = findViewById(R.id.FieldOfExperience);
        setSpinner(categorys, fields);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        Place = findViewById(R.id.Place);
        PlaceText = findViewById(R.id.PlaceText);

        Finished = findViewById(R.id.Finished);
        PlaceState = findViewById(R.id.PlaceState);
        MonthlyRent = findViewById(R.id.MonthlyRent);
        IHaveAPlace = findViewById(R.id.IHaveAPlace);
        ProjectPlaceDescription = findViewById(R.id.PlaceDescription);
        YearsOfExperience = findViewById(R.id.YearsOfExperience);
        FinancialFinancing = findViewById(R.id.FinancialFinancing);
        ExperienceDescription = findViewById(R.id.ExperienceDescription);
        ProjectPlaceAvailable = findViewById(R.id.ProjectPlaceAvailable);
        partnerFinancingLayout = findViewById(R.id.partnerFinancingLayout);
        partnerTechnicalExperienceLayout = findViewById(R.id.partnerTechnicalExperienceLayout);
        financingText = findViewById(R.id.financingText);
        experienceText = findViewById(R.id.experienceText);
        noParticipation = findViewById(R.id.noParticipation);
        if (PData.isProjectPlaceState()){
//          PlaceText.setVisibility(View.GONE);
            Place.setVisibility(View.VISIBLE);
            noParticipation.setVisibility(View.GONE);
            visible = true;
        }

        if(PData.getProjectPlaceNegotiation().equals("مكان المشروع المقترح نهائي لا يقبل التعديل")){
            Place.setVisibility(View.GONE);
            noParticipation.setVisibility(View.VISIBLE);
            visible = false;
        }

        if(PData.isProjectFinancingState()){
            partnerFinancingLayout.setVisibility(View.VISIBLE);
            financingText.setVisibility(View.VISIBLE);
            noParticipation.setVisibility(View.GONE);
            visible = true;
        }

        if (PData.isTechnicalExperienceState()){
            partnerTechnicalExperienceLayout.setVisibility(View.VISIBLE);
            experienceText.setVisibility(View.VISIBLE);
            noParticipation.setVisibility(View.GONE);
            visible = true;
        }
        mainLayout = findViewById(R.id.mainLayout);
        changeColor(mainLayout);
    }

    private void changeColor(LinearLayout l) {
        View v = null;
        for (int i=2;i<l.getChildCount();i++) {
            v = l.getChildAt(i);
            if(v.getTag()!= null && v.getTag().toString().equals("yes")){
                Log.d(TAG, "changeColor: done");
                changeColor((LinearLayout) v);
            }
            else if(v instanceof LinearLayout && ((LinearLayout) v).getOrientation() == LinearLayout.VERTICAL)
            {
                changeColor((LinearLayout)v);
            }
            else if(i%2==1)
            {
//                v.setBackgroundColor(Color.parseColor("#d8d5ce"));
                v.setBackground(ContextCompat.getDrawable(this,R.drawable.design_background));
                v.setAlpha((float) 0.7);
            }
        }
    }

    private void finished() {

        Finished.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!visible){
                    progressDialog.setMessage("Please wait a while....");
                    progressDialog.setTitle("Uploading Data...");
                    progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                    progressDialog.show();
                    mDatabase.child("userPosts").child(PData.getPublisherID()).child("posts").child(String.valueOf(PData.getPostID())).child("Partners").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if(dataSnapshot.child(firebaseUser.getUid()).exists()){
                                Toast.makeText(PartnerFormActivity.this,"لقد تقدمت لهذا المشروع من قبل", Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                            }
                            else{
                                writeNewPost(firebaseUser.getUid());
                                progressDialog.dismiss();
                                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                intent.putExtra("EXIT", true);
                                startActivity(intent);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
                else{
                    boolean one = true, two=false, three=false;
                    if(PData.isProjectPlaceState()){
                        ProjectneedPlace1=true;
                        if (IHaveAPlaceState){
                            if (TextUtils.isEmpty(ProjectPlaceDescription.getText().toString())){
                                Toast.makeText(PartnerFormActivity.this,"الرجاء إكمال كل الخانات .", Toast.LENGTH_SHORT).show();
                                one = false;
                            }
                            else{
                                one = true;
                            }
                            //else{ mDatabase.child(String.valueOf(PostsLength+1)).child("ProjectPlaceDescription").setValue(ProjectPlaceDescription.getText().toString()); }
                            if (RentState){
                                if (TextUtils.isEmpty(MonthlyRent.getText().toString())){
                                    Toast.makeText(PartnerFormActivity.this,"الرجاء إكمال كل الخانات .", Toast.LENGTH_SHORT).show();
                                    one = false;
                                }
                                else{
                                    one = true;
                                }
                                //else{ mDatabase.child(String.valueOf(PostsLength+1)).child("MonthlyRent").setValue(MonthlyRent.getText().toString()); }
                            }
                        }
                    }
                    else{
                        one=true;
                    }
                    if (PData.isProjectFinancingState()){
                        if(TextUtils.isEmpty(FinancialFinancing.getText().toString())){
                            Toast.makeText(PartnerFormActivity.this,"الرجاء إكمال كل الخانات .", Toast.LENGTH_SHORT).show();
                            two = false;
                        }
                        else
                            two = true;
                    }
                    else{
                        two=true;
                    }
                    if (PData.isTechnicalExperienceState()){
                        if (TextUtils.isEmpty(YearsOfExperience.getText().toString())||
                                TextUtils.isEmpty(ExperienceDescription.getText().toString())||
                                TextUtils.isEmpty(fields.getSelectedItem().toString())){
                            Toast.makeText(PartnerFormActivity.this,"الرجاء إكمال كل الخانات .", Toast.LENGTH_SHORT).show();
                            three = false;
                        }
                        else
                            three = true;
                    }
                    else{
                        three=true;
                    }
                    if(one&&two&&three)
                    progressDialog.setMessage("Please wait a while....");
                    progressDialog.setTitle("Uploading Data...");
                    progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                    progressDialog.show();
                    mDatabase.child("userPosts").child(PData.getPublisherID()).child("posts").child(String.valueOf(PData.getPostID())).child("Partners").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if(dataSnapshot.child(firebaseUser.getUid()).exists()){
                                Toast.makeText(PartnerFormActivity.this,"لقد تقدمت لهذا المشروع من قبل", Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                            }
                            else{
                                writeNewPost(firebaseUser.getUid());
                                progressDialog.dismiss();
                                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                intent.putExtra("EXIT", true);
                                startActivity(intent);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
            }
        });
    }

    private void projectPlaceAvailable() {

        ProjectPlaceAvailable.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb= findViewById(checkedId);
                // Check which radio button was clicked
                if(rb.getText().toString().equals("نعم")){
                    IHaveAPlace.setVisibility(View.VISIBLE);
                    IHaveAPlaceState = true;
                }
                else{
                    IHaveAPlace.setVisibility(View.GONE);
                    IHaveAPlaceState = false;
                }
            }
        });
    }

    private void placeState() {

        PlaceState.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = findViewById(checkedId);

                if(rb.getText().toString().equals("إيجار")){
                    MonthlyRent.setVisibility(View.VISIBLE);
                    RentState = true;
                }
                else{
                    MonthlyRent.setVisibility(View.GONE);
                    RentState = false;
                }
            }
        });
    }

    private void writeNewPost(final String userId) {

        NData = new PartnersForm(userId, MonthlyRent.getText().toString(), ProjectPlaceDescription.getText().toString()
                , FinancialFinancing.getText().toString(), ExperienceDescription.getText().toString(), fields.getSelectedItem().toString(),
                YearsOfExperience.getText().toString(), IHaveAPlaceState, RentState, ProjectneedPlace1);

        mDatabase.child("userPosts").child(PData.getPublisherID()).child("posts").child(String.valueOf(PData.getPostID())).child("Partners").child(String.valueOf(userId)).setValue(NData);
        mDatabase.child("userRequests").child(firebaseUser.getUid()).child("posts").child("request").child(PData.getPostID()).setValue(PData);

    }

    private void setSpinner(String[] strings, Spinner spinner) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(PartnerFormActivity.this, android.R.layout.simple_spinner_item, strings);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    private void userdata(){
        mReference = FirebaseDatabase.getInstance().getReference("users").child(firebaseUser.getUid());
        mReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                user = dataSnapshot.getValue(User.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}

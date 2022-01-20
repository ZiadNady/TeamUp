package com.aboelnour.teamup.ui.add_your_project;

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
import com.aboelnour.teamup.module.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Calendar;
import java.util.TimeZone;

public class ProjectPage3Activity extends AppCompatActivity {

    private static final String TAG = "ProjectPage3Activity";
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    private FirebaseUser firebaseUser;
    private DatabaseReference mReference;
    private LinearLayout projectPlaceLayout, projectFinancingLayout, technicalExperienceLayout,IHaveAPlace,mainLayout;
    private RadioGroup IOwnTheProjectPlace, PlaceOwnershipStatus;
    private String MonthlyRent = "", ProjectPlaceDescription = "", PublisherFinancialContribution = "", PublisherYearsOfExperience = "", DescriptionOfPublisherExperience = "";
    private EditText monthlyRent, projectPlaceDescription, publisherFinancialContribution, publisherYearsOfExperience, descriptionOfPublisherExperience;
    private TextView PlaceText, noParticipation;
    private boolean TechnicalExperienceState = false, projectPlaceState = false, projectFinancingState = false, iOwnTheProjectPlace = false, placeOwnershipStatus = true;
    private User user;
    private Intent intent;
    private DataPostModel P1Data,P2Data,P3Data;
    private Button Finished, prev;
    private Spinner publisherTechnichalExperience;
    String[] categorys = {"صناعي", "سياحي", "نقل و مواصلات", "حيواني", "طبي", "هندسي", "تعليمي", "تجاري", "زراعي", "إداري"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_project_page3);

        Initialize();
        userdata();
        RadioGroup();
        setdata(categorys,publisherTechnichalExperience);
        goback();

        Finished.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkData();
            }
        });



    }

    private void goback() {

        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    private void Initialize() {

        intent = getIntent();
        P1Data = intent.getExtras().getParcelable("P1Data");
        Log.d(TAG, "Initialize: fist page " + P1Data.getPartnerGender());
        P2Data = intent.getExtras().getParcelable("P2Data");
        Log.d(TAG, "Initialize: second page " + P2Data);
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        projectPlaceState = P2Data.isProjectPlaceState();
        projectFinancingState = P2Data.isProjectFinancingState();
        TechnicalExperienceState = P2Data.isTechnicalExperienceState();
        projectPlaceLayout = findViewById(R.id.projectPlaceLayout);
        projectFinancingLayout = findViewById(R.id.projectFinancingLayout);
        technicalExperienceLayout = findViewById(R.id.technicalExperienceLayout);
        PlaceText = findViewById(R.id.PlaceText);
        monthlyRent = findViewById(R.id.monthlyRent);
        projectPlaceDescription = findViewById(R.id.projectPlaceDescription);
        publisherFinancialContribution = findViewById(R.id.publisherFinancialContribution);
        publisherYearsOfExperience = findViewById(R.id.publisherYearsOfExperience);
        descriptionOfPublisherExperience = findViewById(R.id.descriptionOfPublisherExperience);
        IOwnTheProjectPlace = findViewById(R.id.iOwnProjectPlace);
        PlaceOwnershipStatus = findViewById(R.id.placeOwnershipStatus);
        IHaveAPlace = findViewById(R.id.IHaveAPlace);
        Finished = findViewById(R.id.Finished);
        prev = findViewById(R.id.prev);
        publisherTechnichalExperience = findViewById(R.id.publisherTechnichalExperience);
        noParticipation = findViewById(R.id.noParticipation);

        if (projectPlaceState){
            projectPlaceLayout.setVisibility(View.VISIBLE);
            noParticipation.setVisibility(View.GONE);
        }
        if (projectFinancingState){
            projectFinancingLayout.setVisibility(View.VISIBLE);
            noParticipation.setVisibility(View.GONE);
        }
        if (TechnicalExperienceState){
            technicalExperienceLayout.setVisibility(View.VISIBLE);
            noParticipation.setVisibility(View.GONE);
        }
        mainLayout = findViewById(R.id.mainLayout);
        changeColor(mainLayout);
    }

    private void checkData(){
        boolean finish = false, one = false, two = false, three = false, one1 = false, one2 = false;

        while (!finish) {
            if (projectPlaceState && !one) {
                if (iOwnTheProjectPlace) {
                    if (!placeOwnershipStatus) {
                        if (TextUtils.isEmpty(monthlyRent.getText().toString())) {
                            Toast.makeText(this, "الرجاء إكمال كل الخانات .", Toast.LENGTH_SHORT).show();
                            Log.d(TAG, "checkData: monthlyRent");
                            break;
                        } else {
                            MonthlyRent = monthlyRent.getText().toString();
                            one1 = true;
                        }
                    }
                    else{
                        one1 = true;
                    }
                    if (TextUtils.isEmpty(projectPlaceDescription.getText().toString())) {
                        Toast.makeText(this, "الرجاء إكمال كل الخانات .", Toast.LENGTH_SHORT).show();
                        Log.d(TAG, "checkData: projectPlaceDescription ");
                        break;
                    } else {
                        ProjectPlaceDescription = projectPlaceDescription.getText().toString();
                        one2 = true;
                    }

                    if (one1 && one2)
                        one = true;
                }
                else
                    one = true;
            }
            else if (projectFinancingState && !two) {
                if (TextUtils.isEmpty(publisherFinancialContribution.getText().toString())) {
                    Toast.makeText(this, "الرجاء إكمال كل الخانات .", Toast.LENGTH_SHORT).show();
                    break;
                } else {
                    PublisherFinancialContribution = publisherFinancialContribution.getText().toString();
                    two = true;
                }
            }
            else if (TechnicalExperienceState && !three) {
                if (TextUtils.isEmpty(publisherYearsOfExperience.getText().toString()) || TextUtils.isEmpty(descriptionOfPublisherExperience.getText().toString())) {
                    Toast.makeText(this, "الرجاء إكمال كل الخانات .", Toast.LENGTH_SHORT).show();
                    break;
                }
                else {
                    PublisherYearsOfExperience = publisherYearsOfExperience.getText().toString();
                    DescriptionOfPublisherExperience = descriptionOfPublisherExperience.getText().toString();
                    three = true;
                }
            }
            else{
                finish = true;
            }
        }

        if (finish){
            writeNewPost(firebaseUser.getUid());
            Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
//                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("EXIT", true);
            startActivity(intent);
            finish();}


    }

    private void writeNewPost(String userId) {

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        
        
        
        Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
        int Day = calendar.get(Calendar.DATE);
        int Month = calendar.get(Calendar.MONTH)+1;
        int Year = calendar.get(Calendar.YEAR);
        String publisherTechnichalExperienceSTRING = "";
        if (TechnicalExperienceState)
            publisherTechnichalExperienceSTRING = publisherTechnichalExperience.getSelectedItem().toString();
        String KEY = mDatabase.push().getKey();

        P3Data = new DataPostModel(P1Data.getNumberOfPartners(), 0, P1Data.getProjectStartMonth(), P1Data.getProjectStartYear(),
                P1Data.getPartnersAgeFrom(), P1Data.getPartnersAgeTo(), Day, Month, Year, P1Data.getPartnerGender(), P1Data.getPartnersCountry(),
                P1Data.getPartnersGovernorate(), P1Data.getPartnersEducation(), P1Data.getProjectState(), userId, P2Data.getProjectCategory(),
                P2Data.getProjectName(), P2Data.getProjectCurrency(), P2Data.getProjectDescription(), P2Data.getProjectCountry(), P2Data.getProjectGovernorate(),
                P2Data.getProjectCost(), P2Data.getOperatingCost(), P2Data.getTheExpectedProfitFrom(), P2Data.getTheExpectedProfitTo(),
                P2Data.getYearsOfExperience(), P2Data.getFieldOfExperience(), P2Data.getDescriptionOfTechnicalExperience(), monthlyRent.getText().toString(),
                projectPlaceDescription.getText().toString(), publisherFinancialContribution.getText().toString(), publisherYearsOfExperience.getText().toString(),
                descriptionOfPublisherExperience.getText().toString(), KEY, projectPlaceState, P2Data.getProjectPlaceNegotiation(), projectFinancingState,
                TechnicalExperienceState, iOwnTheProjectPlace, placeOwnershipStatus, publisherTechnichalExperienceSTRING);



        db.collection("Posts").document(KEY).set(P3Data);
//                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
//                    @Override
//                    public void onSuccess(DocumentReference documentReference) {
//                        Toast.makeText(ProjectPage3Activity.this, "yes :)", Toast.LENGTH_SHORT).show();
//                    }
//                })
//                .addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        Toast.makeText(ProjectPage3Activity.this, "no :(", Toast.LENGTH_SHORT).show();
//                    }
//                });
//        mDatabase.child("Posts").child(KEY).setValue(P3Data);
        mDatabase.child("userPosts").child(userId).child("posts").child(KEY).setValue(P3Data);

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

    private void RadioGroup(){
        IOwnTheProjectPlace.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = findViewById(checkedId);
                if (rb.getTag().equals("yes")){
                    IHaveAPlace.setVisibility(View.VISIBLE);
                    iOwnTheProjectPlace = true;
                }
                else{
                    IHaveAPlace.setVisibility(View.GONE);
                    iOwnTheProjectPlace = false;
                }
            }
        });

        PlaceOwnershipStatus.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = findViewById(checkedId);
                if (rb.getTag().equals("owned")){
                    monthlyRent.setVisibility(View.GONE);
                    placeOwnershipStatus = true;
                }
                else{
                    monthlyRent.setVisibility(View.VISIBLE);
                    placeOwnershipStatus = false;
                }
            }
        });
    }

    private void setdata(String[] strings, Spinner spinner) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(ProjectPage3Activity.this, android.R.layout.simple_spinner_item, strings);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
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
}
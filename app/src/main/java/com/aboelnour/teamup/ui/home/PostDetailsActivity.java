package com.aboelnour.teamup.ui.home;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.aboelnour.teamup.FullYourData;
import com.aboelnour.teamup.R;
import com.aboelnour.teamup.module.DataPostModel;
import com.aboelnour.teamup.module.User;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class PostDetailsActivity extends AppCompatActivity {


    private static final String TAG = "PostDetailsActivity";
    private String adapter ;
    private DataPostModel DPM;
    private FirebaseUser firebaseUser;
    private User user,publisher;
    private DatabaseReference mReference;
    private TextView PostReleaseDate, UserName, UserCountry, UserTeratory, ProjectPlaceCountry, ProjectPlaceTeratory, ProjectName,
            ProjectCategory, currency, ProjectDescription, NumberOfPartners, projectStartMonth, projectStartYear, PartnersAgesFrom,
            PartnersAgesTo, ProjectCost, PowerCost, theExpectedProfitFrom, theExpectedProfitTo, yearsOfExperience, Years,
            fieldOfExperience, descriptionOfTechnicalExperience, projectPlaceDescription, publisherFinancialContribution,
            publisherFieldOfExperience, publisherYearsOfExperience, publisherExperienceDescription, monthlyRent, monthlyRentCount,
            partnersCountry, partnersGovernorate, projectPlaceDescriptionTextView, noParticipation;

    private LinearLayout mainLayout,projectPlace, MoneyToBuild, technicalExperience, publisherContributionLayout, projectPlaceLayout,
            projectFinancingLayout, technicalExperienceLayout, apartment, apartmentState;

    RadioButton male, female, both, underMid, mid, high, nothing, alreadyExists, underConstruction, ProjectPlaceNegotiationNo,
            ProjectPlaceNegotiationYes, owned, notOwned, yes, no, MoneyToBuildYes, MoneyToBuildNo, TechnicalExperienceYes, TechnicalExperienceNo,
            yesIOwnProjectPlace, noIDoNotOwnProjectPlace;

    private CircleImageView postOwnerPhoto;
    Button ToPartnerForm;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_details);

        initialize();
        ToPartnerForm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextPage();
            }
        });

    }

    private void initialize(){
        mainLayout = findViewById(R.id.mainLayout);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        Bundle data = getIntent().getExtras();
        DPM = data.getParcelable("data");
        publisher = data.getParcelable("user");
        adapter = data.getParcelable("Adapter");

        PostReleaseDate = findViewById(R.id.PostReleaseDate);
        PostReleaseDate.setText(DPM.getDay()+"/"+DPM.getMonth()+"/"+DPM.getYear());

        UserName = findViewById(R.id.UserName);
        UserName.setText(publisher.getFirstName()+" "+publisher.getLastName());

        UserCountry = findViewById(R.id.UserCountry);
        UserCountry.setText(publisher.getCountry());

        UserTeratory = findViewById(R.id.UserGovernorate);
        UserTeratory.setText(publisher.getTeratory());

        ProjectName = findViewById(R.id.ProjectName);
        ProjectName.setText(DPM.getProjectName());

        ProjectCategory = findViewById(R.id.ProjectCategory);
        ProjectCategory.setText(DPM.getProjectCategory());

        partnersCountry = findViewById(R.id.partnersCountry);
        partnersCountry.setText(DPM.getPartnersCountry());

        partnersGovernorate = findViewById(R.id.partnersGovernorate);
        partnersGovernorate.setText(DPM.getPartnersGovernorate());

        currency = findViewById(R.id.currency);
        currency.setText(DPM.getProjectCurrency());

        ProjectDescription = findViewById(R.id.ProjectDescription);
        ProjectDescription.setText(DPM.getProjectDescription());

        NumberOfPartners = findViewById(R.id.numberOfPartners);
        NumberOfPartners.setText(String.valueOf(DPM.getNumberOfPartners()));

        male = findViewById(R.id.male);
        female = findViewById(R.id.female);
        both = findViewById(R.id.both);

        if(DPM.getPartnerGender().equals("ذكر"))
        {male.setChecked(true);}
        else if (DPM.getPartnerGender().equals("انثي"))
        {female.setChecked(true);}
        else
        {both.setChecked(true);}

        Log.d(TAG, "initialize: "+ DPM.getPartnerGender());

        projectStartMonth = findViewById(R.id.projectStartMonth);
        projectStartMonth.setText(Integer.toString(DPM.getProjectStartMonth()));

        projectStartYear = findViewById(R.id.projectStartYear);
        projectStartYear.setText(Integer.toString(DPM.getProjectStartYear()));

        underMid = findViewById(R.id.underMid);
        mid = findViewById(R.id.mid);
        high = findViewById(R.id.high);
        nothing = findViewById(R.id.nothing);

        if(DPM.getPartnersEducation().equals("تحت المتوسط"))
        {underMid.setChecked(true);}
        else if(DPM.getPartnersEducation().equals("متوسط"))
        {mid.setChecked(true);}
        else if(DPM.getPartnersEducation().equals("عالي"))
        {high.setChecked(true);}
        else
        {nothing.setChecked(true);}

        alreadyExists = findViewById(R.id.alreadyExists);
        underConstruction = findViewById(R.id.underConstruction);

        if(DPM.getProjectState().equals("قائم بالفعل"))
        {alreadyExists.setChecked(true);}
        else
        {underConstruction.setChecked(true);}
        Toast.makeText(this, DPM.getProjectState(), Toast.LENGTH_SHORT).show();


        PartnersAgesFrom = findViewById(R.id.PartnersAgesFrom);
        PartnersAgesFrom.setText(Integer.toString(DPM.getPartnersAgeFrom()));

        PartnersAgesTo = findViewById(R.id.PartnersAgesTo);
        PartnersAgesTo.setText(Integer.toString(DPM.getPartnersAgeTo()));

        publisherFinancialContribution = findViewById(R.id.publisherFinancialContribution);
        publisherFinancialContribution.setText(DPM.getPublisherFinancialContribution());

        publisherFieldOfExperience = findViewById(R.id.publisherFieldOfExperience);
        publisherFieldOfExperience.setText(DPM.getPublisherTechnichalExperience());

        publisherYearsOfExperience = findViewById(R.id.publisherYearsOfExperience);
        publisherYearsOfExperience.setText(DPM.getPublisherYearsOfExperience());

        publisherExperienceDescription = findViewById(R.id.publisherExperienceDescription);
        publisherExperienceDescription.setText(DPM.getDescriptionOfPublisherExperience());

        projectPlace = findViewById(R.id.projectPlace);

        MoneyToBuild = findViewById(R.id.MoneyToBuild);

        technicalExperience = findViewById(R.id.technicalExperience);

        publisherContributionLayout = findViewById(R.id.publisherContributionLayout);

        projectFinancingLayout = findViewById(R.id.projectFinancingLayout);

        technicalExperienceLayout = findViewById(R.id.technicalExperienceLayout);

        postOwnerPhoto = findViewById(R.id.postOwnerPhoto);
        Log.d(TAG, "initialize: "+publisher.getImageURL());
        try {
            StorageReference storageReference;
            storageReference = FirebaseStorage.getInstance().getReferenceFromUrl(publisher.getImageURL());
            storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    Picasso.get().load(uri).into(postOwnerPhoto);
                }
            });
        }catch (Exception e){
            postOwnerPhoto.setImageDrawable(getResources().getDrawable(R.drawable.photo));
        }
        ToPartnerForm = findViewById(R.id.ToPartnerForm);
        Log.d(TAG, "adapter: "+adapter);
        try {
            if (adapter.equals("requestAdapter"))
            {
                ToPartnerForm.setVisibility(View.GONE);
            }
        }
        catch (Exception e){
        }

        noParticipation = findViewById(R.id.noParticipation);

        if (DPM.isProjectPlaceState()){
            yes = findViewById(R.id.yes);
            yes.setChecked(true);
            projectPlace.setVisibility(View.VISIBLE);

            noParticipation.setVisibility(View.GONE);

            ProjectPlaceCountry = findViewById(R.id.ProjectPlaceCountry);
            ProjectPlaceCountry.setText(DPM.getProjectCountry());

            ProjectPlaceTeratory = findViewById(R.id.ProjectPlaceGovernorate);
            ProjectPlaceTeratory.setText(DPM.getProjectGovernorate());

            ProjectPlaceNegotiationYes = findViewById(R.id.ProjectPlaceNegotiationYes);
            ProjectPlaceNegotiationNo = findViewById(R.id.ProjectPlaceNegotiationNo);

            if(DPM.getProjectPlaceNegotiation().equals("مكان المشروع المقترح : نهائي لا يقبل التعديل"))
            {ProjectPlaceNegotiationNo.setChecked(true);}
            else
            {ProjectPlaceNegotiationYes.setChecked(true);}

            publisherContributionLayout.setVisibility(View.VISIBLE);

            projectPlaceLayout = findViewById(R.id.projectPlaceLayout);
            projectPlaceLayout.setVisibility(View.VISIBLE);

            yesIOwnProjectPlace = findViewById(R.id.yesIOwnProjectPlace);
            noIDoNotOwnProjectPlace = findViewById(R.id.noIDoNotOwnProjectPlace);

            if (DPM.isiOwnTheProjectPlace()) {

                apartmentState = findViewById(R.id.apartmentState);
                apartmentState.setVisibility(View.VISIBLE);

                yesIOwnProjectPlace.setChecked(true);
                owned = findViewById(R.id.owned);
                notOwned = findViewById(R.id.notOwned);
                if (DPM.isPlaceOwnershipStatus()){
                    owned.setChecked(true);
                }
                else{
                    notOwned.setChecked(true);
                    apartment = findViewById(R.id.apartment);
                    apartment.setVisibility(View.VISIBLE);
                    monthlyRent = findViewById(R.id.monthlyRent);
                    monthlyRent.setText(DPM.getMonthlyRent());
                    monthlyRentCount = findViewById(R.id.monthlyRentCount);
                }
                projectPlaceDescription = findViewById(R.id.projectPlaceDescription);
                projectPlaceDescription.setText(DPM.getProjectPlaceDescription());
            }
            else{
                noIDoNotOwnProjectPlace.setChecked(true);
            }

        }
        else{
            no = findViewById(R.id.no);
            no.setChecked(true);
        }

        if (DPM.isProjectFinancingState()){

            noParticipation.setVisibility(View.GONE);

            MoneyToBuildYes = findViewById(R.id.MoneyToBuildYes);
            MoneyToBuildYes.setChecked(true);
            MoneyToBuild.setVisibility(View.VISIBLE);

            ProjectCost = findViewById(R.id.ProjectCost);
            ProjectCost.setText(DPM.getProjectCost());

            PowerCost = findViewById(R.id.PowerCost);
            PowerCost.setText(DPM.getOperatingCost());

            theExpectedProfitFrom = findViewById(R.id.theExpectedProfitFrom);
            theExpectedProfitFrom.setText(DPM.getTheExpectedProfitFrom());

            theExpectedProfitTo = findViewById(R.id.theExpectedProfitTo);
            theExpectedProfitTo.setText(DPM.getTheExpectedProfitTo());

            publisherContributionLayout.setVisibility(View.VISIBLE);

            projectFinancingLayout.setVisibility(View.VISIBLE);
            publisherFinancialContribution.setText(DPM.getPublisherFinancialContribution());
        }
        else{
            MoneyToBuildNo = findViewById(R.id.MoneyToBuildNo);
            MoneyToBuildNo.setChecked(true);
        }

        if (DPM.isTechnicalExperienceState())
        {
            noParticipation.setVisibility(View.GONE);
            TechnicalExperienceYes = findViewById(R.id.TechnicalExperienceYes);
            TechnicalExperienceYes.setChecked(true);

            technicalExperience.setVisibility(View.VISIBLE);

            yearsOfExperience = findViewById(R.id.yearsOfExperience);
            yearsOfExperience.setVisibility(View.VISIBLE);
            yearsOfExperience.setText(DPM.getYearsOfExperience());

            Years = findViewById(R.id.years);
            Years.setVisibility(View.VISIBLE);
            if (DPM.getYearsOfExperience().equals(1)){
            Years.setText(" سنة ");
            yearsOfExperience.setVisibility(View.GONE);
            }
            else if (DPM.getYearsOfExperience().equals(2)){
            Years.setText(" سنتان ");
            yearsOfExperience.setVisibility(View.GONE);
            }
            else{
                Years.setText(" سنوات ");
            }

            fieldOfExperience = findViewById(R.id.fieldOfExperience);
            fieldOfExperience.setText(DPM.getFieldOfExperience());

            descriptionOfTechnicalExperience = findViewById(R.id.descriptionOfTechnicalExperience);
            descriptionOfTechnicalExperience.setText(DPM.getDescriptionOfTechnicalExperience());

            publisherContributionLayout.setVisibility(View.VISIBLE);

            technicalExperienceLayout.setVisibility(View.VISIBLE);
            publisherFieldOfExperience.setText(DPM.getPublisherTechnichalExperience());
            publisherYearsOfExperience.setText(DPM.getPublisherYearsOfExperience());
            publisherExperienceDescription.setText(DPM.getDescriptionOfPublisherExperience());
        }
        else{
            TechnicalExperienceNo = findViewById(R.id.TechnicalExperienceNo);
            TechnicalExperienceNo.setChecked(true);
        }
//        changeColor(mainLayout);
    }

    private void changeColor(LinearLayout l) {
        View v = null;
        for (int i=1;i<l.getChildCount();i++) {
            v = l.getChildAt(i);
            v.setPadding(5,5,5,5);
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
            }
        }
    }

    void nextPage(){

        if ((DPM.getPublisherID()).equals(firebaseUser.getUid())){
            Toast.makeText(this,"لا يمكن الاشتراك في مشروعك", Toast.LENGTH_SHORT).show();
        }
        else {
//            progressDialog.setMessage("الرجاء الانتظار");
//            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//            progressDialog.setIndeterminate(true);
//            progressDialog.show();
            userdata();
//            progressDialog.dismiss();

        }
    }

    private void userdata(){
        mReference = FirebaseDatabase.getInstance().getReference("users").child(firebaseUser.getUid());
        mReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                user = dataSnapshot.getValue(User.class);
                if (TextUtils.isEmpty(user.getFirstName())|| TextUtils.isEmpty(user.getLastName())||
                        TextUtils.isEmpty(user.getCountry())|| TextUtils.isEmpty(user.getTeratory())||
                        TextUtils.isEmpty(user.getPhoneNumber())|| TextUtils.isEmpty(user.getEmail())||
                        TextUtils.isEmpty(user.getGender())|| TextUtils.isEmpty(user.getDay())||
                        TextUtils.isEmpty(user.getMonth())|| TextUtils.isEmpty(user.getYear())){
                    Intent intent = new Intent(PostDetailsActivity.this, FullYourData.class);
                    intent.putExtra("user",user);
                    startActivity(intent);
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
//                    progressDialog.dismiss();
                }
                else{
                    Intent intent = new Intent(PostDetailsActivity.this, PartnerFormActivity.class);
                    intent.putExtra("data", DPM);
                    startActivity(intent);
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
//                    progressDialog.dismiss();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}

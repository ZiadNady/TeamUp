package com.aboelnour.teamup.ui.myPosts;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.aboelnour.teamup.R;
import com.aboelnour.teamup.module.DataPostModel;
import com.aboelnour.teamup.module.PartnersForm;
import com.aboelnour.teamup.module.User;
import com.aboelnour.teamup.module.functions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class PartnerDetailActivity extends AppCompatActivity {

    private static final String TAG = "PartnerDetailActivity";
    private Bundle bundle;
    private PartnersForm form;
    private DataPostModel model;
    private User user;
    private CircleImageView partnerPhoto;
    private TextView partnerName, partnerCountry, partnerTeratory, PlaceState, RentPrice, MonthlyRent, PlaceDescription, FinancialFinancing,
            FieldOfExperience, YearsOfExperience, ExperienceDescription, neededExperience, PhoneNumber, age, gender;
    private LinearLayout place, financing, experience, formLayout, mainLayout;
    private ImageButton accept, refuse, backArraw;
    private DatabaseReference databaseReference ;
    private FirebaseFirestore db;
    private int position;
    private String prevPage;
    private FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.partner_details);

        initialize();
        accept();
        refuse();
        backArraw();

    }

    private void initialize() {

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        bundle = getIntent().getExtras();

        user = bundle.getParcelable("user");

        form = bundle.getParcelable("data");

        model = bundle.getParcelable("model");

        prevPage = bundle.getString("state");

        position = bundle.getInt("position");

        partnerPhoto = findViewById(R.id.partnerPhoto);
        Picasso.get().load(user.getImageURL()).into(partnerPhoto);

        partnerName = findViewById(R.id.partnerName);
        partnerName.setText(user.getFirstName()+" "+user.getLastName());

        partnerCountry = findViewById(R.id.partnerCountry);
        partnerCountry.setText(user.getCountry());

        partnerTeratory = findViewById(R.id.partnerTeratory);
        partnerTeratory.setText(user.getTeratory());

        PlaceState = findViewById(R.id.PlaceState);

        RentPrice = findViewById(R.id.RentPrice);

        PlaceDescription = findViewById(R.id.PlaceDescription);

        FinancialFinancing = findViewById(R.id.FinancialFinancing);

        FieldOfExperience = findViewById(R.id.publisherFieldOfExperience);

        YearsOfExperience = findViewById(R.id.YearsOfExperience);

        ExperienceDescription = findViewById(R.id.ExperienceDescription);

        place = findViewById(R.id.place);

        financing = findViewById(R.id.financing);

        experience = findViewById(R.id.experience);

        formLayout = findViewById(R.id.form);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("userPosts").child(model.getPublisherID()).child("posts").child(model.getPostID());

        db = FirebaseFirestore.getInstance();

        accept = findViewById(R.id.accept);

        if(prevPage.equals("accept"))
        {
            accept.setVisibility(View.GONE);
        }

        refuse = findViewById(R.id.refuse);

        backArraw = findViewById(R.id.backArrow);

        PhoneNumber = findViewById(R.id.PhoneNumber);
        PhoneNumber.setText(user.getPhoneNumber());


        age = findViewById(R.id.age);
        age.setText(user.getYear() + "/" + user.getMonth() + "/" + user.getDay());


        gender = findViewById(R.id.gender);
        gender.setText(user.getGender());

        if (model.isProjectPlaceState()){
//          PlaceText.setVisibility(View.GONE);
            formLayout.setVisibility(View.VISIBLE);
            place.setVisibility(View.VISIBLE);
            RentPrice.setText(form.getMonthlyRent());
            PlaceDescription.setText(form.getProjectPlaceDescription());

        }

        if(model.getProjectPlaceNegotiation().equals("مكان المشروع المقترح نهائي لا يقبل التعديل")){
            place.setVisibility(View.GONE);
            formLayout.setVisibility(View.GONE);
        }

        if(model.isProjectFinancingState()){
            formLayout.setVisibility(View.VISIBLE);
            financing.setVisibility(View.VISIBLE);
            FinancialFinancing.setText(form.getFinancialFinancing());

        }

        if (model.isTechnicalExperienceState()){
            formLayout.setVisibility(View.VISIBLE);
            experience.setVisibility(View.VISIBLE);
            ExperienceDescription.setText(form.getExperienceDescription());
            FieldOfExperience.setText(form.getFieldOfExperience());
            YearsOfExperience.setText(form.getYearsOfExperience());
        }

        mainLayout = findViewById(R.id.mainLayout);
        functions func = new functions();
        func.changeColor(mainLayout,this);

    }

    private void accept(){
        accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                View popupView = LayoutInflater.from(PartnerDetailActivity.this).inflate(R.layout.remove_post_popup_window_layout, null);
                final PopupWindow popupWindow = new PopupWindow(popupView, WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
                Button btnDismiss = popupView.findViewById(R.id.closePopupBtn);
                Button accept = popupView.findViewById(R.id.remove);
                popupWindow.showAtLocation(popupView, Gravity.CENTER,0,0);
                btnDismiss.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popupWindow.dismiss();
                    }
                });

                accept.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        DatabaseReference mRef = FirebaseDatabase.getInstance().getReference();
                        mRef.child("userRequests").child(form.getPartnerId()).child("posts").child(model.getPostID()).removeValue();
                        mRef.child("userRequests").child(form.getPartnerId()).child("posts").child("accept").child(model.getPostID()).setValue(model);
                        databaseReference.child("Partners").child(form.getPartnerId()).removeValue();
                        databaseReference.child("accepted").child(form.getPartnerId()).setValue(form);
                        db.collection("Posts").document(model.getPostID()).update("numberOfAcceptedPartners", model.getNumberOfAcceptedPartners() + 1);
//                        mRef.child("Posts").child(dataPostModel.getPostID()).child("numberOfAcceptedPartners").setValue(dataPostModel.getNumberOfAcceptedPartners()+1);
                        mRef.child("userPosts").child(model.getPublisherID()).child("posts").child(model.getPostID()).child("numberOfAcceptedPartners").setValue(model.getNumberOfAcceptedPartners()+1);
                        popupWindow.dismiss();
                        Intent intent = new Intent();
                        intent.putExtra("position",position);
                        setResult(RESULT_FIRST_USER,intent);
                        finish();
                    }
                });
            }
        });
    }

    private void refuse(){
        refuse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                View popupView = LayoutInflater.from(PartnerDetailActivity.this).inflate(R.layout.remove_post_popup_window_layout, null);
                final PopupWindow popupWindow = new PopupWindow(popupView, WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
                Button btnDismiss = popupView.findViewById(R.id.closePopupBtn);
                Button refuse = popupView.findViewById(R.id.remove);
                popupWindow.showAtLocation(popupView, Gravity.CENTER,0,0);
                btnDismiss.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popupWindow.dismiss();
                    }
                });

                refuse.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DatabaseReference mRef = FirebaseDatabase.getInstance().getReference();
                        if(prevPage.equals("accept")){
                            mRef.child("userPosts").child(firebaseUser.getUid()).child("posts").child(model.getPostID()).child("accepted").child(form.getPartnerId()).removeValue();
//                databaseReference.child("Partners").child(data.getPartnerId()).removeValue();
                            db.collection("Posts").document(model.getPostID()).update("numberOfAcceptedPartners", model.getNumberOfAcceptedPartners() - 1);
//                mRef.child("Posts").child(dataPostModel.getPostID()).child("numberOfAcceptedPartners").setValue(dataPostModel.getNumberOfAcceptedPartners() - 1);
                            mRef.child("userRequests").child(form.getPartnerId()).child("posts").child("accept").child(model.getPostID()).removeValue();
                            mRef.child("userPosts").child(model.getPublisherID()).child("posts").child(model.getPostID()).child("numberOfAcceptedPartners").setValue(model.getNumberOfAcceptedPartners() - 1);
                        }
                        else{
                            mRef.child("userRequests").child(form.getPartnerId()).child("posts").child("request").child(model.getPostID()).removeValue();
                            databaseReference.child("Partners").child(form.getPartnerId()).removeValue();
                        }
                        popupWindow.dismiss();
                        Intent intent = new Intent();
                        intent.putExtra("position",position);
                        setResult(RESULT_FIRST_USER,intent);
                        finish();
                    }
                });
            }
        });
    }

    private void backArraw(){
        backArraw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }


}

package com.aboelnour.teamup.ui.add_your_project;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.aboelnour.teamup.FullYourData;
import com.aboelnour.teamup.R;
import com.aboelnour.teamup.module.DataPostModel;
import com.aboelnour.teamup.module.User;
import com.aboelnour.teamup.module.functions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.TimeZone;

public class AddYourProjectFragment extends Fragment {

    private static final String TAG = "AddYourProjectFragment";
    private EditText numOfPartners, partnersAgesFrom, partnersAgesTo;
    private Spinner partnersCountry, partnersGovernorate, projectStartMonth, projectStartYear;
    private String Gender="النوعين", Education="لا يهم المستوي التعليمي", projectState="قيد الإنشاء";
    private RadioGroup partnersGender, partnersEducation, ProjectState;
    private DatabaseReference mReference;
    private FirebaseUser firebaseUser;
    private User user;
    private ProgressDialog progressDialog;
    private LinearLayout mainLayout;
    Button button;
    private String[] countries;
    private String[] Governorates;
    private LinearLayout mianLayout;
    private String[] Year = new String[100]  ,Month = {"1","2","3","4","5","6","7","8","9","10","11","12"};

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.content_project_page1, container, false);

        {
            firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
            progressDialog = new ProgressDialog(view.getContext());
            progressDialog.setMessage("الرجاء الانتظار");
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setIndeterminate(true);
            progressDialog.show();
            userdata();
            progressDialog.dismiss();
            numOfPartners = view.findViewById(R.id.numOfPartners);
            partnersCountry = view.findViewById(R.id.partnersCountry);
            partnersGovernorate = view.findViewById(R.id.partnersGovernorate);

            placeData("Country.txt");
            projectStartMonth = view.findViewById(R.id.projectStartMonth);
            projectStartYear = view.findViewById(R.id.projectStartYear);
            partnersAgesFrom = view.findViewById(R.id.partnersAgesFrom);
            partnersAgesTo = view.findViewById(R.id.partnersAgesTo);
            button = view.findViewById(R.id.Page1Button);
            partnersGender = view.findViewById(R.id.partnersGender);
            partnersEducation = view.findViewById(R.id.partnersEducation);
            ProjectState = view.findViewById(R.id.ProjectState);

            mianLayout = view.findViewById(R.id.mainLayout);

        }

        fillYear();

        fillMonth();

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item,countries);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        partnersCountry.setAdapter(adapter);

        partnersCountry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(!TextUtils.isEmpty(partnersCountry.getSelectedItem().toString())) {
                    String basic = "اختبار.txt";
                    String teratory = partnersCountry.getSelectedItem().toString();
                    String b = basic.replace("اختبار",teratory);
                    placeData(b);
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item, Governorates);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    partnersGovernorate.setAdapter(adapter);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userdata();
                if(numOfPartners.getText().toString().equals(0)|| TextUtils.isEmpty(numOfPartners.getText().toString())){
                    Toast.makeText(getActivity(),"الرجاء إدخال عدد الشركاء .", Toast.LENGTH_SHORT).show();
                }
                else if (TextUtils.isEmpty(partnersAgesTo.getText().toString())|| TextUtils.isEmpty(partnersAgesFrom.getText().toString())){
                    Toast.makeText(getActivity(), "برجاء إدخال اعمار المشاركين من و إلي", Toast.LENGTH_SHORT).show();
                }
                else if (Integer.parseInt(partnersAgesTo.getText().toString())< Integer.parseInt(partnersAgesFrom.getText().toString())){
                    Toast.makeText(getActivity(), "خطأ في اعمار المشاركين", Toast.LENGTH_SHORT).show();
                }
                else if(TextUtils.isEmpty(partnersCountry.getSelectedItem().toString())||
                        TextUtils.isEmpty(partnersGovernorate.getSelectedItem().toString())||
                        TextUtils.isEmpty(projectStartMonth.getSelectedItem().toString())||
                        TextUtils.isEmpty(projectStartYear.getSelectedItem().toString())){
                    Toast.makeText(getActivity(),"الرجاء إكمال كل الخانات .", Toast.LENGTH_SHORT).show();
                }
                else{
                    DataPostModel dataPostModel = new DataPostModel(toInt(numOfPartners),toInt2(projectStartMonth),toInt2(projectStartYear),toInt(partnersAgesFrom),toInt(partnersAgesTo),Gender,toString1(partnersCountry)
                            ,toString1(partnersGovernorate),Education,projectState);
                    Intent intent = new Intent(getActivity(), ProjectPage2Activity.class);
                    intent.putExtra("P1Data",dataPostModel);
                    Toast.makeText(getActivity(), dataPostModel.getPartnerGender(), Toast.LENGTH_SHORT).show();
                    startActivity(intent);
                }
            }
        });

        partnersGender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb= view.findViewById(checkedId);
                // Check which radio button was clicked
                if(rb.getText().toString().equals("ذكر")){
                    Gender = "ذكر";
                }
                else if(rb.getText().toString().equals("انثي")){
                    Gender = "انثي";
                }
                else{
                    Gender = "النوعين";
                }
            }
        });

        partnersEducation.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb= view.findViewById(checkedId);
                // Check which radio button was clicked
                if(rb.getText().toString().equals("تحت المتوسط")){
                    Education = "تحت المتوسط";
                }
                else if(rb.getText().toString().equals("متوسط")){
                    Education = "متوسط";
                }
                else if(rb.getText().toString().equals("عالي")){
                    Education = "عالي";
                }
                else{
                    Education = "لا يهم المستوي التعليمي";
                }
            }
        });

        ProjectState.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb= view.findViewById(checkedId);
                // Check which radio button was clicked
                if(rb.getText().toString().equals("قائم بالفعل")){
                    projectState = "قائم بالفعل";
                }
                else if(rb.getText().toString().equals("قيد الإنشاء")){
                    projectState = "قيد الإنشاء";
                }
            }
        });
        return view;
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
                    Intent intent = new Intent(getActivity(), FullYourData.class);
                    intent.putExtra("user",user);
                    startActivity(intent);
                    progressDialog.dismiss();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void placeData(String place)
    {
        String text = "";
        try {
            InputStream is = getActivity().getAssets().open(place);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            text = new String(buffer);
        }
        catch (IOException e){

        }
        if (place=="Country.txt")
            countries  = text.split("  ");
        else{
            Governorates = text.split("  ");
        }
    }

    private int toInt(EditText x){
        int z = Integer.parseInt(x.getText().toString());
        return z;
    }
    private int toInt2(Spinner x){
        int z = Integer.parseInt(x.getSelectedItem().toString());
        return z;
    }
    private String toString1(Spinner x){
        String z = x.getSelectedItem().toString();
        return z;
    }

    private void fillYear(){

        Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
        int Year = calendar.get(calendar.YEAR);

        for ( int i = 0 ; i<100; i++)
        {
            this.Year[i] = String.valueOf(Year);
            Year++;
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item,this.Year);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        projectStartYear.setAdapter(adapter);
    }

    private void fillMonth(){
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_spinner_item,Month);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        projectStartMonth.setAdapter(adapter);
    }
}
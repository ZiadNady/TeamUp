package com.aboelnour.teamup.ui.add_your_project;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.aboelnour.teamup.R;
import com.aboelnour.teamup.module.DataPostModel;

import java.io.IOException;
import java.io.InputStream;


public class ProjectPage2Activity extends AppCompatActivity {

    private static final String TAG = "ProjectPage2Activity";
    Intent intent;
    Spinner projectCategory,projectCurrency,projectCountry,projectGovernorate,fieldOfExperience;
    EditText projectName,projectDescription,projectCost,operatingCost,theExpectedProfitFrom,theExpectedProfitTo,yearsOfExperience, descriptionOfTechnicalExperience;
    RadioGroup projectPlaceState,projectPlaceNegotiation,projectFinancingState,technicalExperienceState;
    LinearLayout ProjectPlace,MoneyToBuild,TechnicalExperience,mainLayout;
    Button next,prev;
    private String[] countries;
    private String[] Teratorys;
    private String[] Currency;
    DataPostModel P1Data;
    String ProjectCountry = "", ProjectGovernorate = "", ProjectPlaceNegotiation = "", ProjectCost = "", OperatingCost = "", TheExpectedProfitFrom = "",
            TheExpectedProfitTo = "", YearsOfExperience = "", FieldOfExperience = "", DescriptionOfTechnicalExperience = "";
    String[] categorys = {"صناعي","مشتريات", "سياحي", "نقل و مواصلات", "حيواني", "طبي", "هندسي", "تعليمي", "تجاري", "زراعي","إداري","استيراد","تصدير","اعمال خيرية"};
    String[] special = { "صناعي","مشتريات", "سياحي", "نقل و مواصلات", "حيواني", "طبي", "هندسي", "تعليمي", "تجاري", "زراعي","إداري","استيراد","تصدير"};

    Boolean ProjectPlaceState = false, ProjectFinancingState = false, TecnicalExperienceState = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_project_page2);

        intent = getIntent();
        P1Data = getIntent().getExtras().getParcelable("P1Data");
        projectCategory = findViewById(R.id.projectCategory);
        projectCurrency = findViewById(R.id.projectCurrency);
        projectCountry = findViewById(R.id.projectCountry);
        projectGovernorate = findViewById(R.id.projectGovernorate);
        fieldOfExperience = findViewById(R.id.publisherFieldOfExperience);
        projectName = findViewById(R.id.projectName);
        projectDescription = findViewById(R.id.projectDescription);
        projectCost = findViewById(R.id.projectCost);
        operatingCost = findViewById(R.id.operatingCost);
        theExpectedProfitFrom = findViewById(R.id.theExpectedProfitFrom);
        theExpectedProfitTo = findViewById(R.id.theExpectedProfitTo);
        yearsOfExperience = findViewById(R.id.yearsOfExperience);
        descriptionOfTechnicalExperience = findViewById(R.id.DescriptionOfTechnicalExperience);
        projectPlaceState = findViewById(R.id.projectPlaceState);
        projectPlaceNegotiation = findViewById(R.id.projectPlaceNegotiation);
        projectFinancingState = findViewById(R.id.projectFinancingState);
        technicalExperienceState = findViewById(R.id.technicalExperienceState);
        ProjectPlace = findViewById(R.id.ProjectPlace);
        MoneyToBuild = findViewById(R.id.MoneyToBuild);
        TechnicalExperience = findViewById(R.id.TechnicalExperience);
        next = findViewById(R.id.next);
        prev = findViewById(R.id.prev);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean finish = false, two = false, three = false, four = false;

                while (!finish){
                    if ((TextUtils.isEmpty(projectCategory.getSelectedItem().toString()) || TextUtils.isEmpty(projectName.getText().toString()) ||
                            TextUtils.isEmpty(projectCurrency.getSelectedItem().toString()) || TextUtils.isEmpty(projectDescription.getText().toString()))) {
                        Toast.makeText(ProjectPage2Activity.this, "الرجاء إكمال كل الخانات .", Toast.LENGTH_SHORT).show();
                        break;
                    }
                    else if (ProjectPlaceState && !two) {
                        Log.d(TAG, "onClick: ");
                        if (TextUtils.isEmpty(projectCountry.getSelectedItem().toString()) || TextUtils.isEmpty(projectGovernorate.getSelectedItem().toString())) {
                            Toast.makeText(ProjectPage2Activity.this, "الرجاء إكمال كل الخانات .", Toast.LENGTH_SHORT).show();
                            break;
                        } else {
                            ProjectCountry = projectCountry.getSelectedItem().toString();
                            ProjectGovernorate = projectGovernorate.getSelectedItem().toString();
                            two = true;
                        }
                    }
                    else if (ProjectFinancingState && !three) {
                        if (TextUtils.isEmpty(projectCost.getText().toString()) || TextUtils.isEmpty(operatingCost.getText().toString())
                                || TextUtils.isEmpty(theExpectedProfitFrom.getText().toString()) || TextUtils.isEmpty(theExpectedProfitTo.getText().toString())) {
                            Toast.makeText(ProjectPage2Activity.this, "الرجاء إكمال كل الخانات .", Toast.LENGTH_SHORT).show();
                            break;
                        } else {
                            ProjectCost = projectCost.getText().toString();
                            OperatingCost = operatingCost.getText().toString();
                            TheExpectedProfitFrom = theExpectedProfitFrom.getText().toString();
                            TheExpectedProfitTo = theExpectedProfitTo.getText().toString();
                            three = true;
                        }
                    }
                    else if (TecnicalExperienceState && !four) {
                        if (TextUtils.isEmpty(yearsOfExperience.getText().toString()) || TextUtils.isEmpty(fieldOfExperience.getSelectedItem().toString())
                                || TextUtils.isEmpty(descriptionOfTechnicalExperience.getText().toString())) {
                            Toast.makeText(ProjectPage2Activity.this, "الرجاء إكمال كل الخانات .", Toast.LENGTH_SHORT).show();
                            break;
                        } else {
                            YearsOfExperience = yearsOfExperience.getText().toString();
                            FieldOfExperience = fieldOfExperience.getSelectedItem().toString();
                            DescriptionOfTechnicalExperience = descriptionOfTechnicalExperience.getText().toString();
                            four = true;
                        }
                    }
                    else{
                        finish = true;
                    }
                }
                if(finish){
                    DataPostModel P2Data = new DataPostModel(projectCategory.getSelectedItem().toString(), projectName.getText().toString(),
                            projectCurrency.getSelectedItem().toString(), projectDescription.getText().toString(), ProjectCountry, ProjectGovernorate,
                            ProjectCost, OperatingCost, TheExpectedProfitFrom, TheExpectedProfitTo, YearsOfExperience, FieldOfExperience, DescriptionOfTechnicalExperience,
                            ProjectPlaceState, ProjectPlaceNegotiation, ProjectFinancingState, TecnicalExperienceState);
                    Intent intent = new Intent(ProjectPage2Activity.this, ProjectPage3Activity.class);
                    intent.putExtra("P1Data",P1Data);
                    intent.putExtra("P2Data",P2Data);
                    startActivity(intent);
                }
            }
        });

        prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        place();
        RadioGroups();

        setSpinner(categorys, projectCategory);
        setSpinner(special, fieldOfExperience);
        placeData("currency.txt", projectCurrency, Currency);

        mainLayout = findViewById(R.id.mainLayout);
        changeColor(mainLayout);

    }

    private void RadioGroups(){
        projectPlaceState.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()

        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb= findViewById(checkedId);
                // Check which radio button was clicked
                if(rb.getTag().equals("yes")){
                    ProjectPlace.setVisibility(View.VISIBLE);
                    placeData("Country.txt",projectCountry,countries);
                    ProjectPlaceState = true;
                    ProjectPlaceNegotiation = "مكان المشروع المقترح : يقبل المناقشة عند اكتمال العدد";
                }
                else{
                    ProjectPlace.setVisibility(View.GONE);
                    ProjectPlaceState = false; }
            }
        });

        projectPlaceNegotiation.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = findViewById(checkedId);
                if (rb.getTag().equals("no")){
                    ProjectPlaceNegotiation = "مكان المشروع المقترح : نهائي لا يقبل التعديل";
                }
                else{
                    ProjectPlaceNegotiation = "مكان المشروع المقترح : يقبل المناقشة عند اكتمال العدد";
                }
            }
        });

        projectFinancingState.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = findViewById(checkedId);
                if (rb.getTag().equals("yes")){
                    MoneyToBuild.setVisibility(View.VISIBLE);
                    ProjectFinancingState = true;
                }
                else{
                    MoneyToBuild.setVisibility(View.GONE);
                    ProjectFinancingState = false;
                }
            }
        });

        technicalExperienceState.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = findViewById(checkedId);
                if(rb.getTag().equals("yes")){
                    TechnicalExperience.setVisibility(View.VISIBLE);
                    TecnicalExperienceState = true;
                }
                else{
                    TechnicalExperience.setVisibility(View.GONE);
                    TecnicalExperienceState = false;
                }
            }
        });
    }

    private int toInt(EditText x) {
        return Integer.parseInt(x.getText().toString());
    }

    private int toInt2(Spinner x) {
        return Integer.parseInt(x.getSelectedItem().toString());
    }

    private String toString1(Spinner x) {
        return x.getSelectedItem().toString();
    }

    private void placeData(String place, Spinner spinner, String[] strings) {
        String text = "";
        try {
            InputStream is = getAssets().open(place);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            text = new String(buffer);
        } catch (IOException ignored) {

        }
        strings = text.split("  ");
        setSpinner(strings, spinner);
    }

    private void setSpinner(String[] strings, Spinner spinner) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(ProjectPage2Activity.this, android.R.layout.simple_spinner_item, strings);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }

    private void place(){

        projectCountry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(!TextUtils.isEmpty(projectCountry.getSelectedItem().toString())) {
                    String basic = "اختبار.txt";
                    String teratory = projectCountry.getSelectedItem().toString();
                    String b = basic.replace("اختبار",teratory);
                    placeData(b,projectGovernorate,Teratorys);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
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
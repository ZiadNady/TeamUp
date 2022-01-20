package com.aboelnour.teamup.ui.search;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.aboelnour.teamup.R;
import com.aboelnour.teamup.module.Search;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks;
import com.google.firebase.dynamiclinks.PendingDynamicLinkData;

import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.TimeZone;

public class SearchActivity extends AppCompatActivity {

    private static final String TAG = "SearchActivity";
    Spinner searchCategory, searchProjectPlaceCountry, SearchProjectPlaceTeratory, searchProjectPublishMonth, searchProjectPublishYear, searchProjectStartMonth,
            searchProjectStartYear, searchCurrency;
    EditText searchProjectName, searchPartnersNumFrom, searchPartnersNumTo, searchPartnersAgeFrom, searchPartnersAgeTo, searchPartnersPlaceCountry, searchPartnersPlaceTeratory, searchProjectCostFrom, searchProjectCostTo,
            searchTechnicalExperienceFrom, searchTechnicalExperienceTo, expectedProfitFrom, expectedProfitTo;
    RadioGroup searchPartnersEducation, searchGender;
    Button searchButton;
    String[] categorys = {"صناعي","مشتريات", "سياحي", "نقل و مواصلات", "حيواني", "طبي", "هندسي", "تعليمي", "تجاري", "زراعي","إداري","استيراد","تصدير","اعمال خيرية"};
    String Education, Gender;
    private String[] countries;
    private String[] Teratorys;
    private String[] Year = new String[101], Month = {" ","1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"};
    private String[] Currency;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
//        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);



        initialize();

        noZero(searchPartnersNumFrom);
        noZero(searchPartnersNumTo);
        noZero(searchPartnersAgeFrom);
        noZero(searchPartnersAgeTo);
        noZero(searchProjectCostFrom);
        noZero(searchProjectCostTo);
        noZero(searchTechnicalExperienceFrom);
        noZero(searchTechnicalExperienceTo);
        noZero(expectedProfitFrom);
        noZero(expectedProfitTo);

        placeData("currency.txt", searchCurrency, Currency);

        placeData("Country.txt");

        fillYear();

        fillMonth();

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, countries);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        searchProjectPlaceCountry.setAdapter(adapter);

        searchProjectPlaceCountry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(!TextUtils.isEmpty(searchProjectPlaceCountry.getSelectedItem().toString())) {
                    String basic = "اختبار.txt";
                    String teratory = searchProjectPlaceCountry.getSelectedItem().toString();
                    String b = basic.replace("اختبار",teratory);
                    placeData(b);
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(SearchActivity.this,android.R.layout.simple_spinner_item,Teratorys);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    SearchProjectPlaceTeratory.setAdapter(adapter);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ArrayAdapter<String> aa = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categorys);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        searchCategory.setAdapter(aa);

        searchPartnersEducation.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = findViewById(checkedId);
                // Check which radio button was clicked
                if (rb.getText().toString().equals("تحت المتوسط")) {
                    Education = "تحت المتوسط";
                } else if (rb.getText().toString().equals("متوسط")) {
                    Education = "متوسط";
                } else if (rb.getText().toString().equals("عالي")) {
                    Education = "عالي";
                } else {
                    Education = "لا يهم المستوي التعليمي";
                }
            }
        });

        searchGender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = findViewById(checkedId);
                // Check which radio button was clicked
                if (rb.getText().toString().equals("ذكر")) {
                    Gender = "ذكر";
                } else if (rb.getText().toString().equals("انثي")) {
                    Gender = "انثي";
                } else {
                    Gender = "النوعين";
                }
            }
        });

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Search search = new Search(searchPartnersNumFrom.getText().toString(), searchPartnersNumTo.getText().toString()
                        , searchPartnersAgeFrom.getText().toString(), searchPartnersAgeTo.getText().toString()
                        , searchProjectCostFrom.getText().toString(), searchProjectCostTo.getText().toString()
                        , searchTechnicalExperienceFrom.getText().toString(), searchTechnicalExperienceTo.getText().toString()
                        , searchProjectPublishMonth.getSelectedItem().toString(), searchProjectPublishYear.getSelectedItem().toString()
                        , searchProjectStartMonth.getSelectedItem().toString(), searchProjectStartYear.getSelectedItem().toString()
                        , searchCategory.getSelectedItem().toString(), searchProjectName.getText().toString()
                        , searchProjectPlaceCountry.getSelectedItem().toString(), SearchProjectPlaceTeratory.getSelectedItem().toString()
                        , Gender, Education, expectedProfitFrom.getText().toString(), expectedProfitTo.getText().toString(),searchCurrency.getSelectedItem().toString());

                Intent intent = new Intent(SearchActivity.this, SearchResultActivity.class);
                intent.putExtra("search", search);
                startActivity(intent);
                finish();
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void noZero(EditText editText) {
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().length() == 1 && s.toString().startsWith("0")) {
                    s.clear();
                }
            }
        });
    }

    private void initialize() {

        searchCategory = findViewById(R.id.searchCategory);
        searchProjectName = findViewById(R.id.searchProjectName);
        searchPartnersNumFrom = findViewById(R.id.searchPartnersNumFrom);
        searchPartnersNumTo = findViewById(R.id.searchPartnersNumTo);
        searchPartnersAgeFrom = findViewById(R.id.searchPartnersAgeFrom);
        searchPartnersAgeTo = findViewById(R.id.searchPartnersAgeTo);
        searchProjectPlaceCountry = findViewById(R.id.searchProjectPlaceCountry);
        SearchProjectPlaceTeratory = findViewById(R.id.SearchProjectPlaceTeratory);

        //        searchPartnersPlaceCountry = (EditText)findViewById(R.id.searchPartnersPlaceCountry);
//        searchPartnersPlaceTeratory = (EditText)findViewById(R.id.searchPartnersPlaceTeratory);
        searchProjectCostFrom = findViewById(R.id.searchProjectCostFrom);
        searchProjectCostTo = findViewById(R.id.searchProjectCostTo);
        searchTechnicalExperienceFrom = findViewById(R.id.searchTechnicalExperienceFrom);
        searchTechnicalExperienceTo = findViewById(R.id.searchTechnicalExperienceTo);
        searchProjectPublishMonth = findViewById(R.id.searchProjectPublishMonth);
        searchProjectPublishYear = findViewById(R.id.searchProjectPublishYear);
        searchProjectStartMonth = findViewById(R.id.searchProjectStartMonth);
        searchProjectStartYear = findViewById(R.id.searchProjectStartYear);
        searchPartnersEducation = findViewById(R.id.searchPartnersEducation);
        searchGender = findViewById(R.id.searchGender);
        searchButton = findViewById(R.id.searchButton);
        expectedProfitFrom = findViewById(R.id.expectedProfitFrom);
        expectedProfitTo = findViewById(R.id.expectedProfitTo);
        searchCurrency = findViewById(R.id.currency);

    }

    private void placeData(String place) {
        String text = "";
        try {
            InputStream is = getAssets().open(place);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            text = new String(buffer);
        } catch (IOException e) {

        }
        if (place == "Country.txt")
            countries = text.split("  ");
        else {
            Teratorys = text.split("  ");
        }
    }

    private void fillYear() {

        Calendar calendar = Calendar.getInstance(TimeZone.getDefault());
        int Year = 2020;
        this.Year[0] = " ";
        for (int i = 1; i < 101; i++) {
            this.Year[i] = String.valueOf(Year);
            Year++;
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(SearchActivity.this, android.R.layout.simple_spinner_item, this.Year);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        searchProjectPublishYear.setAdapter(adapter);
        searchProjectStartYear.setAdapter(adapter);
    }

    private void fillMonth() {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(SearchActivity.this, android.R.layout.simple_spinner_item, Month);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        searchProjectPublishMonth.setAdapter(adapter);
        searchProjectStartMonth.setAdapter(adapter);

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
        ArrayAdapter<String> adapter = new ArrayAdapter<>(SearchActivity.this, android.R.layout.simple_spinner_item, strings);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
    }
}
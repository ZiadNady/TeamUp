package com.aboelnour.teamup;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.aboelnour.teamup.module.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;


public class FullYourData extends AppCompatActivity {

    Bundle bundle;
    final Calendar myCalendar = Calendar.getInstance();
    LinearLayout nameLayout,placeLayout,phoneLayout,emailLayout,genderLayout,birthdayLayout;
    boolean nameState = false,placeState = false,phonesSate = false,emailState = false,birthdayState = false;
    EditText firstName,lastName,email,phoneNumber;
    Spinner country,teratory;
    RadioGroup gender;
    TextView BirthDay;
    String userGender  = "ذكر" ,FirstName,LastName,Country,Teratory,Email,Day,Month,Year,PhoneNumber,ImageURI ="https://firebasestorage.googleapis.com/v0/b/aboelnour-e9c60.appspot.com/o/photo.png?alt=media&token=31495cba-4294-4059-8059-b3bf2dd3c00f";
    User user;
    DatePickerDialog.OnDateSetListener date;
    private String[] countries;
    private String[] Teratorys;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_your_data);
//        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        bundle = getIntent().getExtras();
        try {
            user = bundle.getParcelable("user");
        }
        catch (Exception e){
            user = new User();
        }


        nameLayout = findViewById(R.id.nameLayout);
        placeLayout = findViewById(R.id.placeLayout);
        phoneLayout = findViewById(R.id.phoneNumberLayout);
        emailLayout = findViewById(R.id.emailLayout);
        genderLayout = findViewById(R.id.genderLayout);
        birthdayLayout = findViewById(R.id.birthdayLayout);
        firstName = findViewById(R.id.firstName);
        lastName = findViewById(R.id.lastName);
        country = findViewById(R.id.country);
        teratory = findViewById(R.id.teratory);
        email = findViewById(R.id.email);
        gender = findViewById(R.id.gender);
        phoneNumber = findViewById(R.id.phoneNumber);
        BirthDay = findViewById(R.id.BirthDay);

        date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
                Day = Integer.toString(dayOfMonth);
                Month = Integer.toString(monthOfYear);
                Year = Integer.toString(year);
            }

        };
        placeData("Country.txt");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(FullYourData.this,android.R.layout.simple_spinner_item,countries);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        country.setAdapter(adapter);

        country.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(!TextUtils.isEmpty(country.getSelectedItem().toString())) {
                    String basic = "اختبار.txt";
                    String Teratory = country.getSelectedItem().toString();
                    String b = basic.replace("اختبار",Teratory);
                    placeData(b);
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(FullYourData.this,android.R.layout.simple_spinner_item,Teratorys);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    teratory.setAdapter(adapter);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        if(!TextUtils.isEmpty(user.getFirstName())&&!TextUtils.isEmpty(user.getLastName()))
        {
            nameLayout.setVisibility(View.GONE);
            FirstName = user.getFirstName();
            LastName = user.getLastName();
            nameState = true;
        }

        if (!TextUtils.isEmpty(user.getCountry())&&!TextUtils.isEmpty(user.getTeratory()))
        {
            placeLayout.setVisibility(View.GONE);
            Country = user.getCountry();
            Teratory = user.getTeratory();
            placeState = true;
        }

        if (!TextUtils.isEmpty(user.getEmail()))
        {
            emailLayout.setVisibility(View.GONE);
            Email = user.getEmail();
            emailState = true;
        }

        if(!TextUtils.isEmpty(user.getDay())&&!TextUtils.isEmpty(user.getMonth())&&!TextUtils.isEmpty(user.getYear())){
            birthdayLayout.setVisibility(View.GONE);
            Day = user.getDay();
            Month = user.getMonth();
            Year = user.getYear();
            birthdayState = true;
        }

        if (!TextUtils.isEmpty(user.getGender())){
            genderLayout.setVisibility(View.GONE);
            userGender = user.getGender();
         }

        if (!TextUtils.isEmpty(user.getPhoneNumber()))
        {
            phoneLayout.setVisibility(View.GONE);
            PhoneNumber = user.getPhoneNumber();
            phonesSate = true;
        }

        gender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb= findViewById(checkedId);
                // Check which radio button was clicked
                if(rb.getText().toString().equals("ذكر")){
                    userGender = "ذكر";
                }
                else if(rb.getText().toString().equals("انثي")){
                    userGender = "انثي";
                }
            }
        });
    }


    public void Complete(View view) {

        if (!nameState){
            if(TextUtils.isEmpty(firstName.getText().toString())|| TextUtils.isEmpty(lastName.getText().toString()))
            {
                fill();
            }
            else {
                FirstName = firstName.getText().toString();
                LastName = lastName.getText().toString();
                nameState = true;
            }
        }

        else if (!placeState){
            if(TextUtils.isEmpty(country.getSelectedItem().toString())|| TextUtils.isEmpty(teratory.getSelectedItem().toString()))
            {
                fill();
            }
            else {
                Country = country.getSelectedItem().toString();
                Teratory = teratory.getSelectedItem().toString();
                placeState = true;
            }
        }

        else if (!phonesSate){
            if(TextUtils.isEmpty(phoneNumber.getText().toString()))
            {
                fill();
            }
            else {
                PhoneNumber = phoneNumber.getText().toString();
                phonesSate = true;
            }
        }

        else if (!emailState){
            if(TextUtils.isEmpty(email.getText().toString()))
            {
                fill();
            }
            else {
                Email = email.getText().toString();
                emailState = true;
            }
        }

        else if (!birthdayState){
            /*TextUtils.isEmpty(day.getText().toString())|| TextUtils.isEmpty(month.getText().toString())|| TextUtils.isEmpty(year.getText().toString())*/
            if(BirthDay.equals(""))
            {
                fill();
            }
            else {
                //Day = day.getText().toString();
                //Month = month.getText().toString();
                //Year = year.getText().toString();
                birthdayState = true;
            }
        }

        else{
            FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
            User newUser = new User(FirstName,LastName,Country,Teratory,PhoneNumber,Email,ImageURI,userGender,Day,Month,Year);
            DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("users").child(firebaseUser.getUid());
            mDatabase.setValue(newUser);
            finish();
        }

    }

    private void fill(){
        Toast.makeText(this,"الرجاء إكمال كل الخانات .", Toast.LENGTH_SHORT).show();
    }

    private void placeData(String place)
    {
        String text = "";
        try {
            InputStream is = FullYourData.this.getAssets().open(place);
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
            Teratorys = text.split("  ");
        }
    }

    public void datePicker(View view) {
        DatePickerDialog datePickerDialog = new DatePickerDialog(FullYourData.this, date, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.getDatePicker().setMaxDate(myCalendar.getTimeInMillis());
        datePickerDialog.show();
    }

    private void updateLabel() {
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        BirthDay.setText(sdf.format(myCalendar.getTime()));
    }
}

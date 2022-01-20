package com.aboelnour.teamup.ui.UserSittings;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.aboelnour.teamup.FullYourData;
import com.aboelnour.teamup.R;
import com.aboelnour.teamup.module.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rilixtech.widget.countrycodepicker.CountryCodePicker;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;

public class EditProfileActivity extends AppCompatActivity {

    private CountryCodePicker ccp;
    private EditText editPhoneNumber,editBirthday,firstName,lastName;
    private String day,month,year;
    private DatePicker picker;
    private TextView userName,phoneNumber,country,governorate,age,gender;
    private LinearLayout userNameLayout,editUserNameLayout,phoneNumberLayout
            ,editPhoneNumberLayout,addressLayout,editAddressLayout
            ,birthdayLayout,editBirthdayLayout,genderLayout,editGenderLayout,userDataLayout;
    private User user;
    private Spinner editCountry,editGovernorate;
    private String[] countries;
    private String[] governorates;
    private String fname,lname,phoneNum,countryTemp,governorateTemp,ageTemp,genderTemp;
    private RadioGroup genderRG;
    private CircleImageView editImage;
    private DatabaseReference mRef;
    private FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        init();


    }

    private void init() {
        user = getIntent().getExtras().getParcelable("user");

        userDataLayout = findViewById(R.id.userDataLayout);


        //username data
        fname = user.getFirstName();
        lname = user.getLastName();
        userName = findViewById(R.id.UserName);
        userName.setText(fname+" "+lname);
        userNameLayout = findViewById(R.id.userNameLayout);
        editUserNameLayout = findViewById(R.id.editUserNameLayout);
        firstName = findViewById(R.id.firstName);
        lastName = findViewById(R.id.lastName);

        //phone data
        phoneNum = user.getPhoneNumber();
        phoneNumber =findViewById(R.id.phoneNumber);
        phoneNumber.setText(phoneNum);
        phoneNumberLayout = findViewById(R.id.phoneNumberLayout);
        ccp = (CountryCodePicker) findViewById(R.id.ccp);
        editPhoneNumber = findViewById(R.id.editPhoneNumber);
        editPhoneNumberLayout = findViewById(R.id.editPhoneNumberLayout);
        ccp.registerPhoneNumberTextView(editPhoneNumber);

        //place data
        editCountry = findViewById(R.id.editCountry);
        editGovernorate = findViewById(R.id.editGovernorate);
        addressLayout = findViewById(R.id.addressLayout);
        editAddressLayout = findViewById(R.id.editAddressLayout);
        country = findViewById(R.id.country);
        governorate = findViewById(R.id.governorate);
        countryTemp = user.getCountry();
        governorateTemp = user.getTeratory();
        country.setText(countryTemp);
        governorate.setText(governorateTemp);
        placeData("Country.txt");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(EditProfileActivity.this,android.R.layout.simple_spinner_item,countries);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        editCountry.setAdapter(adapter);

        editCountry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(!TextUtils.isEmpty(editCountry.getSelectedItem().toString())) {
                    String basic = "اختبار.txt";
                    String Teratory = editCountry.getSelectedItem().toString();
                    String b = basic.replace("اختبار",Teratory);
                    placeData(b);
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(EditProfileActivity.this,android.R.layout.simple_spinner_item,governorates);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    editGovernorate.setAdapter(adapter);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        //birthday data
        editBirthday = findViewById(R.id.editBirthday);
        birthdayLayout = findViewById(R.id.birthdayLayout);
        editBirthdayLayout = findViewById(R.id.editBirthdayLayout);
        age = findViewById(R.id.age);
        ageTemp = user.getYear()+"/"+user.getMonth()+"/"+user.getDay();
        age.setText(ageTemp);

        //gender data
        genderLayout = findViewById(R.id.genderLayout);
        editGenderLayout = findViewById(R.id.editGenderLayout);
        genderTemp = user.getGender();
        gender = findViewById(R.id.gender);
        gender.setText(genderTemp);
        genderRG = findViewById(R.id.genderRG);

        //personal image
        editImage = findViewById(R.id.editImage);
        Picasso.get().load(user.getImageURL()).into(editImage);

        //Firebase
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

    }

    public void openBirthDay(View view) {

        View popupView = LayoutInflater.from(EditProfileActivity.this).inflate(R.layout.birthday_spinner, null);
        final PopupWindow popupWindow = new PopupWindow(popupView, WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
        picker = popupView.findViewById(R.id.datePicker1);
        Button birthdayPicked = popupView.findViewById(R.id.birthdayPicked );
        popupWindow.showAtLocation(popupView, Gravity.CENTER,0,0);


        picker.setMaxDate(new Date().getTime()-10);
        birthdayPicked.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                day = String.valueOf(picker.getDayOfMonth());
                month = String.valueOf(picker.getMonth());
                year = String.valueOf(picker.getYear());

                editBirthday.setText(day+"/"+month+"/"+year);

                popupWindow.dismiss();
            }
        });

        popupView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                //Close the window when clicked
                popupWindow.dismiss();
                return true;
            }
        });

    }

    public void toEdit(View view) {
        switch (view.getId()) {
            case R.id.editUserNameBtn: {
                userNameLayout.setVisibility(View.GONE);
                firstName.setHint(fname);
                lastName.setHint(lname);
                editUserNameLayout.setVisibility(View.VISIBLE);
                break;
            }
            case R.id.editPhoneNumberBtn:{
                phoneNumberLayout.setVisibility(View.GONE);
                editPhoneNumber.setHint(phoneNum);
                editPhoneNumberLayout.setVisibility(View.VISIBLE);
                break;
            }
            case R.id.EditAddressBtn:{
                addressLayout.setVisibility(View.GONE);
                editAddressLayout.setVisibility(View.VISIBLE);
                break;
            }
            case R.id.editBirthdayBtn:{
                birthdayLayout.setVisibility(View.GONE);
                editBirthday.setHint(ageTemp);
                editBirthdayLayout.setVisibility(View.VISIBLE);
                break;
            }
            case R.id.EditGenderBtn:{
                genderLayout.setVisibility(View.GONE);
                editGenderLayout.setVisibility(View.VISIBLE);
                break;
            }
        }
    }

    public void checkEdit(View view) {
        switch (view.getId()){
            case R.id.confirmNameBtn: {
                if (TextUtils.isEmpty(firstName.getText().toString()) || TextUtils.isEmpty(lastName.getText().toString())){
                    Toast.makeText(this, "You must full all fields to change it.", Toast.LENGTH_SHORT).show();
                    editUserNameLayout.setVisibility(View.GONE);
                    userNameLayout.setVisibility(View.VISIBLE);
                }

                else {
                    fname = firstName.getText().toString();
                    lname = lastName.getText().toString();
                    userName.setText(fname + " " + lname);
                    user.setFirstName(fname);
                    user.setLastName(lname);
                    editUserNameLayout.setVisibility(View.GONE);
                    userNameLayout.setVisibility(View.VISIBLE);

                }
                break;
            }
            case R.id.confirmPhoneNumberBtn:{
                if(TextUtils.isEmpty(editPhoneNumber.getText().toString())){
                    Toast.makeText(this, "You must full all fields to change it.", Toast.LENGTH_SHORT).show();
                    editPhoneNumberLayout.setVisibility(View.GONE);
                    phoneNumberLayout.setVisibility(View.VISIBLE);
                }
                else{
                    phoneNum = "("+ccp.getSelectedCountryCodeWithPlus()+")-"+phoneNumber.getText().toString();
                    phoneNumber.setText(phoneNum);
                    user.setPhoneNumber(phoneNum);
                    editPhoneNumberLayout.setVisibility(View.GONE);
                    phoneNumberLayout.setVisibility(View.VISIBLE);
                }
                break;
            }
            case R.id.confirmAddressBtn:{
                if(editCountry.getSelectedItemPosition()==0||editGovernorate.getSelectedItemPosition()==0) {
                    Toast.makeText(this, "You must full all fields to change it.", Toast.LENGTH_SHORT).show();
                    editAddressLayout.setVisibility(View.GONE);
                    addressLayout.setVisibility(View.VISIBLE);
                }
                else{
                    countryTemp = editCountry.getSelectedItem().toString();
                    governorateTemp = editGovernorate.getSelectedItem().toString();
                    user.setCountry(countryTemp);
                    user.setTeratory(governorateTemp);
                    country.setText(countryTemp);
                    governorate.setText(governorateTemp);
                    editAddressLayout.setVisibility(View.GONE);
                    addressLayout.setVisibility(View.VISIBLE);
                }
                break;
            }
            case R.id.confirmBirthDayBtn:{
                if(TextUtils.isEmpty(editBirthday.getText().toString()))
                {
                    Toast.makeText(this, "You must full all fields to change it.", Toast.LENGTH_SHORT).show();
                    editBirthdayLayout.setVisibility(View.GONE);
                    birthdayLayout.setVisibility(View.VISIBLE);
                }
                else{
                    ageTemp = editBirthday.getText().toString();
                    user.setDay(day);
                    user.setMonth(month);
                    user.setYear(year);
                    age.setText(ageTemp);
                    editBirthdayLayout.setVisibility(View.GONE);
                    birthdayLayout.setVisibility(View.VISIBLE);
                }
                break;
            }
            case R.id.confirmGenderBtn:{
                genderRG.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
                {
                    @Override
                    public void onCheckedChanged(RadioGroup group, int checkedId) {
                        RadioButton rb= findViewById(checkedId);
                        // Check which radio button was clicked
                        if(rb.getText().toString().equals("ذكر")){
                            genderTemp = "ذكر";
                        }
                        else if(rb.getText().toString().equals("انثي")){
                            genderTemp = "انثي";
                        }
                    }
                });
                user.setGender(genderTemp);
                gender.setText(genderTemp);
                editGenderLayout.setVisibility(View.GONE);
                genderLayout.setVisibility(View.VISIBLE);
                break;
            }
        }
    }

    private void placeData(String place)
    {
        String text = "";
        try {
            InputStream is = EditProfileActivity.this.getAssets().open(place);
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
            governorates = text.split("  ");
        }
    }

    public void editData(View view) {

        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("برداء الانتظار");
        progressDialog.setMessage("جاري تحديث بياناتك.");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();
        mRef = FirebaseDatabase.getInstance().getReference().child("users").child(firebaseUser.getUid());
        mRef.setValue(user).isSuccessful();
        progressDialog.dismiss();
        finish();
    }
}

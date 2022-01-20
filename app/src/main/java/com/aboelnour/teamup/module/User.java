package com.aboelnour.teamup.module;

import android.os.Parcel;
import android.os.Parcelable;

public class User implements Parcelable {
    private String firstName = "", lastName = "", country = "", teratory = "",phoneNumber = "",email = "",imageURL = "",gender = "",day = "",month = "",year = "";

    public User(String firstName, String lastName, String country, String teratory, String phoneNumber, String email, String imageURL, String gender, String day, String month, String year) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.country = country;
        this.teratory = teratory;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.imageURL = imageURL;
        this.gender = gender;
        this.day = day;
        this.month = month;
        this.year = year;
    }

    public User() {}


    protected User(Parcel in) {
        firstName = in.readString();
        lastName = in.readString();
        country = in.readString();
        teratory = in.readString();
        phoneNumber = in.readString();
        email = in.readString();
        imageURL = in.readString();
        gender = in.readString();
        day = in.readString();
        month = in.readString();
        year = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(firstName);
        dest.writeString(lastName);
        dest.writeString(country);
        dest.writeString(teratory);
        dest.writeString(phoneNumber);
        dest.writeString(email);
        dest.writeString(imageURL);
        dest.writeString(gender);
        dest.writeString(day);
        dest.writeString(month);
        dest.writeString(year);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setTeratory(String teratory) {
        this.teratory = teratory;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getCountry() {
        return country;
    }

    public String getTeratory() {
        return teratory;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public String getImageURL() {
        return imageURL;
    }

    public String getGender() {
        return gender;
    }

    public String getDay() {
        return day;
    }

    public String getMonth() {
        return month;
    }

    public String getYear() {
        return year;
    }


    public static Creator<User> getCREATOR() {
        return CREATOR;
    }
}


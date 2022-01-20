package com.aboelnour.teamup.module;

import android.os.Parcel;
import android.os.Parcelable;

public class Search implements Parcelable {

    String partnersNumFrom,partnersNumTo,partnersAgeFrom,partnersAgeTo,projectCostFrom, projectCostTo, experienceYearsFrom, experienceYearsTo,projectPublishMonth, projectPublishYear,projectStartMonth,projectStartYear;
    String category,projectName,projectPlaceCountry,projectplaceTeratory,partnersGender,partnersEducation,expectedProfitFrom,expectedProfitTo,currency;


    public Search(String partnersNumFrom, String partnersNumTo, String partnersAgeFrom, String partnersAgeTo, String projectCostFrom, String projectCostTo, String experienceYearsFrom, String experienceYearsTo, String projectPublishMonth, String projectPublishYear, String projectStartMonth, String projectStartYear, String category, String projectName, String projectPlaceCountry, String projectplaceTeratory, String partnersGender, String partnersEducation, String expectedProfitFrom, String expectedProfitTo, String currency) {
        this.partnersNumFrom = partnersNumFrom;
        this.partnersNumTo = partnersNumTo;
        this.partnersAgeFrom = partnersAgeFrom;
        this.partnersAgeTo = partnersAgeTo;
        this.projectCostFrom = projectCostFrom;
        this.projectCostTo = projectCostTo;
        this.experienceYearsFrom = experienceYearsFrom;
        this.experienceYearsTo = experienceYearsTo;
        this.projectPublishMonth = projectPublishMonth;
        this.projectPublishYear = projectPublishYear;
        this.projectStartMonth = projectStartMonth;
        this.projectStartYear = projectStartYear;
        this.category = category;
        this.projectName = projectName;
        this.projectPlaceCountry = projectPlaceCountry;
        this.projectplaceTeratory = projectplaceTeratory;
        this.partnersGender = partnersGender;
        this.partnersEducation = partnersEducation;
        this.expectedProfitFrom = expectedProfitFrom;
        this.expectedProfitTo = expectedProfitTo;
        this.currency = currency;
    }

    public Search() {
    }

    protected Search(Parcel in) {
        partnersNumFrom = in.readString();
        partnersNumTo = in.readString();
        partnersAgeFrom = in.readString();
        partnersAgeTo = in.readString();
        projectCostFrom = in.readString();
        projectCostTo = in.readString();
        experienceYearsFrom = in.readString();
        experienceYearsTo = in.readString();
        projectPublishMonth = in.readString();
        projectPublishYear = in.readString();
        projectStartMonth = in.readString();
        projectStartYear = in.readString();
        category = in.readString();
        projectName = in.readString();
        projectPlaceCountry = in.readString();
        projectplaceTeratory = in.readString();
        partnersGender = in.readString();
        partnersEducation = in.readString();
        expectedProfitFrom = in.readString();
        expectedProfitTo = in.readString();
        currency = in.readString();
    }

    public static final Creator<Search> CREATOR = new Creator<Search>() {
        @Override
        public Search createFromParcel(Parcel in) {
            return new Search(in);
        }

        @Override
        public Search[] newArray(int size) {
            return new Search[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(partnersNumFrom);
        parcel.writeString(partnersNumTo);
        parcel.writeString(partnersAgeFrom);
        parcel.writeString(partnersAgeTo);
        parcel.writeString(projectCostFrom);
        parcel.writeString(projectCostTo);
        parcel.writeString(experienceYearsFrom);
        parcel.writeString(experienceYearsTo);
        parcel.writeString(projectPublishMonth);
        parcel.writeString(projectPublishYear);
        parcel.writeString(projectStartMonth);
        parcel.writeString(projectStartYear);
        parcel.writeString(category);
        parcel.writeString(projectName);
        parcel.writeString(projectPlaceCountry);
        parcel.writeString(projectplaceTeratory);
        parcel.writeString(partnersGender);
        parcel.writeString(partnersEducation);
        parcel.writeString(expectedProfitFrom);
        parcel.writeString(expectedProfitTo);
        parcel.writeString(currency);
    }

    public String getPartnersNumFrom() {
        return partnersNumFrom;
    }

    public String getPartnersNumTo() {
        return partnersNumTo;
    }

    public String getPartnersAgeFrom() {
        return partnersAgeFrom;
    }

    public String getPartnersAgeTo() {
        return partnersAgeTo;
    }

    public String getProjectCostFrom() {
        return projectCostFrom;
    }

    public String getProjectCostTo() {
        return projectCostTo;
    }

    public String getExperienceYearsFrom() {
        return experienceYearsFrom;
    }

    public String getExperienceYearsTo() {
        return experienceYearsTo;
    }

    public String getProjectPublishMonth() {
        return projectPublishMonth;
    }

    public String getProjectPublishYear() {
        return projectPublishYear;
    }

    public String getProjectStartMonth() {
        return projectStartMonth;
    }

    public String getProjectStartYear() {
        return projectStartYear;
    }

    public String getCategory() {
        return category;
    }

    public String getProjectName() {
        return projectName;
    }

    public String getProjectPlaceCountry() {
        return projectPlaceCountry;
    }

    public String getProjectplaceTeratory() {
        return projectplaceTeratory;
    }

    public String getPartnersGender() {
        return partnersGender;
    }

    public String getPartnersEducation() {
        return partnersEducation;
    }

    public String getExpectedProfitFrom() {
        return expectedProfitFrom;
    }

    public String getExpectedProfitTo() {
        return expectedProfitTo;
    }

    public String getCurrency() {
        return currency;
    }

    public static Creator<Search> getCREATOR() {
        return CREATOR;
    }
}

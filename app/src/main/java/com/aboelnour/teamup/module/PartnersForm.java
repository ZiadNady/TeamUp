package com.aboelnour.teamup.module;

import android.os.Parcel;
import android.os.Parcelable;

public class PartnersForm implements Parcelable {

    String partnerId, monthlyRent, projectPlaceDescription, financialFinancing, experienceDescription, fieldOfExperience, yearsOfExperience;
    boolean iHaveAPlaceState, rentState, projectneedPlace1;

    public PartnersForm(String partnerId, String monthlyRent, String projectPlaceDescription, String financialFinancing, String experienceDescription,
                        String fieldOfExperience, String yearsOfExperience, boolean iHaveAPlaceState, boolean rentState, boolean projectneedPlace1) {
        this.partnerId = partnerId;
        this.monthlyRent = monthlyRent;
        this.projectPlaceDescription = projectPlaceDescription;
        this.financialFinancing = financialFinancing;
        this.experienceDescription = experienceDescription;
        this.fieldOfExperience = fieldOfExperience;
        this.yearsOfExperience = yearsOfExperience;
        this.iHaveAPlaceState = iHaveAPlaceState;
        this.rentState = rentState;
        this.projectneedPlace1 = projectneedPlace1;
    }


    protected PartnersForm(Parcel in) {
        partnerId = in.readString();
        monthlyRent = in.readString();
        projectPlaceDescription = in.readString();
        financialFinancing = in.readString();
        experienceDescription = in.readString();
        fieldOfExperience = in.readString();
        yearsOfExperience = in.readString();
        iHaveAPlaceState = in.readByte() != 0;
        rentState = in.readByte() != 0;
        projectneedPlace1 = in.readByte() != 0;
    }

    public static final Creator<PartnersForm> CREATOR = new Creator<PartnersForm>() {
        @Override
        public PartnersForm createFromParcel(Parcel in) {
            return new PartnersForm(in);
        }

        @Override
        public PartnersForm[] newArray(int size) {
            return new PartnersForm[size];
        }
    };

    public PartnersForm() {

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(partnerId);
        parcel.writeString(monthlyRent);
        parcel.writeString(projectPlaceDescription);
        parcel.writeString(financialFinancing);
        parcel.writeString(experienceDescription);
        parcel.writeString(fieldOfExperience);
        parcel.writeString(yearsOfExperience);
        parcel.writeByte((byte) (iHaveAPlaceState ? 1 : 0));
        parcel.writeByte((byte) (rentState ? 1 : 0));
        parcel.writeByte((byte) (projectneedPlace1 ? 1 : 0));
    }

    public String getPartnerId() {
        return partnerId;
    }

    public String getMonthlyRent() {
        return monthlyRent;
    }

    public String getProjectPlaceDescription() {
        return projectPlaceDescription;
    }

    public String getFinancialFinancing() {
        return financialFinancing;
    }

    public String getExperienceDescription() {
        return experienceDescription;
    }

    public String getFieldOfExperience() {
        return fieldOfExperience;
    }

    public String getYearsOfExperience() {
        return yearsOfExperience;
    }

    public boolean isiHaveAPlaceState() {
        return iHaveAPlaceState;
    }

    public boolean isRentState() {
        return rentState;
    }

    public boolean isProjectneedPlace1() {
        return projectneedPlace1;
    }

    public static Creator<PartnersForm> getCREATOR() {
        return CREATOR;
    }
}

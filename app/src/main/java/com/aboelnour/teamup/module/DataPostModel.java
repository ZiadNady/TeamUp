package com.aboelnour.teamup.module;

import android.os.Parcel;
import android.os.Parcelable;

public class DataPostModel implements Parcelable {

    int numberOfPartners, numberOfAcceptedPartners, projectStartMonth, projectStartYear, partnersAgeFrom, partnersAgeTo, day, month, year;
    String partnerGender, partnersCountry, partnersGovernorate, partnersEducation, projectState, publisherID, projectCategory, projectName, projectCurrency, projectDescription, projectCountry, projectGovernorate, projectCost
            , operatingCost, theExpectedProfitFrom, theExpectedProfitTo, yearsOfExperience, fieldOfExperience, descriptionOfTechnicalExperience, monthlyRent, projectPlaceDescription, publisherFinancialContribution
            , publisherYearsOfExperience, descriptionOfPublisherExperience, postID, projectPlaceNegotiation, publisherTechnichalExperience, sharedImgLink;
    boolean projectPlaceState, projectFinancingState, technicalExperienceState, iOwnTheProjectPlace, placeOwnershipStatus;

    public DataPostModel() {
    }

    public DataPostModel(int numberOfPartners, int numberOfAcceptedPartners, int projectStartMonth, int projectStartYear, int partnersAgeFrom, int partnersAgeTo, int day, int month, int year, String partnerGender, String partnersCountry, String partnersGovernorate, String partnersEducation, String projectState, String publisherID, String projectCategory, String projectName, String projectCurrency, String projectDescription, String projectCountry, String projectGovernorate, String projectCost, String operatingCost, String theExpectedProfitFrom, String theExpectedProfitTo, String yearsOfExperience, String fieldOfExperience, String descriptionOfTechnicalExperience, String monthlyRent, String projectPlaceDescription, String publisherFinancialContribution, String publisherYearsOfExperience, String descriptionOfPublisherExperience, String postID, boolean projectPlaceState, String projectPlaceNegotiation, boolean projectFinancingState, boolean technicalExperienceState, boolean iOwnTheProjectPlace, boolean placeOwnershipStatus, String publisherTechnichalExperience) {
        this.numberOfPartners = numberOfPartners;
        this.numberOfAcceptedPartners = numberOfAcceptedPartners;
        this.projectStartMonth = projectStartMonth;
        this.projectStartYear = projectStartYear;
        this.partnersAgeFrom = partnersAgeFrom;
        this.partnersAgeTo = partnersAgeTo;
        this.day = day;
        this.month = month;
        this.year = year;
        this.partnerGender = partnerGender;
        this.partnersCountry = partnersCountry;
        this.partnersGovernorate = partnersGovernorate;
        this.partnersEducation = partnersEducation;
        this.projectState = projectState;
        this.publisherID = publisherID;
        this.projectCategory = projectCategory;
        this.projectName = projectName;
        this.projectCurrency = projectCurrency;
        this.projectDescription = projectDescription;
        this.projectCountry = projectCountry;
        this.projectGovernorate = projectGovernorate;
        this.projectCost = projectCost;
        this.operatingCost = operatingCost;
        this.theExpectedProfitFrom = theExpectedProfitFrom;
        this.theExpectedProfitTo = theExpectedProfitTo;
        this.yearsOfExperience = yearsOfExperience;
        this.fieldOfExperience = fieldOfExperience;
        this.descriptionOfTechnicalExperience = descriptionOfTechnicalExperience;
        this.monthlyRent = monthlyRent;
        this.projectPlaceDescription = projectPlaceDescription;
        this.publisherFinancialContribution = publisherFinancialContribution;
        this.publisherYearsOfExperience = publisherYearsOfExperience;
        this.descriptionOfPublisherExperience = descriptionOfPublisherExperience;
        this.postID = postID;
        this.projectPlaceState = projectPlaceState;
        this.projectPlaceNegotiation = projectPlaceNegotiation;
        this.projectFinancingState = projectFinancingState;
        this.technicalExperienceState = technicalExperienceState;
        this.iOwnTheProjectPlace = iOwnTheProjectPlace;
        this.placeOwnershipStatus = placeOwnershipStatus;
        this.publisherTechnichalExperience = publisherTechnichalExperience;
    }

    public DataPostModel(int numberOfPartners, int projectStartMonth, int projectStartYear, int partnersAgeFrom, int partnersAgeTo, String partnerGender, String partnersCountry, String partnersGovernorate, String partnersEducation, String projectState) {
        this.numberOfPartners = numberOfPartners;
        this.projectStartMonth = projectStartMonth;
        this.projectStartYear = projectStartYear;
        this.partnersAgeFrom = partnersAgeFrom;
        this.partnersAgeTo = partnersAgeTo;
        this.partnerGender = partnerGender;
        this.partnersCountry = partnersCountry;
        this.partnersGovernorate = partnersGovernorate;
        this.partnersEducation = partnersEducation;
        this.projectState = projectState;
    }

    public DataPostModel(String projectCategory, String projectName, String projectCurrency, String projectDescription, String projectCountry, String projectGovernorate, String projectCost, String operatingCost, String theExpectedProfitFrom, String theExpectedProfitTo, String yearsOfExperience, String fieldOfExperience, String descriptionOfTechnicalExperience, boolean projectPlaceState, String projectPlaceNegotiation, boolean projectFinancingState, boolean technicalExperienceState) {
        this.projectCategory = projectCategory;
        this.projectName = projectName;
        this.projectCurrency = projectCurrency;
        this.projectDescription = projectDescription;
        this.projectCountry = projectCountry;
        this.projectGovernorate = projectGovernorate;
        this.projectCost = projectCost;
        this.operatingCost = operatingCost;
        this.theExpectedProfitFrom = theExpectedProfitFrom;
        this.theExpectedProfitTo = theExpectedProfitTo;
        this.yearsOfExperience = yearsOfExperience;
        this.fieldOfExperience = fieldOfExperience;
        this.descriptionOfTechnicalExperience = descriptionOfTechnicalExperience;
        this.projectPlaceState = projectPlaceState;
        this.projectPlaceNegotiation = projectPlaceNegotiation;
        this.projectFinancingState = projectFinancingState;
        this.technicalExperienceState = technicalExperienceState;
    }

    public DataPostModel(int numberOfPartners, int numberOfAcceptedPartners, int projectStartMonth, int projectStartYear, int partnersAgeFrom, int partnersAgeTo, int day, int month, int year, String partnerGender, String partnersCountry, String partnersGovernorate, String partnersEducation, String projectState, String publisherID, String projectCategory, String projectName, String projectCurrency, String projectDescription, String projectCountry, String projectGovernorate, String projectCost, String operatingCost, String theExpectedProfitFrom, String theExpectedProfitTo, String yearsOfExperience, String fieldOfExperience, String descriptionOfTechnicalExperience, String monthlyRent, String projectPlaceDescription, String publisherFinancialContribution, String publisherYearsOfExperience, String descriptionOfPublisherExperience, String postID, String projectPlaceNegotiation, String publisherTechnichalExperience, String sharedImgLink, boolean projectPlaceState, boolean projectFinancingState, boolean technicalExperienceState, boolean iOwnTheProjectPlace, boolean placeOwnershipStatus) {
        this.numberOfPartners = numberOfPartners;
        this.numberOfAcceptedPartners = numberOfAcceptedPartners;
        this.projectStartMonth = projectStartMonth;
        this.projectStartYear = projectStartYear;
        this.partnersAgeFrom = partnersAgeFrom;
        this.partnersAgeTo = partnersAgeTo;
        this.day = day;
        this.month = month;
        this.year = year;
        this.partnerGender = partnerGender;
        this.partnersCountry = partnersCountry;
        this.partnersGovernorate = partnersGovernorate;
        this.partnersEducation = partnersEducation;
        this.projectState = projectState;
        this.publisherID = publisherID;
        this.projectCategory = projectCategory;
        this.projectName = projectName;
        this.projectCurrency = projectCurrency;
        this.projectDescription = projectDescription;
        this.projectCountry = projectCountry;
        this.projectGovernorate = projectGovernorate;
        this.projectCost = projectCost;
        this.operatingCost = operatingCost;
        this.theExpectedProfitFrom = theExpectedProfitFrom;
        this.theExpectedProfitTo = theExpectedProfitTo;
        this.yearsOfExperience = yearsOfExperience;
        this.fieldOfExperience = fieldOfExperience;
        this.descriptionOfTechnicalExperience = descriptionOfTechnicalExperience;
        this.monthlyRent = monthlyRent;
        this.projectPlaceDescription = projectPlaceDescription;
        this.publisherFinancialContribution = publisherFinancialContribution;
        this.publisherYearsOfExperience = publisherYearsOfExperience;
        this.descriptionOfPublisherExperience = descriptionOfPublisherExperience;
        this.postID = postID;
        this.projectPlaceNegotiation = projectPlaceNegotiation;
        this.publisherTechnichalExperience = publisherTechnichalExperience;
        this.sharedImgLink = sharedImgLink;
        this.projectPlaceState = projectPlaceState;
        this.projectFinancingState = projectFinancingState;
        this.technicalExperienceState = technicalExperienceState;
        this.iOwnTheProjectPlace = iOwnTheProjectPlace;
        this.placeOwnershipStatus = placeOwnershipStatus;
    }

    protected DataPostModel(Parcel in) {
        numberOfPartners = in.readInt();
        numberOfAcceptedPartners = in.readInt();
        projectStartMonth = in.readInt();
        projectStartYear = in.readInt();
        partnersAgeFrom = in.readInt();
        partnersAgeTo = in.readInt();
        day = in.readInt();
        month = in.readInt();
        year = in.readInt();
        partnerGender = in.readString();
        partnersCountry = in.readString();
        partnersGovernorate = in.readString();
        partnersEducation = in.readString();
        projectState = in.readString();
        publisherID = in.readString();
        projectCategory = in.readString();
        projectName = in.readString();
        projectCurrency = in.readString();
        projectDescription = in.readString();
        projectCountry = in.readString();
        projectGovernorate = in.readString();
        projectCost = in.readString();
        operatingCost = in.readString();
        theExpectedProfitFrom = in.readString();
        theExpectedProfitTo = in.readString();
        yearsOfExperience = in.readString();
        fieldOfExperience = in.readString();
        descriptionOfTechnicalExperience = in.readString();
        monthlyRent = in.readString();
        projectPlaceDescription = in.readString();
        publisherFinancialContribution = in.readString();
        publisherYearsOfExperience = in.readString();
        descriptionOfPublisherExperience = in.readString();
        postID = in.readString();
        projectPlaceNegotiation = in.readString();
        publisherTechnichalExperience = in.readString();
        sharedImgLink = in.readString();
        projectPlaceState = in.readByte() != 0;
        projectFinancingState = in.readByte() != 0;
        technicalExperienceState = in.readByte() != 0;
        iOwnTheProjectPlace = in.readByte() != 0;
        placeOwnershipStatus = in.readByte() != 0;
    }

    public static final Creator<DataPostModel> CREATOR = new Creator<DataPostModel>() {
        @Override
        public DataPostModel createFromParcel(Parcel in) {
            return new DataPostModel(in);
        }

        @Override
        public DataPostModel[] newArray(int size) {
            return new DataPostModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(numberOfPartners);
        parcel.writeInt(numberOfAcceptedPartners);
        parcel.writeInt(projectStartMonth);
        parcel.writeInt(projectStartYear);
        parcel.writeInt(partnersAgeFrom);
        parcel.writeInt(partnersAgeTo);
        parcel.writeInt(day);
        parcel.writeInt(month);
        parcel.writeInt(year);
        parcel.writeString(partnerGender);
        parcel.writeString(partnersCountry);
        parcel.writeString(partnersGovernorate);
        parcel.writeString(partnersEducation);
        parcel.writeString(projectState);
        parcel.writeString(publisherID);
        parcel.writeString(projectCategory);
        parcel.writeString(projectName);
        parcel.writeString(projectCurrency);
        parcel.writeString(projectDescription);
        parcel.writeString(projectCountry);
        parcel.writeString(projectGovernorate);
        parcel.writeString(projectCost);
        parcel.writeString(operatingCost);
        parcel.writeString(theExpectedProfitFrom);
        parcel.writeString(theExpectedProfitTo);
        parcel.writeString(yearsOfExperience);
        parcel.writeString(fieldOfExperience);
        parcel.writeString(descriptionOfTechnicalExperience);
        parcel.writeString(monthlyRent);
        parcel.writeString(projectPlaceDescription);
        parcel.writeString(publisherFinancialContribution);
        parcel.writeString(publisherYearsOfExperience);
        parcel.writeString(descriptionOfPublisherExperience);
        parcel.writeString(postID);
        parcel.writeString(projectPlaceNegotiation);
        parcel.writeString(publisherTechnichalExperience);
        parcel.writeString(sharedImgLink);
        parcel.writeByte((byte) (projectPlaceState ? 1 : 0));
        parcel.writeByte((byte) (projectFinancingState ? 1 : 0));
        parcel.writeByte((byte) (technicalExperienceState ? 1 : 0));
        parcel.writeByte((byte) (iOwnTheProjectPlace ? 1 : 0));
        parcel.writeByte((byte) (placeOwnershipStatus ? 1 : 0));
    }

    public int getNumberOfPartners() {
        return numberOfPartners;
    }

    public void setNumberOfPartners(int numberOfPartners) {
        this.numberOfPartners = numberOfPartners;
    }

    public int getNumberOfAcceptedPartners() {
        return numberOfAcceptedPartners;
    }

    public void setNumberOfAcceptedPartners(int numberOfAcceptedPartners) {
        this.numberOfAcceptedPartners = numberOfAcceptedPartners;
    }

    public int getProjectStartMonth() {
        return projectStartMonth;
    }

    public void setProjectStartMonth(int projectStartMonth) {
        this.projectStartMonth = projectStartMonth;
    }

    public int getProjectStartYear() {
        return projectStartYear;
    }

    public void setProjectStartYear(int projectStartYear) {
        this.projectStartYear = projectStartYear;
    }

    public int getPartnersAgeFrom() {
        return partnersAgeFrom;
    }

    public void setPartnersAgeFrom(int partnersAgeFrom) {
        this.partnersAgeFrom = partnersAgeFrom;
    }

    public int getPartnersAgeTo() {
        return partnersAgeTo;
    }

    public void setPartnersAgeTo(int partnersAgeTo) {
        this.partnersAgeTo = partnersAgeTo;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getPartnerGender() {
        return partnerGender;
    }

    public void setPartnerGender(String partnerGender) {
        this.partnerGender = partnerGender;
    }

    public String getPartnersCountry() {
        return partnersCountry;
    }

    public void setPartnersCountry(String partnersCountry) {
        this.partnersCountry = partnersCountry;
    }

    public String getPartnersGovernorate() {
        return partnersGovernorate;
    }

    public void setPartnersGovernorate(String partnersGovernorate) {
        this.partnersGovernorate = partnersGovernorate;
    }

    public String getPartnersEducation() {
        return partnersEducation;
    }

    public void setPartnersEducation(String partnersEducation) {
        this.partnersEducation = partnersEducation;
    }

    public String getProjectState() {
        return projectState;
    }

    public void setProjectState(String projectState) {
        this.projectState = projectState;
    }

    public String getPublisherID() {
        return publisherID;
    }

    public void setPublisherID(String publisherID) {
        this.publisherID = publisherID;
    }

    public String getProjectCategory() {
        return projectCategory;
    }

    public void setProjectCategory(String projectCategory) {
        this.projectCategory = projectCategory;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getProjectCurrency() {
        return projectCurrency;
    }

    public void setProjectCurrency(String projectCurrency) {
        this.projectCurrency = projectCurrency;
    }

    public String getProjectDescription() {
        return projectDescription;
    }

    public void setProjectDescription(String projectDescription) {
        this.projectDescription = projectDescription;
    }

    public String getProjectCountry() {
        return projectCountry;
    }

    public void setProjectCountry(String projectCountry) {
        this.projectCountry = projectCountry;
    }

    public String getProjectGovernorate() {
        return projectGovernorate;
    }

    public void setProjectGovernorate(String projectGovernorate) {
        this.projectGovernorate = projectGovernorate;
    }

    public String getProjectCost() {
        return projectCost;
    }

    public void setProjectCost(String projectCost) {
        this.projectCost = projectCost;
    }

    public String getOperatingCost() {
        return operatingCost;
    }

    public void setOperatingCost(String operatingCost) {
        this.operatingCost = operatingCost;
    }

    public String getTheExpectedProfitFrom() {
        return theExpectedProfitFrom;
    }

    public void setTheExpectedProfitFrom(String theExpectedProfitFrom) {
        this.theExpectedProfitFrom = theExpectedProfitFrom;
    }

    public String getTheExpectedProfitTo() {
        return theExpectedProfitTo;
    }

    public void setTheExpectedProfitTo(String theExpectedProfitTo) {
        this.theExpectedProfitTo = theExpectedProfitTo;
    }

    public String getYearsOfExperience() {
        return yearsOfExperience;
    }

    public void setYearsOfExperience(String yearsOfExperience) {
        this.yearsOfExperience = yearsOfExperience;
    }

    public String getFieldOfExperience() {
        return fieldOfExperience;
    }

    public void setFieldOfExperience(String fieldOfExperience) {
        this.fieldOfExperience = fieldOfExperience;
    }

    public String getDescriptionOfTechnicalExperience() {
        return descriptionOfTechnicalExperience;
    }

    public void setDescriptionOfTechnicalExperience(String descriptionOfTechnicalExperience) {
        this.descriptionOfTechnicalExperience = descriptionOfTechnicalExperience;
    }

    public String getMonthlyRent() {
        return monthlyRent;
    }

    public void setMonthlyRent(String monthlyRent) {
        this.monthlyRent = monthlyRent;
    }

    public String getProjectPlaceDescription() {
        return projectPlaceDescription;
    }

    public void setProjectPlaceDescription(String projectPlaceDescription) {
        this.projectPlaceDescription = projectPlaceDescription;
    }

    public String getPublisherFinancialContribution() {
        return publisherFinancialContribution;
    }

    public void setPublisherFinancialContribution(String publisherFinancialContribution) {
        this.publisherFinancialContribution = publisherFinancialContribution;
    }

    public String getPublisherYearsOfExperience() {
        return publisherYearsOfExperience;
    }

    public void setPublisherYearsOfExperience(String publisherYearsOfExperience) {
        this.publisherYearsOfExperience = publisherYearsOfExperience;
    }

    public String getDescriptionOfPublisherExperience() {
        return descriptionOfPublisherExperience;
    }

    public void setDescriptionOfPublisherExperience(String descriptionOfPublisherExperience) {
        this.descriptionOfPublisherExperience = descriptionOfPublisherExperience;
    }

    public String getPostID() {
        return postID;
    }

    public void setPostID(String postID) {
        this.postID = postID;
    }

    public String getProjectPlaceNegotiation() {
        return projectPlaceNegotiation;
    }

    public void setProjectPlaceNegotiation(String projectPlaceNegotiation) {
        this.projectPlaceNegotiation = projectPlaceNegotiation;
    }

    public String getPublisherTechnichalExperience() {
        return publisherTechnichalExperience;
    }

    public void setPublisherTechnichalExperience(String publisherTechnichalExperience) {
        this.publisherTechnichalExperience = publisherTechnichalExperience;
    }

    public String getSharedImgLink() {
        return sharedImgLink;
    }

    public void setSharedImgLink(String sharedImgLink) {
        this.sharedImgLink = sharedImgLink;
    }

    public boolean isProjectPlaceState() {
        return projectPlaceState;
    }

    public void setProjectPlaceState(boolean projectPlaceState) {
        this.projectPlaceState = projectPlaceState;
    }

    public boolean isProjectFinancingState() {
        return projectFinancingState;
    }

    public void setProjectFinancingState(boolean projectFinancingState) {
        this.projectFinancingState = projectFinancingState;
    }

    public boolean isTechnicalExperienceState() {
        return technicalExperienceState;
    }

    public void setTechnicalExperienceState(boolean technicalExperienceState) {
        this.technicalExperienceState = technicalExperienceState;
    }

    public boolean isiOwnTheProjectPlace() {
        return iOwnTheProjectPlace;
    }

    public void setiOwnTheProjectPlace(boolean iOwnTheProjectPlace) {
        this.iOwnTheProjectPlace = iOwnTheProjectPlace;
    }

    public boolean isPlaceOwnershipStatus() {
        return placeOwnershipStatus;
    }

    public void setPlaceOwnershipStatus(boolean placeOwnershipStatus) {
        this.placeOwnershipStatus = placeOwnershipStatus;
    }

    public static Creator<DataPostModel> getCREATOR() {
        return CREATOR;
    }
}
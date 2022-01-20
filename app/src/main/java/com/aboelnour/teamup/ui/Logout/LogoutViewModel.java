package com.aboelnour.teamup.ui.Logout;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class LogoutViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public LogoutViewModel() {
//        mText = new MutableLiveData<>();
//        mText.setValue("This is send fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
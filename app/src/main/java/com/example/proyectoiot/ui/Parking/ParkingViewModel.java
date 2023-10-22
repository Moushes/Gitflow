package com.example.proyectoiot.ui.Parking;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ParkingViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public ParkingViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Parking");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
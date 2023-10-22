package com.example.proyectoiot.ui.luz;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class LuzViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public LuzViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("Aqui va el boton de la luz");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
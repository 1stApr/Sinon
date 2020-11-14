package com.crocussativus.wallpaper.ui.featured;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class FeaturedViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public FeaturedViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is slideshow fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
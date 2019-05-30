package com.example.tailor0;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.example.tailor0.entity.HandbkMerki;

import java.util.List;

public class HandbkMerkiViewModel extends AndroidViewModel {
    // TODO: Implement the ViewModel
    private DictRepository handbkMerkiRepo;
    private LiveData<List<HandbkMerki>> mAllMerki;

    public HandbkMerkiViewModel(@NonNull Application application) {
        super(application);
        handbkMerkiRepo = new DictRepository(application);
    }

    public LiveData<List<HandbkMerki>> getAllMerki() {
        mAllMerki = handbkMerkiRepo.getmAllMerki();
        return mAllMerki;
    }
}

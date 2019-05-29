package com.example.tailor0;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.example.tailor0.entity.ProductType;

import java.util.List;

public class ProductViewModel extends AndroidViewModel {
    private DictRepository productRepository;
    private LiveData<List<ProductType>> mAllProd;
    public ProductViewModel(@NonNull Application application) {
        super(application);
            productRepository = new DictRepository(application);
    }

    public LiveData<List<ProductType>> getAllProd() {
        mAllProd = productRepository.getmAllProd();
        return mAllProd;
    }
//    public LiveData<List<ProductType>> getAllProd() {
//        mAllProd = productRepository.getmAllProd();
//        return mAllProd;
//    }

    public void insert (ProductType productType){
        productRepository.insert(productType);
    }

    public void update (ProductType productType){
        productRepository.update(productType);
    }
}

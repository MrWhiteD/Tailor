package com.example.tailor0;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.example.tailor0.entity.Customer;

import java.util.List;

public class CustomerViewModel extends AndroidViewModel {
    private CustomerRepository customerRepository;
    private LiveData<List<Customer>> mAllCust;
    public CustomerViewModel(@NonNull Application application) {
        super(application);
        customerRepository = new CustomerRepository(application);
        mAllCust = customerRepository.getmAllCust();
    }

    public LiveData<List<Customer>> getAllCust() {
        return mAllCust;
    }

    public void insert (Customer customer){
        customerRepository.insert(customer);
    }

    public void update (Customer customer){
        customerRepository.update(customer);
    }
}

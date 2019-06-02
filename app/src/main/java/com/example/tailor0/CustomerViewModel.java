package com.example.tailor0;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.example.tailor0.entity.Cust2Merki;
import com.example.tailor0.entity.Customer;
import com.example.tailor0.entity.FullCust2Merki;

import java.util.List;

public class CustomerViewModel extends AndroidViewModel {
    private CustomerRepository customerRepository;
    private LiveData<List<Customer>> mAllCust;
    private LiveData<List<FullCust2Merki>> mFullCust2Merki;

    public CustomerViewModel(@NonNull Application application) {
        super(application);
        customerRepository = new CustomerRepository(application);
    }

    public LiveData<List<Customer>> getAllCust() {
        mAllCust = customerRepository.getmAllCust();
        return mAllCust;
    }

    public LiveData<List<FullCust2Merki>> getFullCust2Merki(Long custId) {
        mFullCust2Merki = customerRepository.getFullCust2Merki(custId);
        return mFullCust2Merki;
    }

    public void insert_c2m (Cust2Merki cust2Merki){
        customerRepository.insert_c2m(cust2Merki);
    }

    public void insert (Customer customer){
        customerRepository.insert(customer);
    }

    public void update (Customer customer){
        customerRepository.update(customer);
    }
}

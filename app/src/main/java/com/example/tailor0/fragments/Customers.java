package com.example.tailor0.fragments;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tailor0.CustomerListAdapter;
import com.example.tailor0.CustomerViewModel;
import com.example.tailor0.NewCustomer;
import com.example.tailor0.R;
import com.example.tailor0.entity.Customer;

import java.util.List;

import static android.app.Activity.RESULT_OK;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Customers.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Customers#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Customers extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;
    private CustomerViewModel customerViewModel;
    public static final int NEW_CUSTOMER_ACTIVITY_REQUEST_CODE = 1;
    public static final int UPD_CUSTOMER_ACTIVITY_REQUEST_CODE = 2;
    public Customers() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Customer.
     */
    // TODO: Rename and change types and number of parameters
    public static Customers newInstance(String param1, String param2) {
        Customers fragment = new Customers();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_customers, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.recyclerview);
//        final CustomerListAdapter customerListAdapter = new CustomerListAdapter(view.getContext());
        final CustomerListAdapter customerListAdapter = new CustomerListAdapter(this);
        recyclerView.setAdapter(customerListAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));


        customerViewModel = ViewModelProviders.of(getActivity()).get(CustomerViewModel.class);

        customerViewModel.getAllCust().observe(this, new Observer<List<Customer>>() {
            @Override
            public void onChanged(@Nullable final List<Customer> customers) {
                // Update the cached copy of the words in the adapter.
                customerListAdapter.setCustomer(customers);
            }
        });

        FloatingActionButton fabC = view.findViewById(R.id.fabC);
        fabC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(view.getContext(), NewCustomer.class);
                Intent intent = new Intent(getActivity(), NewCustomer.class);
                intent.putExtra("REQUEST_MODE", "add");
//                ((Activity) view.getContext()).startActivityForResult(intent, NEW_CUSTOMER_ACTIVITY_REQUEST_CODE);
                startActivityForResult(intent, NEW_CUSTOMER_ACTIVITY_REQUEST_CODE);
            }
        });
//        Toolbar toolbar = getActivity().findViewById(R.id.toolbar);
//        toolbar.setTitle("Клиенты");
        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if ((requestCode == NEW_CUSTOMER_ACTIVITY_REQUEST_CODE || requestCode == UPD_CUSTOMER_ACTIVITY_REQUEST_CODE) && resultCode == RESULT_OK) {
            Customer customer = new Customer();
            customer.fio = data.getStringExtra("fio");
            customer.phone = data.getStringExtra("phone");
            customer.notes = data.getStringExtra("notes");
            customer.email = data.getStringExtra("email");

            if (requestCode == NEW_CUSTOMER_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
                customerViewModel.insert(customer);
            }
            if (requestCode == UPD_CUSTOMER_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
                customer.id = data.getLongExtra("id", 0);
                customerViewModel.update(customer);
            }
        }
    }


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }


    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}

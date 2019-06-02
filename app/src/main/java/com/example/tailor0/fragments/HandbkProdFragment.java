package com.example.tailor0.fragments;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.tailor0.NewProdType;
import com.example.tailor0.OrderActivity;
import com.example.tailor0.ProductViewModel;
import com.example.tailor0.R;
import com.example.tailor0.entity.ProductType;

import java.util.Collections;
import java.util.List;

import static android.app.Activity.RESULT_OK;

public class HandbkProdFragment extends Fragment {
    private HandbkAdapter adapter;
    private String mHandbkType; //Параметр - тип справочника
    public static final int NEW_PROD_ACTIVITY_REQUEST_CODE = 1;
    public static final int UPD_PROD_ACTIVITY_REQUEST_CODE = 2;
    ProductViewModel productViewModel;

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
    private OnFragmentInteractionListener mListener;

    private RecyclerView recyclerView;

    // Это отображаемый список заказов. Вначале он заполняется из базы, а затем отображается в адаптере
    private List<ProductType> productTypes;

    public HandbkProdFragment() {
        // Required empty public constructor
    }

    public static HandbkProdFragment newInstance(String arg) {
        HandbkProdFragment fragment = new HandbkProdFragment();
        Bundle args = new Bundle();
        args.putString("handbType", arg);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mHandbkType = getArguments().getString("handbType", "");
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if ((requestCode == NEW_PROD_ACTIVITY_REQUEST_CODE || requestCode == UPD_PROD_ACTIVITY_REQUEST_CODE) && resultCode == RESULT_OK) {
            ProductType productType = new ProductType();
            if (requestCode == UPD_PROD_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
                productType.id = data.getLongExtra("prodTypeId", 0);
            }
            productType.name = data.getStringExtra("prodTypeName");
            productType.notes = data.getStringExtra("prodTypeNotes");

            productViewModel.insert(productType);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_handbk, container, false);

        if (getArguments() != null) {
        }

        FloatingActionButton fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), NewProdType.class);
                intent.putExtra("prodTypeId", -1);
                startActivityForResult(intent, NEW_PROD_ACTIVITY_REQUEST_CODE);
            }
        });
        adapter = new HandbkAdapter();

        recyclerView = view.findViewById(R.id.handbk_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
        // Inflate the layout for this fragment
        return view;
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

    @Override
    public void onResume() {
        super.onResume();

        productViewModel = ViewModelProviders.of(this).get(ProductViewModel.class);
        productViewModel.getAllProd().observe(this, new Observer<List<ProductType>>() {
            @Override
            public void onChanged(@Nullable final List<ProductType> productTps) {
                adapter.setProduct(productTps);
            }
        });

        adapter.notifyDataSetChanged();
    }

    private void handleOnClick(ProductType productType) {
        Intent intent = new Intent(getActivity(), NewProdType.class);
        intent.putExtra("prodTypeId", productType.id);
        intent.putExtra("prodTypeName", productType.name);
        intent.putExtra("prodTypeNotes", productType.notes);

        startActivityForResult(intent, UPD_PROD_ACTIVITY_REQUEST_CODE);
    }


    private class HandbkHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ProductType productType;

        TextView dateStart;
        TextView dateEnd;
        TextView comment;

        public HandbkHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            dateStart = itemView.findViewById(R.id.dictName);
//            tvDateEnd = itemView.findViewById(R.id.order_date_end);
//            tvProdType = itemView.findViewById(R.id.order_comment);

        }

        @Override
        public void onClick(View v) {

            handleOnClick(productType);

        }

        public void bind(ProductType productType){
            this.productType = productType;
            dateStart.setText(productType.name);
//            tvDateEnd.setText(order.tvDateEnd);
//            tvProdType.setText(order.note);
        }
    }

    private class HandbkAdapter extends RecyclerView.Adapter<HandbkHolder>{

        List<ProductType> mproductTypes = Collections.emptyList();
        @NonNull
        @Override
        public HandbkHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            View view = inflater.inflate(R.layout.handbk_item_layout, viewGroup, false);
            return new HandbkHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull HandbkHolder handbkHolder, int i) {
            // А здесь напрямую обращаемся к списку заказов в классе фрагмента
            ProductType productType = mproductTypes.get(i);
            handbkHolder.bind(productType);
        }

        @Override
        public int getItemCount() {
            return mproductTypes.size();
//            return 2;
        }

        public void setProduct(List<ProductType> productTypes) {
            mproductTypes = productTypes;
            notifyDataSetChanged();
        }
    }

}
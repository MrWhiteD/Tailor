package com.example.tailor0.fragments;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tailor0.HandbkMerkiViewModel;
import com.example.tailor0.R;
import com.example.tailor0.entity.HandbkMerki;

import java.util.Collections;
import java.util.List;

public class HandbkMerkiFragment extends Fragment {

    private HandbkMerkiAdapter adapter;
    private HandbkMerkiViewModel mViewModel;
    private RecyclerView recyclerView;

    public static HandbkMerkiFragment newInstance() {
        return new HandbkMerkiFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.handbk_merki_fragment, container, false);
        adapter = new HandbkMerkiAdapter();

        recyclerView = view.findViewById(R.id.handbk_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(HandbkMerkiViewModel.class);
        // TODO: Use the ViewModel
        mViewModel.getAllMerki().observe(this, new Observer<List<HandbkMerki>>() {
            @Override
            public void onChanged(@Nullable final List<HandbkMerki> handbkMerkis) {
//                 Update the cached copy of the words in the adapter.
//                ((MyAdapter) packageTypesAdapter).setCustomer(customers);
                adapter.setProduct(handbkMerkis);
            }
        });
    }

    private void handleOnClick(HandbkMerki handbkMerki) {
        // А в этом месте передается объект заказа из элемента списка, которого коснулись
        // Соответственно мы здесь можем вызвать активити для редактирования заказа
//        Intent intent = OrderActivity.newIntent(getContext(), productType.id);
//        startActivity(intent);
        Toast toast = Toast.makeText(getContext(), "На рисунке мерка номер - " + handbkMerki.id + "\n" + handbkMerki.description, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    private class HandbkMerkiHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        HandbkMerki handbkMerki;

        TextView tvShortName;
        TextView tvName;

        public HandbkMerkiHolder(@NonNull View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            tvShortName = itemView.findViewById(R.id.tvShortName);
            tvName = itemView.findViewById(R.id.tvName);

        }

        @Override
        public void onClick(View v) {
            handleOnClick(handbkMerki);
        }

        public void bind(HandbkMerki handbkMerki){
            this.handbkMerki = handbkMerki;
            tvShortName.setText(handbkMerki.short_name);
            tvName.setText(handbkMerki.name);
//            dateEnd.setText(order.dateEnd);
//            comment.setText(order.note);
        }
    }

    private class HandbkMerkiAdapter extends RecyclerView.Adapter<HandbkMerkiHolder>{

        List<HandbkMerki> mHandbkMerki = Collections.emptyList();
        @NonNull
        @Override
        public HandbkMerkiHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            View view = inflater.inflate(R.layout.handbk_merki_item, viewGroup, false);
            return new HandbkMerkiHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull HandbkMerkiHolder handbkMerkiHolder, int i) {
            // А здесь напрямую обращаемся к списку заказов в классе фрагмента
            HandbkMerki productType = mHandbkMerki.get(i);
            handbkMerkiHolder.bind(productType);
        }

        @Override
        public int getItemCount() {
            return mHandbkMerki.size();
//            return 2;
        }

        public void setProduct(List<HandbkMerki> handbkMerkis) {
            mHandbkMerki = handbkMerkis;
            notifyDataSetChanged();
        }
    }
}

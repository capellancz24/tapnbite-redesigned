package com.example.tapnbite.UserFragment;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.tapnbite.Class.Food;
import com.example.tapnbite.Class.OrderDetail;
import com.example.tapnbite.R;
import com.example.tapnbite.UserFragment.Adapter.OrderDetailAdapter;
import java.util.ArrayList;
import java.util.List;

public class OrderFragment extends Fragment {

    private RecyclerView recyclerViewOrders;
    private OrderDetailAdapter adapter;
    private List<OrderDetail> orderList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_order, container, false);
        recyclerViewOrders = view.findViewById(R.id.recyclerViewOrders);
        recyclerViewOrders.setLayoutManager(new LinearLayoutManager(getContext()));

        orderList = new ArrayList<>();
        loadOrders();

        adapter = new OrderDetailAdapter(orderList);
        recyclerViewOrders.setAdapter(adapter);

        return view;
    }

    private void loadOrders() {
        List<Food> foodItems = new ArrayList<>();

    }
}
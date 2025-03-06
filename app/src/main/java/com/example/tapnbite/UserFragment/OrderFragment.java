package com.example.tapnbite.UserFragment;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
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
        /* Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_order, container, false);*/
        private void loadOrders() {
            List<Food> foodItems = new ArrayList<>();
            foodItems.add(new Food("101", "Burger", "Beef burger", "Fast Food", "Canteen 1", "Burger Shop", "10 min", 150, "burger.jpg"));
            foodItems.add(new Food("102", "Pizza", "Cheese Pizza", "Fast Food", "Canteen 2", "Pizza Hut", "15 min", 250, "pizza.jpg"));

            orderList.add(new OrderDetail("28", foodItems, "On Process", "C123", "March 3, 2025"));
            orderList.add(new OrderDetail("29", foodItems, "Pending Review", "C124", "March 3, 2025"));
        }

}



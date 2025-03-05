package com.example.tapnbite.UserFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import com.bumptech.glide.Glide;
import com.example.tapnbite.R;

public class FoodDetailsFragment extends Fragment {

    private String foodId, name, description, categoryId, imageUrl, canteenStaffId, availability;
    private int price, prepTime;
    private ImageButton ibRemove, ibAdd, ibBack;
    private TextView tvQuantity;
    private Button addtocart;

    public FoodDetailsFragment() {
        // Required empty public constructor
    }

    public static FoodDetailsFragment newInstance(String param1, String param2) {
        FoodDetailsFragment fragment = new FoodDetailsFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            foodId = getArguments().getString("foodId");
            name = getArguments().getString("name");
            description = getArguments().getString("description");
            price = getArguments().getInt("price");
            prepTime = getArguments().getInt("prepTime");
            categoryId = getArguments().getString("categoryId");
            imageUrl = getArguments().getString("imageUrl");
            canteenStaffId = getArguments().getString("canteenStaffId");
            availability = getArguments().getString("availability");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_food_details, container, false);

        // Find the UI elements
        ImageView ivFoodPicture = view.findViewById(R.id.ivFoodPicture);
        TextView tvFoodName = view.findViewById(R.id.tvFoodName);
        TextView tvFoodDescription = view.findViewById(R.id.tvFoodDescription);
        TextView tvPrice = view.findViewById(R.id.tvPrice);
        TextView tvPrepTime = view.findViewById(R.id.tvPrepTime);
        tvQuantity = view.findViewById(R.id.tvQuantity);
        ibRemove = view.findViewById(R.id.ibRemove);
        ibAdd = view.findViewById(R.id.ibAdd);
        ibBack = view.findViewById(R.id.ibBack);
        addtocart = view.findViewById(R.id.btnAddtoCart);

        // Update the UI elements with the retrieved data
        Glide.with(this).load(imageUrl).into(ivFoodPicture);
        tvFoodName.setText(name);
        tvFoodDescription.setText(description);
        tvPrice.setText(String.valueOf(price));
        tvPrepTime.setText(String.valueOf(prepTime));

        // Set up the click listeners
        ibAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateQuantity(1);
            }
        });

        ibRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateQuantity(-1);
            }
        });

        ibBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigateUp();
            }
        });

        addtocart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Add the item to the cart
                // You can use the quantity and other details to add the item to the cart
                // For now, we will just navigate back to the previous screen
                Toast.makeText(getContext(), "Item added to cart.", Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    private void updateQuantity(int change) {
        int currentQuantity = Integer.parseInt(tvQuantity.getText().toString());
        int newQuantity = currentQuantity + change;
        if (newQuantity >= 1) {
            tvQuantity.setText(String.valueOf(newQuantity));
        }
    }
}
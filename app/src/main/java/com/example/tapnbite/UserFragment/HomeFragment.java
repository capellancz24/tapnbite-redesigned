package com.example.tapnbite.UserFragment;

import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.tapnbite.Class.Food;
import com.example.tapnbite.R;
import com.example.tapnbite.UserFragment.Adapter.ProductsAdapter;
import com.example.tapnbite.ViewModel.SharedViewModel;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.search.SearchBar;
import com.google.android.material.search.SearchView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private TextView canteenName, categoriesSeeAll, popularFoodSeeAll;
    private SearchBar searchBar;
    private ImageButton notification;
    private CardView meals, snacks, drinks, desserts;
    private RecyclerView popularFood;
    private MaterialToolbar materialToolbar;
    private View view;
    private SearchView searchView;
    private RecyclerView rvProducts;
    private ProductsAdapter productsAdapter;
    private List<Food> productList;
    private RequestQueue requestQueue;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_home, container, false);

        canteenName = view.findViewById(R.id.tvCanteenNum);
        searchView = view.findViewById(R.id.svItems);
        popularFoodSeeAll = view.findViewById(R.id.tvSeeAll1);

        searchBar = view.findViewById(R.id.search_bar);
        searchBar.inflateMenu(R.menu.filter_menu);

        // Initialize ViewModel
        SharedViewModel viewModel = new ViewModelProvider(requireActivity()).get(SharedViewModel.class);

        // Observe the selected chip
        viewModel.getSelectedChip().observe(getViewLifecycleOwner(), chipText -> {
            canteenName.setText(chipText); // Update the canteenName TextView
        });

        searchBar.setOnMenuItemClickListener(menuItem -> {
            switch (menuItem.getItemId()) {
                case R.id.action_search:
                    // Handle the search action
                    Navigation.findNavController(view).navigate(R.id.navigateToFilterFragment);
                    return true;
                // Add other cases if you have more menu items
                default:
                    return false;
            }
        });

        categoriesSeeAll = view.findViewById(R.id.tvSeeAll);
        categoriesSeeAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.navigateToAllCategoriesFragment);
            }
        });

        popularFoodSeeAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchView.setVisibility(View.VISIBLE); // Make the SearchView visible
                searchView.show();
                searchView.getEditText().setText("");
            }
        });


        rvProducts = view.findViewById(R.id.rvFoodProducts);
        rvProducts.setHasFixedSize(true);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        rvProducts.setLayoutManager(gridLayoutManager);

        productList = new ArrayList<>();
        productsAdapter = new ProductsAdapter(getContext(), productList);
        rvProducts.setAdapter(productsAdapter);

        requestQueue = Volley.newRequestQueue(getContext());
        fetchProducts();



        return view;
    }

    private void fetchProducts() {
        String url = "https://mocki.io/v1/1830be16-229f-42ca-8e5e-44684015db0a"; // Replace with your server URL

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject product = response.getJSONObject(i);
                                String foodId = product.getString("id");
                                String name = product.getString("item_name");
                                String description = product.getString("item_desc");
                                int price = product.getInt("item_price");
                                int prepTime = product.getInt("item_preptime");
                                String categoryId = product.getString("category_id");
                                String imageUrl = product.getString("item_image");
                                String canteenStaffId = product.getString("canteenstaff_id");
                                String availability = product.getString("availability");

                                productList.add(new Food(foodId, name, description, price, prepTime, categoryId, imageUrl, canteenStaffId, availability));
                            }
                            productsAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                });

        requestQueue.add(jsonArrayRequest);
    }
}
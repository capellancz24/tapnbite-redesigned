package com.example.tapnbite;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.tapnbite.Class.Food;
import com.example.tapnbite.UserFragment.Adapter.MenuAdapter;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AddMenuFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddMenuFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private BottomAppBar bottomAppBar;
    private FloatingActionButton addMenuFab;
    private TextInputEditText foodName, foodPrice, foodDescription, foodImage, foodPreparationTime;
    private AutoCompleteTextView foodCategory, store;
    private ImageButton back;
    private Button add;

    public AddMenuFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddMenuFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddMenuFragment newInstance(String param1, String param2) {
        AddMenuFragment fragment = new AddMenuFragment();
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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_menu, container, false);

        foodName = view.findViewById(R.id.inputFoodName);
        foodPrice = view.findViewById(R.id.inputPrice);
        foodDescription = view.findViewById(R.id.inputDescription);
        foodImage = view.findViewById(R.id.inputImageUrl);
        foodPreparationTime = view.findViewById(R.id.inputPrepTime);
        foodCategory = view.findViewById(R.id.actCategory);
        store = view.findViewById(R.id.inputStore);

        add = view.findViewById(R.id.btnAdd);
        back = view.findViewById(R.id.ibClose);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        return view;
    }

    private void clearInputFields() {
        foodName.setText("");
        foodPrice.setText("");
        foodDescription.setText("");
        foodImage.setText("");
        foodPreparationTime.setText("");
        foodCategory.setText("");
        store.setText("");
    }

}
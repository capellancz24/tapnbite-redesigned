package com.example.tapnbite;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.example.tapnbite.Class.Food;
import com.example.tapnbite.UserFragment.Adapter.MenuAdapter;
import com.example.tapnbite.UserFragment.Adapter.OnMenuItemClickListener;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;
import java.util.List;

public class MenuManagementFragment extends Fragment implements OnMenuItemClickListener, MenuAdapter.OnMenuItemClickListener {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    private MaterialToolbar materialToolbar;
    private BottomAppBar bottomAppBar;
    private FloatingActionButton addMenuFab;
    private RecyclerView recyclerView;

    public MenuManagementFragment() {
        // Required empty public constructor
    }

    public static MenuManagementFragment newInstance(String param1, String param2) {
        MenuManagementFragment fragment = new MenuManagementFragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_menu, container, false);

        materialToolbar = view.findViewById(R.id.materialToolbar);
        bottomAppBar = view.findViewById(R.id.bottomAppBar);
        addMenuFab = view.findViewById(R.id.btnAdd);
        recyclerView = view.findViewById(R.id.rvMenus);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        sampleListItem();
        materialToolbarClick();
        bottomAppBarClick();
        return view;
    }

    @Override
    public void onMenuItemClick(Food menuItem) {
        Toast.makeText(getContext(), "Clicked: " + menuItem.getName(), Toast.LENGTH_SHORT).show();

    }

    public void bottomAppBarClick() {
        bottomAppBar.setOnMenuItemClickListener(new BottomAppBar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.action_edit:
                        Navigation.findNavController(item.getActionView()).navigate(R.id.navigateToAddMenuFragmentSave);
                        return true;
                    case R.id.action_delete:
                        return true;
                    default:
                        return false;
                }
            }
        });

        addMenuFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.navigateToAddMenuFragment);
            }
        });
    }

    private void materialToolbarClick() {
        materialToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.navigateToDashboardFragment);
            }
        });
    }

    private void sampleListItem() {
        List<Food> menuItems = new ArrayList<>();
        menuItems.add(new Food("1", "Kare-Kare", "Delicious peanut stew", "Meal", "Canteen 1", "Store 1", "5 Min", 100, ""));
        menuItems.add(new Food("2", "Adobo", "Savory chicken stew", "Meal", "Canteen 2", "Store 2", "10 Min", 120, ""));
        menuItems.add(new Food("3", "Sinigang", "Tamarind soup", "Soup", "Canteen 3", "Store 3", "15 Min", 90, ""));



        MenuAdapter adapter = new MenuAdapter(menuItems, this);
        recyclerView.setAdapter(adapter);
    }
}
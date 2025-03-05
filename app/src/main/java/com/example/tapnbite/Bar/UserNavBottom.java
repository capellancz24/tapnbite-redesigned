package com.example.tapnbite.Bar;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.example.tapnbite.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.HashSet;
import java.util.Set;

public class UserNavBottom extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private static final Set<Integer> HIDDEN_DESTINATIONS = new HashSet<>();

    static {
        HIDDEN_DESTINATIONS.add(R.id.allCategoriesFragment);
        HIDDEN_DESTINATIONS.add(R.id.filterFragment);
        HIDDEN_DESTINATIONS.add(R.id.searchFragment);
        HIDDEN_DESTINATIONS.add(R.id.privacyPolicyFragment);
        HIDDEN_DESTINATIONS.add(R.id.termsAndConditionFragment);
        HIDDEN_DESTINATIONS.add(R.id.faqsFragment);
        HIDDEN_DESTINATIONS.add(R.id.splashScreenFragment);
        HIDDEN_DESTINATIONS.add(R.id.onBoarding1Fragment);
        HIDDEN_DESTINATIONS.add(R.id.onBoarding2Fragment);
        HIDDEN_DESTINATIONS.add(R.id.onBoarding3Fragment);
        HIDDEN_DESTINATIONS.add(R.id.loginFragment);
        HIDDEN_DESTINATIONS.add(R.id.registerFragment);
        HIDDEN_DESTINATIONS.add(R.id.addMenuFragment);
        HIDDEN_DESTINATIONS.add(R.id.addStoreFragment);
        HIDDEN_DESTINATIONS.add(R.id.menuManagementFragment);
        HIDDEN_DESTINATIONS.add(R.id.settingsFragment);
        HIDDEN_DESTINATIONS.add(R.id.successFragment);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_bottom_navigation);

        bottomNavigationView = findViewById(R.id.bottom_nav_view);

        NavHostFragment navHostFragment = (NavHostFragment)
                getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);

        NavController navController = navHostFragment.getNavController();
        NavigationUI.setupWithNavController(bottomNavigationView, navController);

        // Set up a listener for destination changes
        navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
            if (HIDDEN_DESTINATIONS.contains(destination.getId())) {
                bottomNavigationView.setVisibility(View.GONE);
            } else {
                bottomNavigationView.setVisibility(View.VISIBLE);
            }
        });
    }
}
package com.example.tapnbite.UserFragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.tapnbite.R;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.tapnbite.Menu_Activity; // Import Menu_Activity
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class LoginFragment extends Fragment {

    private Button login, signup;
    private TextView forgotPassword;
    private TextInputEditText email, password;
    private TextInputLayout txtLayoutNUEmail, txtLayoutPassword;
    private RegisterFragment.DatabaseHelper DB;

    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        // Initialize views
        login = view.findViewById(R.id.btnLogin);
        signup = view.findViewById(R.id.btnSignUp);
        email = view.findViewById(R.id.inputNUEmail);
        password = view.findViewById(R.id.inputPassword);
        forgotPassword = view.findViewById(R.id.tvForgotPassword);
        txtLayoutNUEmail = view.findViewById(R.id.txtLayoutEmail);
        txtLayoutPassword = view.findViewById(R.id.txtLayoutPassword);

        // Initialize DBHelper
        DB = new RegisterFragment.DatabaseHelper(getActivity());

        // Set up listeners
        signup.setOnClickListener(v -> Navigation.findNavController(v).navigate(R.id.navigateToRegisterFragment));
        login.setOnClickListener(v -> handleLogin());
        forgotPassword.setOnClickListener(v -> handleForgotPassword());

        // Set up text watchers
        emailAndPasswordTextWatcher();

        return view;
    }

    private void handleLogin() {
        if (!validateEmailAndPassword()) {
            return;
        }

        String emailValue = email.getText().toString().trim();
        String passwordValue = password.getText().toString().trim();

        if (DB.checkEmailAndPassword(emailValue, passwordValue)) {
            Toast.makeText(getActivity(), "Login Success", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getActivity(), Menu_Activity.class); // Replace with your target activity
            startActivity(intent);
            requireActivity().finish(); // Close the login fragment
        } else {
            Toast.makeText(getActivity(), "Login Failed", Toast.LENGTH_SHORT).show();
        }
    }

    private void handleForgotPassword() {
        // Add logic for handling forgot password
        Toast.makeText(getActivity(), "Forgot Password Clicked", Toast.LENGTH_SHORT).show();
    }

    private void emailAndPasswordTextWatcher() {
        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                txtLayoutNUEmail.setError(null);
                txtLayoutNUEmail.setErrorEnabled(false);
            }
        });

        password.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus) {
                txtLayoutPassword.setEndIconMode(TextInputLayout.END_ICON_PASSWORD_TOGGLE);
            } else {
                txtLayoutPassword.setEndIconMode(TextInputLayout.END_ICON_NONE);
            }
        });

        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                txtLayoutPassword.setError(null);
                txtLayoutPassword.setErrorEnabled(false);
            }
        });
    }

    private boolean validateEmailAndPassword() {
        String emailValue = email.getText().toString().trim();
        String passwordValue = password.getText().toString().trim();

        if (emailValue.isEmpty()) {
            txtLayoutNUEmail.setError("Please enter your email.");
            return false;
        }

        if (passwordValue.isEmpty()) {
            txtLayoutPassword.setError("Please enter your password.");
            return false;
        }

        return true;
    }
}
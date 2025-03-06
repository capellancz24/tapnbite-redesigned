package com.example.tapnbite.UserFragment;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.tapnbite.Class.VolleySingleton;
import com.example.tapnbite.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LoginFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LoginFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String LOGIN_URL = "http://192.168.18.6/tapnbite/users/readUsers.php";
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private View view;
    private Button login, signup;
    private TextInputLayout txtLayoutNUEmail, txtLayoutPassword;
    private TextInputEditText email, password;

    public LoginFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment loginFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LoginFragment newInstance(String param1, String param2) {
        LoginFragment fragment = new LoginFragment();
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
        view = inflater.inflate(R.layout.fragment_login, container, false);

        signup = view.findViewById(R.id.btnSignUp);

        txtLayoutNUEmail = view.findViewById(R.id.txtLayoutEmail);
        txtLayoutPassword = view.findViewById(R.id.txtLayoutPassword);
        email = view.findViewById(R.id.inputEmail);
        password = view.findViewById(R.id.inputPassword);

        //navigate to signup
        signup.setOnClickListener(view -> Navigation.findNavController(view).navigate(R.id.navigateToRegisterFragment));

        login = view.findViewById(R.id.btnLogin);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = email.getText().toString();
                String pass = password.getText().toString();

                if (!username.isEmpty() && !pass.isEmpty()) {
                    loginUser(username, pass);
                } else {
                    Toast.makeText(getContext(), "Please enter username and password", Toast.LENGTH_SHORT).show();
                }
            }
        });

        emailAndPasswordTextWatcher();
        return view;
    }

    private void loginUser(final String email, final String password) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, LOGIN_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);

                            // Check for error in the response
                            if (jsonResponse.has("error")) {
                                Toast.makeText(getContext(), jsonResponse.getString("error"), Toast.LENGTH_SHORT).show();
                                return;
                            }

                            String userType = jsonResponse.getString("user_type");

                            if (!userType.isEmpty()) {
                                // Navigate to the appropriate fragment based on user type
                                if (userType.equals("customer")) {
                                    Navigation.findNavController(view).navigate(R.id.navigateToHomeFragment);
                                } else if (userType.equals("canteen staff")) {
                                    Toast.makeText(getContext(), "Canteen Staff", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(getContext(), "Invalid credentials", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getContext(), "Error parsing response", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(), "Login failed: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("email", email);
                params.put("password", password);
                return params;
            }
        };

        VolleySingleton.getInstance(getContext()).addToRequestQueue(stringRequest);
    }

    private void emailAndPasswordTextWatcher() {
        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                txtLayoutNUEmail.setError(null);
                txtLayoutNUEmail.setErrorEnabled(false);
            }
        });

        password.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    txtLayoutPassword.setEndIconMode(TextInputLayout.END_ICON_PASSWORD_TOGGLE);
                } else {
                    txtLayoutPassword.setEndIconMode(TextInputLayout.END_ICON_NONE);
                }
            }
        });

        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                txtLayoutPassword.setError(null);
                txtLayoutPassword.setErrorEnabled(false);
            }
        });
    }

    private Boolean validateEmailAndPassword() {
        String val = password.getText().toString();
        String val2 = email.getText().toString();

        if (val.isEmpty()) {
            txtLayoutPassword.setError("Please enter your password.");
            return false;
        }

        if (val2.isEmpty()) {
            txtLayoutNUEmail.setError("Please enter your email.");
            return false;
        }
        return true;
    }

}
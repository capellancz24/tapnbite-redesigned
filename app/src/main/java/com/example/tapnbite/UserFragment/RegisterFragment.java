package com.example.tapnbite.UserFragment;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.tapnbite.Class.VolleySingleton;
import com.example.tapnbite.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.button.MaterialButtonToggleGroup;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterFragment extends Fragment {

    private static final String REGISTER_URL = "http://192.168.18.6/tapnbite/userCreate.php";
    private View view;
    private TextInputLayout txtLayoutFirstName, txtLayoutLastName, txtLayoutEmail, txtLayoutNUID, txtLayoutCanteen, txtLayoutStoreName, txtLayoutPassword, txtLayoutConfirmPassword;
    private TextInputEditText inputFirstName, inputLastName, inputEmail, inputNUID, inputStoreName, inputPassword, inputConfirmPassword;
    private Button login, signUp;
    private CheckBox cbAgreement;
    private MaterialButtonToggleGroup toggleGroup;
    private AutoCompleteTextView canteenAutoComplete;
    private String dataUserType = "null";

    public RegisterFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_register, container, false);

        txtLayoutFirstName = view.findViewById(R.id.txtLayoutFirstName);
        txtLayoutLastName = view.findViewById(R.id.txtLayoutLastName);
        txtLayoutEmail = view.findViewById(R.id.txtLayoutEmail);
        txtLayoutNUID = view.findViewById(R.id.txtLayoutNUID);

        txtLayoutCanteen = view.findViewById(R.id.txtLayoutCanteen); //autocomplete txt view

        txtLayoutStoreName = view.findViewById(R.id.txtLayoutStoreName);
        txtLayoutPassword = view.findViewById(R.id.txtLayoutPassword);
        txtLayoutConfirmPassword = view.findViewById(R.id.txtLayoutConfirmPassword);

        inputFirstName = view.findViewById(R.id.inputFirstName);
        inputLastName = view.findViewById(R.id.inputLastName);
        inputEmail = view.findViewById(R.id.inputEmail);
        inputNUID = view.findViewById(R.id.inputNUID);

        canteenAutoComplete = view.findViewById(R.id.inputCanteenNum); //autocomplete txt view

        inputStoreName = view.findViewById(R.id.inputStoreName);
        inputPassword = view.findViewById(R.id.inputPassword);
        inputConfirmPassword = view.findViewById(R.id.inputConfirmPassword);

        cbAgreement = view.findViewById(R.id.cbAgreement);
        signUp = view.findViewById(R.id.btnSignUp);
        login = view.findViewById(R.id.btnLogin);
        login = view.findViewById(R.id.btnLogin);
        toggleGroup = view.findViewById(R.id.toggleButton);

        //navigate to login fragment
        login.setOnClickListener(v -> Navigation.findNavController(v).navigate(R.id.navigateToLoginFragment));

        cbAgreement.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    showTermsAndConditions();
                } else {
                    signUp.setEnabled(false);
                }
            }
        });

        String[] canteenLocations = {"Canteen 1", "Canteen 2"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_dropdown_item_1line, canteenLocations);
        canteenAutoComplete.setAdapter(adapter);

        toggleGroup.setSingleSelection(true);
        toggleGroup.check(R.id.btnCustomer); // Set default selection
        toggleGroup.addOnButtonCheckedListener((group, checkedId, isChecked) -> {
            if (isChecked) {
                if (checkedId == R.id.btnCustomer) {
                    clearTextFields();
                    clearErrors();

                    dataUserType = "customer";

                    txtLayoutNUID.setVisibility(View.VISIBLE);
                    txtLayoutFirstName.setVisibility(View.VISIBLE);
                    inputLastName.setVisibility(View.VISIBLE);

                    txtLayoutCanteen.setVisibility(View.GONE); //here
                    txtLayoutStoreName.setVisibility(View.GONE);
                } else if (checkedId == R.id.btnCanteenStaff) {
                    clearTextFields();
                    clearErrors();

                    dataUserType = "canteen staff";

                    txtLayoutNUID.setVisibility(View.GONE);

                    txtLayoutFirstName.setVisibility(View.GONE);
                    txtLayoutLastName.setVisibility(View.GONE);

                    txtLayoutCanteen.setVisibility(View.VISIBLE); // here
                    txtLayoutStoreName.setVisibility(View.VISIBLE);
                }
            }
        });

        signupClick();

        firstNameTextWatcher();
        lastNameTextWatcher();
        schoolIDTextWatcher();
        emailTextWatcher();
        passwordTextWatcher();
        storeNameTextWatcher();
        canteenNumTextWatcher();

        return view;
    }

    private void signupClick(){
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isValid = true; // Track overall validity

                // Clear previous errors
                clearErrors();

                // Validate fields based on user type
                if (toggleGroup.getCheckedButtonId() == R.id.btnCustomer) {
                    dataUserType = "customer"; // Set user type to customer
                    isValid &= validateFirstName();
                    isValid &= validateLastName();
                    isValid &= validateSchoolID();
                    isValid &= validateSchoolEmail();
                    isValid &= validatePassword();
                    // Store name and canteen number are not required for customers
                } else if (toggleGroup.getCheckedButtonId() == R.id.btnCanteenStaff) {
                    dataUserType = "canteen staff"; // Set user type to canteen staff
                    isValid &= validateStaffEmail(); // Email is required for staff
                    isValid &= validateStoreName(); // Store name is required for staff
                    isValid &= validateCanteenNum(); // Canteen number is required for staff
                    isValid &= validatePassword(); // Password is required
                }

                // Check if agreement is checked
                if (!cbAgreement.isChecked()) {
                    Toast.makeText(getActivity(), "You must agree to the terms and conditions.", Toast.LENGTH_SHORT).show();
                    isValid = false;
                }

                // If all validations pass, proceed with account creation
                if (isValid) {
                    String dataFirstName = inputFirstName.getText().toString().trim();
                    String dataLastName = inputLastName.getText().toString().trim();
                    String dataEmail = inputEmail.getText().toString().trim();
                    String dataNUID = inputNUID.getText().toString().trim();
                    String dataPassword = inputPassword.getText().toString().trim();
                    String dataCanteenNum = canteenAutoComplete.getText().toString();
                    String storeName = inputStoreName.getText().toString().trim();

                    createUser(dataFirstName, dataLastName, dataNUID, dataEmail, dataPassword, dataUserType, dataEmail, storeName, dataCanteenNum);
                } else {
                    Toast.makeText(getActivity(), "Please fill in all required fields.", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void createUser (final String firstName, final String lastName, final String schoolId, final String schoolEmail,
                             final String password, final String userType, final String staffEmail,
                             final String storeName, final String canteenLocation) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, REGISTER_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(getContext(), "Registration successful", Toast.LENGTH_SHORT).show();
                        Navigation.findNavController(view).navigate(R.id.navigateToLoginFragment);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(), "Registration failed: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("pass", password);
                params.put("user_type", userType);

                if ("customer".equalsIgnoreCase(userType)) {
                    params.put("first_name", firstName);
                    params.put("last_name", lastName);
                    params.put("school_id", schoolId);
                    params.put("school_email", schoolEmail);
                } else if ("canteen staff".equalsIgnoreCase(userType)) {
                    params.put("staff_email", staffEmail);
                    params.put("store_name", storeName);
                    params.put("canteen_location", canteenLocation); // Ensure this matches ENUM values
                }
                return params;
            }
        };

        VolleySingleton.getInstance(getContext()).addToRequestQueue(stringRequest);
    }

    private void showTermsAndConditions() {
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getActivity());

        View bottomSheetView = LayoutInflater.from(getActivity()).inflate(R.layout.sheet_termsandconditions, null);

        bottomSheetDialog.setContentView(bottomSheetView);
        bottomSheetDialog.setCancelable(false);

        Button agree = bottomSheetView.findViewById(R.id.btnAgree);
        Button decline = bottomSheetView.findViewById(R.id.btnDecline);

        agree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDialog.dismiss();
                showPrivacyPolicy();
            }
        });

        decline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDialog.dismiss();
                cbAgreement.setChecked(false);
                signUp.setEnabled(false); // Ensure the button is disabled if declined

                Toast.makeText(getActivity(), "You have declined the terms and conditions", Toast.LENGTH_LONG).show();
            }
        });
        bottomSheetDialog.show();
    }

    private void showPrivacyPolicy() {
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getActivity());

        View bottomSheetView = LayoutInflater.from(getActivity()).inflate(R.layout.sheet_privacypolicy, null);

        bottomSheetDialog.setContentView(bottomSheetView);
        bottomSheetDialog.setCancelable(false);

        Button agree = bottomSheetView.findViewById(R.id.btnAgree);
        Button decline = bottomSheetView.findViewById(R.id.btnDecline);

        agree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDialog.dismiss();
                cbAgreement.setChecked(true);
                signUp.setEnabled(true);
            }
        });

        decline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDialog.dismiss();
                cbAgreement.setChecked(false);
                signUp.setEnabled(false);

                Toast.makeText(getActivity(), "You have declined the privacy policy.", Toast.LENGTH_SHORT).show();
            }
        });
        bottomSheetDialog.show();
    }

    private void firstNameTextWatcher() {
        inputFirstName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                txtLayoutFirstName.setError(null);
                txtLayoutFirstName.setErrorEnabled(false);
            }
        });
    }

    private void lastNameTextWatcher() {
        inputLastName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                txtLayoutLastName.setError(null);
                txtLayoutLastName.setErrorEnabled(false);
            }
        });
    }

    private void schoolIDTextWatcher() {
        inputNUID.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                txtLayoutNUID.setError(null);
                txtLayoutNUID.setErrorEnabled(false);
            }
        });
    }

    private void storeNameTextWatcher() {
        inputStoreName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                txtLayoutStoreName.setError(null);
                txtLayoutStoreName.setErrorEnabled(false);
            }
        });
    }

    private void canteenNumTextWatcher() {
        canteenAutoComplete.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                txtLayoutCanteen.setError(null);
                txtLayoutCanteen.setErrorEnabled(false);
            }
        });
    }

    private void emailTextWatcher() {
        inputEmail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                txtLayoutEmail.setError(null);
                txtLayoutEmail.setErrorEnabled(false);
            }
        });
    }

    private void passwordTextWatcher() {
        inputPassword.addTextChangedListener(new TextWatcher() {
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

        inputPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    txtLayoutPassword.setEndIconMode(TextInputLayout.END_ICON_PASSWORD_TOGGLE);
                    txtLayoutConfirmPassword.setEndIconMode(TextInputLayout.END_ICON_PASSWORD_TOGGLE);
                } else {
                    txtLayoutPassword.setEndIconMode(TextInputLayout.END_ICON_NONE);
                    txtLayoutConfirmPassword.setEndIconMode(TextInputLayout.END_ICON_NONE);
                }
            }
        });

        inputConfirmPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                txtLayoutConfirmPassword.setError(null);
                txtLayoutConfirmPassword.setErrorEnabled(false);
            }
        });

        inputConfirmPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    txtLayoutConfirmPassword.setEndIconMode(TextInputLayout.END_ICON_PASSWORD_TOGGLE);
                    txtLayoutPassword.setEndIconMode(TextInputLayout.END_ICON_PASSWORD_TOGGLE);
                } else {
                    txtLayoutPassword.setEndIconMode(TextInputLayout.END_ICON_NONE);
                    txtLayoutConfirmPassword.setEndIconMode(TextInputLayout.END_ICON_NONE);
                }
            }
        });
    }

    private Boolean validateStaffEmail(){
        String val = inputEmail.getText().toString().trim();

        String emailPattern = "^[a-zA-Z]+[a-zA-Z]*[0-9]*@(gmail\\.com|yahoo\\.com)$";

        if (val.isEmpty()) {
            txtLayoutEmail.setError("This field is required.");
            return false;
        }

        Pattern pattern = Pattern.compile(emailPattern);
        Matcher matcher = pattern.matcher(val);

        if (!matcher.matches()) {
            txtLayoutEmail.setError("Invalid Email Format.");
            return false;
        }

        return true;
    }

    private Boolean validateStoreName() {
        String val = inputStoreName.getText().toString().trim();

        if (val.isEmpty()) {
            txtLayoutStoreName.setError("This field is required.");
            return false;
        }

        if (val.length() <= 5) {
            txtLayoutStoreName.setError("Store name is too short.");
            return false;
        }

        if (val.length() >= 30) {
            txtLayoutStoreName.setError("Store name is too long.");
            return false;
        }

        return true;
    }

    private Boolean validateFirstName() {
        String val = inputFirstName.getText().toString().trim();

        if (val.isEmpty()) {
            txtLayoutFirstName.setError("This field is required.");
            return false;
        }

        // Check for valid characters (letters, spaces, hyphens, apostrophes)
        String namePattern = "^[a-zA-Z\\s'-]+$"; // Allows letters, spaces, hyphens, and apostrophes
        Pattern pattern = Pattern.compile(namePattern);
        Matcher matcher = pattern.matcher(val);

        if (!matcher.matches()) {
            txtLayoutFirstName.setError("Invalid name format.");
            return false;
        }

        return true;
    }

    private Boolean validateLastName() {
        String val = inputLastName.getText().toString().trim();

        if (val.isEmpty()) {
            txtLayoutLastName.setError("This field is required.");
            return false;
        }

        // Check for valid characters (letters, spaces, hyphens, apostrophes)
        String namePattern = "^[a-zA-Z\\s'-]+$"; // Allows letters, spaces, hyphens, and apostrophes
        Pattern pattern = Pattern.compile(namePattern);
        Matcher matcher = pattern.matcher(val);

        if (!matcher.matches()) {
            txtLayoutLastName.setError("Invalid name format.");
            return false;
        }

        return true;
    }

    private Boolean validateSchoolID() {
        String val = inputNUID.getText().toString().trim();

        if (val.isEmpty()) {
            txtLayoutNUID.setError("This field is required.");
            return false;
        }

        if (val.length() != 11) {
            txtLayoutNUID.setError("Must be 11 characters long. (e.g. 2025-172077)");
            return false;
        }

        String regex = "^(\\d{4})-(\\d{6})$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(val);

        if (!matcher.matches()) {
            txtLayoutNUID.setError("Invalid format. Use YYYY-ID (e.g. 2025-172077)");
            return false;
        }

        return true;
    }

    private Boolean validateSchoolEmail() {
        String val = inputEmail.getText().toString().trim();

        String emailPattern = "^[a-zA-Z]+[a-zA-Z]*[0-9]*@(nu-dasma\\.edu\\.ph|students\\.nu-dasma\\.edu\\.ph)$";

        if (val.isEmpty()) {
            txtLayoutEmail.setError("This field is required.");
            return false;
        }

        Pattern pattern = Pattern.compile(emailPattern);
        Matcher matcher = pattern.matcher(val);

        if (!matcher.matches()) {
            txtLayoutEmail.setError("Invalid NU Email format.");
            return false;
        }

        return true;
    }

    private Boolean validateCanteenNum() {
        String val = canteenAutoComplete.getText().toString().trim();
        if (val.isEmpty()) {
            txtLayoutCanteen.setError("This field is required.");
            return false;
        }

        return true;
    }

    private Boolean validatePassword() {
        String val = inputPassword.getText().toString().trim();
        String val2 = inputConfirmPassword.getText().toString().trim();

        if (val.isEmpty() && val2.isEmpty()) {
            txtLayoutPassword.setError("This field is required.");
            txtLayoutConfirmPassword.setError("This field is required.");
            return false;
        }

        if (val.isEmpty() || val2.isEmpty()) {
            txtLayoutPassword.setError("This field is required.");
            txtLayoutConfirmPassword.setError("This field is required.");
            return false;
        }

        if (val.length() < 8) {
            txtLayoutPassword.setError("Password must be at least 8 characters long.");
            return false;
        }

        if (!isPasswordStrong(val)) {
            txtLayoutPassword.setError("Password must contain exactly one special character.");
            return false;
        }

        if (!val.equals(val2)) {
            txtLayoutConfirmPassword.setError("Passwords do not match.");
            return false;
        }

        return true;
    }

    private boolean isPasswordStrong(String password) {
        int specialCharCount = 0;
        for (char c : password.toCharArray()) {
            if (!Character.isLetterOrDigit(c)) {
                specialCharCount++;
            }
        }

        return specialCharCount == 1;
    }

    public void clearTextFields() {
        inputFirstName.setText("");
        inputLastName.setText("");
        inputEmail.setText("");
        inputNUID.setText("");
        inputPassword.setText("");
        inputConfirmPassword.setText("");
        canteenAutoComplete.setText("");
        inputStoreName.setText("");
        cbAgreement.setChecked(false);
    }

    public void clearErrors() {
        txtLayoutFirstName.setError(null);
        txtLayoutLastName.setError(null);
        txtLayoutEmail.setError(null);
        txtLayoutNUID.setError(null);
        txtLayoutPassword.setError(null);
        txtLayoutConfirmPassword.setError(null);
        txtLayoutCanteen.setError(null);
        txtLayoutStoreName.setError(null);

        txtLayoutFirstName.setErrorEnabled(false);
        txtLayoutLastName.setErrorEnabled(false);
        txtLayoutEmail.setErrorEnabled(false);
        txtLayoutNUID.setErrorEnabled(false);
        txtLayoutPassword.setErrorEnabled(false);
        txtLayoutConfirmPassword.setErrorEnabled(false);
        txtLayoutCanteen.setErrorEnabled(false);
        txtLayoutStoreName.setErrorEnabled(false);
    }
}
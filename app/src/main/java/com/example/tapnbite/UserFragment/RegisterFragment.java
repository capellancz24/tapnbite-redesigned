package com.example.tapnbite.UserFragment;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.tapnbite.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.regex.Pattern;

public class RegisterFragment extends Fragment {

    private TextInputEditText fullName, schoolID, nuEmail, password, confirmPassword;
    private TextInputLayout txtLayoutFullName, txtLayoutSchoolID, txtLayoutNUEmail, txtLayoutPassword, txtLayoutConfirmPassword;
    private Button login, signup;
    private CheckBox agreement;
    private DatabaseHelper DB;

    public RegisterFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register, container, false);
        DB = new DatabaseHelper(getActivity()); // Initialize DatabaseHelper
        initializeViews(view);
        setupListeners();
        return view;
    }

    private void initializeViews(View view) {
        fullName = view.findViewById(R.id.inputFullName);
        schoolID = view.findViewById(R.id.inputNUID);
        nuEmail = view.findViewById(R.id.inputNUEmail);
        password = view.findViewById(R.id.inputPassword);
        confirmPassword = view.findViewById(R.id.inputConfirmPassword);

        txtLayoutFullName = view.findViewById(R.id.txtLayoutFullName);
        txtLayoutSchoolID = view.findViewById(R.id.txtLayoutNUID);
        txtLayoutNUEmail = view.findViewById(R.id.txtLayoutEmail);
        txtLayoutPassword = view.findViewById(R.id.txtLayoutPassword);
        txtLayoutConfirmPassword = view.findViewById(R.id.txtLayoutConfirmPassword);

        login = view.findViewById(R.id.btnLogin);
        signup = view.findViewById(R.id.btnSignUp);
        agreement = view.findViewById(R.id.cbAgreement);
    }

    private void setupListeners() {
        // Navigate to LoginFragment
        login.setOnClickListener(v -> Navigation.findNavController(v).navigate(R.id.navigateToLoginFragment));

        // Handle Sign Up
        signup.setOnClickListener(v -> handleSignUp());

        // Text watchers
        setupTextWatchers();

        // Agreement checkbox
        agreement.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (!isChecked) {
                Toast.makeText(getActivity(), "Please accept terms and conditions", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void handleSignUp() {
        if (validateInputs() && agreement.isChecked()) {
            String fullNameValue = fullName.getText().toString().trim();
            String schoolIDValue = schoolID.getText().toString().trim();
            String nuEmailValue = nuEmail.getText().toString().trim();
            String passwordValue = password.getText().toString().trim();

            if (passwordValue.equals(confirmPassword.getText().toString().trim())) {
                if (!DB.checkEmail(nuEmailValue)) {
                    if (DB.addCustomerData(fullNameValue, schoolIDValue, nuEmailValue, passwordValue)) {
                        Toast.makeText(getActivity(), "Account Created Successfully!", Toast.LENGTH_SHORT).show();
                        Navigation.findNavController(getView()).navigate(R.id.navigateToLoginFragment);
                    } else {
                        Toast.makeText(getActivity(), "Failed to create account.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getActivity(), "Email already exists.", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getActivity(), "Passwords do not match.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void setupTextWatchers() {
        fullName.addTextChangedListener(createTextWatcher(txtLayoutFullName));
        schoolID.addTextChangedListener(createTextWatcher(txtLayoutSchoolID));
        nuEmail.addTextChangedListener(createTextWatcher(txtLayoutNUEmail));
        password.addTextChangedListener(createTextWatcher(txtLayoutPassword));
        confirmPassword.addTextChangedListener(createTextWatcher(txtLayoutConfirmPassword));
    }

    private TextWatcher createTextWatcher(TextInputLayout layout) {
        return new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                layout.setError(null);
                layout.setErrorEnabled(false);
            }
        };
    }

    private boolean validateInputs() {
        return validateFullName() && validateSchoolID() && validateSchoolEmail() && validatePassword();
    }

    private boolean validateFullName() {
        String val = fullName.getText().toString().trim();
        if (val.isEmpty()) {
            txtLayoutFullName.setError("This field is required.");
            return false;
        }
        if (val.split("\\s+").length < 2) {
            txtLayoutFullName.setError("Please enter both first and last name.");
            return false;
        }
        if (!Pattern.compile("^[a-zA-Z\\s'-]+$").matcher(val).matches()) {
            txtLayoutFullName.setError("Invalid name format.");
            return false;
        }
        if (val.length() < 2 || val.length() > 25) {
            txtLayoutFullName.setError("Name must be between 2 and 25 characters long.");
            return false;
        }
        return true;
    }

    private boolean validateSchoolID() {
        String val = schoolID.getText().toString().trim();
        if (val.isEmpty() || val.length() != 11 || !Pattern.compile("^(\\d{4})-(\\d{6})$").matcher(val).matches()) {
            txtLayoutSchoolID.setError("Must be 11 characters long in format YYYY-ID (e.g. 2025-172077).");
            return false;
        }
        return true;
    }

    private boolean validateSchoolEmail() {
        String val = nuEmail.getText().toString().trim();
        if (val.isEmpty() || !Pattern.compile("^[a-zA-Z]+[a-zA-Z0-9]*@(nu-dasma\\.edu\\.ph|students\\.nu-dasma\\.edu\\.ph)$").matcher(val).matches()) {
            txtLayoutNUEmail.setError("Invalid NU Email format.");
            return false;
        }
        return true;
    }

    private boolean validatePassword() {
        String val = password.getText().toString().trim();
        String val2 = confirmPassword.getText().toString().trim();
        if (val.isEmpty() || val2.isEmpty() || val.length() < 8 || !isPasswordStrong(val) || !val.equals(val2)) {
            if (val.isEmpty()) txtLayoutPassword.setError("This field is required.");
            if (val2.isEmpty()) txtLayoutConfirmPassword.setError("This field is required.");
            if (val.length() < 8) txtLayoutPassword.setError("Password must be at least 8 characters long.");
            if (!isPasswordStrong(val)) txtLayoutPassword.setError("Password must contain at least one special character.");
            if (!val.equals(val2)) txtLayoutConfirmPassword.setError("Passwords do not match.");
            return false;
        }
        return true;
    }

    private boolean isPasswordStrong(String password) {
        return password.chars().filter(c -> !Character.isLetterOrDigit(c)).count() >= 1;
    }

    // DatabaseHelper Class
    public static class DatabaseHelper extends SQLiteOpenHelper {
        private static final int DATABASE_VERSION = 1;
        private static final String DATABASE_NAME = "TapNBite.db";
        private static final String TABLE_USERS = "users";
        private static final String COLUMN_EMAIL = "email";
        private static final String COLUMN_FULL_NAME = "full_name";
        private static final String COLUMN_SCHOOL_ID = "school_id";
        private static final String COLUMN_PASSWORD = "password";

        public DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            String CREATE_USERS_TABLE = "CREATE TABLE " + TABLE_USERS + "("
                    + COLUMN_EMAIL + " TEXT PRIMARY KEY,"
                    + COLUMN_FULL_NAME + " TEXT,"
                    + COLUMN_SCHOOL_ID + " TEXT,"
                    + COLUMN_PASSWORD + " TEXT" + ")";
            db.execSQL(CREATE_USERS_TABLE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
            onCreate(db);
        }

        public boolean checkEmail(String email) {
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.query(TABLE_USERS, new String[]{COLUMN_EMAIL}, COLUMN_EMAIL + "=?",
                    new String[]{email}, null, null, null, null);
            boolean exists = cursor.getCount() > 0;
            cursor.close();
            return exists;
        }

        public boolean addCustomerData(String fullName, String schoolID, String email, String password) {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(COLUMN_FULL_NAME, fullName);
            values.put(COLUMN_SCHOOL_ID, schoolID);
            values.put(COLUMN_EMAIL, email);
            values.put(COLUMN_PASSWORD, password);

            long result = db.insert(TABLE_USERS, null, values);
            return result != -1;
        }
}
}
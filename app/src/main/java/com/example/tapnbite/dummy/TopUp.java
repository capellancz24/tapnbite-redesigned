package com.example.tapnbite.dummy;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import com.example.tapnbite.R;

public class TopUp extends AppCompatActivity {

    private EditText etAmount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_up);

        etAmount = findViewById(R.id.etAmount);
        Button btnSend = findViewById(R.id.btnSend);
        Button btnBackspace = findViewById(R.id.btnBackspace);

        int[] buttonIds = {
                R.id.btn0, R.id.btn1, R.id.btn2, R.id.btn3, R.id.btn4,
                R.id.btn5, R.id.btn6, R.id.btn7, R.id.btn8, R.id.btn9,
                R.id.btnDot
        };

        View.OnClickListener keypadClickListener = v -> {
            if (etAmount != null && v instanceof TextView) {
                String currentText = etAmount.getText().toString();
                String newText = currentText + ((TextView) v).getText().toString();
                etAmount.setText(newText);
                etAmount.setSelection(newText.length());
            }
        };

        for (int id : buttonIds) {
            View button = findViewById(id);
            if (button != null) button.setOnClickListener(keypadClickListener);
        }

        btnBackspace.setOnClickListener(v -> {
            if (etAmount != null) {
                String currentText = etAmount.getText().toString();
                if (!currentText.isEmpty()) {
                    StringBuilder sb = new StringBuilder(currentText);
                    sb.deleteCharAt(sb.length() - 1);
                    etAmount.setText(sb.toString());
                    etAmount.setSelection(sb.length());
                }
            }
        });

        btnSend.setOnClickListener(v -> {
            String amount = etAmount.getText().toString().trim();

            if (TextUtils.isEmpty(amount) || amount.equals("0") || amount.equals("0.00")) {
                etAmount.setError("Enter a valid amount");
                return;
            }

            Intent intent = new Intent(TopUp.this, WalletReceipt.class);
            intent.putExtra("amount", amount);
            startActivity(intent);
        });
    }
}

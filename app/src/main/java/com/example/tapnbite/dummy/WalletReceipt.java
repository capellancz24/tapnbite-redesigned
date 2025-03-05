package com.example.tapnbite.dummy;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.example.tapnbite.Activities.MainActivity;
import com.example.tapnbite.R;

public class WalletReceipt extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet_receipt);

        TextView tvAmount = findViewById(R.id.tvAmount);
        TextView transaction = findViewById(R.id.walletaction);
        Button btnDone = findViewById(R.id.btnDone);

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("amount")) {
            String amount = intent.getStringExtra("amount");
            tvAmount.setText("₱ " + amount);
        }

        transaction.setOnClickListener(v -> {
            Intent transactionIntent = new Intent(WalletReceipt.this, WalletTransaction.class);
            startActivity(transactionIntent);
        });

        btnDone.setOnClickListener(v -> {
            Intent tointent = new Intent(WalletReceipt.this, MainActivity.class);
            tointent.putExtra("navigateToProfile", true);
            tointent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(tointent);
            finish();
        });


    }
}

package com.example.tapnbite.dummy;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.tapnbite.R;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class WalletTransaction extends AppCompatActivity {

    private RecyclerView rvTransactions;
    private TransactionAdapter transactionAdapter;
    private List<String> transactionList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet_transaction);

        rvTransactions = findViewById(R.id.rvTransactions);
        rvTransactions.setLayoutManager(new LinearLayoutManager(this));

        ImageButton btnBack = findViewById(R.id.btnBack);

        btnBack.setOnClickListener(v -> onBackPressed());

        loadTransactions();

        transactionAdapter = new TransactionAdapter(transactionList);
        rvTransactions.setAdapter(transactionAdapter);
    }

    private void loadTransactions() {
        SharedPreferences sharedPreferences = getSharedPreferences("wallet_transactions", Context.MODE_PRIVATE);
        String transactions = sharedPreferences.getString("transactions", "");

        if (!transactions.isEmpty()) {
            transactionList = new ArrayList<>(Arrays.asList(transactions.split("\n")));
        } else {
            transactionList.add("No transactions yet.");
        }

        if (transactionAdapter != null) {
            transactionAdapter.updateTransactions(transactionList);
        }
    }
}

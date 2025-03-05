package com.example.tapnbite.dummy;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.tapnbite.R;
import java.util.List;

public class TransactionAdapter extends RecyclerView.Adapter<TransactionAdapter.TransactionViewHolder> {

    private List<String> transactionList;

    public TransactionAdapter(List<String> transactionList) {
        this.transactionList = transactionList;
    }

    @NonNull
    @Override
    public TransactionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.wallet_item_transaction, parent, false);
        return new TransactionViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TransactionViewHolder holder, int position) {
        holder.tvTransaction.setText(transactionList.get(position));
    }

    @Override
    public int getItemCount() {
        return transactionList.size();
    }

    public void updateTransactions(List<String> newTransactions) {
        this.transactionList = newTransactions;
        notifyDataSetChanged();
    }

    public static class TransactionViewHolder extends RecyclerView.ViewHolder {
        TextView tvTransaction;

        public TransactionViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTransaction = itemView.findViewById(R.id.tvTransaction);
        }
    }
}

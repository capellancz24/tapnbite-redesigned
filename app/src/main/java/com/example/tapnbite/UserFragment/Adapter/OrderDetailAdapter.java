package com.example.tapnbite.UserFragment.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tapnbite.Class.OrderDetail;
import com.example.tapnbite.R;
import java.util.List;
public class OrderDetailAdapter extends RecyclerView.Adapter<OrderDetailAdapter.ViewHolder> {
    private List<OrderDetail> orderList;

    public OrderDetailAdapter(List<OrderDetail> orderList) {
        this.orderList = orderList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.viewholder_order_detail, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        OrderDetail order = orderList.get(position);

        // Set order ID and status
        //holder.orderIdTextView.setText("Order ID: " + order.getOrderId());
        //holder.statusTextView.setText("Status: " + order.getStatus()); //-previous
        holder.tvStatus.setText(order.getStatus());

        // Display first food item's name and price (if available)
        if (!order.getFoodItems().isEmpty()) {
            //FOOD
            holder.tvFoodName.setText(order.getFoodItems().get(0).getName());
            //PRICE
            holder.tvPrice.setText(order.getFoodItems().get(0).getFoodPrice());
            //TOTAL ITEM
            holder.tvTotalItem.setText(String.valueOf(order.getFoodItems().size()));
            //DATE
            holder.tvDate.setText(order.getOrderDate());

            //STORE
            holder.tvStore.setText(order.getFoodItems().get(0).getStore());
            //CANTEEN
            holder.tvCanteenLoc.setText(order.getFoodItems().get(0).getCanteen());
        } else {
//            holder.tvFoodName.setText("Food: N/A");
//            holder.tvPrice.setText("0.00");
        }
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvOrderID, tvFoodName, tvPrice, tvStatus, tvDate, tvTotalItem, tvStore, tvCanteenLoc;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            //orderIdTextView = itemView.findViewById(R.id.tvOrderId);
            tvFoodName = itemView.findViewById(R.id.tvFoodName);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            tvStatus = itemView.findViewById(R.id.tvProcess);
            tvDate = itemView.findViewById(R.id.tvDate);
            tvTotalItem = itemView.findViewById(R.id.tvTotalItem);
            tvStore = itemView.findViewById(R.id.tvStore);
            tvCanteenLoc = itemView.findViewById(R.id.tvCanteenLoc);

        }
    }
}

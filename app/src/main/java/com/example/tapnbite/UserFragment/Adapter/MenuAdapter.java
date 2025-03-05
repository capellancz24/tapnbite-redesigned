package com.example.tapnbite.UserFragment.Adapter;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tapnbite.Class.Food;
import com.example.tapnbite.R;

import java.util.List;

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.MenuViewHolder> {

    private List<Food> menuItems;
    private OnMenuItemClickListener listener;

    public MenuAdapter(List<Food> menuItems, OnMenuItemClickListener listener) {
        this.menuItems = menuItems;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MenuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewholder_menu, parent, false);
        return new MenuViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MenuViewHolder holder, int position) {
        Food menuItem = menuItems.get(position);
        holder.bind(menuItem, listener);
    }

    @Override
    public int getItemCount() {
        return menuItems.size();
    }

    public interface OnMenuItemClickListener {
        void onMenuItemClick(Food food);
    }

    public static class MenuViewHolder extends RecyclerView.ViewHolder {

        private TextView tvFoodName;
        private TextView tvCanteen;
        private TextView tvFoodType;
        private TextView tvPrepTime;

        public MenuViewHolder(@NonNull View itemView) {
            super(itemView);
            tvFoodName = itemView.findViewById(R.id.tvFoodName);
            tvCanteen = itemView.findViewById(R.id.tvCanteen);
            tvFoodType = itemView.findViewById(R.id.tvFoodType);
            tvPrepTime = itemView.findViewById(R.id.tvPrepTime);
        }

        public void bind(final Food menuItem, final OnMenuItemClickListener listener) {
            tvFoodName.setText(menuItem.getName());
            tvCanteen.setText(menuItem.getCanteen());
            tvFoodType.setText(menuItem.getCategory());
            tvPrepTime.setText(menuItem.getPrepTime());
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onMenuItemClick(menuItem);
                }
            });
        }
    }
}

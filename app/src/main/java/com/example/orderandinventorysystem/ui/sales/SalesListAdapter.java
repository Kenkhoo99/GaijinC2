package com.example.orderandinventorysystem.ui.sales;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.orderandinventorysystem.Model.Sales;
import com.example.orderandinventorysystem.R;

import java.util.List;

public class SalesListAdapter extends RecyclerView.Adapter<SalesListAdapter.ViewHolder> {

    private List<Sales> mData;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;

    // data is passed into the constructor
    public SalesListAdapter(Context context, List<Sales> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.sales_list_layout, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Sales sales = mData.get(position);
        holder.name.setText(sales.getSaleCustName());
        holder.id.setText(sales.getSalesID());
        holder.date.setText(sales.getSalesDate());
        holder.status.setText(sales.getSalesStatus());
        holder.price.setText(String.format("%.2f", sales.getSalesPrice()));
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }

    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView name, id, date, status, price;

        ViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.cust_name_view);
            id = itemView.findViewById(R.id.sales_order_id);
            date = itemView.findViewById(R.id.sales_order_date);
            status = itemView.findViewById(R.id.sales_order_status2);
            price = itemView.findViewById(R.id.sales_order_price);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    // convenience method for getting data at click position
    public Sales getItem(int id) {
        return mData.get(id);
    }

    // allows clicks events to be caught
    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}

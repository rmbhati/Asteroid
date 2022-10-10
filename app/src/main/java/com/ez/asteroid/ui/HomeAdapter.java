package com.ez.asteroid.ui;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.ez.asteroid.R;

import java.util.ArrayList;
import java.util.HashMap;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder> {
    private ArrayList<HashMap<String, String>> listdata;
    private Context context;

    public HomeAdapter(Context context, ArrayList<HashMap<String, String>> listdata) {
        this.listdata = listdata;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.row_main, parent, false);
        return new ViewHolder(listItem);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        HashMap<String, String> map = listdata.get(position);
        holder.date.setText("Date: " + map.get("date"));
        holder.id.setText("Ast ID: " + map.get("astID"));
        holder.fast.setText("Fastest Speed: " + map.get("fast"));
        holder.close.setText("Closest Distance: " + map.get("min"));
        holder.avg.setText("Min AVG:" + map.get("min_avg") + "  Max AVG: " + map.get("max_avg"));
    }

    @Override
    public int getItemCount() {
        return listdata.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        public TextView date, id, fast, close, avg;

        public ViewHolder(View itemView) {
            super(itemView);
            date = itemView.findViewById(R.id.date);
            id = itemView.findViewById(R.id.id);
            fast = itemView.findViewById(R.id.fast);
            close = itemView.findViewById(R.id.close);
            avg = itemView.findViewById(R.id.avg);
        }
    }
}

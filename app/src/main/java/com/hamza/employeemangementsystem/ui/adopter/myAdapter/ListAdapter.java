package com.hamza.employeemangementsystem.ui.adopter.myAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hamza.employeemangementsystem.R;

import java.util.ArrayList;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {

    private ArrayList<String> list;
    private Context context;
    private final ItemClickHandler listener;
    public ListAdapter(Context context, ArrayList<String> list, ItemClickHandler listener) {
        this.context = context;
        this.list = list;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.item_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String item = list.get(position);
        holder.txtReport.setText(list.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener (){
            @Override
            public void onClick(View v) {
                if(listener!=null){
                   listener.ViewReportClick(item);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setList(ArrayList<String> list) {
        this.list=list;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtReport;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtReport = itemView.findViewById(R.id.txtReport);
        }
    }
}


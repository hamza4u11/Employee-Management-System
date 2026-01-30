package com.hamza.employeemangementsystem.ui.adopter.myAdapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hamza.employeemangementsystem.R;
import com.hamza.employeemangementsystem.data.model.Employee;

import java.util.ArrayList;
import java.util.List;

public class myAdapter extends RecyclerView.Adapter<myAdapter.holder> {

    private List<Employee> list = new ArrayList<>();
    private Context context;
    private final EmployeeClickHandler listener;
    public myAdapter(EmployeeClickHandler listener) {

        this.listener = listener;
    }
    public void setList( List<Employee> newList) {
        this.list = newList;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_profile,parent,false);
        return new holder(view);
    }

    @Override
    public void onBindViewHolder(
            @NonNull holder holder,
            int position
    ) {
        Employee employee = list.get(position);
        holder.txtName.setText(employee.name);
        holder.txtRole.setText(employee.designation);
        holder.layout.setTag(employee.id);
        holder.loginBtn.setText(String.valueOf(employee.id));
        holder.itemView.setOnClickListener(v -> {
            if(this.listener!=null){
                this.listener.onItemClick(employee);
            }
        });
    }
    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }


    class holder extends RecyclerView.ViewHolder{

        ImageView img1;
        TextView txtName, txtRole;
        LinearLayout layout;
        Button loginBtn;


        public holder(@NonNull View itemView) {
            super(itemView);
            img1= itemView.findViewById(R.id.img1);
            txtName= itemView.findViewById(R.id.txtName);
            txtRole = itemView.findViewById(R.id.txtRole);
            layout = itemView.findViewById(R.id.employeeId);
            loginBtn = itemView.findViewById(R.id.loginBtn);
        }
    }
}

package com.hamza.employeemangementsystem.ui.adopter.myAdapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.graphics.drawable.IconCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.hamza.employeemangementsystem.R;
import com.hamza.employeemangementsystem.data.model.Attendance;
import com.hamza.employeemangementsystem.data.model.Report;
import com.hamza.employeemangementsystem.utils.DateTimeUtlis;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class ReportAdapter extends RecyclerView.Adapter<ReportAdapter.ViewHolder>{

    private List<Attendance> list;
    private Context context;

    public ReportAdapter(Context context) {
        this.context = context;
    }


    @NonNull
    @Override
    public ReportAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.item_report, parent, false);
        return new ReportAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReportAdapter.ViewHolder holder, int position) {
        Attendance attendance = list.get(position);
        holder.empName.setText(attendance.name);
        holder.empStatus.setText(Objects.equals(attendance.status, "0") ? "In-Active" : "Active");
        holder.reportDate.setText(attendance.date);
        holder.checkInTime.setText( attendance.checkInTime == null ? "" : DateTimeUtlis.getShared().convertStringToTime(attendance.checkInTime));
        holder.checkoutTime.setText(attendance.checkOutTime == null ? "" : DateTimeUtlis.getShared().convertStringToTime(attendance.checkOutTime));
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }
    public void setList(List<Attendance> list) {
        this.list=list;
        notifyDataSetChanged();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView empName,empStatus, reportDate, checkInLabel,checkInTime, checkOutLabel,checkoutTime;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            empName = itemView.findViewById(R.id.empName);
            empStatus= itemView.findViewById(R.id.empStatus);
            reportDate= itemView.findViewById(R.id.reportDate);
            checkInLabel = itemView.findViewById(R.id.checkInLabel);
            checkInTime =itemView.findViewById(R.id.checkInTime);
            checkOutLabel = itemView.findViewById(R.id.checkOutLabel);
            checkoutTime = itemView.findViewById(R.id.checkOutTime);
        }
    }
}

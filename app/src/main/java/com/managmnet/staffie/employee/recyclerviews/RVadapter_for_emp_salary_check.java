package com.managmnet.staffie.employee.recyclerviews;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.managmnet.staffie.R;
import com.managmnet.staffie.employee.modles.Attendance_model;

import java.util.ArrayList;

public class RVadapter_for_emp_salary_check extends RecyclerView.Adapter<RVadapter_for_emp_salary_check.viewhodler> {

    Context context;
    ArrayList<Attendance_model> list;

    public RVadapter_for_emp_salary_check(Context context, ArrayList<Attendance_model> list)
    {
        this.context=context;
        this.list=list;
    }

    @NonNull
    @Override
    public viewhodler onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_for_emp_attedance,null,false);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        v.setLayoutParams(lp);
        return new viewhodler(v);
    }

    @Override
    public void onBindViewHolder(@NonNull viewhodler holder, int position) {

        holder.date.setText(list.get(position).getA_date());
        holder.status.setText(list.get(position).getA_status());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class viewhodler extends RecyclerView.ViewHolder{

        TextView  date,status;
        public viewhodler(View v)
        {
            super(v);

            date = v.findViewById(R.id.emp_attendance_date);
            status = v.findViewById(R.id.emp_attendance_status);

        }
    }

}

package com.managmnet.staffie.employee.recyclerviews;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.managmnet.staffie.R;
import com.managmnet.staffie.employee.EmployeeDetailTaskToComplete;
import com.managmnet.staffie.employee.modles.EmployeeDetails;

import java.util.ArrayList;

public class RVadapter_for_task_to_do extends RecyclerView.Adapter<RVadapter_for_task_to_do.viewholder>{

    Context context;
    ArrayList<EmployeeDetails> list;
    public RVadapter_for_task_to_do(Context context,ArrayList<EmployeeDetails> list)
    {
        this.context=context;
        this.list=list;
    }
    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_employee_assigned_task,null,false);

        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        v.setLayoutParams(lp);
        return new viewholder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {
        holder.task_name.setText(list.get(position).getTask_name());
        holder.task_due_date.setText(list.get(position).getTask_due_date());
        holder.task_type.setText(list.get(position).getTask_priority());
        holder.task_by.setText(list.get(position).getTask_by());

        holder.task_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle b = new Bundle();
                java.lang.String t_name = holder.task_name.getText().toString();
                java.lang.String t_due_date = holder.task_due_date.getText().toString();
                java.lang.String t_type = holder.task_type.getText().toString();
                java.lang.String t_by = holder.task_by.getText().toString();

                b.putString("task_name",t_name);
                b.putString("task_due_date",t_due_date);
                b.putString("task_start_date",list.get(position).getTask_start_Date());
                b.putString("task_type",t_type);
                b.putString("task_desc",list.get(position).getTask_desc());
                b.putString("task_priority",list.get(position).getTask_priority());
                b.putString("task_by",t_by);

                AppCompatActivity activity = (AppCompatActivity) v.getContext();
                EmployeeDetailTaskToComplete f = new EmployeeDetailTaskToComplete();
                f.setArguments(b);
                activity.getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.emp_screen_navigation_menu,f).commit();

//                Intent i = new Intent(context,EmployeeDetailTaskToComplete.class);
//
//                i.putExtra("task_name",task_name);
//                i.putExtra("task_due_date",task_due_date);
//                i.putExtra("task_type",task_type);
//                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class viewholder extends RecyclerView.ViewHolder{
        TextView task_name,task_due_date,task_type,task_by;

        public viewholder(View v)
        {
            super(v);

            task_name = v.findViewById(R.id.task_name);
            task_due_date = v.findViewById(R.id.task_due);
            task_type = v.findViewById(R.id.task_type);
            task_by = v.findViewById(R.id.task_by);
        }
    }

    public void getItemDetails(int position){

        Intent i = new Intent(context,EmployeeDetailTaskToComplete.class);
        i.putExtra("position",position);
        context.startActivity(i);
    }

}

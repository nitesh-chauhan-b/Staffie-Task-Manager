package com.managmnet.staffie.admin.recyclerviews;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.managmnet.staffie.R;
import com.managmnet.staffie.admin.fragments.AdminDetailTaskView;
import com.managmnet.staffie.admin.models.Admin_Task_Details;

import java.util.ArrayList;

public class RVadapter_for_admin_task_view extends RecyclerView.Adapter<RVadapter_for_admin_task_view.viewholder> {

    Context context;
    ArrayList<Admin_Task_Details>  list;
    public RVadapter_for_admin_task_view(Context context,  ArrayList<Admin_Task_Details>  list)
    {
        this.context=context;
        this.list=list;
    }
    @NonNull
    @Override
    public RVadapter_for_admin_task_view.viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_admin_task_view,null,false);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        v.setLayoutParams(lp);
        return new RVadapter_for_admin_task_view.viewholder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RVadapter_for_admin_task_view.viewholder holder, int position) {
        holder.task_name.setText(list.get(position).getTask_name());
        holder.task_assigned_to.setText(list.get(position).getTask_priority());
        holder.task_status.setText(list.get(position).getTask_status());


        holder.task_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle b = new Bundle();
                String t_name = holder.task_name.getText().toString();
                String t_assigned_to = holder.task_assigned_to.getText().toString();
                String t_status = holder.task_status.getText().toString();
                String t_desc = list.get(position).getTask_desc();
                String t_start_date = list.get(position).getStart_date();
                String t_due_date = list.get(position).getDue_date();
                String t_priority = list.get(position).getTask_priority();


                b.putString("task_name",t_name);
                b.putString("task_assigned_to",t_assigned_to);
                b.putString("task_status",t_status);
                b.putString("task_desc",t_desc);
                b.putString("task_start_date",t_start_date);
                b.putString("task_due_date",t_due_date);
                b.putString("task_priority",t_priority);


                AppCompatActivity activity = (AppCompatActivity) v.getContext();
                AdminDetailTaskView f = new AdminDetailTaskView();
                f.setArguments(b);
                activity.getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.adm_screen_navigation_menu,f).commit();

            }
        });

        if(list.get(position).getTask_status().equals("Done"))
        {
            holder.task_status.setBackgroundResource(R.color.greenforstatus);
        }
        else if(list.get(position).getTask_status().equals("Stuck"))
        {
            holder.task_status.setBackgroundResource(R.color.redforstatus);
        }
        else if(list.get(position).getTask_status().equals("In progress"))
        {
            holder.task_status.setBackgroundResource(R.color.orangeforstatus);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class viewholder extends RecyclerView.ViewHolder{

        TextView task_name,task_assigned_to,task_status;
        public viewholder(View v)
        {
            super(v);
            task_name = v.findViewById(R.id.adm_task_view_task_name);
            task_assigned_to = v.findViewById(R.id.adm_task_view_task_assigned_to);
            task_status = v.findViewById(R.id.adm_task_view_status);

        }
    }
}

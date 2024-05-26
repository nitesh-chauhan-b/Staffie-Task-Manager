package com.managmnet.staffie.manager.recyclerviews;

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
import com.managmnet.staffie.manager.fragments.ManagerDetailAdminTasks;
import com.managmnet.staffie.manager.models.ManagerDetails;

import java.util.ArrayList;

public class RVadapter_for_manager_admin_taskview extends RecyclerView.Adapter<RVadapter_for_manager_admin_taskview.viewholder> {

    Context context;
    ArrayList<ManagerDetails> list;
    public RVadapter_for_manager_admin_taskview(Context context,   ArrayList<ManagerDetails> list){
        this.context=context;
        this.list=list;
    }
    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_manager_assigned_task_view,null,false);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        v.setLayoutParams(lp);
        return new viewholder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {
        holder.task_name.setText(list.get(position).getTask_name());
        holder.task_desc.setText(list.get(position).getTask_desc());
        holder.task_due_date.setText(list.get(position).getTask_due_date());
        holder.task_priority.setText(list.get(position).getTask_priority());

        holder.task_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle b = new Bundle();
                String t_name = holder.task_name.getText().toString();
                String t_due_date = holder.task_due_date.getText().toString();
                String t_desc = holder.task_desc.getText().toString();


                b.putString("task_name",t_name);
                b.putString("task_start_date",list.get(position).getTask_start_date());
                b.putString("task_due_date",t_due_date);
                b.putString("task_priority",list.get(position).getTask_priority());
                b.putString("task_desc",t_desc);
                b.putString("assigned_member",list.get(position).getTask_assigned_mng());


                AppCompatActivity activity = (AppCompatActivity) v.getContext();
                ManagerDetailAdminTasks f = new ManagerDetailAdminTasks();
                f.setArguments(b);
                activity.getSupportFragmentManager().beginTransaction().addToBackStack(null).replace(R.id.mng_screen_navigation_menu,f).commit();

            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class viewholder extends RecyclerView.ViewHolder{

        TextView task_name,task_desc,task_due_date,task_priority;
        public viewholder(View v)
        {
            super(v);
            task_name = v.findViewById(R.id.mng_assigned_task_name);
            task_desc = v.findViewById(R.id.mng_assigned_task_desc);
            task_due_date = v.findViewById(R.id.mng_assigned_task_due_time);
            task_priority = v.findViewById(R.id.mng_assigned_task_priority);

        }
    }
}

package com.managmnet.staffie.manager.recyclerviews;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import com.managmnet.staffie.R;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.managmnet.staffie.employee.modles.EmployeeDetails;

import java.util.ArrayList;

public class RVadapter_for_manager_adding_employee extends RecyclerView.Adapter<RVadapter_for_manager_adding_employee.viewholder>{

    Context context;
    ArrayList<EmployeeDetails> list;
    public RVadapter_for_manager_adding_employee(Context context, ArrayList<EmployeeDetails> list)
    {
        this.context=context;
        this.list=list;
    }
    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_for_mng_adding_emp,null);
        return new viewholder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {
        holder.adding_emp_name.setText(list.get(position).getAssigned_task_emp_name());

        holder.remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class viewholder extends RecyclerView.ViewHolder{

        EditText adding_emp_name;
        ImageView remove;
        public viewholder(View v)
        {
            super(v);
            adding_emp_name = v.findViewById(R.id.adding_emp_name);
            remove = v.findViewById(R.id.remove_emp_icon);
        }
    }
}

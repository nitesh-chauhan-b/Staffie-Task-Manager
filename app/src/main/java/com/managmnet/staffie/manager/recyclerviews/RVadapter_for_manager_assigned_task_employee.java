package com.managmnet.staffie.manager.recyclerviews;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.managmnet.staffie.R;
import com.managmnet.staffie.employee.modles.EmployeeDetails;

import java.util.ArrayList;

public class RVadapter_for_manager_assigned_task_employee extends RecyclerView.Adapter<RVadapter_for_manager_assigned_task_employee.viewholder> {
    ArrayList<EmployeeDetails> list;
    Context context;
    public RVadapter_for_manager_assigned_task_employee(Context context, ArrayList<EmployeeDetails> list){
        this.context=context;
        this.list=list;
    }
    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_for_mnager_assigned_task_member,null,false);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        v.setLayoutParams(lp);
        return new viewholder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {
        holder.emp_name.setText(list.get(position).getAssigned_task_emp_name());

        holder.menu_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //creating a popup menu
                PopupMenu popup = new PopupMenu(context, holder.menu_icon);
                //inflating menu from xml resource
                popup.inflate(R.menu.mng_assigned_task_emp_menu);
                //adding click listener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {

                        if(item.getItemId()==R.id.mng_assigned_task_emp_menu)
                        {
                            try{
                                for(int i=0; i<position; i++)
                                {
                                    remove_emp(position);
                                }
                            }
                            catch(Exception e){
                            Toast.makeText(context,e.getMessage(),Toast.LENGTH_SHORT).show();
                            }
                        }
                        return false;
                    }
                });
                popup.show();

            }
        });

    }
    @Override
    public int getItemCount() {
        return list.size();
    }

    class viewholder extends RecyclerView.ViewHolder{
        TextView emp_name;
        ImageView menu_icon;
        public viewholder(View v)
        {
            super(v);
            emp_name = v.findViewById(R.id.mng_assigned_task_emp_name);
            menu_icon = v.findViewById(R.id.mng_emp_menu);
        }
    }

    public void remove_emp(int position)
    {

        list.remove(position);
        notifyItemRemoved(position);
//        for(int i=position; i<(list.size());i++)
//        {
//            list.set(position-1,list.get(position));
//        }
    }
}


package com.managmnet.staffie.manager.fragments;

import android.database.Observable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.Toolbar;

import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.managmnet.staffie.R;
import com.managmnet.staffie.employee.modles.EmployeeDetails;
import com.managmnet.staffie.manager.models.ManagerDetails;
import com.managmnet.staffie.manager.navigationfragments.Manager_Screen_Fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import kotlinx.coroutines.DisposableHandle;


public class ManagerDetailTaskView extends Fragment implements Toolbar.OnMenuItemClickListener {

    ImageView back;
    ArrayList<EmployeeDetails> list;
    ArrayList<java.lang.String> edited_emp_names;
    ArrayList<String> list_in_string;

    int list_pos=0;
    RecyclerView rv;
    LinearLayout assigned_task_emp_list;
    TextView task_name,task_due_date,task_instructions;

    DatabaseReference reference;
    String t_name, t_assigned_to, t_status, t_start_date, t_due_date, t_desc, t_priority;
    View assigned_emp_view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v= inflater.inflate(R.layout.fragment_manager_detail_task_view, null);

        back = v.findViewById(R.id.back_button);
        task_name = v.findViewById(R.id.mng_assigned_task_name);
        task_due_date = v.findViewById(R.id.mng_assigned_task_due_date);
        task_instructions = v.findViewById(R.id.mng_assigned_task_instruction);
        assigned_task_emp_list = v.findViewById(R.id.assigned_task_emp_list);
        assigned_task_emp_list.removeAllViews();



        reference = FirebaseDatabase.getInstance().getReference("Tasks/Manager");
        list_in_string = new ArrayList<>();


        BottomAppBar ba = getActivity().findViewById(R.id.bottom_menu);
        FloatingActionButton fa = getActivity().findViewById(R.id.create_task_menu);
        fa.setVisibility(View.GONE);
        ba.setVisibility(View.GONE);


        Toolbar toolbar;
        toolbar = (Toolbar) v.findViewById(R.id.mng_emp_toolbar);
        toolbar.inflateMenu(R.menu.mng_assigned_task_menu);
        toolbar.setOnMenuItemClickListener(this);

        list = new ArrayList<>();
        edited_emp_names = new ArrayList<>();


        Bundle b = getArguments();
        assert b != null;
        t_name = b.getString("task_name");
        t_assigned_to = b.getString("task_assigned_to");
        t_status = b.getString("task_status");
        t_start_date = b.getString("task_start_date");
        t_due_date = b.getString("task_due_date");
        t_desc = b.getString("task_desc");
        t_priority = b.getString("task_priority");


        task_name.setText(t_name);
        task_due_date.setText(t_due_date);
        task_instructions.setText(t_desc);


        display_member_list();

        task_name.setText(t_name);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ManagerTaskView f = new ManagerTaskView();
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.mng_screen_navigation_menu,f);
                getActivity().getSupportFragmentManager().popBackStack();

                ft.addToBackStack("something");
                ft.commit();

            }
        });




        Bundle edited_details = getArguments();
        int e_is_called = edited_details.getInt("iscalled");

        if(e_is_called==1)
        {
            java.lang.String e_task_name = edited_details.getString("task_name");
            java.lang.String e_task_start_date = edited_details.getString("task_start_date");
            java.lang.String e_task_due_date = edited_details.getString("task_due_date");
            java.lang.String e_task_desc = edited_details.getString("task_desc");
            edited_emp_names = edited_details.getStringArrayList("edit_emp_names");

//            task_name.setText(e_task_name);
//            task_due_date.setText(e_task_due_date);
            task_instructions.setText(e_task_desc);

        }
//        refresh_list();

        return v;
    }
    public void refresh_list() {
        if (list.size() == 0) {
//            Toast.makeText(getActivity(), "Empty list", Toast.LENGTH_SHORT).show();
        } else {
            for (int i = 0; i < list.size(); i++) {
                assigned_employee(list.get(i).toString(), i);
            }
        }
    }
    public void display_member_list() {
//        list.clear();
        for(int i=0; i<list.size(); i++)
        {
            list.remove(i);
        }
        assigned_task_emp_list.removeAllViews();
        reference.child(t_name).addValueEventListener(new ValueEventListener() {
            private Object HashMap;

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if(snapshot.exists())
                {
                    Map map = (Map) snapshot.getValue();
                    try{
                        ArrayList<String> got_list= (ArrayList<String>) map.get("assigned_member");
                        if (got_list == null) {
                            Toast.makeText(getActivity(), "No Member is assigned!", Toast.LENGTH_SHORT).show();
                        } else {
                            for (int i = 0; i < got_list.size(); i++) {
                                list.add(new EmployeeDetails(got_list.get(i)));
                                list_in_string.add(got_list.get(i));
                                assigned_employee(list.get(i).getAssigned_task_emp_name(), i);
                            }
                        }
                    }
                    catch (Exception e)
                    {
                        Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
                    }

                }
                }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        ((AppCompatActivity)getActivity()).getSupportActionBar().hide();
    }

    @Override
    public void onStop() {
        super.onStop();
        ((AppCompatActivity)getActivity()).getSupportActionBar().show();
    }

    public void assigned_employee(java.lang.String added_name,int Pos){
        View assigned_emp_view = getLayoutInflater().inflate(R.layout.rv_for_mnager_assigned_task_member,null);
        if (added_name == null) {

        }
        else{
            TextView emp_name = assigned_emp_view.findViewById(R.id.mng_assigned_task_emp_name);
            emp_name.setText(added_name);
            list_pos++;

            ImageView remove_emp= assigned_emp_view.findViewById(R.id.mng_emp_menu);
            remove_emp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    remove_emp(assigned_emp_view,emp_name.getText().toString(), Pos);
                }
            });

            assigned_task_emp_list.addView(assigned_emp_view);
        }

    }

    public void remove_emp(View v,String emp_name, int pos)
    {
        reference.child(t_name).child("assigned_member").child(String.valueOf(pos)).removeValue();
//        list.remove(pos);
//        assigned_task_emp_list.removeAllViews();
        list.clear();
        assigned_task_emp_list.removeAllViews();
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        if(item.getItemId()==R.id.mng_assigned_task_menu)
        {
            ManagerAssignedTask_Edit f = new ManagerAssignedTask_Edit();
            Bundle list_details = new Bundle();
            list_details.putSerializable("list",list);
            list_details.putStringArrayList("real_list",list_in_string);
            list_details.putString("task_name",t_name);
            list_details.putString("task_instruction",t_desc);
            list_details.putString("task_due_date",t_due_date);
            list_details.putString("task_start_date", t_start_date);
            list_details.putString("task_priority", t_priority);
            list_details.putString("task_status", t_status);

            f.setArguments(list_details);
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.replace(R.id.mng_screen_navigation_menu,f);
            getActivity().getSupportFragmentManager().popBackStack();

            ft.addToBackStack("something");
            ft.commit();
        }
        else if(item.getItemId()==R.id.mng_assigned_task_menu_delete)
        {
            reference.child(t_name).setValue(null);
            ManagerTaskView f = new ManagerTaskView();
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.replace(R.id.mng_screen_navigation_menu,f);
            getActivity().getSupportFragmentManager().popBackStack();

            ft.addToBackStack("something");
            ft.commit();
        }
        return false;
    }


}
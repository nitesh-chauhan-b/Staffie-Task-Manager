package com.managmnet.staffie.manager.fragments;

import android.app.ProgressDialog;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.managmnet.staffie.R;
import com.managmnet.staffie.employee.EmployeeCompletedTasks;
import com.managmnet.staffie.employee.EmployeeScreen;
import com.managmnet.staffie.employee.modles.EmployeeDetails;
import com.managmnet.staffie.employee.modles.Files;
import com.managmnet.staffie.employee.recyclerviews.Rvadapter_for_employee_submitted_tasks;
import com.managmnet.staffie.manager.ManagerScreen;
import com.managmnet.staffie.manager.recyclerviews.Rvadapter_for_employee_submitted_tasks_mng;

import java.util.ArrayList;
import java.util.Map;


public class Emp_Mng_DetailCompletedTaskView extends Fragment implements Toolbar.OnMenuItemClickListener {

    ImageView back;
    TextView task_name,task_due_date,task_instructions;

    String t_name, t_assigned_to, t_status, t_start_date, t_due_date, t_desc, t_priority,t_type,t_by;
    Button submit_btn;

    LinearLayout adding_layout;
    ArrayList<String> list_in_string;
    LinearLayout assigned_task_emp_list;

    ArrayList<EmployeeDetails> list;
    DatabaseReference reference;
    ManagerScreen managerScreen;
    RecyclerView rv;

    int count=0;

    //Selecting Uploading File
    Uri selectedFileUri;
    FirebaseStorage storage;
    FirebaseDatabase db;
    ProgressDialog progress;
    FirebaseAuth auth;
    String File_name;
    FirebaseUser user;
    String filename;
    ArrayList<Files> all_files;
    Rvadapter_for_employee_submitted_tasks_mng adapter;


    //For Editing
    ArrayList<java.lang.String> edited_emp_names;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View  v = inflater.inflate(R.layout.fragment_manager_detail_completed_task_view, container, false);


        back = v.findViewById(R.id.back_button);
        task_name = v.findViewById(R.id.mng_completed_task_name);
        task_due_date = v.findViewById(R.id.emp_completed_task_due_date);
        task_instructions = v.findViewById(R.id.emp_completed_task_instruction);
//        submit_btn = v.findViewById(R.id.emp_completed_task_undo_submit);


        managerScreen= (ManagerScreen) getActivity();
        assert managerScreen != null;
        managerScreen.toolbar.setVisibility(View.GONE);
        managerScreen.setDrawerEnabled(false);
        managerScreen.create_button.setVisibility(View.GONE);
        managerScreen.bottomAppBar.setVisibility(View.GONE);

        Toolbar toolbar;
        toolbar = (Toolbar) v.findViewById(R.id.mng_emp_toolbar);
        toolbar.inflateMenu(R.menu.mng_assigned_task_menu);
        toolbar.setOnMenuItemClickListener(this);


        assigned_task_emp_list = v.findViewById(R.id.assigned_task_mng_list);
        rv = v.findViewById(R.id.emp_uploaded_tasks);
        assigned_task_emp_list.removeAllViews();
        list = new ArrayList<>();
        all_files = new ArrayList<>();


        storage = FirebaseStorage.getInstance();
        db = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();

        user= auth.getCurrentUser();


        reference = FirebaseDatabase.getInstance().getReference("Tasks/Manager");
        list_in_string = new ArrayList<>();


        Bundle b = getArguments();
        assert b != null;
        t_name = b.getString("task_name");
        t_due_date = b.getString("task_due_date");
        t_type = b.getString("task_type");
        t_by = b.getString("task_by");
        t_start_date = b.getString("task_start_date");
        t_desc = b.getString("task_desc");
        t_priority = b.getString("task_priority");
        t_status = b.getString("task_status");

        task_name.setText(t_name);
        task_due_date.setText(t_start_date+" - "+t_due_date);
        task_instructions.setText(t_desc);

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

        display_member_list();
        firebase_files();

        LinearLayoutManager lm = new LinearLayoutManager(getActivity());
        rv.setLayoutManager(lm);


        Bundle edited_details = getArguments();
        int e_is_called = edited_details.getInt("iscalled");

        if(e_is_called==1)
        {
            java.lang.String e_task_name = edited_details.getString("task_name");
            java.lang.String e_task_start_date = edited_details.getString("task_start_date");
            java.lang.String e_task_due_date = edited_details.getString("task_due_date");
            java.lang.String e_task_desc = edited_details.getString("task_desc");
            edited_emp_names = edited_details.getStringArrayList("edit_emp_names");

            task_name.setText(e_task_name);
//            task_due_date.setText(e_task_start_date+" - "+e_task_due_date);
            task_instructions.setText(e_task_desc);

        }

        return v;
    }


    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }


    public void display_member_list() {
        for(int i=0; i<list.size(); i++)
        {
            list.remove(i);
        }
        assigned_task_emp_list.removeAllViews();

        reference.child(t_name).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                try{
                    if(snapshot.exists())
                    {
                        Map map = (Map) snapshot.getValue();

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

                }
                catch (Exception e)
                {
//                    Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void assigned_employee(java.lang.String added_name,int Pos){
        View assigned_emp_view = getLayoutInflater().inflate(R.layout.rv_for_mnager_assigned_task_member,null);
        if (added_name == null) {

        }
        else{
            TextView emp_name = assigned_emp_view.findViewById(R.id.mng_assigned_task_emp_name);
            emp_name.setText(added_name);
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
        list.clear();
        assigned_task_emp_list.removeAllViews();
    }

    public void firebase_files(){

        FirebaseRecyclerOptions<Files> options;
        options = new FirebaseRecyclerOptions.Builder<Files>()
                .setQuery(FirebaseDatabase.getInstance().getReference().child("Completed Tasks").child("submitted").child("Employee").child(user.getUid()).child(t_name),
                        Files.class)
                .build();

        adapter = new Rvadapter_for_employee_submitted_tasks_mng(options);
        rv.setAdapter(adapter);
    }


    @Override
    public boolean onMenuItemClick(MenuItem item) {

        if(item.getItemId()==R.id.mng_assigned_task_menu && count==0)
        {
            count=1;
            ManagerAssignedTask_Edit f = new ManagerAssignedTask_Edit();

            Bundle list_details = new Bundle();
            list_details.putSerializable("list",list);
            list_details.putStringArrayList("real_list",list_in_string);
            list_details.putString("task_name",t_name);
            list_details.putString("task_instruction",task_instructions.getText().toString());
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
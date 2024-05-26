package com.managmnet.staffie.manager.fragments;

import android.app.ProgressDialog;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
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
import com.managmnet.staffie.employee.modles.EmployeeDetails;
import com.managmnet.staffie.employee.modles.Files;
import com.managmnet.staffie.manager.ManagerScreen;
import com.managmnet.staffie.manager.recyclerviews.RVadapter_for_mng_detail_completed_task_view;

import java.util.ArrayList;
import java.util.Map;


public class ManagerDetailCompletedTaskVeiw extends Fragment {


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
    RVadapter_for_mng_detail_completed_task_view adapter;
    FirebaseDatabase database;
    DatabaseReference db_ref;


    ProgressDialog progressDialog;
    int count=0;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v= inflater.inflate(R.layout.fragment_manager_detail_completed_task_veiw, container, false);


        back = v.findViewById(R.id.back_button);
        task_name = v.findViewById(R.id.mng_completed_task_name);
        task_due_date = v.findViewById(R.id.emp_completed_task_due_date);
        task_instructions = v.findViewById(R.id.emp_completed_task_instruction);




        managerScreen= (ManagerScreen) getActivity();
        assert managerScreen != null;
        managerScreen.toolbar.setVisibility(View.GONE);
        managerScreen.setDrawerEnabled(false);
        managerScreen.create_button.setVisibility(View.GONE);
        managerScreen.bottomAppBar.setVisibility(View.GONE);

        assigned_task_emp_list = v.findViewById(R.id.assigned_task_mng_list);
        rv = v.findViewById(R.id.emp_uploaded_tasks);
        assigned_task_emp_list.removeAllViews();
        list = new ArrayList<>();
        all_files = new ArrayList<>();


        storage = FirebaseStorage.getInstance();
        db = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();

        user= auth.getCurrentUser();


        reference = FirebaseDatabase.getInstance().getReference("Tasks/Admin");
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
                    Map map = (Map) snapshot.getValue();
//                        HashMap<String,String> map = new HashMap<String,String>();

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

//                    Toast.makeText(getActivity(), "Error :"+e.getMessage(), Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
//                Toast.makeText(getActivity(), "Error :"+error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    public void assigned_employee(java.lang.String added_name,int Pos){
        View assigned_emp_view = getLayoutInflater().inflate(R.layout.rv_manager_names_of_task,null);
        if (added_name == null) {

        }
        else{
            TextView emp_name = assigned_emp_view.findViewById(R.id.mng_assigned_task_emp_name);
            emp_name.setText(added_name);

            assigned_task_emp_list.addView(assigned_emp_view);
        }

    }

    public void firebase_files(){

        FirebaseRecyclerOptions<Files> options;
        options = new FirebaseRecyclerOptions.Builder<Files>()
                .setQuery(FirebaseDatabase.getInstance().getReference().child("Completed Tasks").child("submitted").child("Manager").child(user.getUid()).child(t_name),
                        Files.class)
                .build();

        adapter = new RVadapter_for_mng_detail_completed_task_view(options);
        rv.setAdapter(adapter);
    }



}
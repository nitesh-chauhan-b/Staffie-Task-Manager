package com.managmnet.staffie.employee;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.managmnet.staffie.R;
import com.managmnet.staffie.employee.modles.EmployeeDetails;
import com.managmnet.staffie.employee.recyclerviews.RVadapter_for_task_to_do;

import java.util.ArrayList;
import java.util.Map;


public class EmployeeTaskToComplete extends Fragment {

    RecyclerView rv;
    ArrayList<EmployeeDetails> list;
    FirebaseDatabase database;
    DatabaseReference db_ref;
    EmployeeScreen employeeScreen;
    ProgressDialog progressDialog;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        View v= inflater.inflate(R.layout.fragment_employee_task_to_complete, null);

        rv = v.findViewById(R.id.task_to_complete_rv);
        list= new ArrayList<>();

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Please wait");

        progressDialog.show();
        database = FirebaseDatabase.getInstance();

        db_ref = FirebaseDatabase.getInstance().getReference("Tasks/Manager/");

        employeeScreen = (EmployeeScreen) getActivity();
        employeeScreen.toolbar.setVisibility(View.VISIBLE);
        employeeScreen.bottomAppBar.setVisibility(View.VISIBLE);
        employeeScreen.setDrawerEnabled(true);


        db_ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {

                    for (DataSnapshot snap : snapshot.getChildren()) {

                        java.lang.String by = "Manager";
                        Map map = (Map) snap.getValue();
                        java.lang.String t_name = map.get("task_name").toString();
                        java.lang.String t_start_date = map.get("start_date").toString();
                        java.lang.String t_due_date = map.get("due_date").toString();
                        java.lang.String t_desc = map.get("task_description").toString();
                        java.lang.String t_priority = map.get("task_priority").toString();

                        EmployeeDetails user = new EmployeeDetails();
                        user.setTask_name(t_name);
                        user.setTask_start_Date(t_start_date);
                        user.setTask_desc(t_desc);
                        user.setTask_due_date(t_due_date);
                        user.setTask_priority(t_priority);
                        user.setTask_by(by);
                        list.add(user);
//                        list.add(new EmployeeDetails(t_name,t_due_date,t_priority,by));
                        refresh();
                        progressDialog.dismiss();
                    }
                } else {
                    Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        //Scrolling Animation
        final int[] state = new int[1];

        rv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {

                super.onScrollStateChanged(recyclerView, newState);
                state[0]= newState;

            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if(dy>0 && (state[0]==0 || state[0]==2))
                {
                    hidetoolbar();
                }
                else if(dy<-10){
                    showtoolbar();
                }

            }
        });



        RVadapter_for_task_to_do adapter = new RVadapter_for_task_to_do(getActivity(),list);
        LinearLayoutManager lm = new LinearLayoutManager(getActivity());


        rv.setLayoutManager(lm);
        rv.setAdapter(adapter);

        return v;
    }

    private void hidetoolbar() {
        employeeScreen.toolbar.setVisibility(View.GONE);
        employeeScreen.bottomAppBar.setVisibility(View.GONE);

    }

    private void showtoolbar() {
        employeeScreen.toolbar.setVisibility(View.VISIBLE);
        employeeScreen.bottomAppBar.setVisibility(View.VISIBLE);
    }
    public void refresh() {
        RVadapter_for_task_to_do adapter = new RVadapter_for_task_to_do(getActivity(),list);
        LinearLayoutManager lm = new LinearLayoutManager(getActivity());
        rv.setLayoutManager(lm);
        rv.setAdapter(adapter);
    }

}
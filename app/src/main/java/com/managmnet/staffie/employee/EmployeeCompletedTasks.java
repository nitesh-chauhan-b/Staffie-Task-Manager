package com.managmnet.staffie.employee;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

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
import com.managmnet.staffie.employee.recyclerviews.RVadapter_for_completed_task;
import com.managmnet.staffie.employee.recyclerviews.RVadapter_for_task_to_do;
import com.managmnet.staffie.manager.fragments.ManagerAdminTasks;

import java.util.ArrayList;
import java.util.Map;


public class EmployeeCompletedTasks extends Fragment {
    RecyclerView rv;
    ImageView back;
    ArrayList<EmployeeDetails> list;
    EmployeeScreen employeeScreen;
    Toolbar toolbar;

    FirebaseDatabase database;
    DatabaseReference db_ref;
    FirebaseUser user;
    FirebaseAuth auth;
    ProgressDialog progressDialog;
    int count=0;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v= inflater.inflate(R.layout.fragment_employee_completed_tasks, null);

        rv = v.findViewById(R.id.competed_task_rv);
        list= new ArrayList<>();
        back = v.findViewById(R.id.back_button);
        toolbar = v.findViewById(R.id.emp_toolbar);

        employeeScreen = (EmployeeScreen) getActivity();
        employeeScreen.toolbar.setVisibility(View.GONE);
        employeeScreen.bottomAppBar.setVisibility(View.VISIBLE);
        employeeScreen.setDrawerEnabled(false);


        auth =FirebaseAuth.getInstance();
        user = auth.getCurrentUser();

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Please wait");


        database = FirebaseDatabase.getInstance();

        db_ref = FirebaseDatabase.getInstance().getReference("Completed Tasks/submitted/Employee/");

        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser f_user = auth.getCurrentUser();
        db_ref.child(user.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    progressDialog.show();
                    for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                        for (DataSnapshot snap : snapshot1.getChildren()) {


                        Map map = (Map) snap.getValue();
                            String u_name = f_user.getDisplayName();
                        boolean is_task_submitted = (boolean) map.get("is_submitted");
                        if(is_task_submitted)
                        {
                            count=count+1;
                            String by = "Employee";
                            java.lang.String t_name = map.get("task_name").toString();
                            java.lang.String t_start_date = map.get("task_start_date").toString();
                            java.lang.String t_due_date = map.get("task_due_date").toString();
                            java.lang.String t_desc = map.get("task_desc").toString();
                            java.lang.String t_priority = map.get("task_priority").toString();

                            EmployeeDetails user = new EmployeeDetails();
                            user.setTask_name(t_name);
                            user.setTask_start_Date(t_start_date);
                            user.setTask_desc(t_desc);
                            user.setTask_due_date(t_due_date);
                            user.setTask_priority(t_priority);
                            user.setTask_by(by);
//                            if(!u_name.equals("")){
//                                user.setTask_by(f_user.getDisplayName());
//                            }
//                            else{


//                            }

                                list.add(user);
                                refresh();
                            refresh();
//                        list.add(new EmployeeDetails(t_name,t_due_date,t_priority,by));
                            progressDialog.dismiss();
                        }
                        progressDialog.dismiss();

                    }}
                } else {
                    Toast.makeText(getActivity(), "No task is been completed!", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                progressDialog.dismiss();
            }
        });



        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EmployeeTaskToComplete f = new EmployeeTaskToComplete();
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.emp_screen_navigation_menu,f);
                getActivity().getSupportFragmentManager().popBackStack();

                ft.addToBackStack("something");
                ft.commit();
                refresh();
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



        RVadapter_for_completed_task adapter = new RVadapter_for_completed_task(getActivity(),list);
        rv.setAdapter(adapter);
        LinearLayoutManager lm = new LinearLayoutManager(getActivity());
        rv.setLayoutManager(lm);
        return v;
    }


    @Override
    public void onResume() {
        super.onResume();
        refresh();
    }

    public void refresh() {

        LinearLayoutManager lm = new LinearLayoutManager(getActivity());
        rv.setLayoutManager(lm);

        RVadapter_for_completed_task adapter = new RVadapter_for_completed_task(getActivity(),list);
        rv.setAdapter(adapter);
    }
    private void hidetoolbar() {
        toolbar.setVisibility(View.GONE);
        back.setVisibility(View.GONE);
        employeeScreen.bottomAppBar.setVisibility(View.GONE);

    }

    private void showtoolbar() {
        toolbar.setVisibility(View.VISIBLE);
        back.setVisibility(View.VISIBLE);
        employeeScreen.bottomAppBar.setVisibility(View.VISIBLE);
    }

}
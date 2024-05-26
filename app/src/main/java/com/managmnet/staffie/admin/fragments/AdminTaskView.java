package com.managmnet.staffie.admin.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.managmnet.staffie.R;
import com.managmnet.staffie.admin.AdminScreen;
import com.managmnet.staffie.admin.models.Admin_Task_Details;
import com.managmnet.staffie.admin.navigationfragments.DrawerLocker;
import com.managmnet.staffie.admin.recyclerviews.RVadapter_for_admin_task_view;

import java.util.ArrayList;
import java.util.Map;

public class AdminTaskView extends Fragment {
    RecyclerView rv;

    private static AdminTaskView instance = null;
    ArrayList<Admin_Task_Details> task_details;

    FirebaseDatabase database;
    DatabaseReference db_ref;
    AdminDetailTaskView d;
    FloatingActionButton fa;
    ProgressDialog progressDialog;

    AdminScreen adminScreen;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_admin_task_view, null);
        instance = this;
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setTitle("Loading...");
        progressDialog.setMessage("Please wait");

//        String name = getActivity().getSupportFragmentManager().getBackStackEntryAt(0).getName();
//        getActivity().getSupportFragmentManager().popBackStack(name, FragmentManager.POP_BACK_STACK_INCLUSIVE);


        rv = v.findViewById(R.id.admin_task_view_rv);
        d = new AdminDetailTaskView();
        BottomAppBar ba = getActivity().findViewById(R.id.bottom_menu);
        fa= getActivity().findViewById(R.id.create_task_menu);
        fa.setVisibility(View.VISIBLE);
        ba.setVisibility(View.VISIBLE);
        ((DrawerLocker) getActivity()).setDrawerEnabled(true);

//        ba.setBackgroundColor(getResources().getColor(android.R.color.transparent));

        task_details = new ArrayList<>();

        database = FirebaseDatabase.getInstance();
        db_ref = FirebaseDatabase.getInstance().getReference("Tasks/Admin/");

        get_all_data();

        //Hiding Toolbar on Scroll
        final int[] state = new int[1];
        adminScreen = (AdminScreen) getActivity();
        adminScreen.toolbar.setVisibility(View.VISIBLE);
        adminScreen.bottomAppBar.setVisibility(View.VISIBLE);
        adminScreen.create_button.setVisibility(View.VISIBLE);

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


        refresh();
        RVadapter_for_admin_task_view adapter = new RVadapter_for_admin_task_view(getActivity(), task_details);
        LinearLayoutManager lm = new LinearLayoutManager(getActivity());

        rv.setLayoutManager(lm);
        rv.setAdapter(adapter);
        return v;
    }

    private void hidetoolbar() {
        adminScreen.toolbar.setVisibility(View.GONE);
        adminScreen.create_button.setVisibility(View.GONE);
        adminScreen.bottomAppBar.setVisibility(View.GONE);

    }

    private void showtoolbar() {
        adminScreen.toolbar.setVisibility(View.VISIBLE);
        adminScreen.create_button.setVisibility(View.VISIBLE);
        adminScreen.bottomAppBar.setVisibility(View.VISIBLE);
    }

    public static AdminTaskView getInstance() {
        return instance;
    }

    public void get_all_data()
    {
        fa.setBackgroundColor(getResources().getColor(android.R.color.transparent));
        progressDialog.show();
        db_ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                try{
                    if (snapshot.exists()) {


                        for (DataSnapshot snap : snapshot.getChildren()) {

                            String status = "In progress";
                            Map map = (Map) snap.getValue();
                            String t_name = map.get("task_name").toString();
                            String t_start_date = map.get("start_date").toString();
                            String t_due_date = map.get("due_date").toString();
                            String t_desc = map.get("task_description").toString();
                            String t_priority = map.get("task_priority").toString();
                            String t_status = map.get("task_status").toString();


                            if(t_name.equals(""))
                            {
                                Toast.makeText(adminScreen, "Task name is emplty", Toast.LENGTH_SHORT).show();
                            }
                            
                            Admin_Task_Details user = new Admin_Task_Details();
                            user.setTask_name(t_name);
                            user.setStart_date(t_start_date);
                            user.setDue_date(t_due_date);
                            user.setTask_desc(t_desc);
                            user.setTask_priority(t_priority);
                            user.setTask_status(t_status);

                            task_details.add(user);
                            refresh();
                            progressDialog.dismiss();
                        }
                    } else {
                        Toast.makeText(getActivity(), "No Task is been assigned", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();

                    }
                }
                catch(Exception e)
                {
                    Toast.makeText(getActivity(), "Message :"+e.getMessage(), Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                progressDialog.dismiss();
            }
        });
    }
    public void refresh() {
        RVadapter_for_admin_task_view adapter = new RVadapter_for_admin_task_view(getActivity(), task_details);
        LinearLayoutManager lm = new LinearLayoutManager(getActivity());

        rv.setLayoutManager(lm);
        rv.setAdapter(adapter);


    }


    public void addTask() {
        refresh();
    }
}
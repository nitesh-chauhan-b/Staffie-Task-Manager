package com.managmnet.staffie.manager.fragments;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.managmnet.staffie.common_model.setTask;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;

import com.managmnet.staffie.R;
import com.managmnet.staffie.employee.EmployeeTaskToComplete;
import com.managmnet.staffie.manager.ManagerScreen;
import com.managmnet.staffie.manager.models.ManagerDetails;
import com.managmnet.staffie.manager.recyclerviews.RVadapter_for_manager_admin_taskview;

import java.util.ArrayList;
import java.util.Map;


public class ManagerAdminTasks extends Fragment {
    RecyclerView rv;

    private static ManagerAdminTasks instance = null;
    FirebaseDatabase database;
    DatabaseReference db_ref;
    ImageView back;
    ProgressDialog progressDialog;
    ArrayList<ManagerDetails> list;
    ManagerScreen managerScreen;
    String t_name,t_start_date,t_due_date,t_desc,t_priority;
    ArrayList<String> assigned_member;
    Toolbar toolbar;
    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstateStates)
    {
        View V = inflater.inflate(R.layout.fragment_manager_admin_tasks,null);
        rv = V.findViewById(R.id.mng_assigned_taskview_rv);
        toolbar = V.findViewById(R.id.mng_toolbar);

        list = new ArrayList<>();
        assigned_member = new ArrayList<>();
        instance = this;
        ((AppCompatActivity) getActivity()).getSupportActionBar();
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setTitle("Loading...");
        progressDialog.setMessage("Please wait");
        progressDialog.show();

        managerScreen = (ManagerScreen) getActivity();
        managerScreen.toolbar.setVisibility(View.GONE);
        managerScreen.setDrawerEnabled(false);
        managerScreen.create_button.setVisibility(View.VISIBLE);
        managerScreen.bottomAppBar.setVisibility(View.VISIBLE);

        back = V.findViewById(R.id.back_button);
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
        database = FirebaseDatabase.getInstance();

        db_ref = FirebaseDatabase.getInstance().getReference("Tasks/Admin/");

        db_ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {

                    for (DataSnapshot snap : snapshot.getChildren()) {


                            Map map = (Map) snap.getValue();
                            assigned_member = (ArrayList<String>) map.get("assigned_member");
                            t_name = map.get("task_name").toString();
                            t_start_date = map.get("start_date").toString();
                            t_due_date = map.get("due_date").toString();
                            t_desc = map.get("task_description").toString();
                            t_priority = map.get("task_priority").toString();

                            ManagerDetails user = new ManagerDetails();
                            user.setTask_name(t_name);
                            user.setTask_desc(t_desc);
                            user.setTask_due_date(t_due_date);
                            user.setTask_start_date(t_start_date);
                            user.setTask_priority(t_priority);
                            user.setAssigned_member(assigned_member);

                            list.add(user);
                            refresh();
                            progressDialog.dismiss();
                    }
                }

                else{
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

        RVadapter_for_manager_admin_taskview adapter = new RVadapter_for_manager_admin_taskview(getActivity(),list);
        LinearLayoutManager lm = new LinearLayoutManager(getActivity());

        rv.setLayoutManager(lm);
        rv.setAdapter(adapter);
        progressDialog.dismiss();

        return V;
    }
    private void hidetoolbar() {
        toolbar.setVisibility(View.GONE);
        back.setVisibility(View.GONE);
        managerScreen.create_button.setVisibility(View.GONE);
        managerScreen.bottomAppBar.setVisibility(View.GONE);

    }

    private void showtoolbar() {
        toolbar.setVisibility(View.VISIBLE);
        back.setVisibility(View.VISIBLE);
        managerScreen.create_button.setVisibility(View.VISIBLE);
        managerScreen.bottomAppBar.setVisibility(View.VISIBLE);
    }
    public void adddata()
    {


        Bundle b = getArguments();
        if(b == null)
        {
            Toast.makeText(getActivity(), "Empty data", Toast.LENGTH_SHORT).show();
        }
        else{
            String t_name = b.getString("task_name");
            String t_start_date = b.getString("task_start_date");
            String t_due_date = b.getString("task_due_date");
            String t_priority = b.getString("task_priority");
            String t_desc = b.getString("task_desc");

            list.add(new ManagerDetails(t_name,t_desc,t_start_date+" - "+t_due_date));

            refresh();
        }

    }
    public void refresh()
    {
        RVadapter_for_manager_admin_taskview adapter = new RVadapter_for_manager_admin_taskview(getActivity(),list);
        LinearLayoutManager lm = new LinearLayoutManager(getActivity());

        rv.setLayoutManager(lm);
        rv.setAdapter(adapter);
        progressDialog.dismiss();

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
    public static ManagerAdminTasks getInstance(){
        return instance;
    }

    public void addTaskForManager(@NonNull setTask task)
    {
        String T_name = task.getTask_name();
        String T_start_date = task.getStart_date();
        String T_due_data = task.getDue_date();
        String T_task_priority = task.getTask_priority();
        String T_task_desc = task.getTask_description();

        list.add(new ManagerDetails(T_name,T_task_desc,T_start_date+" - "+T_due_data));

        refresh();

    }

}
package com.managmnet.staffie.manager.fragments;

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

import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.managmnet.staffie.R;
import com.managmnet.staffie.admin.navigationfragments.DrawerLocker;
import com.managmnet.staffie.common_model.setTask;
import com.managmnet.staffie.manager.ManagerScreen;
import com.managmnet.staffie.manager.models.ManagerDetails;
import com.managmnet.staffie.manager.recyclerviews.RVadapter_for_manager_taskview;

import java.util.ArrayList;
import java.util.Map;


public class ManagerTaskView extends Fragment {
    RecyclerView rv;
    private static ManagerTaskView instance = null;
    ArrayList<ManagerDetails> list;

    FirebaseDatabase database;
    DatabaseReference db_ref;
    FloatingActionButton fa;
    ProgressDialog progressDialog;
    ManagerScreen managerScreen;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstateStates) {
        View V = inflater.inflate(R.layout.fragment_manager_task_view, null);
        instance = this;

        rv = V.findViewById(R.id.manager_taskview_rv);
        list = new ArrayList<>();

//        BottomAppBar ba = getActivity().findViewById(R.id.bottom_menu);
//        fa= getActivity().findViewById(R.id.create_task_menu);
//        fa.setVisibility(View.VISIBLE);
//        ba.setVisibility(View.VISIBLE);
//
//        ((DrawerLocker) getActivity()).setDrawerEnabled(false);
        managerScreen= (ManagerScreen) getActivity();
        assert managerScreen != null;
        managerScreen.toolbar.setVisibility(View.VISIBLE);
        managerScreen.create_button.setVisibility(View.VISIBLE);
        managerScreen.bottomAppBar.setVisibility(View.VISIBLE);
        managerScreen.setDrawerEnabled(true);

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Please wait");
        progressDialog.setTitle("Loading...");

        progressDialog.show();
        database = FirebaseDatabase.getInstance();
        db_ref = FirebaseDatabase.getInstance().getReference("Tasks/Manager/");

        get_all_data();

        //Scrolling animation
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


        RVadapter_for_manager_taskview adapter = new RVadapter_for_manager_taskview(getActivity(), list);
        rv.setAdapter(adapter);
        LinearLayoutManager lm = new LinearLayoutManager(getActivity());
        rv.setLayoutManager(lm);
        return V;
    }
    private void hidetoolbar() {
        managerScreen.toolbar.setVisibility(View.GONE);
        managerScreen.create_button.setVisibility(View.GONE);
        managerScreen.bottomAppBar.setVisibility(View.GONE);

    }

    private void showtoolbar() {
        managerScreen.toolbar.setVisibility(View.VISIBLE);
        managerScreen.create_button.setVisibility(View.VISIBLE);
        managerScreen.bottomAppBar.setVisibility(View.VISIBLE);
    }

    public void get_all_data(){
        db_ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                try{
                    if (snapshot.exists()) {

                        for (DataSnapshot snap : snapshot.getChildren()) {

                            Map map = (Map) snap.getValue();
                            String t_name = map.get("task_name").toString();
                            String t_start_date = map.get("start_date").toString();
                            String t_due_date = map.get("due_date").toString();
                            String t_desc = map.get("task_description").toString();
                            String t_priority = map.get("task_priority").toString();
                            String t_status = map.get("task_status").toString();

                            ManagerDetails mng = new ManagerDetails();
                            mng.setTask_name(t_name);
                            mng.setTask_priority(t_priority);
                            mng.setTask_start_date(t_start_date);
                            mng.setTask_due_date(t_due_date);
                            mng.setTask_desc(t_desc);
                            mng.setTask_status(t_status);
                            list.add(mng);
                            refresh();
                            progressDialog.dismiss();
                        }
                    } else {
                        Toast.makeText(getActivity(), "No task is been assigned", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();

                    }
                }
                catch(Exception e)
                {
                    Toast.makeText(managerScreen, "Error :"+e.getMessage(), Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }

            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {
//                Toast.makeText(getActivity(), "No task is been assigned", Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });
    }

    public void refresh() {
        RVadapter_for_manager_taskview adapter = new RVadapter_for_manager_taskview(getActivity(), list);
        rv.setAdapter(adapter);
        LinearLayoutManager lm = new LinearLayoutManager(getActivity());
        rv.setLayoutManager(lm);
    }

    public static ManagerTaskView getInstance() {
        return instance;
    }


    public void addTask(@NonNull setTask task) {
        refresh();
    }
}
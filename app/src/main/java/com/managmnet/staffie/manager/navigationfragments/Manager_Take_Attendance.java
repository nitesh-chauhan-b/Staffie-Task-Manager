package com.managmnet.staffie.manager.navigationfragments;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.shape.CornerFamily;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.managmnet.staffie.R;
import com.managmnet.staffie.admin.fragments.AdminTaskView;
import com.managmnet.staffie.admin.navigationfragments.DrawerLocker;
import com.managmnet.staffie.employee.modles.Employee_User;
import com.managmnet.staffie.employee.modles.User_Employee;
import com.managmnet.staffie.manager.ManagerScreen;
import com.managmnet.staffie.manager.fragments.ManagerTaskView;
import com.managmnet.staffie.manager.models.Manager_User;
import com.managmnet.staffie.manager.recyclerviews.RVadapter_for_manager_attendace_taking_view;

import java.util.ArrayList;
import java.util.Calendar;


public class Manager_Take_Attendance extends Fragment {
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle toggle;
    NavigationView nv;

    ArrayList<User_Employee> list;
    FirebaseDatabase database,db_for_attendance;
    DatabaseReference db_ref;
    ProgressDialog progressDialog;
    ImageView back;
    RecyclerView rv;

    Calendar c = Calendar.getInstance();
    String get_current_date, a_attendance_type;
    String a_current_date;
    String a_emp_id;
    String a_emp_name;
    int a_list_pos;
    int a_emp_pic;
    boolean is_done;
    ManagerScreen managerScreen;
    Button done;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v=  inflater.inflate(R.layout.fragment_manager__take__attendace, null);

        list = new ArrayList<>();
        is_done = false;

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setTitle("Loading...");
        progressDialog.setMessage("Please wait");
        back = v.findViewById(R.id.back_button);
        progressDialog.show();

        managerScreen= (ManagerScreen) getActivity();
        assert managerScreen != null;
        managerScreen.toolbar.setVisibility(View.GONE);
        managerScreen.setDrawerEnabled(false);
        managerScreen.create_button.setVisibility(View.VISIBLE);
        managerScreen.bottomAppBar.setVisibility(View.VISIBLE);

        db_for_attendance = FirebaseDatabase.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
//
//        ManagerScreen managerScreen = (getActivity());
//        managerScreen.setDrawerEnabled(false);

        // set current date into datepicker
        String month_in_string = find_month_name(month);
        get_current_date = day + " " + month_in_string + ", " + year;


        database = FirebaseDatabase.getInstance();
        db_ref = FirebaseDatabase.getInstance().getReference("Attendance Management/Manager/Requests");
        get_all_data();
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(mMessageReceiver,
                new IntentFilter("custom-message"));
        rv= v.findViewById(R.id.mng_attendance_taking_rv);

        LinearLayoutManager lm = new LinearLayoutManager(getActivity());
        RVadapter_for_manager_attendace_taking_view adapter = new RVadapter_for_manager_attendace_taking_view(getActivity(),list);

        rv.setLayoutManager(lm);
        rv.setAdapter(adapter);


        done = v.findViewById(R.id.submit_attendance);

        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (is_done) {
                    if (a_emp_name == null) {

                    } else if (a_emp_pic == 0) {

                    } else if (a_emp_id == null) {

                    } else if (a_list_pos !=0) {

                    } else if (a_current_date == null) {
                        Toast.makeText(getActivity(), a_current_date, Toast.LENGTH_SHORT).show();
                    } else if (a_attendance_type == null) {
                        Toast.makeText(getActivity(), a_attendance_type, Toast.LENGTH_SHORT).show();
                    } else {

                        if (a_attendance_type.equals("Present")) {

                            Manager_User user = new Manager_User();
                            user.setMng_id(a_emp_id);
                            user.setMng_name(a_emp_name);
                            user.setMng_pic(a_emp_pic);
                            user.setMng_attendance_date(a_current_date);


                            db_for_attendance.getReference().child("Attendance Management").child("Manager").child("Employee Attendance").child(a_attendance_type).
                                    child(user.getMng_attendance_date()).child(user.getMng_id()).setValue(user);


                            db_for_attendance.getReference().child("Attendance Management").child("Manager").child("Requests").
                                    child(user.getMng_attendance_date()).child(user.getMng_id()).removeValue();

                            list.remove(a_list_pos);

                            refresh();

                        } else if (a_attendance_type.equals("Absent")) {

                            Manager_User user = new Manager_User();
                            user.setMng_id(a_emp_id);
                            user.setMng_name(a_emp_name);
                            user.setMng_pic(a_emp_pic);
                            user.setMng_attendance_date(a_current_date);


                            db_for_attendance.getReference().child("Attendance Management").child("Manager").child("Employee Attendance").child(a_attendance_type).
                                    child(user.getMng_attendance_date()).child(user.getMng_id()).setValue(user);


                            db_for_attendance.getReference().child("Attendance Management").child("Manager").child("Requests").
                                    child(user.getMng_attendance_date()).child(user.getMng_id()).removeValue();

                            list.remove(a_list_pos);
                            refresh();

                        } else if (a_attendance_type.equals("Half Day")) {
                            Manager_User user = new Manager_User();
                            user.setMng_id(a_emp_id);
                            user.setMng_name(a_emp_name);
                            user.setMng_pic(a_emp_pic);
                            user.setMng_attendance_date(a_current_date);


                            db_for_attendance.getReference().child("Attendance Management").child("Manager").child("Employee Attendance").child(a_attendance_type).
                                    child(user.getMng_attendance_date()).child(user.getMng_id()).setValue(user);


                            db_for_attendance.getReference().child("Attendance Management").child("Manager").child("Requests").
                                    child(user.getMng_attendance_date()).child(user.getMng_id()).removeValue();

                            list.remove(a_list_pos);
                            refresh();

                        } else if (a_attendance_type.equals("Double Day")) {

                            Manager_User user = new Manager_User();
                            user.setMng_id(a_emp_id);
                            user.setMng_name(a_emp_name);
                            user.setMng_pic(a_emp_pic);
                            user.setMng_attendance_date(a_current_date);

                            //Adding Data to database
                            db_for_attendance.getReference().child("Attendance Management").child("Manager").child("Employee Attendance").child(a_attendance_type).
                                    child(user.getMng_attendance_date()).child(user.getMng_id()).setValue(user);

                            //Removing data
                            db_for_attendance.getReference().child("Attendance Management").child("Manager").child("Requests").
                                    child(user.getMng_attendance_date()).child(user.getMng_id()).removeValue();

                            list.remove(a_list_pos);
                            get_all_data();

                        }
                    }

                }
            }
        });





        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ManagerTaskView f = new ManagerTaskView();
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.mng_screen_navigation_menu, f);
                getActivity().getSupportFragmentManager().popBackStack();

                ft.addToBackStack("something");
                ft.commit();

            }
        });

//        drawerLayout = v.findViewById(R.id.mng_drawer);
//        nv = v.findViewById(R.id.mng_navigation_view);

        Toolbar toolbar = v.findViewById(R.id.mng_toolbar);

        return v;
    }
    public String find_month_name(int month) {
        int month_no = month + 1;
        String month_name;
        switch (month_no) {
            case 1:
                month_name = "Jan";
                break;
            case 2:
                month_name = "Feb";
                break;
            case 3:
                month_name = "Mar";
                break;
            case 4:
                month_name = "Apr";
                break;
            case 5:
                month_name = "May";
                break;
            case 6:
                month_name = "Jun";
                break;
            case 7:
                month_name = "Jul";
                break;
            case 8:
                month_name = "Aug";
                break;
            case 9:
                month_name = "Sep";
                break;
            case 10:
                month_name = "Oct";
                break;
            case 11:
                month_name = "Nov";
                break;
            case 12:
                month_name = "Dec";
                break;
            default:
                month_name = "";

        }
        return month_name;
    }
    public void get_all_data()
    {
        progressDialog.show();
        for(int i=0; i<list.size(); i++)
        {
            list.remove(i);
        }
        db_ref.child(get_current_date).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot snap : snapshot.getChildren()) {

                        try{
                            if (snap == null) {
                                Toast.makeText(getActivity(), "Empty Data", Toast.LENGTH_SHORT).show();
                            }
                            else{
                                long emp_img = (long) snap.child("emp_pic").getValue();
                                String emp_name = (String) snap.child("emp_name").getValue();
                                String emp_id = (String) snap.child("emp_id").getValue();
                                Integer emp_img2 = Integer.valueOf(String.valueOf(emp_img));
                                String emp_attendance_date = (String) snap.child("emp_attendance_date").getValue();
                                list.add(new User_Employee(emp_id,emp_name,emp_img2,emp_attendance_date));

                                progressDialog.dismiss();
                                refresh();
                            }
                        }
                        catch (Exception e)
                        {
                            Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
                        }


                    }
                } else {
                    Toast.makeText(getActivity(), "No Request is been found!", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();

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

    public BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            // Get extra data included in the Intent
            a_emp_id = intent.getStringExtra("mng_id");
            a_emp_name = intent.getStringExtra("mng_name");
            a_emp_pic = intent.getIntExtra("mng_pic", 0);
            a_current_date = intent.getStringExtra("current_date");
            a_attendance_type = intent.getStringExtra("attendance_type");
            a_list_pos = intent.getIntExtra("list_position",0);

            is_done = true;

        }
    };


    public void onBackPressed() {
        FragmentManager fm = getActivity().getSupportFragmentManager();
        for(int i=0; i<fm.getBackStackEntryCount(); i++)
        {
            fm.popBackStack();
        }
        super.getActivity().onBackPressed();

    }
    public void refresh()
    {
        LinearLayoutManager lm = new LinearLayoutManager(getActivity());
        RVadapter_for_manager_attendace_taking_view adapter = new RVadapter_for_manager_attendace_taking_view(getActivity(),list);

        rv.setLayoutManager(lm);
        rv.setAdapter(adapter);
    }
}
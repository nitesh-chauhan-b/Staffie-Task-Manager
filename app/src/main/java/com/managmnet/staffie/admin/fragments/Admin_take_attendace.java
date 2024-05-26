package com.managmnet.staffie.admin.fragments;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.managmnet.staffie.R;
import com.managmnet.staffie.admin.models.Admin_Task_Details;
import com.managmnet.staffie.admin.navigationfragments.DrawerLocker;
import com.managmnet.staffie.admin.recyclerviews.RVadapter_for_admin_take_attendace;
import com.managmnet.staffie.manager.models.ManagerDetails;
import com.managmnet.staffie.manager.models.Manager_User;
import com.managmnet.staffie.manager.recyclerviews.RVadapter_for_manager_attendace_taking_view;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Map;


public class Admin_take_attendace extends Fragment {

    Calendar c = Calendar.getInstance();
    ImageView back;
    private static AdminTaskView instance = null;
    Button done;
    ArrayList<Manager_User> list;
    RecyclerView rv;
    FirebaseDatabase database, db_for_attendance;
    DatabaseReference db_ref;
    ProgressDialog progressDialog;
    String get_current_date, a_attendance_type;
    String a_current_date;
    String a_emp_id;
    String a_emp_name;
    int a_list_pos;
    int a_emp_pic;
    boolean is_done;
    FloatingActionButton fa;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_admin_take_attendace, container, false);


        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setTitle("Loading...");
        progressDialog.setMessage("Please wait");

        is_done = false;
        db_for_attendance = FirebaseDatabase.getInstance();

        BottomAppBar ba = getActivity().findViewById(R.id.bottom_menu);
        fa= getActivity().findViewById(R.id.create_task_menu);
        fa.setVisibility(View.VISIBLE);
        ba.setVisibility(View.VISIBLE);

        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        ((DrawerLocker) getActivity()).setDrawerEnabled(false);

        // set current date into datepicker
        String month_in_string = find_month_name(month);
        get_current_date = day + " " + month_in_string + ", " + year;
        progressDialog.show();

        list = new ArrayList<>();
        database = FirebaseDatabase.getInstance();
        db_ref = FirebaseDatabase.getInstance().getReference("Attendance Management/Admin/Requests");
        get_all_data();

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


                            db_for_attendance.getReference().child("Attendance Management").child("Admin").child("Manager Attendance").child(a_attendance_type).
                                    child(user.getMng_attendance_date()).child(user.getMng_id()).setValue(user);


                            db_for_attendance.getReference().child("Attendance Management").child("Admin").child("Requests").
                                    child(user.getMng_attendance_date()).child(user.getMng_id()).removeValue();

                            list.remove(a_list_pos);

                            refresh();

                        } else if (a_attendance_type.equals("Absent")) {

                            Manager_User user = new Manager_User();
                            user.setMng_id(a_emp_id);
                            user.setMng_name(a_emp_name);
                            user.setMng_pic(a_emp_pic);
                            user.setMng_attendance_date(a_current_date);


                            db_for_attendance.getReference().child("Attendance Management").child("Admin").child("Manager Attendance").child(a_attendance_type).
                                    child(user.getMng_attendance_date()).child(user.getMng_id()).setValue(user);

                            db_for_attendance.getReference().child("Attendance Management").child("Admin").child("Requests").
                                    child(user.getMng_attendance_date()).child(user.getMng_id()).removeValue();
                            list.remove(a_list_pos);
                            refresh();

                        } else if (a_attendance_type.equals("Half Day")) {
                            Manager_User user = new Manager_User();
                            user.setMng_id(a_emp_id);
                            user.setMng_name(a_emp_name);
                            user.setMng_pic(a_emp_pic);
                            user.setMng_attendance_date(a_current_date);


                            db_for_attendance.getReference().child("Attendance Management").child("Admin").child("Manager Attendance").child(a_attendance_type).
                                    child(user.getMng_attendance_date()).child(user.getMng_id()).setValue(user);
                            db_for_attendance.getReference().child("Attendance Management").child("Admin").child("Requests").
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
                            db_for_attendance.getReference().child("Attendance Management").child("Admin").child("Manager Attendance").child(a_attendance_type).
                                    child(user.getMng_attendance_date()).child(user.getMng_id()).setValue(user);

                            //Removing Requests from database
                            db_for_attendance.getReference().child("Attendance Management").child("Admin").child("Requests").
                                    child(user.getMng_attendance_date()).child(user.getMng_id()).removeValue();
                            list.remove(a_list_pos);
                            get_all_data();

                        }
                    }

                }
            }
        });


        back = v.findViewById(R.id.back_button);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AdminTaskView f = new AdminTaskView();
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.adm_screen_navigation_menu, f);
                getActivity().getSupportFragmentManager().popBackStack();

                ft.addToBackStack("something");
                ft.commit();

            }
        });

        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(mMessageReceiver,
                new IntentFilter("custom-message"));

        rv = v.findViewById(R.id.adm_attendance_taking_rv);

        LinearLayoutManager lm = new LinearLayoutManager(getActivity());
        RVadapter_for_admin_take_attendace adapter = new RVadapter_for_admin_take_attendace(getActivity(), list);

        rv.setLayoutManager(lm);
        rv.setAdapter(adapter);


        return v;
    }

    public void get_all_data() {

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


                        if (snap == null) {
                            Toast.makeText(getActivity(), "Empty Data", Toast.LENGTH_SHORT).show();
                        } else {
                            long mng_img = (long) snap.child("mng_pic").getValue();
                            String mng_id = (String) snap.child("mng_id").getValue();
                            String mng_name = (String) snap.child("mng_name").getValue();
                            Integer mng_img2 = Integer.valueOf(String.valueOf(mng_img));
                            String attendance_date = (String) snap.child("mng_attendance_date").getValue();


                            list.add(new Manager_User(mng_id, mng_name, mng_img2, attendance_date));
                            progressDialog.dismiss();
                            refresh();
                        }

                    }
                } else {
                    Toast.makeText(getActivity(), "No Attendance is been requested!", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getActivity(), "Error :"+error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
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

    public static AdminTaskView getInstance() {
        return instance;
    }


    public void refresh() {

        LinearLayoutManager lm = new LinearLayoutManager(getActivity());
        RVadapter_for_admin_take_attendace adapter = new RVadapter_for_admin_take_attendace(getActivity(), list);

        rv.setLayoutManager(lm);
        rv.setAdapter(adapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        ((AppCompatActivity) getActivity()).getSupportActionBar().hide();


    }

    @Override
    public void onStop() {
        super.onStop();
        ((AppCompatActivity) getActivity()).getSupportActionBar().show();
    }

}
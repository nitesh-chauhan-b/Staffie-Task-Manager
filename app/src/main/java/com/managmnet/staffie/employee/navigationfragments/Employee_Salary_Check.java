package com.managmnet.staffie.employee.navigationfragments;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.managmnet.staffie.R;
import com.managmnet.staffie.employee.EmployeeScreen;
import com.managmnet.staffie.employee.EmployeeTaskToComplete;
import com.managmnet.staffie.employee.modles.Attendance_model;
import com.managmnet.staffie.employee.modles.EmployeeDetails;
import com.managmnet.staffie.employee.recyclerviews.RVadapter_for_emp_salary_check;

import java.util.ArrayList;
import java.util.Map;


public class Employee_Salary_Check extends Fragment {

    ImageView back;
    EmployeeScreen employeeScreen;
    ArrayList<Attendance_model> list;
    RecyclerView rv;


    FirebaseDatabase database;
    DatabaseReference db_ref;
    FirebaseUser user;
    FirebaseAuth auth;
    ProgressDialog progressDialog;
    int count=0;

    TextView present,absent,halfday,doubleday,total_days;
    int p=0,a=0,h=0,d=0,t=0;
    String date,status;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

       View v=  inflater.inflate(R.layout.fragment_employee__salary__check, null);
        back = v.findViewById(R.id.back_button);
        rv = v.findViewById(R.id.emp_attendace_details_rv);
        present = v.findViewById(R.id.emp_present);
        absent = v.findViewById(R.id.emp_absent);
        halfday = v.findViewById(R.id.emp_halfday);
        doubleday = v.findViewById(R.id.emp_double);
        total_days = v.findViewById(R.id.emp_attendance_total_days);

        list= new ArrayList<>();



        Toolbar toolbar = v.findViewById(R.id.emp_toolbar);

        employeeScreen = (EmployeeScreen) getActivity();
        employeeScreen.toolbar.setVisibility(View.GONE);
        employeeScreen.bottomAppBar.setVisibility(View.GONE);
        employeeScreen.setDrawerEnabled(false);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EmployeeTaskToComplete f = new EmployeeTaskToComplete();
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.emp_screen_navigation_menu,f);
                getActivity().getSupportFragmentManager().popBackStack();

                ft.addToBackStack("something");
                ft.commit();

            }
        });
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Please wait");

        db_ref = FirebaseDatabase.getInstance().getReference("Attendance Management/Manager/Employee Attendance/");

        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser f_user = auth.getCurrentUser();
        try{
        db_ref.child("Present").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    progressDialog.show();
                    for (DataSnapshot snapshot1 : snapshot.getChildren()) {

                        for (DataSnapshot snap : snapshot1.getChildren()) {

                            java.lang.String by = "Employee";
                            Map map = (Map) snap.getValue();

                            String emp_id = map.get("mng_id").toString();
                            if(f_user.getUid().equals(emp_id))
                            {
                                p=p+1;
                                present.setText(String.valueOf(p));
                                t = t+1;
                                total_days.setText(String.valueOf(t));
                                String attendance_date = map.get("mng_attendance_date").toString();
                                String attendance_type = "Present";

                                Attendance_model user = new Attendance_model();
                                user.setA_date(attendance_date);
                                user.setA_status(attendance_type);

                                list.add(user);

                                refresh();
                                progressDialog.dismiss();
                            }

                            progressDialog.dismiss();

                        }}
                } else {
//                    Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                progressDialog.dismiss();
            }
        });

        db_ref.child("Absent").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    progressDialog.show();
                    for (DataSnapshot snapshot1 : snapshot.getChildren()) {

                        for (DataSnapshot snap : snapshot1.getChildren()) {


                            java.lang.String by = "Employee";
                            Map map = (Map) snap.getValue();

                            String emp_id = map.get("mng_id").toString();
                            if(f_user.getUid().equals(emp_id))
                            {
                                a=a+1;
                                absent.setText(String.valueOf(a));
                                t = t+1;
                                total_days.setText(String.valueOf(t));

                                String attendance_date = map.get("mng_attendance_date").toString();
                                String attendance_type = "Absent";

                                Attendance_model user = new Attendance_model();
                                user.setA_date(attendance_date);
                                user.setA_status(attendance_type);
                                refresh();
                                list.add(user);
                                progressDialog.dismiss();
                            }

                            progressDialog.dismiss();

                        }}
                } else {
//                    Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                progressDialog.dismiss();
            }
        });

        db_ref.child("Double Day").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    progressDialog.show();
                    for (DataSnapshot snapshot1 : snapshot.getChildren()) {

                        for (DataSnapshot snap : snapshot1.getChildren()) {

                            java.lang.String by = "Employee";
                            Map map = (Map) snap.getValue();

                            String emp_id = map.get("mng_id").toString();
                            if(f_user.getUid().equals(emp_id))
                            {
                                d=d+1;
                                doubleday.setText(String.valueOf(d));
                                t = t+1;
                                total_days.setText(String.valueOf(t));
                                String attendance_date = map.get("mng_attendance_date").toString();
                                String attendance_type = "Double Day";

                                Attendance_model user = new Attendance_model();
                                user.setA_date(attendance_date);
                                user.setA_status(attendance_type);

                                list.add(user);
                                refresh();
                                d++;
                                progressDialog.dismiss();
                            }

                            progressDialog.dismiss();

                        }}
                } else {
//                    Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                progressDialog.dismiss();
            }
        });

        db_ref.child("Half Day").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        progressDialog.show();
                        for (DataSnapshot snapshot1 : snapshot.getChildren()) {

                            for (DataSnapshot snap : snapshot1.getChildren()) {

                                java.lang.String by = "Employee";
                                Map map = (Map) snap.getValue();

                                String emp_id = map.get("mng_id").toString();
                                if(f_user.getUid().equals(emp_id))
                                {
                                    h=h+1;
                                    halfday.setText(String.valueOf(h));
                                    t = t+1;
                                    total_days.setText(String.valueOf(t));
                                    String attendance_date = map.get("mng_attendance_date").toString();
                                    String attendance_type = "Half Day";

                                    Attendance_model user = new Attendance_model();
                                    user.setA_date(attendance_date);
                                    user.setA_status(attendance_type);

                                    list.add(user);
                                    refresh();

                                    progressDialog.dismiss();
                                }

                                progressDialog.dismiss();

                            }}
                    } else {
//                    Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    progressDialog.dismiss();
                }
            });

        }
        catch (Exception e)
        {
//            Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
        }



        LinearLayoutManager lm = new LinearLayoutManager(getActivity());
        RVadapter_for_emp_salary_check adapter = new RVadapter_for_emp_salary_check(getActivity(),list);

        rv.setLayoutManager(lm);
        rv.setAdapter(adapter);


        return v;
    }

    public void refresh(){

        LinearLayoutManager lm = new LinearLayoutManager(getActivity());
        RVadapter_for_emp_salary_check adapter = new RVadapter_for_emp_salary_check(getActivity(),list);

        rv.setLayoutManager(lm);
        rv.setAdapter(adapter);

    }


    public void onBackPressed() {
        FragmentManager fm = getActivity().getSupportFragmentManager();
            for(int i=0; i<fm.getBackStackEntryCount(); ++i)
            {
                fm.popBackStack();
            }
            super.getActivity().onBackPressed();

    }
}
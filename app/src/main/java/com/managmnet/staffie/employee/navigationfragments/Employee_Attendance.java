package com.managmnet.staffie.employee.navigationfragments;

import android.app.DatePickerDialog;
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

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.managmnet.staffie.R;
import com.managmnet.staffie.employee.EmployeeScreen;
import com.managmnet.staffie.employee.EmployeeTaskToComplete;
import com.managmnet.staffie.employee.modles.Employee_User;
import com.managmnet.staffie.employee.modles.User_Employee;
import com.managmnet.staffie.manager.models.Manager_User;

import java.util.Calendar;


public class Employee_Attendance extends Fragment {

    Calendar c = Calendar.getInstance();
    String s_date;
    EditText start_date;
    ProgressDialog progressDialog;
    FirebaseDatabase database;
    EmployeeScreen employeeScreen;
    Button btn;
    ImageView back;
    EditText emp_name;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v= inflater.inflate(R.layout.fragment_employee__attendance,null);

        start_date = v.findViewById(R.id.emp_select_current_date);
        back = v.findViewById(R.id.back_button);
        employeeScreen = (EmployeeScreen) getActivity();
        employeeScreen.toolbar.setVisibility(View.GONE);
        employeeScreen.bottomAppBar.setVisibility(View.GONE);
        employeeScreen.setDrawerEnabled(false);


        Toolbar toolbar = v.findViewById(R.id.emp_toolbar);
//        setSupportActionBar(toolbar);
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setTitle("Requesting...");
        progressDialog.setMessage("Please wait");
        database = FirebaseDatabase.getInstance();


        emp_name = v.findViewById(R.id.emp_attendance_id);
        btn = v.findViewById(R.id.emp_attendance_request);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.show();

                if(emp_name.getText().toString().equals(""))
                {
                    Toast.makeText(getActivity(), "Please Enter Your Name!", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
                else if(start_date.getText().toString().equals(""))
                {
                    Toast.makeText(getActivity(), "Please Select Attendance date", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
                else{
                    FirebaseAuth auth = FirebaseAuth.getInstance();
                    FirebaseUser f_user  = auth.getCurrentUser();

                    String name =emp_name.getText().toString();
                    User_Employee user = new User_Employee(f_user.getUid(),name,R.drawable.employee,start_date.getText().toString());

                    database.getReference().child("Attendance Management").child("Manager").child("Requests").child(user.getEmp_attendance_date()).child(f_user.getUid()).setValue(user);
                    progressDialog.dismiss();
                    Toast.makeText(getActivity(),"Attendance Requested!",Toast.LENGTH_LONG).show();

                }
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

            }
        });


        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        // set current date into datepicker
        String month_in_string = find_month_name(month);
        s_date = day + " " + month_in_string + ", "+year;
        start_date.setText(s_date);

        start_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(getActivity(), STARTDATE,year,month,day).show();
            }
        });


        return v;
    }
    public String find_month_name(int month){
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

    private DatePickerDialog.OnDateSetListener STARTDATE = (new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker dp, int Year, int Month, int Day) {
            int month_no = Month + 1;
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

            s_date = Day + " " + month_name + ", "+Year;
            start_date.setText(s_date);
        }
    });
    public void onBackPressed() {
        FragmentManager fm = getActivity().getSupportFragmentManager();
        for(int i=0; i<fm.getBackStackEntryCount(); ++i)
        {
            fm.popBackStack();
        }
        super.getActivity().onBackPressed();

    }
}
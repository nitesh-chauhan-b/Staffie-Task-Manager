package com.managmnet.staffie.manager.navigationfragments;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
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
import com.managmnet.staffie.common_model.setTask;
import com.managmnet.staffie.manager.ManagerScreen;
import com.managmnet.staffie.manager.fragments.ManagerTaskView;
import com.managmnet.staffie.manager.models.Manager_User;

import java.util.Calendar;


public class Manager_Attendace extends Fragment {

    Calendar c = Calendar.getInstance();
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle toggle;
    NavigationView nv;
    String s_date;
    FirebaseDatabase database;
    Button btn;
    EditText mng_id;
    ProgressDialog progressDialog;
    EditText start_date;
    ManagerScreen managerScreen;
    ImageView back;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_manager__attendace, null);

        mng_id = v.findViewById(R.id.mng_attendance_id);
        btn = v.findViewById(R.id.mng_attendance_request);
        start_date = v.findViewById(R.id.mng_select_current_date);
        back = v.findViewById(R.id.back_button);
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setTitle("Requesting...");
        progressDialog.setMessage("Please wait");
        database = FirebaseDatabase.getInstance();

        managerScreen= (ManagerScreen) getActivity();
        assert managerScreen != null;
        managerScreen.toolbar.setVisibility(View.GONE);
        managerScreen.setDrawerEnabled(false);
        managerScreen.create_button.setVisibility(View.GONE);
        managerScreen.bottomAppBar.setVisibility(View.GONE);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                managerScreen.is_nav_selected(true);
                ManagerTaskView f = new ManagerTaskView();
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.mng_screen_navigation_menu, f);
                getActivity().getSupportFragmentManager().popBackStack();

                ft.addToBackStack("something");
                ft.commit();

            }
        });


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.show();

                if (mng_id.getText().toString().equals("")) {
                    Toast.makeText(getActivity(), "Please Enter Your Name", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
                else if(start_date.getText().toString().equals(""))
                {
                    Toast.makeText(getActivity(), "Please Select Attendance date", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
                else {

                    FirebaseAuth auth = FirebaseAuth.getInstance();
                    FirebaseUser f_user = auth.getCurrentUser();

                    Manager_User user = new Manager_User();
                    user.setMng_pic(R.drawable.manager);
                    user.setMng_id(f_user.getUid());
                    user.setMng_name(mng_id.getText().toString());
                    user.setMng_attendance_date(start_date.getText().toString());

                    database.getReference().child("Attendance Management").child("Admin").child("Requests").child(start_date.getText().toString()).
                            child(f_user.getUid()).setValue(user);
                    progressDialog.dismiss();
                    Toast.makeText(getActivity(), "Attendance Requested!", Toast.LENGTH_LONG).show();

                }


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
//        drawerLayout = v.findViewById(R.id.mng_drawer);
//        nv = v.findViewById(R.id.mng_navigation_view);

        Toolbar toolbar = v.findViewById(R.id.mng_toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
//
//        toggle = new ActionBarDrawerToggle(getActivity(), drawerLayout, toolbar, R.string.open, R.string.close);
//        toggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.white));
//        drawerLayout.addDrawerListener(toggle);
//        toggle.syncState();


//        nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//
//                if (item.getItemId() == R.id.mng_home) {
//                    onBackPressed();
//                    Manager_Screen_Fragment f = new Manager_Screen_Fragment();
//                    FragmentManager ft = getActivity().getSupportFragmentManager();
//                    ft.beginTransaction()
//                            .addToBackStack(null)
//                            .replace(R.id.mng_screen_navigation_menu, f, null)
//                            .commit();
//                    drawerLayout.closeDrawer(GravityCompat.START);
//                } else if (item.getItemId() == R.id.mng_mng_attendace) {
//
//                    Manager_Take_Attendance f = new Manager_Take_Attendance();
//                    FragmentManager ft = getActivity().getSupportFragmentManager();
//                    ft.beginTransaction()
//                            .addToBackStack(null)
//                            .replace(R.id.mng_screen_navigation_menu, f, null)
//                            .commit();
//                    drawerLayout.closeDrawer(GravityCompat.START);
//                } else if (item.getItemId() == R.id.mng_menu_attendance) {
//
////                    Manager_Attendace f = new Manager_Attendace();
////                    FragmentManager ft = getActivity().getSupportFragmentManager();
////                    ft.beginTransaction()
////                            .addToBackStack(null)
////                            .replace(R.id.mng_screen_navigation_menu,f,null)
////                            .commit();
//                    drawerLayout.closeDrawer(GravityCompat.START);
//                } else if (item.getItemId() == R.id.mng_menu_salary_check) {
//
//                    Manager_Salary_Check f = new Manager_Salary_Check();
//                    FragmentManager ft = getActivity().getSupportFragmentManager();
//                    ft.beginTransaction()
//                            .addToBackStack(null)
//                            .replace(R.id.mng_screen_navigation_menu, f, null)
//                            .commit();
//                    drawerLayout.closeDrawer(GravityCompat.START);
//                } else if (item.getItemId() == R.id.mng_menu_settings) {
//                    drawerLayout.closeDrawer(GravityCompat.START);
//                } else if (item.getItemId() == R.id.mng_menu_help) {
//                    drawerLayout.closeDrawer(GravityCompat.START);
//                }
//                return true;
//            }
//
//        });


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
        for (int i = 0; i < fm.getBackStackEntryCount(); ++i) {
            fm.popBackStack();
        }
        super.getActivity().onBackPressed();

    }
}
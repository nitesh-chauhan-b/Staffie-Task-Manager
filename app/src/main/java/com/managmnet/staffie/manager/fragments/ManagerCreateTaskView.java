package com.managmnet.staffie.manager.fragments;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.managmnet.staffie.R;
import com.managmnet.staffie.admin.fragments.AdminTaskView;
import com.managmnet.staffie.admin.navigationfragments.DrawerLocker;
import com.managmnet.staffie.common_model.setTask;
import com.managmnet.staffie.manager.ManagerScreen;

import java.util.ArrayList;
import java.util.Calendar;


public class ManagerCreateTaskView extends Fragment {
    Calendar c = Calendar.getInstance();
    EditText start_date, due_date, task_name, add_member, task_desc;
    String s_date, d_date;
    TextView moderate, high, very_high, highest;
    Button create_task_btn;
    setTask task;
    FirebaseAuth auth;
    TextView priority_title;
    boolean count = true;
    String task_priority;
    boolean button1 = false, button2 = false, button3 = false, button4 = false;


    //adding emp selection
    LinearLayout adding_layout, adding_layout2, adding_layout3;
    int count1 = 0, r_count = 0, emp_list_pos = 0;
    ArrayList<String> added_mng_list;

    FirebaseDatabase database;
    FirebaseUser user;
    ProgressDialog progressDialog;
    ManagerScreen managerScreen;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_manager_create_task_view, container, false);
        task_name = v.findViewById(R.id.mng_create_task_name);
        start_date = v.findViewById(R.id.mng_create_task_start_date);
        due_date = v.findViewById(R.id.mng_create_task_due_date);
        create_task_btn = v.findViewById(R.id.mng_creates_task);
        task_desc = v.findViewById(R.id.mng_create_task_description);
        priority_title = v.findViewById(R.id.mng_create_task_priority);

        managerScreen = (ManagerScreen) getActivity();
        assert managerScreen != null;
        managerScreen.toolbar.setVisibility(View.GONE);
        managerScreen.create_button.setVisibility(View.GONE);
        managerScreen.bottomAppBar.setVisibility(View.GONE);
        managerScreen.setDrawerEnabled(false);
        database = FirebaseDatabase.getInstance();


//        ((DrawerLocker) getActivity()).setDrawerEnabled(true);

        added_mng_list = new ArrayList<>();

        add_member = v.findViewById(R.id.add_emp_in_task);
        adding_layout = v.findViewById(R.id.adding_layout);
        adding_layout2 = v.findViewById(R.id.adding_layout2);
        adding_layout3 = v.findViewById(R.id.adding_layout3);


        add_member.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (count1 == 0 || count1 == 1) {
                    addEmployee();
                    count1++;
                } else if (count1 == 2 || count1 == 3) {
                    addEmployee2();
                    count1++;
                } else if (count1 == 4 || count1 == 5) {
                    addEmployee3();
                    count1++;
                }
            }
        });

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setTitle("Creating Task");
        progressDialog.setMessage("Please wait..");

        create_task_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.show();
                String Task_name = task_name.getText().toString();
                String Start_date = start_date.getText().toString();
                String Due_date = due_date.getText().toString();
                String Task_priority = task_priority;
                String Task_desc = task_desc.getText().toString();
                ArrayList<String> added_list = added_mng_list;
                task = new setTask();

                task.setTask_name(Task_name);
                task.setStart_date(Start_date);
                task.setDue_date(Due_date);
                task.setTask_priority(Task_priority);
                task.setTask_description(Task_desc);
                task.setTask_status("In progress");

                auth = FirebaseAuth.getInstance();
                user = auth.getCurrentUser();

                if (TextUtils.isEmpty(task_name.getText())) {
                    task_name.setError("Required!");
                    progressDialog.dismiss();
                } else if (TextUtils.isEmpty(start_date.getText())) {
                    start_date.setError("Required!");
                    progressDialog.dismiss();

                } else if (TextUtils.isEmpty(due_date.getText())) {
                    due_date.setError("Required!");
                    progressDialog.dismiss();

                } else if (!(added_list.size() > 0)) {
                    Toast.makeText(getActivity(), "at least one Manager Name required!", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();

                } else if (TextUtils.isEmpty(task_desc.getText())) {
                    task_desc.setError("Required!");
                    progressDialog.dismiss();

                } else if (task_priority == null) {
                    priority_title.setError("Required");
                    progressDialog.dismiss();

                } else {
                    setTask task = new setTask();
                    task.setTask_name(Task_name);
                    task.setStart_date(Start_date);
                    task.setDue_date(Due_date);
                    task.setTask_priority(Task_priority);
                    task.setTask_description(Task_desc);
                    task.setAssigned_member(added_list);
                    task.setTask_status("In progress");
                    database.getReference().child("Tasks").child("Manager").child(Task_name).setValue(task);
                    progressDialog.dismiss();

                    ManagerTaskView.getInstance().get_all_data();

                    ManagerTaskView f = new ManagerTaskView();
                    FragmentTransaction ft = getFragmentManager().beginTransaction();
                    ft.replace(R.id.mng_screen_navigation_menu, f);
                    ft.commit();
                }


            }
        });


        moderate = v.findViewById(R.id.task_priority_moderate);
        high = v.findViewById(R.id.task_priority_high);
        very_high = v.findViewById(R.id.task_priority_very_high);
        highest = v.findViewById(R.id.task_priority_highest);


        moderate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((button1 || button2) || (button3 || button4)) {
                    moderate.setBackground(AppCompatResources.getDrawable(getActivity(), R.drawable.text_veiw_border));
                    moderate.setTextColor(getResources().getColor(R.color.defaulttextcolor));

                    moderate.setBackgroundTintList(AppCompatResources.getColorStateList(getActivity(), R.color.white));
                    high.setBackgroundTintList(AppCompatResources.getColorStateList(getActivity(), R.color.white));
                    very_high.setBackgroundTintList(AppCompatResources.getColorStateList(getActivity(), R.color.white));
                    highest.setBackgroundTintList(AppCompatResources.getColorStateList(getActivity(), R.color.white));


                    high.setBackground(AppCompatResources.getDrawable(getActivity(), R.drawable.text_veiw_border));
                    high.setTextColor(getResources().getColor(R.color.defaulttextcolor));
                    button2 = false;
                    button3 = false;
                    button4 = false;

                    very_high.setBackground(AppCompatResources.getDrawable(getActivity(), R.drawable.text_veiw_border));
                    very_high.setTextColor(getResources().getColor(R.color.defaulttextcolor));

                    highest.setBackground(AppCompatResources.getDrawable(getActivity(), R.drawable.text_veiw_border));
                    highest.setTextColor(getResources().getColor(R.color.defaulttextcolor));

                    moderate.setBackgroundTintList(AppCompatResources.getColorStateList(getActivity(), R.color.greenforstatus));
                    moderate.setTextColor(getResources().getColor(R.color.white));
                } else {
                    moderate.setBackgroundTintList(AppCompatResources.getColorStateList(getActivity(), R.color.greenforstatus));
                    moderate.setTextColor(getResources().getColor(R.color.white));
                    moderate.setBackground(AppCompatResources.getDrawable(getActivity(), R.drawable.text_veiw_border));

                }

                button1 = true;
                task_priority = "";
                task_priority = "Moderate";

            }
        });

        high.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((button1 || button2) || (button3 || button4)) {
                    moderate.setBackground(AppCompatResources.getDrawable(getActivity(), R.drawable.text_veiw_border));
                    moderate.setTextColor(getResources().getColor(R.color.defaulttextcolor));

                    moderate.setBackgroundTintList(AppCompatResources.getColorStateList(getActivity(), R.color.white));
                    high.setBackgroundTintList(AppCompatResources.getColorStateList(getActivity(), R.color.white));
                    very_high.setBackgroundTintList(AppCompatResources.getColorStateList(getActivity(), R.color.white));
                    highest.setBackgroundTintList(AppCompatResources.getColorStateList(getActivity(), R.color.white));

                    high.setBackground(AppCompatResources.getDrawable(getActivity(), R.drawable.text_veiw_border));
                    high.setTextColor(getResources().getColor(R.color.defaulttextcolor));

                    button1 = false;
                    button3 = false;
                    button4 = false;
                    very_high.setBackground(AppCompatResources.getDrawable(getActivity(), R.drawable.text_veiw_border));
                    very_high.setTextColor(getResources().getColor(R.color.defaulttextcolor));

                    highest.setBackground(AppCompatResources.getDrawable(getActivity(), R.drawable.text_veiw_border));
                    highest.setTextColor(getResources().getColor(R.color.defaulttextcolor));

                    high.setBackgroundTintList(AppCompatResources.getColorStateList(getActivity(), R.color.orangeforstatus));
                    high.setTextColor(getResources().getColor(R.color.white));
                } else {
                    high.setBackgroundTintList(AppCompatResources.getColorStateList(getActivity(), R.color.orangeforstatus));
                    high.setTextColor(getResources().getColor(R.color.white));
                    high.setBackground(AppCompatResources.getDrawable(getActivity(), R.drawable.text_veiw_border));


                }
                button2 = true;
                task_priority = "";
                task_priority = "High";
            }
        });

        very_high.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((button1 || button2) || (button3 || button4)) {
                    moderate.setBackground(AppCompatResources.getDrawable(getActivity(), R.drawable.text_veiw_border));
                    moderate.setTextColor(getResources().getColor(R.color.defaulttextcolor));

                    moderate.setBackgroundTintList(AppCompatResources.getColorStateList(getActivity(), R.color.white));
                    high.setBackgroundTintList(AppCompatResources.getColorStateList(getActivity(), R.color.white));
                    very_high.setBackgroundTintList(AppCompatResources.getColorStateList(getActivity(), R.color.white));
                    highest.setBackgroundTintList(AppCompatResources.getColorStateList(getActivity(), R.color.white));

                    high.setBackground(AppCompatResources.getDrawable(getActivity(), R.drawable.text_veiw_border));
                    high.setTextColor(getResources().getColor(R.color.defaulttextcolor));

                    button1 = false;
                    button2 = false;
                    button4 = false;
                    very_high.setBackground(AppCompatResources.getDrawable(getActivity(), R.drawable.text_veiw_border));
                    very_high.setTextColor(getResources().getColor(R.color.defaulttextcolor));

                    highest.setBackground(AppCompatResources.getDrawable(getActivity(), R.drawable.text_veiw_border));
                    highest.setTextColor(getResources().getColor(R.color.defaulttextcolor));

                    very_high.setBackgroundTintList(AppCompatResources.getColorStateList(getActivity(), R.color.redforstatus));
                    very_high.setTextColor(getResources().getColor(R.color.white));
                } else {
                    very_high.setBackgroundTintList(AppCompatResources.getColorStateList(getActivity(), R.color.redforstatus));
                    very_high.setTextColor(getResources().getColor(R.color.white));
                    very_high.setBackground(AppCompatResources.getDrawable(getActivity(), R.drawable.text_veiw_border));

                }
                button3 = true;
                task_priority = "";
                task_priority = "Very High";
            }
        });

        highest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((button1 || button2) || (button3 || button4)) {
                    moderate.setBackground(AppCompatResources.getDrawable(getActivity(), R.drawable.text_veiw_border));
                    moderate.setTextColor(getResources().getColor(R.color.defaulttextcolor));

                    high.setBackground(AppCompatResources.getDrawable(getActivity(), R.drawable.text_veiw_border));
                    high.setTextColor(getResources().getColor(R.color.defaulttextcolor));

                    moderate.setBackgroundTintList(AppCompatResources.getColorStateList(getActivity(), R.color.white));
                    high.setBackgroundTintList(AppCompatResources.getColorStateList(getActivity(), R.color.white));
                    very_high.setBackgroundTintList(AppCompatResources.getColorStateList(getActivity(), R.color.white));
                    highest.setBackgroundTintList(AppCompatResources.getColorStateList(getActivity(), R.color.white));


                    button1 = false;
                    button2 = false;
                    button3 = false;

                    very_high.setBackground(AppCompatResources.getDrawable(getActivity(), R.drawable.text_veiw_border));
                    very_high.setTextColor(getResources().getColor(R.color.defaulttextcolor));

                    highest.setBackground(AppCompatResources.getDrawable(getActivity(), R.drawable.text_veiw_border));
                    highest.setTextColor(getResources().getColor(R.color.defaulttextcolor));

                    highest.setBackgroundTintList(AppCompatResources.getColorStateList(getActivity(), R.color.redforhighest));
                    highest.setTextColor(getResources().getColor(R.color.white));
                } else {
                    highest.setBackgroundTintList(AppCompatResources.getColorStateList(getActivity(), R.color.redforhighest));
                    highest.setTextColor(getResources().getColor(R.color.white));
                    very_high.setBackground(AppCompatResources.getDrawable(getActivity(), R.drawable.text_veiw_border));

                }
                button4 = true;
                task_priority = "";
                task_priority = "Highest";
            }
        });


        start_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new TimePickerDialog(getActivity(), STARTTIME, c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE), false).show();
                new DatePickerDialog(getActivity(), STARTDATE, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)).show();

            }
        });

        due_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new TimePickerDialog(getActivity(), DUETIME, c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE), false).show();
                new DatePickerDialog(getActivity(), DUEDATE, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        return v;
    }


    private void addEmployee() {
        View added_emp = getLayoutInflater().inflate(R.layout.rv_for_mng_adding_emp, null, false);

        EditText emp_name = added_emp.findViewById(R.id.adding_emp_name);
        emp_name.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                String got_text = "";
                if (!hasFocus) {
                    got_text = emp_name.getText().toString();
                }
                if (!got_text.equals(""))
                    added_mng_list.add(got_text);
            }
        });

        ImageView iv = added_emp.findViewById(R.id.remove_emp_icon);

        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                r_count++;
                removeEmployee(added_emp, 1, r_count);
            }
        });

        adding_layout.addView(added_emp);

    }

    private void addEmployee2() {
        View added_emp = getLayoutInflater().inflate(R.layout.rv_for_mng_adding_emp, null, false);


        EditText emp_name = added_emp.findViewById(R.id.adding_emp_name);
//            emp_name.setText(emp_list.get(emp_list_pos).getAssigned_task_emp_name());
//            get_emp_name(emp_name.getText().toString());

        emp_name.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                String got_text = "";
                if (!hasFocus) {
                    got_text = emp_name.getText().toString();
                }
                if (!got_text.equals(""))
                    added_mng_list.add(got_text);
            }
        });

        emp_list_pos++;

        ImageView iv = added_emp.findViewById(R.id.remove_emp_icon);

        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                r_count++;
                removeEmployee(added_emp, 2, r_count);
            }
        });
        adding_layout2.addView(added_emp);

    }

    public void refresh_count() {
        r_count = 0;
    }

    private void addEmployee3() {
        View added_emp = getLayoutInflater().inflate(R.layout.rv_for_mng_adding_emp, null, false);


        EditText emp_name = added_emp.findViewById(R.id.adding_emp_name);

        emp_name.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                String got_text = "";
                if (!hasFocus) {
                    got_text = emp_name.getText().toString();
                }
                if (!got_text.equals(""))
                    added_mng_list.add(got_text);
            }
        });
        emp_list_pos++;

        ImageView iv = added_emp.findViewById(R.id.remove_emp_icon);

        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                r_count++;
                removeEmployee(added_emp, 3, r_count);
            }
        });
        adding_layout3.addView(added_emp);

    }


    private void removeEmployee(View v, int number, int r_count) {


        if (number == 1) {
            adding_layout.removeView(v);
        } else if (number == 2) {
            adding_layout2.removeView(v);
        } else if (number == 3) {
            adding_layout3.removeView(v);
        }
        if (r_count == 5) {
            refresh_count();
            count1 = 0;
        }


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

            s_date = Day + " " + month_name + ", ";
            start_date.setText(s_date);
        }
    });

    private TimePickerDialog.OnTimeSetListener STARTTIME = (new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker tp, int hour, int min) {
            String am_pm;
            if (hour < 12) {
                am_pm = "AM";
            } else {
                am_pm = "PM";
            }
            String time = hour + ":" + min + " " + am_pm;
            start_date.setText(s_date + time);
        }

    });

    private DatePickerDialog.OnDateSetListener DUEDATE = (new DatePickerDialog.OnDateSetListener() {
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

            d_date = Day + " " + month_name + ", ";
            due_date.setText(d_date);
        }
    });

    private TimePickerDialog.OnTimeSetListener DUETIME = (new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker tp, int hour, int min) {
            String am_pm;
            if (hour < 12) {
                am_pm = "AM";
            } else {
                am_pm = "PM";
            }
            String time = hour + ":" + min + " " + am_pm;
            due_date.setText(d_date + time);
        }

    });

}
package com.managmnet.staffie.admin.fragments;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
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

import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.managmnet.staffie.R;
import com.managmnet.staffie.common_model.setTask;
import com.managmnet.staffie.manager.models.ManagerDetails;

import java.util.ArrayList;
import java.util.Calendar;


public class AdminDetailTaskVeiw_Edit extends Fragment {

    Calendar c = Calendar.getInstance();
    int count = 0, r_count = 0, emp_list_pos = 0;
    ArrayList<java.lang.String> edit_emp_names;
    EditText add_member, edit_task_name, edit_task_start_date, edit_task_due_date, edit_task_description;
    java.lang.String pev_task_name, pev_task_desc, pev_task_due_date, pev_task_start_date, pev_task_priority, t_status;
    Button create_task_btn;
    ArrayList<ManagerDetails> emp_list;
    LinearLayout adding_layout, adding_layout2, adding_layout3;
    setTask task;
    boolean button1 = false, button2 = false, button3 = false, button4 = false;
    TextView moderate, high, very_high, highest;
    java.lang.String task_priority;
    ProgressDialog progressDialog;
    java.lang.String s_date, d_date;
    boolean is_changed_names = false;
    EditText emp_name;
    TextView priority_title;
    ArrayList<java.lang.String> added_mng_list, db_prev_list;
    ArrayList<java.lang.String> edit_member;
    ArrayList<java.lang.String> prev_mngs, real_list;
    FirebaseAuth auth;
    FirebaseDatabase database;
    FirebaseUser user;

    DatabaseReference reference;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_admin_detail_task_veiw__edit, container, false);


        emp_list = new ArrayList<>();
        db_prev_list = new ArrayList<>();
        edit_emp_names = new ArrayList<>();
        prev_mngs = new ArrayList<>();
        added_mng_list = new ArrayList<>();
        added_mng_list = new ArrayList<>();

        BottomAppBar ba = getActivity().findViewById(R.id.bottom_menu);
        FloatingActionButton fa = getActivity().findViewById(R.id.create_task_menu);
        fa.setVisibility(View.GONE);
        ba.setVisibility(View.GONE);

        add_member = v.findViewById(R.id.add_mng_in_task);
        adding_layout = v.findViewById(R.id.adding_layout);
        adding_layout2 = v.findViewById(R.id.adding_layout2);
        adding_layout3 = v.findViewById(R.id.adding_layout3);

        reference = FirebaseDatabase.getInstance().getReference("Tasks/Admin");

        edit_task_name = v.findViewById(R.id.adm_assigned_task_edit_task_name);
        edit_task_start_date = v.findViewById(R.id.adm_assigned_task_edit_task_start_date);
        edit_task_due_date = v.findViewById(R.id.adm_assigned_task_edit_task_due_date);
        edit_task_description = v.findViewById(R.id.adm_assigned_task_edit_task_description);
        create_task_btn = v.findViewById(R.id.adm_assigned_task_edit_creates_task);
        priority_title = v.findViewById(R.id.adm_task_priority);

        //from AdminDetailTaskView

        Bundle b = getArguments();
        assert b != null;
        emp_list = (ArrayList<ManagerDetails>) b.getSerializable("list");
        pev_task_name = b.getString("task_name");
        pev_task_desc = b.getString("task_instruction");
        pev_task_due_date = b.getString("task_due_date");
        pev_task_start_date = b.getString("task_start_date");
        pev_task_priority = b.getString("task_priority");
        prev_mngs = (ArrayList<java.lang.String>) b.getSerializable("list");
        real_list = b.getStringArrayList("real_list");

        t_status = b.getString("task_status");

//        Toast.makeText(getActivity(), "Task Status :"+t_status, Toast.LENGTH_SHORT).show();

        edit_task_name.setText(pev_task_name);
        edit_task_description.setText(pev_task_desc);
        edit_task_due_date.setText(pev_task_due_date);
        edit_task_start_date.setText(pev_task_start_date);
//        task_priority = pev_task_priority;
        View added_emp = getLayoutInflater().inflate(R.layout.rv_for_mng_adding_emp, null, false);
        emp_name = added_emp.findViewById(R.id.adding_emp_name);

        moderate = v.findViewById(R.id.task_priority_moderate);
        high = v.findViewById(R.id.task_priority_high);
        very_high = v.findViewById(R.id.task_priority_very_high);
        highest = v.findViewById(R.id.task_priority_highest);


        edit_emp_names = prev_mngs;

        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setTitle("Creating Task");
        progressDialog.setMessage("Please wait..");

        get_prev_list();


        create_task_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                progressDialog.show();
                java.lang.String Task_name = edit_task_name.getText().toString();
                java.lang.String Start_date = edit_task_start_date.getText().toString();
                java.lang.String Due_date = edit_task_due_date.getText().toString();
                java.lang.String Task_priority = task_priority;
                java.lang.String Task_desc = edit_task_description.getText().toString();
                java.lang.String Task_status = t_status;

                auth = FirebaseAuth.getInstance();
                user = auth.getCurrentUser();

                if (TextUtils.isEmpty(edit_task_name.getText())) {
                    edit_task_name.setError("Required!");
                    progressDialog.dismiss();
                } else if (TextUtils.isEmpty(edit_task_start_date.getText())) {
                    edit_task_start_date.setError("Required!");
                    progressDialog.dismiss();

                } else if (TextUtils.isEmpty(edit_task_due_date.getText())) {
                    edit_task_due_date.setError("Required!");
                    progressDialog.dismiss();

                } else if (TextUtils.isEmpty(edit_task_description.getText())) {
                    edit_task_description.setError("Required!");
                    progressDialog.dismiss();

                } else if (task_priority == null) {
                    priority_title.setError("Required");

                    progressDialog.dismiss();

                }
//                else if (Task_status == null) {
////                    Toast.makeText(getActivity(), "Task Status is missing", Toast.LENGTH_SHORT).show();
//                    progressDialog.dismiss();
//                }
                else {
                    emp_name.setCursorVisible(false);
                    setTask task = new setTask();
                    task.setTask_name(Task_name);
                    task.setTask_status(Task_status);
                    task.setStart_date(Start_date);
                    task.setDue_date(Due_date);
                    task.setTask_priority(Task_priority);
                    task.setTask_description(Task_desc);

                    edit_member = new ArrayList<>();
                    task.setAssigned_member(db_prev_list);


                    if (is_changed_anything()) {
                        java.lang.String perv = pev_task_name;

                        FirebaseDatabase cmp_ref = FirebaseDatabase.getInstance();
                        try {
                            cmp_ref.getReference().child("Tasks").child("Admin").child(pev_task_name).setValue(task);
                            go_back();
                            progressDialog.dismiss();
                        } catch (Exception e) {
                            progressDialog.dismiss();
                            go_back();
                        }

                        progressDialog.dismiss();

                    } else {
                        go_back();
                        progressDialog.dismiss();
                    }


                    progressDialog.dismiss();

                }


            }
        });

        add_member.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (count == 0 || count == 1) {
                    addEmployee();
                    count++;
                } else if (count == 2 || count == 3) {
                    addEmployee2();
                    count++;
                } else if (count == 4 || count == 5) {
                    addEmployee3();
                    count++;
                }
            }
        });


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

                }
                button4 = true;
                task_priority = "";
                task_priority = "Highest";
            }
        });

        edit_task_start_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new TimePickerDialog(getActivity(), STARTTIME, c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE), false).show();
                new DatePickerDialog(getActivity(), STARTDATE, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)).show();

            }
        });

        edit_task_due_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new TimePickerDialog(getActivity(), DUETIME, c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE), false).show();
                new DatePickerDialog(getActivity(), DUEDATE, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        return v;
    }


    public void go_back() {
        AdminDetailTaskView f = new AdminDetailTaskView();

        Bundle edit_task_details = new Bundle();

        edit_task_details.putString("task_name", edit_task_name.getText().toString());
        edit_task_details.putInt("iscalled", 1);
        edit_task_details.putString("task_start_date", edit_task_start_date.getText().toString());
        edit_task_details.putString("task_due_date", edit_task_due_date.getText().toString());
        edit_task_details.putString("task_desc", edit_task_description.getText().toString());
        edit_task_details.putString("task_priority", task_priority);
        edit_task_details.putString("task_status", t_status);

        edit_task_details.putStringArrayList("edit_emp_names", db_prev_list);
        f.setArguments(edit_task_details);
        FragmentTransaction ft = getFragmentManager().beginTransaction();

        ft.add(R.id.adm_screen_navigation_menu, f);

        ft.addToBackStack("something");
        progressDialog.dismiss();

        ft.commit();


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

    public boolean is_changed_anything() {

        if (!edit_task_name.getText().toString().equals(pev_task_name) ||
                !edit_task_description.getText().toString().equals(pev_task_desc) ||
                !edit_task_due_date.getText().toString().equals(pev_task_due_date) ||
                !edit_task_start_date.getText().toString().equals(pev_task_name)
        ) {
            return true;
        } else {
            return false;
        }
    }


    public void get_prev_list() {

        DatabaseReference reference_for_list = FirebaseDatabase.getInstance().getReference("Tasks/Admin");
        reference_for_list.child(pev_task_name).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int k = 0;
                try {
                    ArrayList<String> got_list = (ArrayList<String>) snapshot.child("assigned_member").getValue();
                    if (got_list == null) {
//                        Toast.makeText(getActivity(), "No Member is assigned!", Toast.LENGTH_SHORT).show();
                    } else {
                        db_prev_list.addAll(got_list);
                    }
                } catch (Exception e) {

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
//                Toast.makeText(getActivity(), "Error :" + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void addEmployee() {
        View added_emp = getLayoutInflater().inflate(R.layout.rv_for_mng_adding_emp, null, false);
        emp_name = added_emp.findViewById(R.id.adding_emp_name);
//        if (emp_list_pos < 6) {

        emp_name.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                java.lang.String got_text = "";
                if (!hasFocus) {
                    got_text = emp_name.getText().toString();
                }
                if (!got_text.equals("")) {
                    db_prev_list.add(got_text);
                    edit_emp_names.add(got_text);
                    emp_list.add(new ManagerDetails(got_text));
                    real_list.add(got_text);
                    added_mng_list.add(got_text);
                }
            }

        });

        emp_list_pos++;

//        } else {
//            is_changed_names=false;
//        }

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

        emp_name = added_emp.findViewById(R.id.adding_emp_name);

        emp_name.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                java.lang.String got_text = "";
                if (!hasFocus) {
                    got_text = emp_name.getText().toString();
                }
                if (!got_text.equals("")) {
                    db_prev_list.add(got_text);
                    edit_emp_names.add(got_text);
                    emp_list.add(new ManagerDetails(got_text));
                    real_list.add(got_text);
                    added_mng_list.add(got_text);
                }

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

        emp_name = added_emp.findViewById(R.id.adding_emp_name);
        emp_name.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                java.lang.String got_text = "";
                if (!hasFocus) {
                    got_text = emp_name.getText().toString();
                }
                if (!got_text.equals("")) {
                    db_prev_list.add(got_text);
                    edit_emp_names.add(got_text);
                    emp_list.add(new ManagerDetails(got_text));
                    real_list.add(got_text);
                    added_mng_list.add(got_text);
                }


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
            count = 0;
        }


    }

    private DatePickerDialog.OnDateSetListener STARTDATE = (new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker dp, int Year, int Month, int Day) {
            int month_no = Month + 1;
            java.lang.String month_name;
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
            edit_task_start_date.setText(s_date);
        }
    });

    private TimePickerDialog.OnTimeSetListener STARTTIME = (new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker tp, int hour, int min) {
            java.lang.String am_pm;
            if (hour < 12) {
                am_pm = "AM";
            } else {
                am_pm = "PM";
            }
            java.lang.String time = hour + ":" + min + " " + am_pm;
            edit_task_start_date.setText(s_date + time);
        }

    });

    private DatePickerDialog.OnDateSetListener DUEDATE = (new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker dp, int Year, int Month, int Day) {
            int month_no = Month + 1;
            java.lang.String month_name;
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
            edit_task_due_date.setText(d_date);
        }
    });

    private TimePickerDialog.OnTimeSetListener DUETIME = (new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker tp, int hour, int min) {
            java.lang.String am_pm;
            if (hour < 12) {
                am_pm = "AM";
            } else {
                am_pm = "PM";
            }
            java.lang.String time = hour + ":" + min + " " + am_pm;
            edit_task_due_date.setText(d_date + time);
        }

    });

}
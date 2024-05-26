package com.managmnet.staffie.admin.recyclerviews;

import android.content.Context;
import android.content.Intent;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.managmnet.staffie.R;
import com.managmnet.staffie.manager.models.Manager_User;
import com.managmnet.staffie.manager.recyclerviews.RVadapter_for_manager_attendace_taking_view;

import java.util.ArrayList;

public class RVadapter_for_admin_take_attendace extends RecyclerView.Adapter<RVadapter_for_admin_take_attendace.viewholder> {

    Context context;
    ArrayList<Manager_User> list;

    public RVadapter_for_admin_take_attendace(Context context, ArrayList<Manager_User> list)
    {
        this.context= context;
        this.list = list;
    }
    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_manager_attendace_taking_view,null);
//        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        v.setLayoutParams(lp);
        return new viewholder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {
        holder.emp_img.setImageResource(list.get(position).getMng_pic());
        holder.emp_name.setText(list.get(position).getMng_name());

        holder.menu_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PopupMenu popupMenu = new PopupMenu(v.getContext(),holder.menu_icon, Gravity.BOTTOM);
                boolean count=false;
                popupMenu.inflate(R.menu.mng_attendance_taker_menu);

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if(item.getItemId()==R.id.mng_attendance_present)
                        {
                            String attendance_status="Present";
                            holder.emp_attendance_status.setText(attendance_status);

                            Manager_User user = new Manager_User();
                            user.setMng_id(list.get(position).getMng_id());
                            user.setMng_name(list.get(position).getMng_name());
                            user.setMng_pic(list.get(position).getMng_pic());


                            //Sending data to activity
                            String mng_id = list.get(position).getMng_id();
                            String mng_name = list.get(position).getMng_name();
                            int mng_pic = list.get(position).getMng_pic();


                            Intent intent = new Intent("custom-message");
                            intent.putExtra("mng_id",mng_id);
                            intent.putExtra("mng_name",mng_name);
                            intent.putExtra("mng_pic",mng_pic);
                            intent.putExtra("attendance_type",attendance_status);
                            intent.putExtra("list_position",position);
                            intent.putExtra("current_date",list.get(position).getMng_attendance_date());

                            LocalBroadcastManager.getInstance(context).sendBroadcast(intent);

//                            database.getReference().child("Attendance Management").child("Admin").child("Manager Attendance").child("Present").
//                                    child(list.get(position).getMng_id()).setValue(user);


                        }
                        else if(item.getItemId()==R.id.mng_attendance_absent)
                        {
                            String attendance_status="Absent";
                            holder.emp_attendance_status.setText(attendance_status);

                            Manager_User user = new Manager_User();
                            user.setMng_id(list.get(position).getMng_id());
                            user.setMng_name(list.get(position).getMng_name());
                            user.setMng_pic(list.get(position).getMng_pic());

                            //Sending data to activity
                            String mng_id = list.get(position).getMng_id();
                            String mng_name = list.get(position).getMng_name();
                            int mng_pic = list.get(position).getMng_pic();


                            Intent intent = new Intent("custom-message");
                            intent.putExtra("mng_id",mng_id);
                            intent.putExtra("mng_name",mng_name);
                            intent.putExtra("mng_pic",mng_pic);
                            intent.putExtra("current_date",list.get(position).getMng_attendance_date());

                            intent.putExtra("list_position",position);
                            intent.putExtra("attendance_type",attendance_status);
                            LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
                            popupMenu.dismiss();
//                            database.getReference().child("Attendance Management").child("Admin").child("Manager Attendance").child("Absent").
//                                    child(list.get(position).getMng_id()).setValue(user);
                        }
                        else if(item.getItemId()==R.id.mng_attendance_half_day)
                        {
                            String attendance_status="Half Day";
                            holder.emp_attendance_status.setText(attendance_status);
                            Manager_User user = new Manager_User();
                            user.setMng_id(list.get(position).getMng_id());
                            user.setMng_name(list.get(position).getMng_name());
                            user.setMng_pic(list.get(position).getMng_pic());
                            //Sending data to activity
                            String mng_id = list.get(position).getMng_id();
                            String mng_name = list.get(position).getMng_name();
                            int mng_pic = list.get(position).getMng_pic();


                            Intent intent = new Intent("custom-message");
                            intent.putExtra("mng_id",mng_id);
                            intent.putExtra("mng_name",mng_name);
                            intent.putExtra("list_position",position);
                            intent.putExtra("mng_pic",mng_pic);
                            intent.putExtra("attendance_type",attendance_status);

                            intent.putExtra("current_date",list.get(position).getMng_attendance_date());


                            LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
                            popupMenu.dismiss();

//                            database.getReference().child("Attendance Management").child("Admin").child("Manager Attendance").child("Half Day").
//                                    child(list.get(position).getMng_id()).setValue(user);

                        }
                        else if(item.getItemId()==R.id.mng_attendance_double_day)
                        {
                            String attendance_status="Double Day";
                            holder.emp_attendance_status.setText(attendance_status);

                            Manager_User user = new Manager_User();
                            user.setMng_id(list.get(position).getMng_id());
                            user.setMng_name(list.get(position).getMng_name());
                            user.setMng_pic(list.get(position).getMng_pic());


                            //Sending data to activity
                            String mng_id = list.get(position).getMng_id();
                            String mng_name = list.get(position).getMng_name();
                            int mng_pic = list.get(position).getMng_pic();

                            Intent intent = new Intent("custom-message");
                            intent.putExtra("mng_id",mng_id);
                            intent.putExtra("list_position",position);
                            intent.putExtra("mng_name",mng_name);
                            intent.putExtra("mng_pic",mng_pic);
                            intent.putExtra("current_date",list.get(position).getMng_attendance_date());
                            intent.putExtra("attendance_type",attendance_status);
                            LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
                            popupMenu.dismiss();


//                            database.getReference().child("Attendance Management").child("Admin").child("Manager Attendance").child("Double Day").
//                                    child(list.get(position).getMng_id()).setValue(user);

                        }

                        return true;
                    }
                });

                popupMenu.show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class viewholder extends RecyclerView.ViewHolder{

        ImageView emp_img,menu_icon;
        TextView emp_name,emp_attendance_status;
        public viewholder(View view)
        {
            super(view);
            emp_img = view.findViewById(R.id.mng_attendance_taking_emp_image);
            emp_name = view.findViewById(R.id.mng_attendance_taking_emp_name);
            emp_attendance_status = view.findViewById(R.id.mng_attendance_taking_emp_attendance_status);
            menu_icon = view.findViewById(R.id.mng_attendance_taking_menu);


        }
    }
}

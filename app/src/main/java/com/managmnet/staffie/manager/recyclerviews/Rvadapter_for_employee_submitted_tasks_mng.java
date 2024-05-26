package com.managmnet.staffie.manager.recyclerviews;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.managmnet.staffie.R;
import com.managmnet.staffie.employee.Uploaded_PDF_View;
import com.managmnet.staffie.employee.modles.Files;
import com.managmnet.staffie.employee.recyclerviews.Rvadapter_for_employee_submitted_tasks;

public class Rvadapter_for_employee_submitted_tasks_mng extends FirebaseRecyclerAdapter<Files,Rvadapter_for_employee_submitted_tasks_mng.viewholder> {
    public Rvadapter_for_employee_submitted_tasks_mng(@NonNull FirebaseRecyclerOptions<Files> options) {

        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull viewholder viewholder, int i, @NonNull Files files) {
        viewholder.File_name.setText(String.valueOf(files.getFile_name()));

        viewholder.File_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(viewholder.File_name.getContext(), Uploaded_PDF_View.class);
                i.putExtra("file_name",files.getFile_name());
                i.putExtra("file_url",files.getFile_url());

                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                viewholder.File_name.getContext().startActivity(i);
            }
        });

    }




    @NonNull
    @Override
    public Rvadapter_for_employee_submitted_tasks_mng.viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_for_file_view,null,false);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        v.setLayoutParams(lp);
        return new Rvadapter_for_employee_submitted_tasks_mng.viewholder(v);
    }

    class viewholder extends RecyclerView.ViewHolder{

        TextView File_name;

        public viewholder(View v){

            super(v);
            File_name = v.findViewById(R.id.mng_upload_task_name);

        }

    }

}

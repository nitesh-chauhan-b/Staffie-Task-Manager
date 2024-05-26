package com.managmnet.staffie.admin.recyclerviews;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.managmnet.staffie.R;
import com.managmnet.staffie.employee.Uploaded_PDF_View;
import com.managmnet.staffie.employee.modles.Files;
import com.managmnet.staffie.manager.recyclerviews.Rvadapter_for_employee_submitted_tasks_mng;

public class RVadapter_for_mng_submitted_tasks  extends FirebaseRecyclerAdapter<Files,RVadapter_for_mng_submitted_tasks.viewholder> {


    public RVadapter_for_mng_submitted_tasks(@NonNull FirebaseRecyclerOptions<Files> options) {

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
    public RVadapter_for_mng_submitted_tasks.viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_for_file_view,null,false);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        v.setLayoutParams(lp);
        return new RVadapter_for_mng_submitted_tasks.viewholder(v);
    }

    class viewholder extends RecyclerView.ViewHolder{

        TextView File_name;

        public viewholder(View v){

            super(v);
            File_name = v.findViewById(R.id.mng_upload_task_name);

        }

    }


}

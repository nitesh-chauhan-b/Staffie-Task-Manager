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
import com.managmnet.staffie.employee.recyclerviews.RVadapter_for_employee_uploaded_Tasks;

public class RVadapter_for_manager_uploaded_Tasks extends FirebaseRecyclerAdapter<Files, RVadapter_for_manager_uploaded_Tasks.viewholder> {


    public RVadapter_for_manager_uploaded_Tasks(@NonNull FirebaseRecyclerOptions<Files> options) {

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
        viewholder.menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                PopupMenu popup = new PopupMenu(v.getContext(), viewholder.menu);
                //inflating menu from xml resource
                popup.inflate(R.menu.remove_menu);
                //adding click listener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {

                        if(item.getItemId()==R.id.remove_file)
                        {
                            FirebaseStorage storage = FirebaseStorage.getInstance();
                            if(viewholder.File_name==null)
                            {
                                Toast.makeText(v.getContext(), "Null File Name", Toast.LENGTH_SHORT).show();
                            }
                            else{
                                StorageReference storageRef = storage.getReference().child("Manager Completed Tasks").child(viewholder.File_name.getText().toString());

                                FirebaseDatabase db = FirebaseDatabase.getInstance();
                                DatabaseReference dbref = db.getReference();
                                FirebaseAuth auth = FirebaseAuth.getInstance();
                                FirebaseUser user = auth.getCurrentUser();
                                // Create a reference to the file to delete

                                // Delete the file
                                storageRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {

                                        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
                                        Query applesQuery = ref.child("Completed Tasks").child("uploaded").child("Manager").child(user.getUid()).
                                                child(files.getTask_name()).orderByChild("file_name").equalTo(files.getFile_name());


                                        applesQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(DataSnapshot dataSnapshot) {
                                                for (DataSnapshot appleSnapshot: dataSnapshot.getChildren()) {
                                                    appleSnapshot.getRef().removeValue();
                                                }
                                            }

                                            @Override
                                            public void onCancelled(DatabaseError databaseError) {
                                                Toast.makeText(v.getContext(), "Error in finding file", Toast.LENGTH_SHORT).show();
                                            }
                                        });

                                        Toast.makeText(v.getContext(), "File is deleted!", Toast.LENGTH_SHORT).show();

                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception exception) {
                                        Toast.makeText(v.getContext(), "File is not deleted! "+exception.getMessage(), Toast.LENGTH_SHORT).show();

                                    }
                                });

                            }


                        }
                        return false;
                    }
                });
                popup.show();



            }
        });

    }


    @NonNull
    @Override
    public RVadapter_for_manager_uploaded_Tasks.viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_mng_got_task_view,null,false);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        v.setLayoutParams(lp);
        return new RVadapter_for_manager_uploaded_Tasks.viewholder(v);
    }

    class viewholder extends RecyclerView.ViewHolder{

        TextView File_name;
        ImageView menu;
        public viewholder(View v){

            super(v);
            File_name = v.findViewById(R.id.mng_upload_task_name);
            menu = v.findViewById(R.id.mng_got_task_menu);
        }

    }

}


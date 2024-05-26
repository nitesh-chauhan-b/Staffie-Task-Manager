package com.managmnet.staffie.employee;

import static android.app.Activity.RESULT_OK;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavOptions;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.provider.OpenableColumns;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.managmnet.staffie.R;
import com.managmnet.staffie.employee.modles.EmployeeDetails;
import com.managmnet.staffie.employee.modles.Files;
import com.managmnet.staffie.employee.navigationfragments.Employee_Screen_Fragment;
import com.managmnet.staffie.employee.recyclerviews.RVadapter_for_employee_uploaded_Tasks;
import com.managmnet.staffie.manager.models.ManagerDetails;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


public class EmployeeDetailTaskToComplete extends Fragment {

    ImageView back;
    TextView task_name,task_due_date,task_instructions;
    Button submit_btn;
    TextView attach_task,new_task;
    LinearLayout adding_layout;
    EmployeeScreen employeeScreen;
    String t_name, t_assigned_to, t_status, t_start_date, t_due_date, t_desc, t_priority,t_type,t_by;

    ArrayList<String> list_in_string;
    LinearLayout assigned_task_emp_list;

    ArrayList<EmployeeDetails> list;
    DatabaseReference reference;

    RecyclerView rv;


    //Selecting Uploading File
    Uri selectedFileUri;
    FirebaseStorage storage;
    FirebaseDatabase db;
    ProgressDialog progress;
    FirebaseAuth auth;
    String File_name;
    FirebaseUser user;
    String filename;
    ArrayList<Files> all_files;
    RVadapter_for_employee_uploaded_Tasks adapter;
    Files file;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v=  inflater.inflate(R.layout.fragment_employee_detail_task_to_complete, null);

        adding_layout = v.findViewById(R.id.layout_for_emp_work_attachment);
        back = v.findViewById(R.id.back_button);
        task_name = v.findViewById(R.id.emp_assigned_task_name);
        task_due_date = v.findViewById(R.id.emp_assigned_task_due_date);
        task_instructions = v.findViewById(R.id.emp_assigned_task_instruction);
        submit_btn = v.findViewById(R.id.emp_assigned_task_submit);

        attach_task = v.findViewById(R.id.emp_got_attach_icon);


        assigned_task_emp_list = v.findViewById(R.id.assigned_task_mng_list);
        rv = v.findViewById(R.id.emp_uploaded_tasks);
        assigned_task_emp_list.removeAllViews();
        list = new ArrayList<>();
        all_files = new ArrayList<>();

        employeeScreen = (EmployeeScreen) getActivity();
        employeeScreen.toolbar.setVisibility(View.GONE);
        employeeScreen.bottomAppBar.setVisibility(View.GONE);
        employeeScreen.setDrawerEnabled(false);


        LinearLayoutManager lm = new LinearLayoutManager(getActivity());
        rv.setLayoutManager(lm);

        storage = FirebaseStorage.getInstance();
        db = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();

        user= auth.getCurrentUser();


        reference = FirebaseDatabase.getInstance().getReference("Tasks/Manager");
        list_in_string = new ArrayList<>();


        Bundle b = getArguments();
        assert b != null;
        t_name = b.getString("task_name");
        t_due_date = b.getString("task_due_date");
        t_type = b.getString("task_type");
        t_by = b.getString("task_by");
        t_start_date = b.getString("task_start_date");
        t_desc = b.getString("task_desc");
        t_priority = b.getString("task_priority");
        task_name.setText(t_name);

        task_due_date.setText(t_due_date);
        task_instructions.setText(t_desc);

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
        
        attach_task.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popup = new PopupMenu(getActivity(), attach_task);
                //inflating menu from xml resource
                popup.inflate(R.menu.mng_task_adding_menu);
                //adding click listener
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {

                        if(item.getItemId()==R.id.mng_upload_task)
                        {
                            if(ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED)
                            {
                                selectFile();
                                new Handler().postDelayed(new Runnable() {

                                    @Override
                                    public void run() {
                                        if(selectedFileUri!=null)
                                        {
                                            uploadFile(selectedFileUri);
                                        }
                                        else{
                                            Toast.makeText(getContext(), "File is not selected", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }, 5000 );

                            }
                            else{
                                ActivityCompat.requestPermissions(getActivity(),new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},0);
                            }

                        }
                        return false;
                    }
                });
                popup.show();
            }
        });
        display_member_list();
        firebase_files();

        submit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProgressDialog progressDialog = new ProgressDialog(getActivity());
                progressDialog.setTitle("Submitting");
                progressDialog.setMessage("Please wait....");

                progressDialog.show();


                DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
                String key = ref.child("Completed Tasks").child("Employee").child(user.getUid()).
                        child(t_name).push().getKey();
                assert key != null;
                Query applesQuery = ref.child("Completed Tasks").child("uploaded").child("Employee").child(user.getUid()).
                        child(t_name);


                applesQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot appleSnapshot: dataSnapshot.getChildren()) {

                            appleSnapshot.getRef().child("is_submitted").setValue(true);

                            try{
                                FirebaseDatabase db = FirebaseDatabase.getInstance();
                                DatabaseReference df_ref= db.getReference();
                                file.setIs_submitted(true);

                                db.getReference().child("Completed Tasks").child("submitted").child("Employee").child(user.getUid()).child(t_name)
                                        .child(df_ref.push().getKey()).setValue(file);

                                db.getReference().child("Completed Tasks").child("uploaded").child("Employee").child(user.getUid()).child(t_name)
                                        .removeValue();

                                db.getReference("Tasks/Manager/").child(t_name).child("task_status").setValue("Done");

                                Toast.makeText(getActivity(), "Your is successfully uploaded!", Toast.LENGTH_SHORT).show();
                                
//                                FragmentTransaction ft = getFragmentManager().beginTransaction();
//                                ft.replace(R.id.emp_screen_navigation_menu,new EmployeeDetailCompletedTaskView());
//                                ft.addToBackStack(null);
//                                ft.commit();
                            }
                            catch (Exception ex){

                                Toast.makeText(getActivity(), "Error in submitting please remove and re-upload your files ", Toast.LENGTH_SHORT).show();
                            }


                                progressDialog.dismiss();
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Toast.makeText(v.getContext(), "Error in finding file", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                });


            }
        });
        
        return v;
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    //Selecting File From Device
    private void selectFile() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        String [] mimeTypes = {"text/csv", "text/comma-separated-values","application/pdf","image/*","audio/*","video/*","application/msword","application/vnd.openxmlformats-officedocument.wordprocessingml.document", // .doc & .docx
                "application/vnd.ms-powerpoint","application/vnd.openxmlformats-officedocument.presentationml.presentation", // .ppt & .pptx
                "application/vnd.ms-excel","application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", // .xls & .xlsx
                "text/plain",
                "application/pdf",
                "application/zip"};
        intent.setType("*/*");
        intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
        startActivityForResult(intent, 86);

    }

    //Is Permission Granted or not!
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode==9 && grantResults[0]==PackageManager.PERMISSION_GRANTED)
        {
            selectFile();
        }
        else{
            Toast.makeText(getActivity(), "Please grant storage permission", Toast.LENGTH_SHORT).show();
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }


    //Is file is been selected or not!
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        //select file or not
        if(requestCode==86 && resultCode==RESULT_OK && data!=null){
            selectedFileUri = data.getData();
            
            //Getting selected file name
//            Uri uri = data.getData();
            String uriString = selectedFileUri.toString();
            File myFile = new File(uriString);
            String path = myFile.getAbsolutePath();
            String displayName = null;

            if (uriString.startsWith("content://")) {
                Cursor cursor = null;
                try {
                    cursor = getActivity().getContentResolver().query(selectedFileUri, null, null, null, null);
                    if (cursor != null && cursor.moveToFirst()) {
                        File_name = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                    }
                } finally {
                    cursor.close();
                }
            } else if (uriString.startsWith("file://")) {
                File_name = myFile.getName();
            }

            get_task_view();

        }
        else{
            Toast.makeText(getActivity(), "Please select file!", Toast.LENGTH_SHORT).show();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    //Uploading File
    private void uploadFile(Uri selectedFileUri) {
        progress = new ProgressDialog(getActivity());
        progress.setTitle("Uploading File...");
        progress.setProgress(0);
        progress.setProgressStyle(progress.STYLE_HORIZONTAL);
        progress.show();


        filename = File_name;
        final StorageReference reference1= storage.getReference().child("Employee Completed Tasks").child(File_name);

        reference1.putFile(selectedFileUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        DatabaseReference dbref = db.getReference();

                        //Downloading Url
                        reference1.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {

                                file = new Files();
                                file.setFile_name(File_name);
                                file.setFile_url(uri.toString());
                                file.setTask_name(t_name);
                                file.setTask_desc(t_desc);
                                file.setTask_due_date(t_due_date);
                                file.setTask_start_date(t_start_date);
                                file.setTask_priority(t_priority);
                                file.setIs_submitted(false);

                                dbref.child("Completed Tasks").child("uploaded").child("Employee").child(user.getUid()).child(t_name).child(dbref.push().getKey()).
                                setValue(file);


//                                Toast.makeText(getActivity(), "File Successfully Uploaded!", Toast.LENGTH_SHORT).show();
                                progress.hide();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_LONG).show();
                            }
                        });

                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getActivity(), "File is not Successfully Uploaded!", Toast.LENGTH_SHORT).show();
                progress.dismiss();
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                int currentprogress = (int) (100*snapshot.getBytesTransferred()/snapshot.getTotalByteCount());
                progress.setProgress(currentprogress);
            }
        });
    }


    public void display_member_list() {
        for(int i=0; i<list.size(); i++)
        {
            list.remove(i);
        }
        assigned_task_emp_list.removeAllViews();

        reference.child(t_name).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                
                try{
                    Map map = (Map) snapshot.getValue();
//                        HashMap<String,String> map = new HashMap<String,String>();

                    ArrayList<String> got_list= (ArrayList<String>) map.get("assigned_member");
                    if (got_list == null) {
                        Toast.makeText(getActivity(), "No Member is assigned!", Toast.LENGTH_SHORT).show();
                    } else {
                        for (int i = 0; i < got_list.size(); i++) {
                            list.add(new EmployeeDetails(got_list.get(i)));
                            list_in_string.add(got_list.get(i));
                            assigned_employee(list.get(i).getAssigned_task_emp_name(), i);
                        }
                    }
                }
                catch (Exception e)
                {
                    Toast.makeText(employeeScreen.getApplicationContext(), "Error :"+e.getMessage(), Toast.LENGTH_SHORT).show();
                }
               
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    public void assigned_employee(java.lang.String added_name,int Pos){
        View assigned_emp_view = getLayoutInflater().inflate(R.layout.rv_manager_names_of_task,null);
        if (added_name == null) {

        }
        else{
            TextView emp_name = assigned_emp_view.findViewById(R.id.mng_assigned_task_emp_name);
            emp_name.setText(added_name);

            assigned_task_emp_list.addView(assigned_emp_view);
        }

    }

    public void get_task_view()
    {


//        View task_view = getLayoutInflater().inflate(R.layout.rv_mng_got_task_view,null);
//        TextView uploaded_task_name = task_view.findViewById(R.id.mng_upload_task_name);
//        ImageView menu = task_view.findViewById(R.id.mng_got_task_menu);
//        if(File_name!=null)
//        {
//            uploaded_task_name.setText(File_name);
//        }
//        else{
//            Toast.makeText(getActivity(), "The File name is not selected!", Toast.LENGTH_SHORT).show();
//        }
//        menu.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                remove_work(v);
//            }
//        });
//
//        adding_layout.addView(task_view);
    }

    public void remove_work(View v)
    {
        adding_layout.removeView(v);
    }


    public void firebase_files(){

        FirebaseRecyclerOptions<Files> options;
        options = new FirebaseRecyclerOptions.Builder<Files>()
                .setQuery(FirebaseDatabase.getInstance().getReference().child("Completed Tasks").child("uploaded").child("Employee").child(user.getUid()).child(t_name),
                        Files.class)
                .build();

        adapter = new RVadapter_for_employee_uploaded_Tasks(options);
        rv.setAdapter(adapter);
    }


}
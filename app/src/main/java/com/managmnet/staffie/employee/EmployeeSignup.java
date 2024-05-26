package com.managmnet.staffie.employee;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.managmnet.staffie.R;
import com.managmnet.staffie.manager.fragments.Verify_manager_email;
import com.managmnet.staffie.manager.models.Manager_User;


public class EmployeeSignup extends Fragment {

    EditText org_name,emp_id,emp_name,emp_phone,emp_street,emp_city,
            emp_pincode,emp_country,emp_email,emp_pass;


    private FirebaseAuth auth;
    ProgressDialog progressDialog;
    private FirebaseDatabase database;
    Button btn;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v= inflater.inflate(R.layout.fragment_employee_signup,null);
        org_name = v.findViewById(R.id.org_name);
        emp_id = v.findViewById(R.id.emp_id);
        emp_name = v.findViewById(R.id.emp_name);
        emp_phone = v.findViewById(R.id.emp_phone);
        emp_email = v.findViewById(R.id.emp_email);
        emp_pass = v.findViewById(R.id.emp_pass);

        database = FirebaseDatabase.getInstance();
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setTitle("Verifying Account");
        progressDialog.setMessage("Please wait...");

        auth = FirebaseAuth.getInstance();


        btn = v.findViewById(R.id.emp_register);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.show();
                String o_name = org_name.getText().toString();
                String e_id = emp_id.getText().toString();
                String e_name = emp_name.getText().toString();
                String e_phone = emp_phone.getText().toString();
                String e_email = emp_email.getText().toString();
                String e_pass = emp_pass.getText().toString();

                if (TextUtils.isEmpty(org_name.getText()) || TextUtils.isEmpty(emp_id.getText()) ||
                        TextUtils.isEmpty(emp_name.getText()) || TextUtils.isEmpty(emp_phone.getText()) ||
                        TextUtils.isEmpty(emp_email.getText()) || TextUtils.isEmpty(emp_pass.getText())
                ) {
                    org_name.setError("Required!");
                    emp_pass.setError("Required!");
                    emp_id.setError("Required!");
                    emp_name.setError("Required!");
                    emp_phone.setError("Required!");
                    emp_email.setError("Required!");
                    progressDialog.dismiss();

                } else {
                    auth.createUserWithEmailAndPassword(e_email, e_pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful())
                            {
                                FirebaseUser vuser = auth.getCurrentUser();

                                vuser.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Toast.makeText(v.getContext(),"Verification Email is been sent.",Toast.LENGTH_SHORT).show();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(v.getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                });


                                Manager_User user = new Manager_User(o_name ,e_name,
                                        e_phone, e_email, e_pass);

                                String emp_id = task.getResult().getUser().getUid();
                                database.getReference().child("Users").child("Employee").child("Sign Up").child(emp_id).setValue(user);
                                Toast.makeText(getActivity(), "Account Created Successfully!", Toast.LENGTH_SHORT).show();

                                progressDialog.dismiss();

                                //hide keyboard
                                getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

                                Bundle b = new Bundle();
                                b.putString("emp_name", e_name);
                                b.putString("emp_email", e_email);
                                Verify_manager_email f = new Verify_manager_email();
                                f.setArguments(b);

                                FragmentTransaction ft = getFragmentManager().beginTransaction();
                                ft.replace(R.id.adm_login_layout, f);
                                ft.commit();
                            }else {
                                Toast.makeText(getActivity(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                progressDialog.dismiss();
                            }



                        }
                    });
                }


            }
        });
        return v;
    }
}
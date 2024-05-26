package com.managmnet.staffie.login;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.managmnet.staffie.R;
import com.managmnet.staffie.admin.fragments.AdminLogin;
import com.managmnet.staffie.employee.EmployeeLogin;
import com.managmnet.staffie.manager.fragments.ManagerLogin;


public class ForgotPassword extends Fragment {

    Button btn;
    EditText email;
    FirebaseAuth auth;
    FirebaseDatabase database;
    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceStates)
    {
        View v = inflater.inflate(R.layout.fragment_forgot_password,null);

        email = v.findViewById(R.id.v_email);
        btn = v.findViewById(R.id.verify);
        auth = FirebaseAuth.getInstance();

        database = FirebaseDatabase.getInstance();
        Bundle b = getArguments();
        String user_type = b.getString("user_type");


        btn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v)
            {
                String user_email = email.getText().toString();

                if(user_email.equals(""))
                {
                    email.setError("Email Required!");
                }
                else{
                    auth.sendPasswordResetEmail(user_email).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(getActivity(),"Password Reset Email is has been sent.", Toast.LENGTH_LONG).show();


                            if(user_type.equals("admin"))
                            {
                                AdminLogin f = new AdminLogin();
                                FragmentTransaction ft = getFragmentManager().beginTransaction();
                                ft.replace(R.id.adm_login_layout, f);
                                ft.commit();
                            }
                            else if(user_type.equals("manager"))
                            {
                                ManagerLogin f = new ManagerLogin();
                                FragmentTransaction ft = getFragmentManager().beginTransaction();
                                ft.replace(R.id.adm_login_layout, f);
                                ft.commit();
                            }
                            else if(user_type.equals("employee"))
                            {
                                EmployeeLogin f = new EmployeeLogin();
                                FragmentTransaction ft = getFragmentManager().beginTransaction();
                                ft.replace(R.id.adm_login_layout, f);
                                ft.commit();
                            }
                            else{
                                Toast.makeText(getActivity(),user_type,Toast.LENGTH_SHORT).show();
                            }

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(getActivity(),e.getMessage(),Toast.LENGTH_LONG).show();
                        }
                    });
                }

            }
        });

        return v;
    }
}
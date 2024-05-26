package com.managmnet.staffie.admin.fragments;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.managmnet.staffie.R;


public class Verify_admin_email extends Fragment {


    TextView verify_message, verify_message_email;
    Button continue_login;
    TextView resend_email;
    FirebaseAuth auth;
    ProgressDialog progress;
    FirebaseUser user;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_verify_admin_email, container, false);
        verify_message = v.findViewById(R.id.verify_message);
        verify_message_email = v.findViewById(R.id.verify_message_email);
        continue_login = v.findViewById(R.id.adm_email_continue);
        resend_email = v.findViewById(R.id.adm_resent_link);


        progress = new ProgressDialog(getActivity());
        progress.setTitle("Getting to Login Screen");
        progress.setMessage("Please Wait");


        Bundle b = getArguments();
        String admin_name = b.getString("adm_name");
        String admin_mail = b.getString("adm_email");

        verify_message.setText("Hi " + admin_name + "! We sent an email to ");
        verify_message_email.setText(admin_mail + ".");
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        if (user == null) {
            Toast.makeText(getActivity(), "user is not showing", Toast.LENGTH_SHORT).show();
        } else {
            resend_email.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    reload_user();
                    if (!auth.getCurrentUser().isEmailVerified()) {
                        user.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(getActivity(), "Verification Email has been sent.", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
            });

            continue_login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    reload_user();
                    progress.show();
                    final Handler handler = new Handler(Looper.getMainLooper());
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (auth.getCurrentUser().isEmailVerified()) {
                                progress.dismiss();
                                AdminLogin f = new AdminLogin();
                                FragmentTransaction ft = getFragmentManager().beginTransaction();
                                ft.replace(R.id.adm_login_layout, f);
                                ft.commit();
                            } else {
                                Toast.makeText(getActivity(), "Email is Not Verified!", Toast.LENGTH_SHORT).show();
                                progress.dismiss();
                            }
                        }
                    }, 1000);



                }
            });
        }


        return v;
    }

    public void reload_user() {
        user.reload();
    }
}
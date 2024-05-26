package com.managmnet.staffie.admin.fragments;


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
import com.managmnet.staffie.admin.models.Admin_User;


public class AdminSignup extends Fragment {

    EditText org_name, org_address, org_country,
            org_city, org_pincode, adm_name, adm_phone, adm_address,
            adm_pincode, adm_email, adm_pass;
    private FirebaseAuth auth;
    ProgressDialog progressDialog;
    private FirebaseDatabase database;
    Button btn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_admin_signup, null);

        org_name = v.findViewById(R.id.org_name);
        org_address = v.findViewById(R.id.org_address);
        org_country = v.findViewById(R.id.org_country);
        org_city = v.findViewById(R.id.org_city);
        org_pincode = v.findViewById(R.id.org_pincode);
        adm_name = v.findViewById(R.id.adm_name);
        adm_phone = v.findViewById(R.id.adm_phone);
//        adm_address = v.findViewById(R.id.adm_address);
//        adm_pincode = v.findViewById(R.id.adm_pincode);
        adm_email = v.findViewById(R.id.adm_email);
        adm_pass = v.findViewById(R.id.adm_pass);


        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();


        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setTitle("Verifying Account");
        progressDialog.setMessage("Please wait...");
        btn = v.findViewById(R.id.adm_register);


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.show();
                String o_name = org_name.getText().toString();
                String o_address = org_address.getText().toString();
                String o_country = org_country.getText().toString();
                String o_city = org_city.getText().toString();
                String o_pincode = org_pincode.getText().toString();
                String a_name = adm_name.getText().toString();
                String a_phone = adm_phone.getText().toString();
//                String a_address = adm_address.getText().toString();
//                String a_pincode = adm_pincode.getText().toString();
                String a_email = adm_email.getText().toString();
                String a_pass = adm_pass.getText().toString();


                if (TextUtils.isEmpty(org_name.getText()) || TextUtils.isEmpty(org_address.getText()) ||
                        TextUtils.isEmpty(org_country.getText()) || TextUtils.isEmpty(org_city.getText()) ||
                        TextUtils.isEmpty(org_pincode.getText()) ||
                        TextUtils.isEmpty(adm_name.getText()) || TextUtils.isEmpty(adm_phone.getText()) ||
                        TextUtils.isEmpty(adm_email.getText()) || TextUtils.isEmpty(adm_email.getText())
                ) {
                    org_name.setError("Required!");

                    org_address.setError("Required!");
                    org_country.setError("Required!");
                    org_city.setError("Required!");
                    org_pincode.setError("Required!");
                    adm_name.setError("Required!");
                    adm_phone.setError("Required!");
                    adm_email.setError("Required!");
                    adm_pass.setError("Required!" +
                            "");
                    progressDialog.dismiss();

                } else {
                    auth.createUserWithEmailAndPassword(a_email, a_pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
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


                                    Admin_User user = new Admin_User(o_name, o_address, o_country,
                                            o_city, o_pincode, a_name, a_phone, a_email, a_pass);

                                    String adm_id = task.getResult().getUser().getUid();
                                    database.getReference().child("Users").child("Admin").child("Sign Up").child(adm_id).setValue(user);
                                    Toast.makeText(getActivity(), "Account Created Successfully!", Toast.LENGTH_SHORT).show();

                                    progressDialog.dismiss();

                                    //hide keyboard
                                    getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

                                    Bundle b = new Bundle();
                                    b.putString("adm_name", a_name);
                                    b.putString("adm_email", a_email);
                                    Verify_admin_email f = new Verify_admin_email();
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
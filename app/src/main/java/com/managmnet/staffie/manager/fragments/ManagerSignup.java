package com.managmnet.staffie.manager.fragments;

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
import com.managmnet.staffie.manager.models.Manager_User;


public class ManagerSignup extends Fragment {

    EditText org_name,mng_id,mng_name,mng_phone,mng_street,mng_city,
            mng_pincode,mng_country,mng_email,mng_pass;

    private FirebaseAuth auth;
    ProgressDialog progressDialog;
    private FirebaseDatabase database;
    Button btn;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceStates)
    {
        View v= inflater.inflate(R.layout.fragment_manager_signup,null);

        org_name = v.findViewById(R.id.org_name);
        mng_id = v.findViewById(R.id.mng_id);
        mng_name = v.findViewById(R.id.mng_name);
        mng_phone = v.findViewById(R.id.mng_phone);
        mng_email = v.findViewById(R.id.mng_email);
        mng_pass = v.findViewById(R.id.mng_pass);
//        mng_street = v.findViewById(R.id.mng_street);
//        mng_city = v.findViewById(R.id.mng_city);
//        mng_pincode = v.findViewById(R.id.mng_pincode);
//        mng_country = v.findViewById(R.id.mng_country);
        database = FirebaseDatabase.getInstance();
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setTitle("Verifying Account");
        progressDialog.setMessage("Please wait...");

        auth = FirebaseAuth.getInstance();

        btn = v.findViewById(R.id.mng_register);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.show();
                String o_name = org_name.getText().toString();
                String m_id = org_name.getText().toString();
                String m_name = mng_name.getText().toString();
                String m_phone = mng_phone.getText().toString();
                String m_email = mng_email.getText().toString();
                String m_pass = mng_pass.getText().toString();

                if (TextUtils.isEmpty(org_name.getText()) || TextUtils.isEmpty(mng_id.getText()) ||
                        TextUtils.isEmpty(mng_name.getText()) || TextUtils.isEmpty(mng_phone.getText()) ||
                        TextUtils.isEmpty(mng_email.getText()) || TextUtils.isEmpty(mng_pass.getText())
                ) {
                    org_name.setError("Required!");
                    mng_pass.setError("Required!");
                    mng_id.setError("Required!");
                    mng_name.setError("Required!");
                    mng_phone.setError("Required!");
                    mng_email.setError("Required!");
                    progressDialog.dismiss();


                } else {
                    auth.createUserWithEmailAndPassword(m_email, m_pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
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


                                Manager_User user = new Manager_User(o_name ,m_name,
                                        m_phone, m_email, m_pass);

                                String mng_id = task.getResult().getUser().getUid();
                                database.getReference().child("Users").child("Manager").child("Sign Up").child(mng_id).setValue(user);
                                Toast.makeText(getActivity(), "Account Created Successfully!", Toast.LENGTH_SHORT).show();

                                progressDialog.dismiss();

                                //hide keyboard
                                getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

                                Bundle b = new Bundle();
                                b.putString("mng_name", m_name);
                                b.putString("mng_email", m_email);
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
package com.managmnet.staffie.admin.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.FirebaseDatabase;
import com.managmnet.staffie.admin.AdminScreen;
import com.managmnet.staffie.admin.models.Admin_User;
import com.managmnet.staffie.login.ForgotPassword;
import com.managmnet.staffie.R;
import com.managmnet.staffie.login.MainActivity;


public class AdminLogin extends Fragment implements View.OnClickListener {

    EditText admin_email, admin_pass;
    Button login_btn,google_login;

    TextView tv1, tv2;
    FirebaseAuth auth;
    FirebaseUser user;
    FirebaseDatabase database;

    GoogleSignInClient mGoogleSignInClient;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_admin_login, null);

        ProgressDialog p = new ProgressDialog(getActivity());
        p.setMessage("Loading");
        admin_email = v.findViewById(R.id.email);
        admin_pass = v.findViewById(R.id.pass);
        tv1 = v.findViewById(R.id.forgot_pass);
        tv2 = v.findViewById(R.id.sign_up);
        google_login = v.findViewById(R.id.login_with_google);

        google_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        database = FirebaseDatabase.getInstance();
        if(user!=null)
        {
            SharedPreferences pref = getActivity().getSharedPreferences("mypref", Context.MODE_PRIVATE);
            SharedPreferences.Editor ed = pref.edit();
            ed.putBoolean("IsLogin",true);
            ed.putString("user_type","admin");
            ed.apply();


            Intent i = new Intent(getActivity(), AdminScreen.class);
            startActivity(i);

            getActivity().finish();

        }
        tv1.setOnClickListener(this);
        tv2.setOnClickListener(this);

        login_btn = v.findViewById(R.id.login);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(getActivity(),gso);

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                p.show();
                String adm_email = admin_email.getText().toString();
                String adm_pass = admin_pass.getText().toString();

                if (adm_email.equals("") || adm_pass.equals("")) {
                    p.dismiss();
                    Toast.makeText(getActivity(), "User Name or Password is Empty", Toast.LENGTH_SHORT).show();
                } else {

                    auth.signInWithEmailAndPassword(adm_email, adm_pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                SharedPreferences pref = getActivity().getSharedPreferences("mypref", Context.MODE_PRIVATE);
                                SharedPreferences.Editor ed = pref.edit();
                                ed.putBoolean("IsLogin",true);
                                ed.putString("user_type","admin");
                                ed.apply();
                                p.dismiss();
                                getActivity().finish();
                                Intent i = new Intent(getActivity(), AdminScreen.class);
                                startActivity(i);
                            } else {
                                p.dismiss();
                                Toast.makeText(getActivity(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }


            }
        });


        return v;

    }


    @Override
    public void onClick(View v) {
        if (v == tv1) {
            ForgotPassword f = new ForgotPassword();
            Bundle b = new Bundle();
            b.putString("user_type","admin");
            f.setArguments(b);
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.replace(R.id.adm_login_layout, f);
            ft.commit();
        } else if (v == tv2) {
            AdminSignup f = new AdminSignup();
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.replace(R.id.adm_login_layout, f);
            ft.commit();
        }
    }


    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }
    int RC_SIGN_IN = 65;
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                account.getId();
//                String r_db_id = account.getId();
//                FirebaseDatabase database_c = FirebaseDatabase.getInstance();
//                String db_id = database_c.getReference().child("users").child("admin").child(auth.getUid()).getKey();
//
//                Toast.makeText(getActivity(), r_db_id, Toast.LENGTH_SHORT).show();
//                Toast.makeText(getActivity(), db_id, Toast.LENGTH_SHORT).show();

                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w("TAG", "Google sign in failed", e);
            }
        }
    }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        auth.signInWithCredential(credential)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d("TAG", "signInWithCredential:success");
                            FirebaseUser user = auth.getCurrentUser();
                            Admin_User adm_user = new Admin_User(user.getUid(),user.getDisplayName(),user.getPhoneNumber(),user.getEmail());

                            database.getReference().child("Users").child("Admin").child("Using Goggle").
                                    child(user.getUid()).setValue(adm_user);

                            Intent i = new Intent(getActivity(),AdminScreen.class);
                            startActivity(i);

                        } else {
                            Log.w("TAG", "signInWithCredential:failure", task.getException());
//                            updateUI(null);
                        }
                    }
                });
    }

}
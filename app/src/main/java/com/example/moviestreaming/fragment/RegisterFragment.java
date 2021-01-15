package com.example.moviestreaming.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.moviestreaming.R;
import com.example.moviestreaming.global.GetURLs;
import com.example.moviestreaming.view.HomeActivity;

import java.util.HashMap;
import java.util.Map;


public class RegisterFragment extends Fragment {

    TextView goToLoginTxt;
    EditText usernameEDT , emailEDT, passwordEDT, rePasswordEDT;
    Button registerBtn;
    RequestQueue requestQueue;


    public RegisterFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {



        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_register, container, false);

        usernameEDT = view.findViewById(R.id.entry_register_usernameET);
        emailEDT = view.findViewById(R.id.entry_register_emailET);
        passwordEDT = view.findViewById(R.id.entry_register_passwordET);
        rePasswordEDT = view.findViewById(R.id.entry_register_rePasswordET);

        registerBtn = view.findViewById(R.id.registerBtn);

        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(usernameEDT.getText().toString().trim().isEmpty() || usernameEDT.getText().toString().trim().length()<3){

                    usernameEDT.requestFocus();
                    Toast.makeText(getContext(), "username should not be empty and less than 3 characters!!", Toast.LENGTH_SHORT).show();


                }else if(emailEDT.getText().toString().trim().isEmpty()){
                    Toast.makeText(getContext(), "Email Address should not be empty!!", Toast.LENGTH_SHORT).show();
                    emailEDT.requestFocus();

                }else if(passwordEDT.getText().toString().isEmpty() || passwordEDT.getText().toString().trim().length() < 7 || passwordEDT.getText().toString().trim().length()>15){
                    Toast.makeText(getContext(), "password should not be empty and less than 7 and upper than 15 characters!!!", Toast.LENGTH_SHORT).show();
                    passwordEDT.requestFocus();

                }else if(!passwordEDT.getText().toString().trim().equals(rePasswordEDT.getText().toString().trim())){

                    Toast.makeText(getContext(), "your password does not match with each other!!", Toast.LENGTH_SHORT).show();
                    rePasswordEDT.requestFocus();

                }else{

                    requestQueue = Volley.newRequestQueue(getContext());

                    StringRequest stringRequest = new StringRequest(Request.Method.POST, GetURLs.UserInformationURL, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            if(response.trim().equals("wrong email address!")){

                                Toast.makeText(getContext(), response.trim(), Toast.LENGTH_SHORT).show();
                                emailEDT.requestFocus();

                            }else if(response.trim().equals("username should not be less than 3 characters!!")){

                                Toast.makeText(getContext(), response.trim(), Toast.LENGTH_SHORT).show();
                                usernameEDT.requestFocus();

                            }else if(response.trim().equals("password should be upper than 7 and less than 15 characters!")){

                                Toast.makeText(getContext(), response.trim(), Toast.LENGTH_SHORT).show();
                                passwordEDT.requestFocus();

                            }else if(response.trim().equals("Registered Successfully!!")){

                                Toast.makeText(getContext(), response , Toast.LENGTH_LONG).show();
                                saveState();
                                getActivity().getSupportFragmentManager().beginTransaction()
                                        .replace(R.id.entryFL , new VerifyPinFragment(usernameEDT.getText().toString().trim()))
                                        .commit();
                            }else {

                                Toast.makeText(getContext(), response.trim(), Toast.LENGTH_SHORT).show();

                            }

                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(getContext(), error.getMessage()+"", Toast.LENGTH_SHORT).show();
                        }
                    }){
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {

                            HashMap<String , String> params = new HashMap<>();

                            params.put("username" , usernameEDT.getText().toString().trim());
                            params.put("email" , emailEDT.getText().toString().trim());
                            params.put("password" , rePasswordEDT.getText().toString().trim());
                            Long credit = System.currentTimeMillis()/1000 + 518400;
                            params.put("credit" , String.valueOf(credit));

                            return params;
                        }
                    };

                    requestQueue.add(stringRequest );

                }

            }
        });


        goToLoginTxt = view.findViewById(R.id.go_to_login);
        goToLoginTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.entryFL , new LoginFragment()).commit();
            }
        });



        return view;
    }

    private void saveState(){
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("sharePrefMovieStreamingEntry" , Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("openActivityEntry" , true);
        editor.putString("username" , usernameEDT.getText().toString().trim());
        editor.putString("email" , emailEDT.getText().toString().trim());
        editor.commit();
    }
}
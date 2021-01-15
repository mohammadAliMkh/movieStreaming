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

public class LoginFragment extends Fragment {

    TextView goToRegisterTxt , forgotPasswordTxt;
    EditText usernameEDT , passWordEDT;
    Button loginBtn;
    RequestQueue requestQueue;
    SharedPreferences sharedPreferences;



    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        goToRegisterTxt = view.findViewById(R.id.go_to_register);
        goToRegisterTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.entryFL , new RegisterFragment()).commit();
            }
        });


        forgotPasswordTxt = view.findViewById(R.id.forgot_password);

        usernameEDT = view.findViewById(R.id.entry_usernameET);
        passWordEDT = view.findViewById(R.id.entry_passwordET);

        loginBtn = view.findViewById(R.id.loginBtn);
        loginBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                if(usernameEDT.getText().toString().trim().isEmpty()){

                    usernameEDT.requestFocus();
                    Toast.makeText(getContext(), "username is empty!", Toast.LENGTH_SHORT).show();

                }else if(passWordEDT.getText().toString().trim().isEmpty()){

                    passWordEDT.requestFocus();
                    Toast.makeText(getContext(), "password is empty!", Toast.LENGTH_SHORT).show();

                }else{

                    requestServer(usernameEDT.getText().toString().trim() , passWordEDT.getText().toString().trim());

                }
            }
        });

        return view;
    }

    private void saveState(String phone){
        sharedPreferences = getActivity().getSharedPreferences("sharePrefMovieStreamingEntry" , Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("username" , usernameEDT.getText().toString().trim());
        editor.putBoolean("openActivityEntry" , true);
        editor.putString("phoneNumber" , phone);
        editor.commit();
    }

    private void requestServer(String username, String password) {

        requestQueue = Volley.newRequestQueue(getContext());
        String link = GetURLs.LoginURL;

        StringRequest stringRequest = new StringRequest(Request.Method.POST, link, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                if(response.length() == 11){

                    Intent intent = new Intent(getContext() , HomeActivity.class);
                    saveState(response.substring(2));
                    Toast.makeText(getContext(), "Login Successfully!", Toast.LENGTH_SHORT).show();
                    getEmail(username);
                    getActivity().finish();
                    getContext().startActivity(intent);

                }else{

                    Toast.makeText(getContext(), response, Toast.LENGTH_SHORT).show();
                    usernameEDT.requestFocus();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), error.getMessage()+"", Toast.LENGTH_SHORT).show();
                usernameEDT.requestFocus();
            }
        }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                HashMap<String , String> hashMap = new HashMap<>();

                hashMap.put("username" , username);
                hashMap.put("password" , password);


                return hashMap;
            }
        };

        requestQueue.add(stringRequest);

    }

    private void getEmail(String username) {

        requestQueue = Volley.newRequestQueue(getActivity());

        StringRequest request = new StringRequest(Request.Method.POST, GetURLs.GetEmailByUsername, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("email" , response.trim());
                editor.commit();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), error.getMessage()+"", Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String , String> params = new HashMap<>();
                params.put("username" , username);
                return params;
            }
        };

        requestQueue.add(request);
    }
}
package com.example.moviestreaming.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.chaos.view.PinView;
import com.example.moviestreaming.R;
import com.example.moviestreaming.global.GetURLs;
import com.example.moviestreaming.view.HomeActivity;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class VerifyPinFragment extends Fragment {

    PinView phoneNumberView , pinCode;

    Button sendCodeBtn , nextBtnPin , retryBtnPin;

    RelativeLayout relativeLayoutPin , relativeLayoutPhoneNumber;
    RequestQueue requestQueue;
    String username;
    String code;
    public static String template = "TopMovies";


    public VerifyPinFragment(String username) {
        // Required empty public constructor
        this.username = username;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_get_phone_number, container, false);
        phoneNumberView = view.findViewById(R.id.pin_phone_number);
        pinCode = view.findViewById(R.id.pin_entry);

        sendCodeBtn = view.findViewById(R.id.sendCodeBtn);
        nextBtnPin = view.findViewById(R.id.nextPin);
        retryBtnPin = view.findViewById(R.id.retryPin);

        relativeLayoutPin = view.findViewById(R.id.x040);
        relativeLayoutPhoneNumber = view.findViewById(R.id.x041);

        sendCodeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String phoneNumber = "09" + phoneNumberView.getText().toString().trim();

                if(phoneNumber.length() < 11){

                    Toast.makeText(getContext(), "phone number is wrong!!", Toast.LENGTH_SHORT).show();
                    phoneNumberView.requestFocus();

                }else{

                    relativeLayoutPhoneNumber.setVisibility(View.GONE);
                    setPhoneNumberIntoServer(username , phoneNumber);
                    sendVerificationCode(phoneNumber);
                    relativeLayoutPin.setVisibility(View.VISIBLE);

                }


            }
        });

        nextBtnPin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(pinCode.getText().toString().trim().isEmpty() || pinCode.getText().toString().length() < 4){

                    Toast.makeText(getContext(), "Please Enter Pin!!", Toast.LENGTH_SHORT).show();

                }else if(!pinCode.getText().toString().trim().equals(code)){

                    Toast.makeText(getContext(), "Pin Code is WRONG!!!", Toast.LENGTH_SHORT).show();

                }else{

                    Intent intent = new Intent(getContext() , HomeActivity.class);

                    SharedPreferences sharedPreferences = getActivity().getSharedPreferences("sharePrefMovieStreamingEntry" , Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("phoneNumber" , phoneNumberView.getText().toString().trim());
                    editor.commit();

                    getActivity().startActivity(intent);
                    getActivity().finish();

                }


            }
        });

        return  view;
    }

    private void sendVerificationCode(String phoneNumber) {

        int pin = (int)(Math.random() * 10000);
        code = String.valueOf(pin);
        System.out.println(code);
        String url = GetURLs.SendVerificationCodeURL + "receptor=" + phoneNumber + "&token=" + code + "&template=" + template;
        requestQueue = Volley.newRequestQueue(getContext());

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                Log.d("result" , code);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(getContext(), error.getMessage()+"", Toast.LENGTH_SHORT).show();

            }
        });

        requestQueue.add(jsonObjectRequest);


    }

    private void setPhoneNumberIntoServer(String username , String phoneNumber) {

        requestQueue = Volley.newRequestQueue(getContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, GetURLs.UserPhoneInsertionURL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

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

                params.put("username" , username);
                params.put("phone" , phoneNumber);

                return params;
            }
        };

        requestQueue.add(stringRequest);

    }

}
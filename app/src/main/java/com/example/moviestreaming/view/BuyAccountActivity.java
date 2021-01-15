package com.example.moviestreaming.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.StrikethroughSpan;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.moviestreaming.R;
import com.example.moviestreaming.global.GetURLs;
import com.example.moviestreaming.model.Credit;
import com.zarinpal.ewallets.purchase.OnCallbackRequestPaymentListener;
import com.zarinpal.ewallets.purchase.OnCallbackVerificationPaymentListener;
import com.zarinpal.ewallets.purchase.PaymentRequest;
import com.zarinpal.ewallets.purchase.ZarinPal;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

public class BuyAccountActivity extends AppCompatActivity implements View.OnClickListener {

    TextView credit_first , credit_second , credit_third, credit_forth,
            price_first_off, price_second_off, price_third_off, price_forth_off,
            off_first , off_second , off_third, off_forth,
            price_second , price_first , price_third, price_forth;

    CardView one_month , three_month , six_month , one_year;

    public int one , three , six , twelve;

    public static int credit_option = 0;
    public static long total_credit;

    public final static long one_month_credit = 2678400L;
    public final static long three_month_credit = 8035200L;
    public final static long six_month_credit = 16070400L;
    public final static long one_year_credit = 32140800L;

    ImageView back_icon;
    RequestQueue requestQueue;

    String pattern = "###,###";
    DecimalFormat decimalFormat;

    SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_account);


        sharedPreferences = getApplicationContext().getSharedPreferences("sharePrefMovieStreamingEntry" , MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        Uri data = getIntent().getData();
        ZarinPal.getPurchase(this).verificationPayment(data, new OnCallbackVerificationPaymentListener() {
            @Override
            public void onCallbackResultVerificationPayment(boolean isPaymentSuccess, String refID, PaymentRequest paymentRequest) {


                if (isPaymentSuccess) {
                    /* When Payment Request is Success :) */
                    String message = "Your Payment is Success :) " + refID;
                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();

                    switch (credit_option){

                        case 0:
                            total_credit = sharedPreferences.getLong("total_credit" , System.currentTimeMillis() / 1000);

                            if(System.currentTimeMillis()/1000 > total_credit){

                                total_credit = System.currentTimeMillis()/1000 + one_month_credit;

                            }else{

                                total_credit = total_credit + one_month_credit;
                            }

                            editor.putLong("total_credit" , total_credit);
                            editor.commit();
                            saveTheCreditIntoTheServer(total_credit);
                            break;

                        case 1:
                            total_credit = sharedPreferences.getLong("total_credit" , System.currentTimeMillis() / 1000);

                            if(System.currentTimeMillis()/1000 > total_credit){

                                total_credit = System.currentTimeMillis()/1000 + three_month_credit;

                            }else{

                                total_credit = total_credit + three_month_credit;
                            }

                            editor.putLong("total_credit" , total_credit);
                            editor.commit();
                            saveTheCreditIntoTheServer(total_credit);
                            break;

                        case 2:
                            total_credit = sharedPreferences.getLong("total_credit" , System.currentTimeMillis() / 1000);

                            if(System.currentTimeMillis()/1000 > total_credit){

                                total_credit = System.currentTimeMillis()/1000 + six_month_credit;

                            }else{

                                total_credit = total_credit + six_month_credit;
                            }

                            editor.putLong("total_credit" , total_credit);
                            editor.commit();
                            saveTheCreditIntoTheServer(total_credit);
                            break;

                        case 3:
                            total_credit = sharedPreferences.getLong("total_credit" , System.currentTimeMillis() / 1000);

                            if(System.currentTimeMillis()/1000 > total_credit){

                                total_credit = System.currentTimeMillis()/1000 + one_year_credit;

                            }else{

                                total_credit = total_credit + one_year_credit;
                            }

                            editor.putLong("total_credit" , total_credit);
                            editor.commit();
                            saveTheCreditIntoTheServer(total_credit);
                            break;

                    }


                } else {
                    /* When Payment Request is Failure :) */
                    String message = "Your Payment is Failure :(";
                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                }


            }
        });



        setDetails();
        getDetails();

    }

    private void saveTheCreditIntoTheServer(long total_credit) {

        requestQueue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, GetURLs.setCreditForUserURL , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.d("insertCredit" , response);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(BuyAccountActivity.this, error.getMessage()+"", Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> creditMap = new HashMap<>();

                creditMap.put("email" , sharedPreferences.getString("email" , ""));
                creditMap.put("credit" , String.valueOf(total_credit));

                return creditMap;
            }
        };

        requestQueue.add(stringRequest);

    }

    public void setDetails(){

        credit_first = findViewById(R.id.credit_first_period_txt);
        credit_second = findViewById(R.id.credit_second_period_txt);
        credit_third = findViewById(R.id.credit_third_period_txt);
        credit_forth = findViewById(R.id.credit_forth_period_txt);


        price_first_off = findViewById(R.id.credit_first_price_by_off_txt);
        price_second_off = findViewById(R.id.credit_second_price_by_off_txt);
        price_third_off = findViewById(R.id.credit_third_price_by_off_txt);
        price_forth_off = findViewById(R.id.credit_forth_price_by_off_txt);

        off_first = findViewById(R.id.credit_first_off_txt);
        off_second = findViewById(R.id.credit_second_off_txt);
        off_third = findViewById(R.id.credit_third_off_txt);
        off_forth = findViewById(R.id.credit_forth_off_txt);

        price_first = findViewById(R.id.credit_first_price_txt);
        price_second = findViewById(R.id.credit_second_price_txt);
        price_third = findViewById(R.id.credit_third_price_txt  );
        price_forth = findViewById(R.id.credit_forth_price_txt);

        one_month = findViewById(R.id.one_month_CV);
        one_month.setOnClickListener(this);
        three_month = findViewById(R.id.three_month_CV);
        three_month.setOnClickListener(this);
        six_month = findViewById(R.id.six_month_CV);
        six_month.setOnClickListener(this);
        one_year = findViewById(R.id.one_year_CV);
        one_year.setOnClickListener(this);

        back_icon = findViewById(R.id.back_icon_buy_credit);
        back_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

    }

    private void getDetails() {

        requestQueue = Volley.newRequestQueue(this);


        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, GetURLs.creditURL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {

                    JSONArray jsonArray = response.getJSONArray("credit");

                    decimalFormat = new DecimalFormat(pattern);

                    for(int i =0; i<jsonArray.length(); i++){

                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        Credit credit = new Credit();

                        credit.setId(jsonObject.getString("id"));
                        credit.setPeriod(jsonObject.getString("period"));
                        credit.setPrice(jsonObject.getString("price"));
                        credit.setOff(jsonObject.getString("off"));

                        SpannableString price = new SpannableString(credit.getPrice());
                        price.setSpan(new StrikethroughSpan() , 0 , credit.getPrice().trim().length() , Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);


                        switch (i){

                            case 0:
                                credit_option = 0;
                                credit_first.setText(credit.getPeriod().trim());
                                price_first_off.setText(decimalFormat.format(Integer.parseInt(credit.getPrice().trim()) - (Integer.parseInt(credit.getPrice().trim()) * Integer.parseInt(credit.getOff().trim()) / 100)) + " IRR");
                                off_first.setText(credit.getOff() + ".0% OFF");
                                if(credit.getOff().equals("0")){
                                    price_first.setVisibility(View.GONE);
                                }
                                price_first.setText(price);
                                one = Integer.parseInt(credit.getPrice().trim());
                                break;

                            case 1:
                                credit_option = 1;
                                credit_second.setText(credit.getPeriod().trim());
                                price_second_off.setText(decimalFormat.format(Integer.parseInt(credit.getPrice().trim()) - (Integer.parseInt(credit.getPrice().trim()) * Integer.parseInt(credit.getOff().trim()) / 100)) + " IRR");
                                off_second.setText(credit.getOff() + ".0% OFF");
                                if(credit.getOff().equals("0")){
                                    price_second.setVisibility(View.GONE);
                                }
                                price_second.setText(price);
                                three = Integer.parseInt(credit.getPrice().trim());
                                break;

                            case 2:
                                credit_option = 2;
                                credit_third.setText(credit.getPeriod().trim());
                                price_third_off.setText(decimalFormat.format(Integer.parseInt(credit.getPrice().trim()) - (Integer.parseInt(credit.getPrice().trim()) * Integer.parseInt(credit.getOff().trim()) / 100)) + " IRR");
                                off_third.setText(credit.getOff() + ".0% OFF");
                                if(credit.getOff().equals("0")){
                                    price_third.setVisibility(View.GONE);
                                }
                                price_third.setText(price);
                                six = Integer.parseInt(credit.getPrice().trim());
                                break;

                            case 3:
                                credit_option = 3;
                                credit_forth.setText(credit.getPeriod().trim());
                                price_forth_off.setText(decimalFormat.format(Integer.parseInt(credit.getPrice().trim()) - (Integer.parseInt(credit.getPrice().trim()) * Integer.parseInt(credit.getOff().trim()) / 100)) + " IRR");
                                off_forth.setText(credit.getOff() + ".0% OFF");
                                if(credit.getOff().equals("0")){
                                    price_forth.setVisibility(View.GONE);
                                }
                                price_forth.setText(price);
                                twelve = Integer.parseInt(credit.getPrice().trim());
                                break;

                        }


                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(BuyAccountActivity.this, error.getMessage()+"", Toast.LENGTH_SHORT).show();
            }
        });

        requestQueue.add(jsonObjectRequest);
    }

    @Override
    public void onClick(View view) {

        int id = view.getId();

        switch (id){
            case R.id.one_month_CV:
                payTheBill(one);
                break;

            case R.id.three_month_CV:
                payTheBill(three);
                break;

            case R.id.six_month_CV:
                payTheBill(six);
                break;

            case R.id.one_year_CV:
                payTheBill(twelve);
                break;
        }

    }





    public void payTheBill(int amount){

        ZarinPal purchase = ZarinPal.getPurchase(this);
        PaymentRequest payment  = ZarinPal.getPaymentRequest();

        //PaymentRequest payment  = ZarinPal.getSandboxPaymentRequest();

        payment.setMerchantID("71c705f8-bd37-11e6-aa0c-000c295eb8fc");
        payment.setAmount(amount);

        //payment.isZarinGateEnable(true);
        payment.setDescription("In App Purchase Test SDK");
        payment.setCallbackURL("app://app");     /* Your App Scheme */
        payment.setMobile(sharedPreferences.getString("phoneNumber" , ""));            /* Optional Parameters */
        payment.setEmail(sharedPreferences.getString("email" , ""));     /* Optional Parameters */


        purchase.startPayment(payment, new OnCallbackRequestPaymentListener() {
            @Override
            public void onCallbackResultPaymentRequest(int status, String authority, Uri paymentGatewayUri, Intent intent) {


                if (status == 100) {
                   /*
                   When Status is 100 Open Zarinpal PG on Browser
                   */
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "Your Payment Failure :(", Toast.LENGTH_LONG).show();
                }

            }
        });

    }
}
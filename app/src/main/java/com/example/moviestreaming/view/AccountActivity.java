package com.example.moviestreaming.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.moviestreaming.R;

public class AccountActivity extends AppCompatActivity {

    TextView usernameTxt , emailTxt , phoneTxt, typeTxt, remainTxt , lastTxt , helpTxt;

    ImageView back_account;

    Button buyCreditBtn;

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        sharedPreferences = getApplicationContext().getSharedPreferences( "sharePrefMovieStreamingEntry", MODE_PRIVATE);

        back_account = findViewById(R.id.back_icon_account);
        back_account.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });



        usernameTxt = findViewById(R.id.account_username_txt);
        usernameTxt.setText(sharedPreferences.getString("username" , ""));
        emailTxt = findViewById(R.id.account_email_txt);
        emailTxt.setText(sharedPreferences.getString("email" , ""));
        phoneTxt = findViewById(R.id.account_phone_txt);
        phoneTxt.setText("09" + sharedPreferences.getString("phoneNumber" , ""));

        typeTxt = findViewById(R.id.account_type_txt);
        remainTxt = findViewById(R.id.account_remain_txt);

        buyCreditBtn = findViewById(R.id.buy_credit_btn);
        buyCreditBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(AccountActivity.this , BuyAccountActivity.class);
                startActivity(intent);

            }
        });

        if(sharedPreferences.getLong("total_credit" , 0L) == 0L){

            typeTxt.setText("Free");
            typeTxt.setTextColor(Color.RED);
            remainTxt.setText("0");
            remainTxt.setTextColor(Color.RED);
            buyCreditBtn.setVisibility(View.VISIBLE);

        }else{


            typeTxt.setText("VIP");
            typeTxt.setTextColor(getResources().getColor(R.color.colorAccent));

            Long credit = sharedPreferences.getLong("total_credit" , 0L );
            Long remain = credit - (System.currentTimeMillis()/1000);
            remain = remain/(60 * 60 * 24);

            if(remain <= 0){

                typeTxt.setText("FREE");
                typeTxt.setTextColor(Color.RED);
                remainTxt.setText("0");
                remainTxt.setTextColor(Color.RED);
                buyCreditBtn.setVisibility(View.VISIBLE);


            }else if(remain <= 5 && remain > 0){

                remainTxt.setText("+" + remain);
                remainTxt.setTextColor(Color.YELLOW);
                buyCreditBtn.setVisibility(View.VISIBLE);

            }else{

                remainTxt.setText("+" + remain);
                remainTxt.setTextColor(Color.GREEN);
            }




        }

        lastTxt = findViewById(R.id.account_last_charge_txt);
        helpTxt = findViewById(R.id.account_help_txt);


    }
}
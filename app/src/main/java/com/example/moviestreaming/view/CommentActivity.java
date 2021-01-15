package com.example.moviestreaming.view;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.moviestreaming.R;
import com.example.moviestreaming.global.GlobalCommentActivity;


public class CommentActivity extends AppCompatActivity {

    String username;
    String id_movie;

    RecyclerView recyclerView;

    EditText comment_EDT;

    ImageView send_img , back_icon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commnet);

        Bundle bundle = getIntent().getExtras();
        username = bundle.getString("username");
        id_movie = bundle.getString("id");


        recyclerView = findViewById(R.id.comment_RV);

        comment_EDT = findViewById(R.id.edt_comment);

        send_img = findViewById(R.id.send_comment_img);
        back_icon = findViewById(R.id.back_icon_comment);

        // back icon of the comment
        back_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }



        });

        //send the comment to the server
        send_img.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {

                if(comment_EDT.getText().toString().trim().isEmpty()){
                    Toast.makeText(CommentActivity.this, "no comment, please write your comment!!!", Toast.LENGTH_SHORT).show();

                    comment_EDT.requestFocus();

                }else{

                    String comment = comment_EDT.getText().toString().trim();
                    //send comment to server
                    sendCommentToServer(comment);
                    comment_EDT.setText("");

                    //this part is just for closing the android keyboard


                    InputMethodManager inputMethodManager = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);

                    inputMethodManager.hideSoftInputFromWindow(view.getApplicationWindowToken(),0);
                    System.out.println(comment);

                }
            }
        });

        setRecyclerViewComment(recyclerView);






    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void sendCommentToServer(String comment) {


        GlobalCommentActivity globalCommentActivity = new GlobalCommentActivity(this);
        globalCommentActivity.sendComment(comment , String.valueOf(id_movie), username , String.valueOf(java.time.LocalDate.now()));


    }

    private void setRecyclerViewComment(RecyclerView recyclerView) {

        GlobalCommentActivity globalCommentActivity = new GlobalCommentActivity(this , recyclerView);
        globalCommentActivity.setRecyclerViewComment(id_movie);

    }
}
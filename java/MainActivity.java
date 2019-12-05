package com.example.studyroom;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
    Button login_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        login_btn = findViewById(R.id.login);

        if (login_btn.getText().equals("Logout"))
            Toast.makeText(MainActivity.this, "로그인 되었습니다", Toast.LENGTH_LONG);
        try {
            login_btn.setText(bundle.getString("btn"));

        } catch (NullPointerException e) {
        }
    }

    public void onClickedLogin(View v) {
        login_btn = findViewById(R.id.login);
        if (login_btn.getText().equals("Login")) {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
        } else {
            final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle("로그아웃 하시겠습니까?");
            builder.setNegativeButton("아니오", null);
            builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    login_btn.setText("Login");
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        }
    }

    public void onClickedMove(View v) {
        login_btn = findViewById(R.id.login);
        if (login_btn.getText().equals("Login")) {
            final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle("로그인 후 이용해주세요.");
            builder.setPositiveButton("예", null);
            AlertDialog dialog = builder.create();
            dialog.show();
        } else {
            final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle("어느 창으로 이동하시겠습니까?");
            builder.setNegativeButton("분실물 게시판", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent(MainActivity.this, MainBoard.class);
                    startActivity(intent);
                }
            });
            builder.setPositiveButton("자리 예약", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent(MainActivity.this, SeatStatusActivity.class);
                    startActivity(intent);
                }

            });
            AlertDialog dialog = builder.create();
            dialog.show();
        }
    }

    public void onClickedMypage(View v) {
        if (login_btn.getText().equals("Login")) {
            final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
            builder.setTitle("로그인 후 이용해주세요.");
            builder.setPositiveButton("예", null);
            AlertDialog dialog = builder.create();
            dialog.show();
        } else {
            Intent intent = new Intent(MainActivity.this, MypageActivity.class);
            startActivity(intent);
        }
    }
}

//mainactivity에서 backbutton 클릭하면 바로 finish();
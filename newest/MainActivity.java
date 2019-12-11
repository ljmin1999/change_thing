package com.example.studyroom;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private static long back_pressed;

    Button login_btn;
    Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        bundle = intent.getExtras();

        login_btn = findViewById(R.id.login);

        try {
            login_btn.setText(bundle.getString("btn"));
        } catch (NullPointerException e) { }
    }


    @Override
    public void onBackPressed() {
        if (back_pressed + 2000 > System.currentTimeMillis()){
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
            System.exit(0);
        }
        else{
            Toast.makeText(getBaseContext(), "Press once again to exit", Toast.LENGTH_SHORT).show();
            back_pressed = System.currentTimeMillis();
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
                    Bundle bundle2 = new Bundle();
                    bundle2.putString("id", bundle.getString("id"));

                    Intent intent = new Intent(MainActivity.this, MainBoard.class);
                    intent.putExtras(bundle2);

                    startActivity(intent);
                }
            });
            builder.setPositiveButton("자리 예약", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Bundle bundle2 = new Bundle();
                    bundle2.putString("id", bundle.getString("id"));

                    Intent intent = new Intent(MainActivity.this, SeatStatusActivity.class);
                    intent.putExtras(bundle2);

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
            Bundle bundle2 = new Bundle();
            bundle2.putString("id", bundle.getString("id"));

            Intent intent = new Intent(MainActivity.this, MypageActivity.class);
            intent.putExtras(bundle2);

            startActivity(intent);
        }
    }
}
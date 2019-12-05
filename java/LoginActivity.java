package com.example.studyroom;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends AppCompatActivity {
    long Id;
    String psw;
    EditText num_et;
    EditText psw_et;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void onClickedLogin(View v) {
        num_et = (EditText) findViewById(R.id.num);
        psw_et = (EditText) findViewById(R.id.psw);

        List<EditText> ErrorFields = new ArrayList<EditText>();

        if (TextUtils.isEmpty(num_et.getText())) {
            num_et.setError("Check your number.");
            ErrorFields.add(num_et);
        }

        if (TextUtils.isEmpty(psw_et.getText())) {
            psw_et.setError("Check your password.");
            ErrorFields.add(psw_et);
        }

        for (int i = 0; i < ErrorFields.size(); i++) {
            if ((i == ErrorFields.size()-1))
                return;
        }


        if (checkLogin() == true) {
            Toast.makeText(LoginActivity.this, "로그인 되었습니다", Toast.LENGTH_LONG);
            String item = "Logout";
            Bundle bundle = new Bundle();
            bundle.putString("btn", item);

            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            intent.putExtras(bundle);

            startActivity(intent);
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
            builder.setTitle("회원정보 오류입니다.");
            builder.setMessage("학번 또는 비밀번호를 확인해주세요.");
            builder.setPositiveButton("예", null);

            AlertDialog dialog = builder.create();
            dialog.show();
        }
    }

    public void onClickedSignup(View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
        builder.setTitle("가입하시겠습니까?");
        builder.setMessage("컴퓨터정보통신공학과 학생만 이용이 가능합니다.");
        builder.setNegativeButton("아니오", null);
        builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
    public boolean checkLogin() {
        Id = Long.parseLong(num_et.getText().toString());
        psw = psw_et.getText().toString();
//수정 필요

        return true;
    }
}

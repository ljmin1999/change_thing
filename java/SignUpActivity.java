package com.example.studyroom;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SignUpActivity extends AppCompatActivity {
    private DatabaseReference mPostReference;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = database.getReference();

    String Id;
    String name;
    String psw;
    String email;

    EditText name_et;
    EditText num_et;
    EditText psw_et;
    EditText checkpsw_et;
    EditText email_et;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
    }

    public void onClickedRequest(View v) {

        name_et = (EditText) findViewById(R.id.name_et);
        num_et = (EditText) findViewById(R.id.num_et);
        psw_et = (EditText) findViewById(R.id.psw_et);
        checkpsw_et = (EditText) findViewById(R.id.checkpsw_et);
        email_et = (EditText) findViewById(R.id.email_et);

        EditText[] editTexts = {name_et, num_et, psw_et, checkpsw_et, email_et};
        List<EditText> ErrorFields = new ArrayList<EditText>();

        for (EditText edit : editTexts) {
            if (TextUtils.isEmpty(edit.getText())) {
                edit.setError("is required");
                ErrorFields.add(edit);
            }
        }

        if (!TextUtils.equals(psw_et.getText(), checkpsw_et.getText())) {
            checkpsw_et.setError("Please check your password.");
            ErrorFields.add(checkpsw_et);
        }


        if (!Patterns.EMAIL_ADDRESS.matcher(email_et.getText().toString().trim()).matches()) {
            email_et.setError("Invaild e-mail address.");
            ErrorFields.add(email_et);
        }

        for (int i = 0; i < ErrorFields.size(); i++) {
            if ((i == ErrorFields.size()-1))
                return;
        }

        Id = num_et.getText().toString();
        name = name_et.getText().toString();
        psw = psw_et.getText().toString();
        email = email_et.getText().toString();

        databaseReference.child("id_list").orderByChild("id").equalTo(Id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    List<IdPost> IdList = new ArrayList<IdPost>();
                    for (DataSnapshot datasnapshot : dataSnapshot.getChildren()) {
                        IdList.add(datasnapshot.getValue(IdPost.class));
                    }

                    if (!IdList.isEmpty()) {
                        num_et.setError("이미 가입한 학번입니다.");
                        return;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        joinDialog();
    }

    public void postIdDatabase(boolean add){
        mPostReference = FirebaseDatabase.getInstance().getReference();
        Map<String, Object> childUpdates = new HashMap<>();
        Map<String, Object> postValues = null;
        if(add){
            IdPost post = new IdPost(Id, name, psw, email);
            postValues = post.toMap();
        }
        childUpdates.put("/id_list/" + Id, postValues);
        mPostReference.updateChildren(childUpdates);
    }

    public void joinDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(SignUpActivity.this);
        builder.setTitle("가입을 요청하시겠습니까?");
        builder.setMessage("승인 완료시 이용 가능합니다.");
        builder.setNegativeButton("아니오", null);
        builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                postIdDatabase(true);
                String toast_message = "가입이 요청 되었습니다.";
                Toast toast = Toast.makeText(SignUpActivity.this, toast_message, Toast.LENGTH_LONG);
                toast.show();

                Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
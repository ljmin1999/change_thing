package com.example.studyroom;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
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

public class MypageActivity extends AppCompatActivity {
    private DatabaseReference mPostReference;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference databaseReference = database.getReference();

    Bundle bundle;

    String Id;
    String name;
    String psw;
    String email;

    EditText psw_et;
    EditText checkpsw_et;
    EditText email_et;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypage);

        roadData();
    }

    public void roadData() {
        final TextView name_tv = findViewById(R.id.mp_name);
        TextView num_tv = findViewById(R.id.mp_num);
        final EditText email_et = findViewById(R.id.mp_email);

        Intent intent = getIntent();
        bundle = intent.getExtras();

        Id = bundle.getString("id");

        num_tv.setText(Id);

        databaseReference.child("id_list").child(Id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    name = dataSnapshot.child("name").getValue().toString();
                    email = dataSnapshot.child("email").getValue().toString();
                    name_tv.setText(name);
                    email_et.setText(email);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void onClickedChange(View v) {
        psw_et = findViewById(R.id.mp_psw);
        checkpsw_et = findViewById(R.id.mp_checkpsw);
        email_et = findViewById(R.id.mp_email);

        EditText[] editTexts = {psw_et, checkpsw_et, email_et};
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

        email = email_et.getText().toString();
        psw = psw_et.getText().toString();

        AlertDialog.Builder builder = new AlertDialog.Builder(MypageActivity.this);
        builder.setTitle("정보를 수정하시겠습니까?");
        builder.setNegativeButton("아니오", null);
        builder.setPositiveButton("예", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                postIdDatabase(true);
                String toast_message = "수정이 완료 되었습니다. 다시 로그인 해주세요.";
                Toast toast = Toast.makeText(MypageActivity.this, toast_message, Toast.LENGTH_LONG);
                toast.show();

                Intent intent = new Intent(MypageActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
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
}

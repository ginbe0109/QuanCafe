package com.example.user.quancafe.activity.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.user.quancafe.R;

public class LogInActivity extends AppCompatActivity {
    private EditText editAccount,editPassWord;
    private Button btnLogIn, btnSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        AnhXa();
        ActionClick();
    }

    private void ActionClick() {
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),SignUpActivity.class);
                // giữ màn hình khi thoát ra
                intent.addFlags(intent.FLAG_ACTIVITY_NEW_TASK | intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        });
    }

    private void AnhXa() {
        editAccount = (EditText) findViewById(R.id.editLogIn_Account);
        editPassWord = (EditText) findViewById(R.id.editLogIn_Password);
        btnLogIn = (Button) findViewById(R.id.btnLogIn_LogIn);
        btnSignUp = (Button) findViewById(R.id.btnLogIn_SignUp);
    }
}

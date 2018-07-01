package com.example.user.quancafe.activity.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.user.quancafe.R;

public class SignUpActivity extends AppCompatActivity {
    private EditText editName, editAccount, editPass, editPassAgain;
    private Button btnSignUp;
    private TextView textLogIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        AnhXa();
        ActionClick();
    }

    private void ActionClick() {
        textLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LogInActivity.class);
                // giữ màn hình khi thoát ra
                intent.addFlags(intent.FLAG_ACTIVITY_NEW_TASK | intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();
            }
        });
    }

    private void AnhXa() {
        editName = (EditText) findViewById(R.id.editSignUp_Name);
        editAccount = (EditText) findViewById(R.id.editSignUp_Account);
        editPass = (EditText) findViewById(R.id.editSignUp_Password);
        editPassAgain = (EditText) findViewById(R.id.editSignUp_PasswordAgain);
        btnSignUp = (Button) findViewById(R.id.btnSignUp_SignUp);
        textLogIn = (TextView) findViewById(R.id.textSignUp_LogIn);
    }
}

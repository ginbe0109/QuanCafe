package com.example.user.quancafe.activity.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.user.quancafe.R;
import com.example.user.quancafe.activity.ultil.CheckConnect;
import com.example.user.quancafe.activity.ultil.Server;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class SignUpActivity extends AppCompatActivity {
    private EditText editName, editAccount, editPass, editPassAgain;
    private Button btnSignUp;
    private TextView textLogIn;
    private ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        AnhXa();
        ActionClick();
    }
    // ProgressBar
    private void ActionProgressbar(String title, String message) {
        progressDialog = new ProgressDialog(SignUpActivity.this);
        progressDialog.setTitle(title);
        progressDialog.setMessage(message);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
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
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editName.getText().toString().trim();
                String account = editAccount.getText().toString().trim();
                String pass = editPass.getText().toString().trim();
                String passAgain = editPassAgain.getText().toString().trim();

                if(!TextUtils.isEmpty(name) && !TextUtils.isEmpty(account) && !TextUtils.isEmpty(pass) && !TextUtils.isEmpty(passAgain)){
                    if(pass.equals(passAgain)){
                        ActionProgressbar("Đăng ký tài khoản","Vui lòng chờ chờ giây lát để hoàn thành đăng ký");
                        ActionRegistation(name,account,pass);
                    }else{
                        CheckConnect.ShowToast(getApplicationContext(),"Mật khẩu đăng ký không trùng khớp!!");
                    }
                }else{
                    CheckConnect.ShowToast(getApplicationContext(),"Vui lòng điền đầy đủ thông tin!");
                }

            }
        });
    }

    private void ActionRegistation(final String name, final String account, final String pass) {

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest StringRequest = new StringRequest(Request.Method.POST, Server.urlRegistation, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                String message = "";
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    // Nếu thành công
                    if(jsonObject.getInt("success") == 1){
                        message = jsonObject.getString("message");
                        CheckConnect.ShowToast(getApplicationContext(),message);
                        Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();


                    }else{
                        message = jsonObject.getString("message");
                        CheckConnect.ShowToast(getApplicationContext(),message);

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                progressDialog.dismiss();

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
              //  CheckConnect(getApplicationContext(),error.getMessage());
                progressDialog.hide();

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String,String>();
                params.put("TEN",name);
                params.put("EMAIL",account);
                params.put("MATKHAU",pass);
                return params;
            }
        };
        //CheckConnect.ShowToast(getApplicationContext(),name + " "+" "+account+" "+pass);

        requestQueue.add(StringRequest);

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

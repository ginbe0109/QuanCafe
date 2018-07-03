package com.example.user.quancafe.activity.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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

public class LogInActivity extends AppCompatActivity {
    private EditText editAccount,editPassWord;
    private Button btnLogIn, btnSignUp;
    private ProgressDialog progressDialog;


    private static final String PREFS_NAME = "preferences";
    private static final String PREF_UNAME = "Username";
    private static final String PREF_PASSWORD = "Password";
    //private static final String PREF_ISLOGIN = "Login";

    private final String DefaultUnameValue = "";
    private String UnameValue;
    private final String DefaultPasswordValue = "";
    private String PasswordValue;


    //private final boolean DefaultLogin = false;
    public static boolean isLogin = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        AnhXa();
        ActionClick();
    }
    @Override
    protected void onPause() {
        super.onPause();
        saveAccount();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadPreferences();
    }

    private void loadPreferences() {
        SharedPreferences settings = getSharedPreferences(PREFS_NAME,
                Context.MODE_PRIVATE);

        // Get value
        UnameValue = settings.getString(PREF_UNAME, DefaultUnameValue);
        PasswordValue = settings.getString(PREF_PASSWORD, DefaultPasswordValue);
        editAccount.setText(UnameValue);
        editPassWord.setText(PasswordValue);

        // Lấy trạng thại đã lưu
        boolean wasLogin = settings.getBoolean("wasLogin",false);
        if (wasLogin == true){
            sendToMain();
        }

        //sendToMain();
    }

    private void sendToMain() {
        Intent intent = new Intent(LogInActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void saveAccount() {
        SharedPreferences settings = getSharedPreferences(PREFS_NAME,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        boolean wasLogin = isLogin;
        // Edit and commit
        UnameValue = editAccount.getText().toString().trim();
        PasswordValue = editPassWord.getText().toString().trim();
        editor.putString(PREF_UNAME, UnameValue);
        editor.putString(PREF_PASSWORD, PasswordValue);
        // lưu lại trạng thái đã đăng nhập
        editor.putBoolean("wasLogin",wasLogin);
        editor.commit();

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
        btnLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String account = editAccount.getText().toString().trim();
                String pass = editPassWord.getText().toString().trim();
                if (!TextUtils.isEmpty(account) && !TextUtils.isEmpty(pass)){
                    ActionProgressbar("Đăng ký tài khoản","Vui lòng chờ chờ giây lát để hoàn thành đăng ký");
                    ActionLogIn(account,pass);
                }else{
                    CheckConnect.ShowToast(getApplicationContext(),"Vui lòng nhập đầy đủ thông tin");
                }
            }
        });
    }
    // ProgressBar
    private void ActionProgressbar(String title, String message) {
        progressDialog = new ProgressDialog(LogInActivity.this);
        progressDialog.setTitle(title);
        progressDialog.setMessage(message);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
    }

    private void ActionLogIn(final String account, final String pass) {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.urlLogIn, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                String message = "";
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    // nếu thành công
                    if(jsonObject.getInt("success") == 1){
                        isLogin = true;
                        message = jsonObject.getString("message");
                        CheckConnect.ShowToast(getApplicationContext(),message);
                        Intent intent = new Intent(LogInActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }else{
                        isLogin = false;
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
                isLogin = false;
                progressDialog.hide();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<String,String>();
                params.put("EMAIL",account);
                params.put("MATKHAU",pass);
                return params;
            }
        };

        requestQueue.add(stringRequest);
    }

    private void AnhXa() {
        editAccount = (EditText) findViewById(R.id.editLogIn_Account);
        editPassWord = (EditText) findViewById(R.id.editLogIn_Password);
        btnLogIn = (Button) findViewById(R.id.btnLogIn_LogIn);
        btnSignUp = (Button) findViewById(R.id.btnLogIn_SignUp);
    }
}

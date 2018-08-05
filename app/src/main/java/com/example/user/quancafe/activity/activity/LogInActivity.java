package com.example.user.quancafe.activity.activity;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

import static com.example.user.quancafe.activity.ultil.CheckConnect.ShowToast;

public class LogInActivity extends AppCompatActivity {
    private EditText editAccount,editPassWord;
    private Button btnLogIn, btnSignUp;
    private ProgressDialog progressDialog;


    public static final String PREFS_NAME = "preferences";
    private static final String PREF_UNAME = "Username";
    private static final String PREF_PASSWORD = "Password";
    //private static final String PREF_ISLOGIN = "Login";

    private final String DefaultUnameValue = "";
    private String UnameValue;
    private final String DefaultPasswordValue = "";
    private String PasswordValue;


    //private final boolean DefaultLogin = false;
    public static boolean isLogin = false;
    public static int mand = 0;

    TextView textViewConnectWifi;
    //Barcode Scanning
    private ZXingScannerView mScannerView;
    private static final int REQUSET_CODE_CAMERA = 123;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        AnhXa();
        ActionClick();
        ActionBarCode();

    }
    public boolean ConnectToNetworkWPA( String networkSSID, String password )
    {
        try {
            WifiConfiguration conf = new WifiConfiguration();
            conf.SSID = "\"" + networkSSID + "\"";   // Please note the quotes. String should contain SSID in quotes

            conf.preSharedKey = "\"" + password + "\"";

            conf.status = WifiConfiguration.Status.ENABLED;
            conf.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.TKIP);
            conf.allowedGroupCiphers.set(WifiConfiguration.GroupCipher.CCMP);
            conf.allowedKeyManagement.set(WifiConfiguration.KeyMgmt.WPA_PSK);
            conf.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.TKIP);
            conf.allowedPairwiseCiphers.set(WifiConfiguration.PairwiseCipher.CCMP);

            Log.d("connecting", conf.SSID + " " + conf.preSharedKey);

            WifiManager wifiManager = (WifiManager) this.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
            wifiManager.addNetwork(conf);

            Log.d("after connecting", conf.SSID + " " + conf.preSharedKey);

            List<WifiConfiguration> list = wifiManager.getConfiguredNetworks();
            for( WifiConfiguration i : list ) {
                if(i.SSID != null && i.SSID.equals("\"" + networkSSID + "\"")) {
                    wifiManager.disconnect();
                    wifiManager.enableNetwork(i.networkId, true);
                    wifiManager.reconnect();
                    Log.d("re connecting", i.SSID + " " + conf.preSharedKey);

                    break;
                }
            }


            //WiFi Connection success, return true
            return true;
        } catch (Exception ex) {
            System.out.println(Arrays.toString(ex.getStackTrace()));
            return false;
        }
    }

    private void ActionBarCode() {
        textViewConnectWifi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityCompat.requestPermissions(LogInActivity.this,
                        new String[]{Manifest.permission.CAMERA,Manifest.permission.ACCESS_WIFI_STATE,
                                Manifest.permission.CHANGE_WIFI_STATE,Manifest.permission.ACCESS_NETWORK_STATE},
                        REQUSET_CODE_CAMERA
                );
                //ConnectToNetworkWPA("Phuoc","123456789");
            }
        });

    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == REQUSET_CODE_CAMERA && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            checkBarcode(mScannerView);
        }else{
            Toast.makeText(this, "Bạn vui lòng mở camera", Toast.LENGTH_SHORT).show();
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
    // This is your click listener
    public void checkBarcode(View v) {
        try {
            IntentIntegrator integrator = new IntentIntegrator(LogInActivity.this);
//            integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
            integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
            integrator.setPrompt("Scan a barcode");
            integrator.setCameraId(0);  // Use a specific camera of the device
            integrator.setBeepEnabled(false);
            integrator.initiateScan();
            //start the scanning activity from the com.google.zxing.client.android.SCAN intent
            // Programmatically initialize the scanner view
            // setContentView(mScannerView);
        } catch (ActivityNotFoundException anfe) {
            //on catch, show the download dialog
            showDialog(LogInActivity.this, "No Scanner Found", "Download a scanner code activity?", "Yes", "No").show();
        }
    }
    //alert dialog for downloadDialog
    private static AlertDialog showDialog(final Activity act, CharSequence title, CharSequence message, CharSequence buttonYes, CharSequence buttonNo) {
        AlertDialog.Builder downloadDialog = new AlertDialog.Builder(act);
        downloadDialog.setTitle(title);
        downloadDialog.setMessage(message);
        downloadDialog.setPositiveButton(buttonYes, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                Uri uri = Uri.parse("market://search?q=pname:" + "com.google.zxing.client.android");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                try {
                    act.startActivity(intent);
                } catch (ActivityNotFoundException anfe) {

                }
            }
        });
        downloadDialog.setNegativeButton(buttonNo, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        return downloadDialog.show();
    }

    //on ActivityResult method
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                Log.d("MainActivity", "Cancelled scan");
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
            } else {
                ConnectWiFI(result.getContents());
                //ConnectToNetworkWPA("Phone","123456789");
                Log.d("MainActivity", "Scanned");
                //Toast.makeText(this, "Scanned: " + result.getContents(), Toast.LENGTH_LONG).show();
            }
        } else {
            Log.d("MainActivity", "Weird");
            // This is important, otherwise the result will not be passed to the fragment
            super.onActivityResult(requestCode, resultCode, data);
        }
    }


    private void ConnectWiFI(String contents) {
//        Log.d("WIFI","type "+getTypeWifi(contents)+" ssid "+getSSIDWIFI(contents)
//                +" pass "+ getPassWifi(contents));
//        CheckConnect.ShowToast(getApplicationContext(),
//                "type "+getTypeWifi(contents)+" ssid "+getSSIDWIFI(contents)
//                        +" pass "+ getPassWifi(contents));

        ConnectivityManager connectionManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifiCheck = connectionManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

        if (wifiCheck.isAvailable()) {
            // Do whatever here
            CheckConnect.ShowToast(getApplicationContext(),"Thiết bị của bạn đã tồn tại wifi");
        }else{
            ConnectToNetworkWPA(getSSIDWIFI(contents),getPassWifi(contents));

        }



    }

    private String getTypeWifi(String wifi){
        String type ="";
        String typeWifi = "";
        int i = wifi.indexOf("T");
        int y = wifi.indexOf(";");
        if(i>0){
            type = wifi.substring(i,y).trim();
            int k = type.indexOf(":");
            typeWifi = type.substring(k+1,type.length()).trim();

        }
        return typeWifi;
    }

    private String getSSIDWIFI(String wifi){
        String ssid = "";
        int i = wifi.indexOf("S");
        if(i>0){
            String t1 ="";
            t1 = wifi.substring(i).trim();
            int j = t1.indexOf(";");
            String t2 = t1.substring(0,j).trim();
            int k = t2.indexOf(":");
            ssid = t2.substring(k+1).trim();
        }
        return ssid;
    }
    private String getPassWifi(String wifi){
        String pass = "";
        int i = wifi.indexOf("S");
        if(i>0){
            String t1 ="";
            t1 = wifi.substring(i).trim();
            int j = t1.indexOf(";");
            String t2 = t1.substring(j+3).trim();
            int k = t2.indexOf(";");
            pass = t2.substring(0,k).trim();
        }

        return pass;
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
        // Lấy trạng thại đã lưu
        LogInActivity.mand = settings.getInt("mand",0);
        if (wasLogin == true){
            sendToMain();
        }

        //sendToMain();
    }

    private void sendToMain() {

             /* Create an intent that will start the main activity. */
        Intent intent = new Intent(LogInActivity.this, MainActivity.class);
        startActivity(intent);
        //overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
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
        editor.putInt("mand",mand);
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
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            finish();


            }
        });
        btnLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String account = editAccount.getText().toString().trim();
                String pass = editPassWord.getText().toString().trim();
                if (!TextUtils.isEmpty(account) && !TextUtils.isEmpty(pass)){
                    ActionProgressbar("Đăng nhập tài khoản","Vui lòng chờ chờ giây lát để hoàn thành đăng nhập");
                    ActionLogIn(account,pass);
                }else{
                    ShowToast(getApplicationContext(),"Vui lòng nhập đầy đủ thông tin");
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
                        mand = jsonObject.getInt("mand");
                        ShowToast(getApplicationContext(),message);
                        Intent intent = new Intent(LogInActivity.this, MainActivity.class);
                        intent.addFlags(intent.FLAG_ACTIVITY_NEW_TASK | intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(intent);
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                        finish();
                    }else{
                        isLogin = false;
                        message = jsonObject.getString("message");
                        ShowToast(getApplicationContext(),message);

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
        textViewConnectWifi = (TextView) findViewById(R.id.connectwifi);
        mScannerView = new ZXingScannerView(this);
    }
}

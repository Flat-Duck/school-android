package ly.smarthive.school.activity;

import androidx.appcompat.app.AppCompatActivity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Objects;
import java.util.Map;

import ly.smarthive.school.AppController;
import ly.smarthive.school.COMMON;
import ly.smarthive.school.R;
import ly.smarthive.school.SessionManager;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = LoginActivity.class.getSimpleName();
    SessionManager session;
    TextInputEditText inputPassword, inputEmail;
    private ProgressDialog pDialog;
    Button btnSignIn, btnCancel;
    String  email, password;
    boolean status = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        session = new SessionManager(this);

        btnCancel = findViewById(R.id.btnCancel);
        btnSignIn = findViewById(R.id.btnSignIn);
        btnCancel.setOnClickListener(v -> finishAffinity());
        btnSignIn.setOnClickListener(v -> showSignInDialog());
        pDialog = new ProgressDialog(this);
        pDialog.setCancelable(false);
        checkLogin();
    }

    private void showSignInDialog() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final View picker_layout = LayoutInflater.from(this).inflate(R.layout.layout_login, null);
        builder.setView(picker_layout);
        builder.setTitle(R.string.sign_in_title);
        builder.setMessage(R.string.please_use_email);
        inputPassword = picker_layout.findViewById(R.id.passwrod);
        inputEmail = picker_layout.findViewById(R.id.email);
        builder.setPositiveButton(R.string.ok, (dialog, which) -> {
            if (checkNotNULL()) {
                password = Objects.requireNonNull(inputPassword.getText()).toString().trim();
                email = Objects.requireNonNull(inputEmail.getText()).toString().trim();
                Log.d(TAG, "P:" + email + "//W:" + password);
                login();
            } else {
             //   Toast.makeText(getApplicationContext(), R.string.please_enter_credentials, Toast.LENGTH_LONG).show();
            }
        });
        builder.setNegativeButton(R.string.cancel, (dialog, which) -> dialog.cancel());
        builder.show();
    }
    private void login() {
        if (checkNotEmpty()) {
            sendLogin();
        } else {
          //  Toast.makeText(getApplicationContext(), R.string.please_enter_credentials, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onBackPressed() {
        finishAffinity();
    }

    @Override
    public boolean onSupportNavigateUp() {
        //onBackPressed();
        finish();
        return true;
    }
    /**
     * function to verify login details in mysql db
     */
    private void sendLogin() {
        // Tag used to cancel the request
        String tag_string_req = "req_login";
        pDialog.setMessage(getString(R.string.logging_in));
        showDialog();
        StringRequest jsonObjectRequest = new StringRequest(Request.Method.POST, COMMON.LOGIN_URL, response -> {
            Log.d(TAG, "Login Response: " + response);
            hideDialog();
            try {
                JSONObject jObj = new JSONObject(response);
                boolean success = jObj.getBoolean("success");
                if (success) {
                    session.setLogin(true);
                    JSONObject data = jObj.getJSONObject("data");
                    String token = data.getString("accessToken");
                    session.setToken(token);
                    COMMON.CURRENT_USER_PASSWORD = password;
                    COMMON.CURRENT_USER_EMAIL = email;
                    status = true;
                    session.setEmailPassword(email, password, status);
                    startActivity(new Intent(LoginActivity.this, StudentsActivity.class));
                    finish();
                } else {
                    String errorMsg = jObj.getString("message");
                    Log.e(TAG,errorMsg);
                    //Toast.makeText(getApplicationContext(), errorMsg, Toast.LENGTH_LONG).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
                //Toast.makeText(getApplicationContext(), "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                Log.e(TAG,e.getMessage());
            }
        }, error -> {
            Log.e(TAG, "Login Error: " + error.getMessage());
            //Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
            hideDialog();
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("Accept", "application/json");
                params.put("email", email);
                params.put("password", password);
                return params;
            }
        };
        AppController.getInstance().addToRequestQueue(jsonObjectRequest, tag_string_req);
    }



    private void checkLogin() {

        if (session.isLoggedIn() ) {
            Intent intent = new Intent(LoginActivity.this, StudentsActivity.class);
            startActivity(intent);
            finish();
        }
    }

    private boolean checkNotEmpty() {
        return (!password.isEmpty() || !email.isEmpty());
    }

    private boolean checkNotNULL() {
        return (!inputPassword.toString().isEmpty() || !inputEmail.toString().isEmpty());
    }


    private void showDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hideDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }

    public void onStart() {
        super.onStart();
        checkLogin();
    }
}
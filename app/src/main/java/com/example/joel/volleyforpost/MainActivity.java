package com.example.joel.volleyforpost;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private EditText firstName;
    private EditText lastName;
    private EditText email;

    private Button signUp;
    private ProgressDialog mProgress;

    private RequestQueue mRequestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mProgress = new ProgressDialog(this);

        firstName = (EditText)findViewById(R.id.firstField);
        lastName = (EditText)findViewById(R.id.lastField);
        email = (EditText)findViewById(R.id.email);

        signUp = (Button)findViewById(R.id.signUpbtn);
        mRequestQueue = Volley.newRequestQueue(this);


        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mProgress.setMessage("Signing Up ...");
                mProgress.show();

                String url ="https://salty-garden-58363.herokuapp.com/v1/users/login";
                StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                Toast.makeText(MainActivity.this, "Good", Toast.LENGTH_SHORT).show();
                                mProgress.dismiss();


                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MainActivity.this, "Error while Posting Data", Toast.LENGTH_SHORT).show();
                        mProgress.dismiss();
                    }
                }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String,String> params = new HashMap<>();
                        params.put("first",firstName.getText().toString());
                        params.put("last",lastName.getText().toString());
                        params.put("email",email.getText().toString());

                        return params;
                    }

                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        HashMap<String, String> headers = new HashMap<>();
                        headers.put("Content-Type", "application/json");
                        return headers;
                    }
                };

                mRequestQueue.add(stringRequest);

            }
        });

    }
    
}


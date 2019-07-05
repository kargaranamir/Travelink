package m.derakhshan.travelinkapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.github.florent37.materialtextfield.MaterialTextField;
import com.valdesekamdem.library.mdtoast.MDToast;

import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {
    String User_phoneNUmber;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        final String API="http://10.0.2.2:1104/signup";
        final MaterialTextField first_field=findViewById(R.id.first_field);
        final MaterialTextField last_field=findViewById(R.id.last_field);
        final Button Register=findViewById(R.id.register);
        final EditText name=findViewById(R.id.first_name);
        final EditText family=findViewById(R.id.last_name);
        first_field.expand();
        last_field.reduce();
        User_phoneNUmber = getIntent().getStringExtra("phone");
        first_field.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                first_field.expand();
                last_field.reduce();
            }
        });
        last_field.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                first_field.reduce();
                last_field.expand();
            }
        });
        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                StringRequest request = new StringRequest(Request.Method.POST, API, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("Log_response",response);
                        SharedPreferences sh = getSharedPreferences("Information", MODE_PRIVATE);
                        SharedPreferences.Editor editor = sh.edit();
                        editor.putString("token",response);
                        editor.putBoolean("IsLoggedIn",true);
                        editor.apply();

                        Intent intent=new Intent(Register.this,MainActivity.class);
                        startActivity(intent);
                        Register.this.finish();
                        MDToast mdToast=MDToast.makeText(Register.this,"Successfully Registered! Welcome...", Toast.LENGTH_LONG,MDToast.TYPE_SUCCESS);
                        mdToast.show();
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("Log_error",error.toString());

                    }
                }){
                    Map<String,String> params=new HashMap<>();

                    @Override
                    public Map<String, String> getParams() {
                        params.put("phone",User_phoneNUmber);
                        params.put("name",name.getText().toString());
                        params.put("family",family.getText().toString());
                        return params;
                    }
                };
                RequestQueue queue= Volley.newRequestQueue(Register.this);
                queue.add(request);

            }
        });
    }
}

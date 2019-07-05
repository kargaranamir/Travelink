package m.derakhshan.travelinkapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.app.progresviews.ProgressWheel;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.github.florent37.materialtextfield.MaterialTextField;
import com.valdesekamdem.library.mdtoast.MDToast;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;

public class Signup extends AppCompatActivity {

    String user_phone_number;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acitvity_signup);


        SharedPreferences sh = getSharedPreferences("Information", MODE_PRIVATE);
        boolean IsLoggedIN = sh.getBoolean("IsLoggedIn", false);
        if (IsLoggedIN){
            Intent intent=new Intent(Signup.this,MainActivity.class);
            startActivity(intent);
            Signup.this.finish();
        }

        //----------Initializing values
        final TextView alert = findViewById(R.id.alert);
        final ProgressWheel wheel=findViewById(R.id.progress);
        final EditText phone=findViewById(R.id.phonenumber);
        final MaterialTextField field=findViewById(R.id.field);
        final String SMS_API="http://fekretalkhooncheh.ir/SMS.php";
        phone.setHint("Enter Phone Number...");
        field.toggle();


        final Button letSgo=findViewById(R.id.letsgobutton);
        letSgo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(phone.getText().length()==11) {
                    SendCode(SMS_API,alert,field,phone,wheel,letSgo);
                    user_phone_number=phone.getText().toString();

                }
                else{
                    MDToast mdToast=MDToast.makeText(Signup.this,
                            "Your phone number is incorrect...", Toast.LENGTH_SHORT,
                            MDToast.TYPE_ERROR);
                    mdToast.show();
                    YoYo.with(Techniques.Shake)
                            .repeat(1)
                            .playOn(findViewById(R.id.field));
                }


            }
        });

    }





    public void SendCode (final String API, final TextView alert, final MaterialTextField field,
                          final EditText phone, final ProgressWheel wheel, final Button button){

        alert.setTextColor(getResources().getColor(R.color.alertTextColor));
        alert.setText("Please Wait...");
        field.reduce();
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                phone.setHint("Enter 6 digit code");

            }
        });
        alert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        StringRequest request = new StringRequest(Request.Method.POST, API,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(final String response) {
                        Log.i("Log_response",response);
                        wheel.setPercentage(360);
                        field.expand();
                        phone.setText("");
                        new CountDownTimer(120000, 1000) {
                            @Override
                            public void onTick(long millisUntilFinished) {
                                wheel.setPercentage((int) (3 * millisUntilFinished/1000));

                            }
                            @Override
                            public void onFinish() {
                                alert.setText("Didn't get the code? click here!");
                                alert.setTextColor(getResources().getColor(R.color.colorAccent));
                                field.reduce();
                                alert.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        SendCode(API,alert,field,phone,wheel,button);
                                    }
                                });
                            }
                        }.start();
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                alert.setTextColor(getResources().getColor(R.color.alertTextColor));
                                alert.setText("We've Sent 'Activation Code' ");
                                button.setText("Check Code");
                            }
                        });
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (phone.getText().toString().equals(response)){
                                    StringRequest request1=new StringRequest(Request.Method.POST,
                                            "http://10.0.2.2:1104/login", new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response) {
                                            if(response.equals("NOUSER")){
                                                Intent intent=new Intent(Signup.this,Register.class);
                                                intent.putExtra("phone",user_phone_number);
                                                startActivity(intent);
                                                Signup.this.finish();

                                            }
                                            else {
                                                Log.i("Log",response);
                                                SharedPreferences sh = getSharedPreferences("Information", MODE_PRIVATE);
                                                SharedPreferences.Editor editor = sh.edit();
                                                editor.putString("token",response);
                                                editor.putBoolean("IsLoggedIn",true);
                                                editor.apply();
                                                Intent intent=new Intent(Signup.this,MainActivity.class);
                                                startActivity(intent);
                                                Signup.this.finish();
                                            }

                                        }
                                    }, new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError error) {
                                            Log.i("Log_Error",error.toString());

                                        }
                                    }){
                                        Map<String,String>params=new HashMap<>();

                                        @Override
                                        public Map<String, String> getParams() {
                                            params.put("phone",user_phone_number);
                                            return params;
                                        }
                                    };
                                    RequestQueue queue=Volley.newRequestQueue(Signup.this);
                                    queue.add(request1);


                                }
                                else {
                                    MDToast mdToast=MDToast.makeText(Signup.this,
                                            "The code is incorrect!",Toast.LENGTH_SHORT
                                            ,MDToast.TYPE_ERROR);
                                    mdToast.show();
                                }
                            }
                        });




                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(final VolleyError error) {
                Log.i("Log_error", error.toString());
                alert.setText("Something Went Wrong Try again....");
                alert.setTextColor(getResources().getColor(R.color.colorAccent));

            }
        }) {
            Map<String, String> params = new HashMap<>();

            @Override
            public Map<String, String> getParams() {

                params.put("phone", phone.getText().toString());
                return params;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(Signup.this);
        queue.add(request);


    }
}

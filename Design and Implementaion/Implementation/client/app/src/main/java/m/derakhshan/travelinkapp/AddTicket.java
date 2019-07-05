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

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.valdesekamdem.library.mdtoast.MDToast;

import java.util.HashMap;
import java.util.Map;

public class AddTicket extends AppCompatActivity {

    private  String token;
    private  EditText title , question;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_ticket);

        SharedPreferences sh=getSharedPreferences("Information",MODE_PRIVATE);
        token = sh.getString("token","null");

        title = findViewById(R.id.title);
        question=findViewById(R.id.question);

        final Button send=findViewById(R.id.send);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if( title.getText().length()>3 && question.getText().length()>3)
                Send("http://10.0.2.2:1104/sendticket");
                else
                {
                    MDToast mdToast=MDToast.makeText(AddTicket.this,"Title or Question field is Empty! ",
                            Toast.LENGTH_LONG,MDToast.TYPE_ERROR);
                mdToast.show();
                }

            }
        });

    }


    private void Send(String API){

        StringRequest request=new StringRequest(1, API, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                MDToast mdToast=MDToast.makeText(AddTicket.this,"Your message has been send successfully... ",
                        Toast.LENGTH_LONG,MDToast.TYPE_SUCCESS);
                mdToast.show();

                Intent intent=new Intent(AddTicket.this,HelpCenter.class);
                startActivity(intent);
                AddTicket.this.finish();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("Log",error.toString());
                MDToast mdToast=MDToast.makeText(AddTicket.this,"Something went wrong,try again...",
                        Toast.LENGTH_LONG,MDToast.TYPE_ERROR);
                mdToast.show();

            }
        }){
            Map<String,String> params=new HashMap<>();

            @Override
            public Map<String, String> getParams() {
                params.put("token",token);
                params.put("title",title.getText().toString());
                params.put("question",question.getText().toString());
                return params;
            }
        };

        RequestQueue queue= Volley.newRequestQueue(AddTicket.this);
        queue.add(request);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(AddTicket.this,HelpCenter.class);
        startActivity(intent);
        this.finish();
    }

}

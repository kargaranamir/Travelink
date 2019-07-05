package m.derakhshan.travelinkapp;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;





import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import m.derakhshan.travelinkapp.RecyclerVIew.Ticket_Adapter;
import m.derakhshan.travelinkapp.RecyclerVIew.Ticket_Model;


public class Tab_NewTickets extends Fragment {

    private String token;
    private ProgressBar progressBar;
    private TextView waite;

    private List<Ticket_Model> ticket_list = new ArrayList<>();
    TextView information;
    RecyclerView recyclerView;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.tab_newticket, container, false);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
         progressBar=getView().findViewById(R.id.progressBar);
         waite=getView().findViewById(R.id.waite);

        final FloatingActionButton add_ticket=getView().findViewById(R.id.floatingActionButton);
        add_ticket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getActivity(),AddTicket.class);
                startActivity(intent);
                getActivity().finish();
            }
        });
        SharedPreferences sh= Objects.requireNonNull(getActivity()).getSharedPreferences("Information", Context.MODE_PRIVATE);
        token = sh.getString("token","null");

        information = Objects.requireNonNull(getView()).findViewById(R.id.textView);
        recyclerView = Objects.requireNonNull(getView()).findViewById(R.id.ticketsRecycle);
        Server();

    }



    private void Server(){

        StringRequest request=new StringRequest(Request.Method.POST, "http://10.0.2.2:1104/opentickets", new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressBar.setVisibility(View.INVISIBLE);
                waite.setText("");
                if (response.length()>10){
                    GsonBuilder builder = new GsonBuilder();
                    Gson mGson = builder.create();
                    ticket_list = Arrays.asList(mGson.fromJson(response, Ticket_Model[].class));
                    Ticket_Adapter adapter = new Ticket_Adapter(ticket_list);
                    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext());
                    recyclerView.setLayoutManager(mLayoutManager);
                    recyclerView.setItemAnimator(new DefaultItemAnimator());
                    recyclerView.setAdapter(adapter);
                    information.setText("");

                }



            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("Log_error",error.toString());

            }
        }){
            Map<String,String> params = new HashMap<>();
            @Override
            public Map<String, String> getParams() {
                params.put("token",token);
                return params;
            }
        };

        RequestQueue queue= Volley.newRequestQueue(Objects.requireNonNull(getContext()));
        queue.add(request);
    }

}
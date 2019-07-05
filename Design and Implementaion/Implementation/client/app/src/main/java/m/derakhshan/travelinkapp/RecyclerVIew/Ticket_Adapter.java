package m.derakhshan.travelinkapp.RecyclerVIew;



import android.graphics.Color;
import android.support.annotation.ColorRes;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.List;

import m.derakhshan.travelinkapp.R;


public class Ticket_Adapter extends RecyclerView.Adapter<Ticket_Adapter.MyViewHolder> {


    public Ticket_Adapter(List<Ticket_Model> tickets) {
        this.tickets = tickets;
    }

    public List<Ticket_Model> tickets;
    class MyViewHolder extends RecyclerView.ViewHolder {
         TextView title, question, status , answer;

         public MyViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.title);
            answer = view.findViewById(R.id.text_answer);
            question = view.findViewById(R.id.text_question);
            status=view.findViewById(R.id.status);

        }
    }



    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.model_tickets, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Ticket_Model tickets = this.tickets.get(position);
        holder.title.setText(tickets.getTitle());
        holder.answer.setText(tickets.getAnswer());
        holder.question.setText(tickets.getQuestion());
        holder.status.setText(tickets.getStatus());
    }

    @Override
    public int getItemCount() {
        return tickets.size();
    }
}
package bgv.fit.bstu.eday;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import bgv.fit.bstu.eday.Models.Task;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.TaskViewHolder >{
    ArrayList<Task> tasks;
    public String number_audit="";
    private static MainActivity context;

    interface OnStateClickListener{
        void onStateClick(Task tasks, int position);
    }

    public TaskAdapter( ArrayList<Task> tasks){
        this.tasks=tasks;
    }

    public class TaskViewHolder extends RecyclerView.ViewHolder{
        TextView name, description, datentime;
        public TaskViewHolder(View view) {
            super(view);
            name=view.findViewById(R.id.tnamet);
            description=view.findViewById(R.id.tdescr);
            datentime=view.findViewById(R.id.tdatentime);
        }
    }

    @NonNull
    @Override
    public TaskAdapter.TaskViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item,parent,false);
        return new TaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskAdapter.TaskViewHolder  holder, final int position) {
        holder.name.setText(tasks.get(position).getName());
        holder.description.setText(tasks.get(position).getDescription());
        holder.datentime.setText(tasks.get(position).getDate()+" "+tasks.get(position).getTime());
        Log.d("name",number_audit);
    }

    @Override
    public int getItemCount() {
        return tasks.size();
    }

}

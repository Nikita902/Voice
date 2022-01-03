package bgv.fit.bstu.eday;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.Toolbar;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;

import bgv.fit.bstu.eday.Models.Task;

public class TaskActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    Context context = this;
    DBHelper databaseHelper;
    SQLiteDatabase db;
    Cursor userCursor;
    TaskAdapter taskAdapter;

    ArrayList<Task> tasks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);
        GetTasks();
        Adapter();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }


    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case (R.id.action_asc):
                Toast.makeText(getApplicationContext(), "Был выбран пункт asc",
                        Toast.LENGTH_SHORT).show();
                tasks.sort(Comparator.comparing(Task::getName));
                taskAdapter.notifyDataSetChanged();
            break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onDeleteTaskButtonClick(View view) {
        String id = view.getTag().toString();

        databaseHelper = new DBHelper(getApplicationContext());
        db = databaseHelper.getWritableDatabase();
        db.delete("Tasks", "Id = ?", new String[]{id});
        GetTasks();
        Adapter();
    }

    public void Adapter(){
        recyclerView = (RecyclerView) findViewById(R.id.list);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        taskAdapter = new TaskAdapter(tasks);
        recyclerView.setAdapter(taskAdapter);
    }

    public void CreateTaskPage(View view){
        Intent intent = new Intent(this,NewTaskActivity.class);
        startActivity(intent);
    }

    private void GetTasks() {
        tasks = new ArrayList<>();
        databaseHelper = new DBHelper(getApplicationContext());
        db = databaseHelper.getWritableDatabase();
        userCursor = db.rawQuery("select * from Tasks", null);
        userCursor.moveToFirst();
        while (!userCursor.isAfterLast()) {
            Task task = new Task();
            task.setId(userCursor.getInt(0));
            task.setName(userCursor.getString(1));
            task.setDescription(userCursor.getString(2));
            task.setDate(userCursor.getString(3));
            task.setTime(userCursor.getString(4));
            task.setUserId(userCursor.getInt(5));
            userCursor.moveToNext();
            tasks.add(task);
        }
    }
}
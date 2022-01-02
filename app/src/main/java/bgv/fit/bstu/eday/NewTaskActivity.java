package bgv.fit.bstu.eday;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.jaredrummler.android.colorpicker.ColorPickerDialog;
import com.jaredrummler.android.colorpicker.ColorPickerDialogListener;
import com.jaredrummler.android.colorpicker.ColorShape;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import bgv.fit.bstu.eday.Models.Task;
import bgv.fit.bstu.eday.Models.User;

public class NewTaskActivity extends AppCompatActivity implements ColorPickerDialogListener {
    EditText name, description, date, time;
    FloatingActionButton voiceRecord;
    Button colors;
    DBHelper databaseHelper;
    SQLiteDatabase db;
    WorkWithDB workWithDB;

    private static final int firstId = 1, secondId = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_task);
        init();
        databaseHelper = new DBHelper(getApplicationContext());
        db = databaseHelper.getWritableDatabase();
        workWithDB = new WorkWithDB(db);
        colors = findViewById(R.id.btnColor);
    }

    ActivityResultLauncher<Intent> ActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == RESULT_OK) {
                        Intent data = result.getData();
                        ArrayList<String> results = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                        String task = results.toString();

                        task = FormatTask(task);
                        DisplayTask(task);
                    }
                }
            }
    );

    public void SaveTask(View view) {
        if (name.getText().toString().equals("") || description.getText().toString().equals("") || date.getText().toString().equals("") || time.getText().toString().equals("")) {
            Toast.makeText(getApplicationContext(), "Заполните все поля", Toast.LENGTH_SHORT).show();
        } else {
            try {
                Task task = new Task();
                task.setName(name.getText().toString());
                task.setColor(name.getCurrentTextColor());
                task.setDescription(description.getText().toString());
                task.setDate(date.getText().toString());
                task.setTime(time.getText().toString());
                task.setUserId(MainActivity.UserId);
                workWithDB.InsertTask(task);
                Toast.makeText(getApplicationContext(), "Успешне добавление", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), TaskActivity.class);
                startActivity(intent);
                Log.d("Task add", "Success");
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(getApplicationContext(), "Не получилось добавить", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void init() {
        name = findViewById(R.id.tnameet);
        description = findViewById(R.id.descret);
        date = findViewById(R.id.dateet);
        time = findViewById(R.id.timeet);
        voiceRecord = findViewById(R.id.btnVoice);
        setDate();
    }

    public void VoiceRecord(View view) {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Скажите слово для распознавания");
        ActivityResultLauncher.launch(intent);
    }

    private String FormatTask(String task) {
        return task.replace("[", "")
                .replace("]", "\n");
    }

    private void DisplayTask(String task) {
        description.setText(task);
    }


    @Override
    public void onColorSelected(int dialogId, int color) {
        switch (dialogId) { // смотрим, какая кнопка нажата
            case firstId:
                name.setTextColor(color);
                break;
        }
    }

    @Override
    public void onDialogDismissed(int dialogId) {
            Toast.makeText(this, "Dialog dismissed", Toast.LENGTH_SHORT).show();
    }

    private void createColorPickerDialog(int id) {
        ColorPickerDialog.newBuilder()
                .setColor(Color.RED)
                .setDialogType(ColorPickerDialog.TYPE_PRESETS)
                .setAllowCustom(true)
                .setAllowPresets(true)
                .setColorShape(ColorShape.SQUARE)
                .setDialogId(id)
                .show(this);
// полный список атрибутов класса ColorPickerDialog смотрите ниже
    }

    public void onClickButton(View view) {
        switch (view.getId()) {
            case R.id.btnColor:
                createColorPickerDialog(firstId);
                Intent intent = new Intent(getApplicationContext(), TaskActivity.class);
                startActivity(intent);
                break;
        }
    }

    public void setDate() {
        Date currentDate = new Date(); //текущее время
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
        String dateText = dateFormat.format(currentDate);
        DateFormat timeFormat = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
        String timeText = timeFormat.format(currentDate);

        date.setText(dateText);
        time.setText(timeText);
    }
}
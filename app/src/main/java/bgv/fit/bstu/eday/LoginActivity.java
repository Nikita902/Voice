package bgv.fit.bstu.eday;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
    private EditText loginField,passwordField;
    public static String log_name;
    DBHelper databaseHelper;
    SQLiteDatabase db;
    WorkWithDB workWithDB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
        databaseHelper = new DBHelper(getApplicationContext());
        db = databaseHelper.getWritableDatabase();
        workWithDB = new WorkWithDB(db);
    }

    public void init(){
        loginField = findViewById(R.id.et_login);
        passwordField = findViewById(R.id.et_password);
    }

    public void Authorization(View view){
        log_name=loginField.getText().toString();
        String log="";
        String pass="";
        log=loginField.getText().toString();
        pass=passwordField.getText().toString();
        if(log.equals("")||pass.equals("")) {
            Toast.makeText(getApplicationContext(), "Пустые поля недопустимы", Toast.LENGTH_SHORT).show();
        }
        else {
            if(workWithDB.SelectUserByLoginAndPassword(log, pass)) {
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                Log.d("User login", "Success");
            }
            else
                Toast.makeText(getApplicationContext(), "Неверный логин или пароль", Toast.LENGTH_SHORT).show();
        }
    }

    public void Registration (View view){
        Intent intent = new Intent(this, RegistrationActivity.class);
        startActivity(intent);
        Log.d("Registration open","Success");
    }
}
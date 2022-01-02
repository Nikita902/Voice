package bgv.fit.bstu.eday;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

public class ProfileActivity extends AppCompatActivity {
    EditText surname,name;
    TextView login;
    ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        init();
    }

    public void init(){
        surname = (EditText) findViewById(R.id.surnamet);
        name = (EditText) findViewById(R.id.namet);
        imageView = (ImageView) findViewById(R.id.imageViewt);
        login=findViewById(R.id.logint);
        surname.setText(MainActivity.UserSurname);
        name.setText(MainActivity.UserName);
        Bitmap bitmap = BitmapFactory.decodeByteArray(MainActivity.UserPhoto,0,MainActivity.UserPhoto.length);
        imageView.setImageBitmap(bitmap);
        login.setText(MainActivity.UserLogin);
    }
}
package bgv.fit.bstu.eday;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "Eday.db";
    private static final int SCHEMA = 1;
    public static Context context;
    static final String UTABLE = "Users";
    public static final String COLUMN_ID = "Id";
    public static final String COLUMN_NAME = "Name";
    public static final String COLUMN_SURNAME = "Surname";
    public static final String COLUMN_PHOTO = "Photo";
    public static final String COLUMN_LOGIN = "Login";
    public static final String COLUMN_PASSWORD = "Password";
    static final String TTABLE = "Tasks";
    public static final String COLUMN_TID = "Id";
    public static final String COLUMN_TNAME = "Name";
    public static final String COLUMN_TDESCRIPTION = "Description";
    public static final String COLUMN_TDATE = "Date";
    public static final String COLUMN_TTIME = "Time";
    public static final String COLUMN_TUID = "UserId";
    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, SCHEMA);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE Users (" + COLUMN_ID
                + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_NAME + " TEXT, "
                + COLUMN_SURNAME + " TEXT, "
                + COLUMN_PHOTO + " BLOB, "
                + COLUMN_LOGIN + " TEXT, "
                + COLUMN_PASSWORD + " TEXT);");

        db.execSQL("CREATE TABLE Tasks (" + COLUMN_TID
                + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_TNAME + " TEXT, "
                + COLUMN_TDESCRIPTION + " TEXT, "
                + COLUMN_TDATE + " TEXT, "
                + COLUMN_TTIME + " TEXT, "
                + COLUMN_TUID + " INTEGER, FOREIGN KEY (UserId) REFERENCES Users (COLUMN_ID) );");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}

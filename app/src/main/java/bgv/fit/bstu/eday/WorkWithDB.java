package bgv.fit.bstu.eday;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import bgv.fit.bstu.eday.Models.Task;
import bgv.fit.bstu.eday.Models.User;

public class WorkWithDB {
    SQLiteDatabase db;
    Cursor userCursor;
    public WorkWithDB(SQLiteDatabase db)
    {
        this.db = db;
    }
    public ArrayList<User> SelectUsers()
    {
        ArrayList<User> users = new ArrayList<>();
        String[] columns = new String[]{DBHelper.COLUMN_ID, DBHelper.COLUMN_NAME, DBHelper.COLUMN_SURNAME, DBHelper.COLUMN_PHOTO, DBHelper.COLUMN_LOGIN, DBHelper.COLUMN_PASSWORD};
        userCursor = db.query(DBHelper.UTABLE, columns, null, null, null, null, null);
        userCursor.moveToFirst();
        User user = new User();
        user.setName(userCursor.getString(1));
        user.setSurname(userCursor.getString(2));
        user.setPhoto(userCursor.getExtras().getByteArray(String.valueOf(3)));
        user.setLogin(userCursor.getString(4));
        user.setPassword(userCursor.getString(5));
        users.add(user);
        while (userCursor.moveToNext()) {
            user = new User();
            user.setName(userCursor.getString(1));
            user.setSurname(userCursor.getString(2));
            user.setPhoto(userCursor.getExtras().getByteArray(String.valueOf(3)));
            user.setLogin(userCursor.getString(4));
            user.setPassword(userCursor.getString(5));
            users.add(user);
        }
        userCursor.close();
        return users;
    }

    public boolean SelectUserByLoginAndPassword(String login, String password)
    {
        ArrayList<User> users = new ArrayList<>();
        String[] columns = new String[]{DBHelper.COLUMN_ID, DBHelper.COLUMN_NAME, DBHelper.COLUMN_SURNAME, DBHelper.COLUMN_PHOTO, DBHelper.COLUMN_LOGIN, DBHelper.COLUMN_PASSWORD};
        userCursor = db.query(DBHelper.UTABLE, columns, DBHelper.COLUMN_LOGIN+"=? and " + DBHelper.COLUMN_PASSWORD+"=?", new String[]{login, password}, null, null, null);
        if(userCursor.getCount()!=0) {
            userCursor.moveToFirst();
            MainActivity.UserId = userCursor.getInt(0);
            MainActivity.UserName = userCursor.getString(1);
            MainActivity.UserSurname = userCursor.getString(2);
            MainActivity.UserPhoto = userCursor.getBlob(3);
            MainActivity.UserLogin = userCursor.getString(4);
            userCursor.close();
            return true;
        }
        else {
            userCursor.close();
            return false;
        }
    }

    public void InsertUser(User user){
        ContentValues cv = new ContentValues();

        cv.put(DBHelper.COLUMN_NAME, user.getName());
        cv.put(DBHelper.COLUMN_SURNAME, user.getSurname());
        cv.put(DBHelper.COLUMN_PHOTO, user.getPhoto());
        cv.put(DBHelper.COLUMN_LOGIN, user.getLogin());
        cv.put(DBHelper.COLUMN_PASSWORD, user.getPassword());
        db.insert(DBHelper.UTABLE, null, cv);
    }

    public void InsertTask(Task task){
        ContentValues cv = new ContentValues();
        cv.put(DBHelper.COLUMN_TNAME, task.getName());
        cv.put(DBHelper.COLUMN_TDESCRIPTION, task.getDescription());
        cv.put(DBHelper.COLUMN_TDATE, task.getDate());
        cv.put(DBHelper.COLUMN_TTIME, task.getTime());
        cv.put(DBHelper.COLUMN_TUID, task.getUserId());
        db.insert(DBHelper.TTABLE, null, cv);
    }
}

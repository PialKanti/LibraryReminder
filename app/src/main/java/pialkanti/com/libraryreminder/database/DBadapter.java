package pialkanti.com.libraryreminder.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.net.Uri;
import android.provider.ContactsContract;
import android.util.Log;


import java.io.BufferedInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import pialkanti.com.libraryreminder.POJO.AlarmDetails;
import pialkanti.com.libraryreminder.POJO.BookDetails;


/**
 * Created by Piyal Shuvro on 8/23/2015.
 */
public class DBadapter {
    private Database database;
    private Context context;
    private SQLiteDatabase db;

    public DBadapter(Context context) {
        this.context = context;
        database = new Database(context);
        db = database.getWritableDatabase();
    }


    public void insertBookDetails(String Book, String Due, String Call_no, String Renew) {
        ContentValues values = new ContentValues();
        values.put(database.Column_BOOK, Book);
        values.put(database.Column_DUE, Due);
        values.put(database.Column_CALL_NO, Call_no);
        values.put(database.Column_RENEW, Renew);

        long insert = db.insert(database.Table_Name, null, values);

    }

    public void insertAlarmDetails(int year, int month, int day, int hour, int minute, int second, String repeat) {
        ContentValues values = new ContentValues();
        values.put(database.Column_YEAR, year);
        values.put(database.Column_MONTH, month);
        values.put(database.Column_DAY, day);
        values.put(database.Column_HOUR, hour);
        values.put(database.Column_MINUTE, minute);
        values.put(database.Column_SECOND, second);
        values.put(database.Column_REPEAT, repeat);


        long insert = db.insert(database.Table_Name, null, values);

    }

    public ArrayList<BookDetails> getBookInfo() {
        BookDetails book = null;
        ArrayList<BookDetails> book_List = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM " + database.Table_Name, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            book = new BookDetails(cursor.getString(1), cursor.getString(3), cursor.getString(2), cursor.getString(4));
            book_List.add(book);
            cursor.moveToNext();
        }
        cursor.close();
        closeDatabase();
        return book_List;
    }

    public ArrayList<AlarmDetails> getAlarmInfo() {
        AlarmDetails alarm = null;
        ArrayList<AlarmDetails> alarm_List = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT * FROM " + database.Alarm_Table_Name, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            alarm = new AlarmDetails(cursor.getInt(1), cursor.getInt(2), cursor.getInt(3), cursor.getInt(4),cursor.getInt(5),cursor.getInt(6),cursor.getString(7));
            alarm_List.add(alarm);
            cursor.moveToNext();
        }
        cursor.close();
        closeDatabase();
        return alarm_List;
    }

    public int BookTableSize() {
        String size_query = "SELECT " + database.Column_BOOK + " FROM " + database.Table_Name;
        Cursor cursor = db.rawQuery(size_query, null);

        return cursor.getCount();
    }

    public int AlarmTableSize() {
        String size_query = "SELECT " + database.Column_YEAR + " FROM " + database.Alarm_Table_Name;
        Cursor cursor = db.rawQuery(size_query, null);

        return cursor.getCount();
    }

    public void closeDatabase() {
        if (db != null) {
            db.close();
        }
    }

    public void deleteDatabase() {
        db.execSQL("delete from " + database.Table_Name);
        db.close();
    }


}

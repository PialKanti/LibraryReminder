package pialkanti.com.libraryreminder.database;

/**
 * Created by Piyal Shuvro on 8/22/2015.
 */

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Pial-PC on 8/16/2015.
 */
public class Database extends SQLiteOpenHelper {
    SQLiteDatabase database;
    Context context;
    private static final String Logtag = "Message";

    private static final int DATABASE_VERSION = 1;

    //Database name
    private static final String Database_Name = "library_database.db";
    //  table name
    public static final String Table_Name = "library_table";
    //table columnsDatabase_Name
    public static final String Column_ID = "_id";
    public static final String Column_BOOK = "bookname";
    public static final String Column_DUE = "due";
    public static final String Column_CALL_NO = "call_no";
    public static final String Column_RENEW = "renew";

    public static final String Alarm_Table_Name = "alarm_table";

    public static final String Column_YEAR = "year";
    public static final String Column_MONTH = "month";
    public static final String Column_DAY = "day";
    public static final String Column_HOUR = "hour";
    public static final String Column_MINUTE = "minute";
    public static final String Column_SECOND = "second";
    public static final String Column_REPEAT = "repeat";

    //Database Create Query
    private static final String CREATE_BOOK_TABLE_QUERY = "CREATE TABLE " + Table_Name + " (" +
            Column_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            Column_BOOK + " TEXT, " +
            Column_DUE + " TEXT, " +
            Column_CALL_NO + " TEXT, " +
            Column_RENEW + " TEXT);";

    private static final String CREATE_ALARM_TABLE_QUERY = "CREATE TABLE " + Alarm_Table_Name + " (" +
            Column_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            Column_YEAR + " INTEGER, " +
            Column_MONTH + " INTEGER, " +
            Column_DAY + " INTEGER, " +
            Column_HOUR + " INTEGER, " +
            Column_MINUTE + " INTEGER, " +
            Column_SECOND + " INTEGER, " +
            Column_REPEAT + " TEXT);";

    public Database(Context context) {
        super(context, Database_Name, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_BOOK_TABLE_QUERY); //Create library_table
        db.execSQL(CREATE_ALARM_TABLE_QUERY); //Create alarm_table
        /*db.execSQL(Password_Table_QUERY);*/
        Log.i(Logtag, "Table has been created.");


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}


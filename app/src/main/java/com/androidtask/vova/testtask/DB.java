package com.androidtask.vova.testtask;

/**
 * Created by User on 20.08.2015.
 */
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DB {

    private static final String DB_NAME = "mydb2";
    private static final int DB_VERSION = 1;
    private static final String DB_TABLE = "mytab";

    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_IMG = "img";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_ABOUT = "about";
    public static final String COLUMN_PRICE = "price";


    private static final String DB_CREATE =
            "create table " + DB_TABLE + "(" +
                    COLUMN_ID + " integer primary key autoincrement, " +
                    COLUMN_IMG + " integer, " +
                    COLUMN_NAME + " text, " +
                    COLUMN_ABOUT + " text, " +
                    COLUMN_PRICE + " integer " +
                    ");";

    private final Context mCtx;

    private int[] waterImageIDs = {
            R.drawable.one,
            R.drawable.two,
            R.drawable.three,
            R.drawable.four,
            R.drawable.five,
            R.drawable.six,
            R.drawable.seven,
            R.drawable.eight,
            R.drawable.nine,
            R.drawable.ten
    };
    private String[] waterNameIDs = {
            "Живая вода",
            "Кристальная вода",
            "Горный хрусталь",
            "Альпин",
            "Серебряная капля",
            "Льдинка детская",
            "Софийский ледник",
            "Домбай Лайт",
            "Седой Кавказ",
            "Пилигрим"
    };
    private String[] waterAboutIDs = {
            "19 л",
            "19 л",
            "19 л",
            "19 л",
            "19 л",
            "19 л",
            "19 л",
            "19 л",
            "19 л",
            "19 л",
    };
    private int[] waterPriceIDs = {
            110,
            112,
            115,
            117,
            120,
            160,
            150,
            140,
            155,
            180
    };

    private DBHelper mDBHelper;
    private SQLiteDatabase mDB;

    public DB(Context ctx) {
        mCtx = ctx;
    }

    // открыть подключение
    public void open() {
        mDBHelper = new DBHelper(mCtx, DB_NAME, null, DB_VERSION);
        mDB = mDBHelper.getWritableDatabase();
    }

    // закрыть подключение
    public void close() {
        if (mDBHelper!=null) mDBHelper.close();
    }

    public Cursor getAllData() {
        return mDB.query(DB_TABLE, null, null, null, null, null, null);
    }

	  /*
	  public void addRec(String txt, int img) {
	    ContentValues cv = new ContentValues();
	    cv.put(COLUMN_NAME, txt);
	    cv.put(COLUMN_IMG, img);
	    mDB.insert(DB_TABLE, null, cv);
	  }*/

    /*
    public void delRec(long id) {
        mDB.delete(DB_TABLE, COLUMN_ID + " = " + id, null);
    }*/


    private class DBHelper extends SQLiteOpenHelper {

        public DBHelper(Context context, String name, CursorFactory factory,
                        int version) {
            super(context, name, factory, version);
        }


        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(DB_CREATE);

            ContentValues cv = new ContentValues();
            for (int i = 0; i < 10; i++) {
                cv.put(COLUMN_NAME,  waterNameIDs[i]);
                cv.put(COLUMN_IMG, waterImageIDs[i]);
                cv.put(COLUMN_ABOUT, waterAboutIDs[i]);
                cv.put(COLUMN_PRICE, waterPriceIDs[i]);
                db.insert(DB_TABLE, null, cv);
            }
            Log.d("debug", "Создана новая база данных");
        }


        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        }
    }


}
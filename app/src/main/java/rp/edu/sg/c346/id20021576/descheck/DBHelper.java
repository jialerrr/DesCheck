package rp.edu.sg.c346.id20021576.descheck;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "hardware.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_HARDWARE = "Hardware";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_DESC = "desc";
    private static final String COLUMN_PRICE = "price";
    private static final String COLUMN_STARS = "stars";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createHardwareTableSql = "CREATE TABLE " + TABLE_HARDWARE + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_NAME + " TEXT, "
                + COLUMN_DESC + " TEXT, "
                + COLUMN_PRICE + " REAL, "
                + COLUMN_STARS + " REAL )";
        db.execSQL(createHardwareTableSql);
        Log.i("info", createHardwareTableSql + "\ncreated tables");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_HARDWARE);
        onCreate(db);
    }

    public long insertHardware(String name, String desc, float price, float stars) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, name);
        values.put(COLUMN_DESC, desc);
        values.put(COLUMN_PRICE, price);
        values.put(COLUMN_STARS, stars);

        long result = db.insert(TABLE_HARDWARE, null, values);

        db.close();
        Log.d("SQL Insert","" + result);
        return result;
    }

    public ArrayList<Hardware> getAllHardwares() {
        ArrayList<Hardware> Hardwareslist = new ArrayList<Hardware>();
        String selectQuery = "SELECT " + COLUMN_ID + ","
                + COLUMN_NAME + "," + COLUMN_DESC + ","
                + COLUMN_PRICE + ","
                + COLUMN_STARS + " FROM " + TABLE_HARDWARE;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                String desc = cursor.getString(2);
                float price = cursor.getFloat(3);
                float stars = cursor.getFloat(4);

                Hardware newHardware = new Hardware(id, name, desc, price, stars);
                Hardwareslist.add(newHardware);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return Hardwareslist;
    }

    public ArrayList<Hardware> getAllHardwaresByStars(int starsFilter) {
        ArrayList<Hardware> Hardwareslist = new ArrayList<Hardware>();

        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns= {COLUMN_ID, COLUMN_NAME, COLUMN_DESC, COLUMN_PRICE, COLUMN_STARS};
        String condition = COLUMN_STARS + ">= ?";
        String[] args = {String.valueOf(starsFilter)};

        Cursor cursor;
        cursor = db.query(TABLE_HARDWARE, columns, condition, args, null, null, null, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                String desc = cursor.getString(2);
                float price = cursor.getFloat(3);
                float stars = cursor.getFloat(4);

                Hardware newHardware = new Hardware(id, name, desc, price, stars);
                Hardwareslist.add(newHardware);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return Hardwareslist;
    }

    public int updateHardware(Hardware data){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, data.getName());
        values.put(COLUMN_DESC, data.getDesc());
        values.put(COLUMN_PRICE, data.getPrice());
        values.put(COLUMN_STARS, data.getStars());
        String condition = COLUMN_ID + "= ?";
        String[] args = {String.valueOf(data.getId())};
        int result = db.update(TABLE_HARDWARE, values, condition, args);
        db.close();
        return result;
    }


    public int deleteHardware(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        String condition = COLUMN_ID + "= ?";
        String[] args = {String.valueOf(id)};
        int result = db.delete(TABLE_HARDWARE, condition, args);
        db.close();
        return result;
    }

}
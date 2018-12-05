package vijaytest.example.com.nearresturant.SQLiteDatabase;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


import java.util.ArrayList;
import java.util.List;

import vijaytest.example.com.nearresturant.Model.SavedData;

/**
 * Created by saiprasanthk on 04-12-2018.
 */

public class SavedDatabaseHelper extends SQLiteOpenHelper {
    Context context;

    private static final String DATABASE_NAME = "fav.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_CONTACTS = "NotificationsTable";

    private static final String KEY_SNO = "sno";
    private static final String KEY_IMG = "img";
    private static final String KEY_NAME = "name";
    private static final String KEY_ADD = "address";
    private static final String KEY_ID = "Id";

    public SavedDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE IF NOT EXISTS " + TABLE_CONTACTS + "("
                + KEY_SNO + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KEY_IMG + " TEXT,"
                + KEY_NAME + " TEXT,"
                + KEY_ADD + " TEXT,"
                + KEY_ID + " TEXT" + ")";


        sqLiteDatabase.execSQL(CREATE_CONTACTS_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);
        // Create tables again
        onCreate(sqLiteDatabase);

    }

    public void addNotification(SavedData savedData) {
        SQLiteDatabase db = this.getWritableDatabase();
        String insert = "INSERT INTO " + TABLE_CONTACTS + "(" + KEY_IMG + "," + KEY_NAME + "," + KEY_ADD + "," + KEY_ID + ")" +
                " VALUES('" + savedData.getImg() + "','" + savedData.getName() + "','" + savedData.getAdd() + "','" + "1" + "')";

        // Inserting Row
        db.execSQL(insert);
        Log.e("Insert Values", savedData.getName());
        //2nd argument is String containing nullColumnHack
        db.close();
    }

    public List<SavedData>

    getAllNotifications() {
        List<SavedData> contactList = new ArrayList<>();
        String selectQuery;
        selectQuery = "SELECT * FROM " + TABLE_CONTACTS + " WHERE " + KEY_ID + "='" + "1" + "'" + " ORDER BY " + KEY_NAME + " DESC";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                SavedData contact = new SavedData();
                contact.setSno(cursor.getString(0));
                contact.setImg(cursor.getString(1));
                contact.setName(cursor.getString(2));
                contact.setAdd(cursor.getString(3));
                contact.setId(cursor.getString(4));
                contactList.add(contact);
            } while (cursor.moveToNext());
        }
        // return contact list
        db.close();
        return contactList;
    }
}

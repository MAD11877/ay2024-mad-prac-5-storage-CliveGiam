package sg.edu.np.mad.madpractical5;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.Random;

public class dbHandler extends SQLiteOpenHelper{

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "users.db";
    public static final String TABLE_USERS = "users";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_DESC = "description";
    public static final String COLUMN_FOLLOWED = "FOLLOWED";

    public dbHandler (Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_USERS_TABLE = "CREATE TABLE " + TABLE_USERS + "(" + COLUMN_NAME + " TEXT," + COLUMN_DESC + " TEXT," + COLUMN_FOLLOWED + " INTEGER,"  + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT" + ")";
        db.execSQL(CREATE_USERS_TABLE);
        for (int i = 0; i<20; i++) {
            Random initrand = new Random();
            int namenum = initrand.nextInt(1000000);
            int descnum = initrand.nextInt(1000000);
            String Name = "Name" + String.valueOf(namenum);
            String Desc = "Description" + String.valueOf(descnum);
            ContentValues values = new ContentValues();
            values.put(COLUMN_NAME, Name);
            values.put(COLUMN_DESC, Desc);
            values.put(COLUMN_FOLLOWED, 0);

            db.insert(TABLE_USERS, null, values);
        }
        db.close();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        onCreate(db);
    }

    public void addUser(User user) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, user.getName());
        values.put(COLUMN_DESC, user.getDescription());
        values.put(COLUMN_FOLLOWED, user.getFollowed());
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_USERS, null, values);
        db.close();
    }

    public ArrayList<User> getUsers() {
        ArrayList<User> users = new ArrayList<User>();
        String query  = "SELECT * FROM " + TABLE_USERS + " ";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        while (cursor.moveToNext()) {
            boolean follow = true;
            if(cursor.getInt(3) == 0) {
                follow = false;
            }
            User temp = new User(cursor.getString(0), cursor.getString(1), cursor.getInt(2), follow);
            users.add(temp);
        }
        return users;
    }

    public void updateUser(User user) {
        ContentValues values = new ContentValues();
        int follow = 0;
        if (user.getFollowed()) { follow = 1; };
        values.put(COLUMN_FOLLOWED, follow);

        SQLiteDatabase db = this.getWritableDatabase();
        db.update(TABLE_USERS, values, "id=String.valueOf(user.getId()))", null); //Fix later

        db.close();
    }


}

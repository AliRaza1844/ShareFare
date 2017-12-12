package sharefare.aliraza.com;

import android.content.ContentValues;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;

import java.util.Date;

/**
 * Created by Ali_Raza on 12/10/2017.
 */

public class ProfileTable {
    private static final String KEY_ROWID = "_cnic";
    private static final String KEY_FIRSTNAME = "first_name";
    private static final String KEY_LASTNAME = "last_name";
    private static final String KEY_DOB = "_dob";
    private static final String KEY_AGE = "_age";
    private static final String KEY_ADDRESS = "_address";
    private static final String KEY_PHONENO = "phone_number";
    private static final String KEY_IMAGE = "_image";

    private final String DATABASE_NAME = "ShareFareDB";
    private final String DATABASE_TABLE = "ProfileTable";
    private final int DATABASE_VERSION = 2;

    private ProfileTable.DBHelper ourHelper;
    private final Context ourContext;
    private SQLiteDatabase ourDatabase;

    public ProfileTable(Context context){
        this.ourContext = context;
    }

    private class DBHelper extends SQLiteOpenHelper {

        public DBHelper(Context context){
            super(context, DATABASE_NAME,null,DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            /*

            CREATE TABLE AccountsTable(_id INTEGER PRIMARY KEY AUTOINCREMENT,
                                    person_name TEXT NOT NULL, _cell TEXT NOT NULL)

            */
            String sqlCode = "CREATE TABLE " + DATABASE_TABLE + " (" +
                    KEY_ROWID + " TEXT PRIMARY KEY, " +
                    KEY_FIRSTNAME + " TEXT NOT NULL, " +
                    KEY_LASTNAME + " TEXT NOT NULL, " +
                    KEY_DOB + " TEXT NOT NULL, " +
                    KEY_AGE + " TEXT NOT NULL, " +
                    KEY_ADDRESS + " TEXT NOT NULL, " +
                    KEY_IMAGE + " TEXT" +
                    KEY_PHONENO + " TEXT NOT NULL);";

            db.execSQL(sqlCode);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
            onCreate(db);
        }
    }

    public ProfileTable open() throws SQLException {
        this.ourHelper = new ProfileTable.DBHelper(this.ourContext);
        this.ourDatabase = this.ourHelper.getWritableDatabase();
        return this;
    }

    public void close(){
        this.ourHelper.close();
    }

    public long createEntry(String cnic, String firstname, String lastname, String dob, String age, String phoneNumber, String address, String image){
        ContentValues cv = new ContentValues();
        cv.put(KEY_ROWID,cnic);
        cv.put(KEY_FIRSTNAME,firstname);
        cv.put(KEY_LASTNAME,lastname);
        cv.put(KEY_DOB,dob);
        cv.put(KEY_ADDRESS,address);
        cv.put(KEY_PHONENO,phoneNumber);
        cv.put(KEY_AGE,age);
        //cv.put(KEY_IMAGE,image);
        return ourDatabase.insert(DATABASE_TABLE,null,cv);
    }

}

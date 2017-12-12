package sharefare.aliraza.com;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Ali_Raza on 12/10/2017.
 */

public class AccountsTable {
    public static final String KEY_ROWID = "_id";
    public static final String KEY_PASSWORD = "_password";

    private final String DATABASE_NAME = "ShareFareDB";
    private final String DATABASE_TABLE = "AccountsTable";
    private final int DATABASE_VERSION = 2;

    private DBHelper ourHelper;
    private final Context ourContext;
    private SQLiteDatabase ourDatabase;

    public AccountsTable(Context context){
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
                    KEY_PASSWORD + " TEXT NOT NULL);";

            db.execSQL(sqlCode);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE);
            onCreate(db);
        }
    }

    public AccountsTable open() throws SQLException {
        this.ourHelper = new DBHelper(this.ourContext);
        this.ourDatabase = this.ourHelper.getWritableDatabase();
        return this;
    }

    public void close(){
        this.ourHelper.close();
    }

    public long createEntry(String email, String password){
        ContentValues cv = new ContentValues();
        cv.put(KEY_ROWID,email);
        cv.put(KEY_PASSWORD,password);
        return ourDatabase.insert(DATABASE_TABLE,null,cv);
    }

    public String getData(){
        String [] colomns = new String []{KEY_ROWID,KEY_PASSWORD};

        Cursor cursor = this.ourDatabase.query(DATABASE_TABLE, colomns,null,null,null,null,null);

        String result = "";

        int iRowID = cursor.getColumnIndex(KEY_ROWID);
        int iPassword = cursor.getColumnIndex(KEY_PASSWORD);

        for (cursor.moveToFirst();!cursor.isAfterLast();cursor.moveToLast()){
            result = result + cursor.getString(iRowID) + cursor.getString(iPassword);
        }
        cursor.close();

        return result;
    }

    public boolean validateUser(String email, String password){
        String [] colomns = new String []{KEY_ROWID,KEY_PASSWORD};

        Cursor cursor = this.ourDatabase.query(DATABASE_TABLE, colomns,null,null,null,null,null);

        int iRowID = cursor.getColumnIndex(KEY_ROWID);
        int iPassword = cursor.getColumnIndex(KEY_PASSWORD);

        for (cursor.moveToFirst();!cursor.isAfterLast();cursor.moveToNext()){
            if(cursor.getString(iRowID).equals(email) && cursor.getString(iPassword).equals(password)){
                return true;
            }
        }

        return false;
    }

    public long deleteEntry(String rowId){
        return this.ourDatabase.delete(DATABASE_TABLE,KEY_ROWID + "=?",new String[]{rowId});
    }

    public long updteEntry(String rowId, String password){
        ContentValues cv = new ContentValues();
        cv.put(KEY_PASSWORD,password);
        cv.put(KEY_ROWID, rowId);

        return this.ourDatabase.update(DATABASE_TABLE, cv, KEY_ROWID + "=?",new String[]{rowId});
    }


}

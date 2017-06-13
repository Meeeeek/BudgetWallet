package michaelkim.budgetingandwalletbased;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Michael Kim on 6/9/2017.
 */

public class transactionDatabase extends SQLiteOpenHelper{

    private static final String DATABASE_NAME = "TRANSACTION.DB";
    private static final int DATABASE_VERSION = 2;
    private static final String CREATE_QUERY =
            "CREATE TABLE " + transactionContract.NewTransaction.TABLE_NAME + "("
                    + transactionContract.NewTransaction.NAME + " TEXT,"
                    + transactionContract.NewTransaction.VALUE + " TEXT,"
                    + transactionContract.NewTransaction.LOCATION + " TEXT,"
                    + transactionContract.NewTransaction.DATE + " TEXT);";

    public transactionDatabase (Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        Log.e("DBT", "Database Created/Opened");
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_QUERY);
        Log.e("DBT", "Table Created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void addTransInfo(String name, String value, String location, String date, SQLiteDatabase sqLiteDatabase){

        ContentValues contentValues = new ContentValues();
        contentValues.put(transactionContract.NewTransaction.NAME, name);
        contentValues.put(transactionContract.NewTransaction.VALUE, value);
        contentValues.put(transactionContract.NewTransaction.LOCATION, location);
        contentValues.put(transactionContract.NewTransaction.DATE, date);
        sqLiteDatabase.insert(transactionContract.NewTransaction.TABLE_NAME, null, contentValues);
        Log.e("DBT", "Information Inserted");

    }

    public Cursor getTransInfo(SQLiteDatabase sqLiteDatabase){

        Cursor cursor;
        String[] projections = {transactionContract.NewTransaction.NAME,
                transactionContract.NewTransaction.VALUE,
                transactionContract.NewTransaction.LOCATION,
                transactionContract.NewTransaction.DATE};
        cursor = sqLiteDatabase.query(transactionContract.NewTransaction.TABLE_NAME, projections, null, null, null, null, null);

        return cursor;

    }

    public void deleteTrans(String name, SQLiteDatabase sqLiteDatabase){
        String selection = transactionContract.NewTransaction.NAME + " LIKE ?";
        String[] selection_args = {name};
        sqLiteDatabase.delete(transactionContract.NewTransaction.TABLE_NAME, selection, selection_args);
    }

}

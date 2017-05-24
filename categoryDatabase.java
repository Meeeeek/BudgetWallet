package michaelkim.budgetingandwalletbased;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Michael Kim on 5/22/2017.
 */

public class categoryDatabase extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "CATEGORYINFO.DB";
    private static final int DATABASE_VERSION = 1;
    private static final String CREATE_QUERY =
            "CREATE TABLE " + categoryContract.NewCategoryInformation.TABLE_NAME +
            "(" + categoryContract.NewCategoryInformation.CATEGORY_NAME + " TEXT,"
            + categoryContract.NewCategoryInformation.CATEGORY_VALUE + " TEXT,"
            + categoryContract.NewCategoryInformation.CATEGORY_TRANSACTIONS + " TEXT);";

    // Creates an initial database if none already exist.
    public categoryDatabase(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        Log.e("DATABASE OPERATIONS", "Database created / opened.");
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_QUERY);
        Log.e("DATABASE OPERATIONS", "Table created...");
    }

    // Method for inserting data.
    public void addCategoryInformation(String name, String value, String transactions, SQLiteDatabase sqLiteDatabase){

        ContentValues contentValues = new ContentValues();
        contentValues.put(categoryContract.NewCategoryInformation.CATEGORY_NAME, name);
        contentValues.put(categoryContract.NewCategoryInformation.CATEGORY_VALUE, value);
        contentValues.put(categoryContract.NewCategoryInformation.CATEGORY_TRANSACTIONS, transactions);
        sqLiteDatabase.insert(categoryContract.NewCategoryInformation.TABLE_NAME, null, contentValues);
        Log.e("DATABASE OPERATIONS", "One row inserted...");

    }

    // Method for retrieving data.
    public Cursor getInformations(SQLiteDatabase sqLiteDatabase){

        Cursor cursor;
        String[] projections = {categoryContract.NewCategoryInformation.CATEGORY_NAME,
                categoryContract.NewCategoryInformation.CATEGORY_VALUE};
        cursor = sqLiteDatabase.query(categoryContract.NewCategoryInformation.TABLE_NAME, projections, null, null, null, null, null);
        return cursor;

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

}

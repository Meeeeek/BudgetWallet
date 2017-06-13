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

public class categoryDatabase extends SQLiteOpenHelper{

    private static final String DATABASE_NAME = "CATEGORY.DB";
    private static final int DATABASE_VERSION = 1;
    private static final String CREATE_QUERY =
            "CREATE TABLE " + categoryContract.NewCategory.TABLE_NAME + "("
                    + categoryContract.NewCategory.NAME + " TEXT,"
                    + categoryContract.NewCategory.VALUE + " TEXT);";

    public categoryDatabase(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        Log.e("DB", "Database Created/Opened");
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_QUERY);
        Log.e("DB", "Table Created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void addCategoryInfo(String name, String value, SQLiteDatabase sqLiteDatabase){

        ContentValues contentValues = new ContentValues();
        contentValues.put(categoryContract.NewCategory.NAME, name);
        contentValues.put(categoryContract.NewCategory.VALUE, value);
        sqLiteDatabase.insert(categoryContract.NewCategory.TABLE_NAME, null, contentValues);
        Log.e("DB", "Information Inserted");

    }

    public Cursor getCategoryInfo(SQLiteDatabase sqLiteDatabase){

        Cursor cursor;
        String[] projections = {categoryContract.NewCategory.NAME, categoryContract.NewCategory.VALUE};
        cursor = sqLiteDatabase.query(categoryContract.NewCategory.TABLE_NAME, projections, null, null, null, null, null);


        return cursor;

    }

    public Cursor getCategory(String name, SQLiteDatabase sqLiteDatabase){
        String[] projections = {categoryContract.NewCategory.VALUE};
        String selection = categoryContract.NewCategory.NAME + " LIKE ?";
        String[] selection_args = {name};
        Cursor cursor = sqLiteDatabase.query(categoryContract.NewCategory.TABLE_NAME,
                projections, selection, selection_args, null, null, null);

        return cursor;
    }

    public void deleteCategory(String name, SQLiteDatabase sqLiteDatabase){
        String selection = categoryContract.NewCategory.NAME + " LIKE ?";
        String[] selection_args = {name};
        sqLiteDatabase.delete(categoryContract.NewCategory.TABLE_NAME, selection, selection_args);
    }

    public int updateCategory(String name, String value, SQLiteDatabase sqLiteDatabase){
        ContentValues contentValues = new ContentValues();
        contentValues.put(categoryContract.NewCategory.VALUE, value);
        String selection = categoryContract.NewCategory.NAME + " LIKE ?";
        String[] selection_args = {name};
        int count = sqLiteDatabase.update(categoryContract.NewCategory.TABLE_NAME, contentValues, selection, selection_args);
        return count;
    }

}

package michaelkim.budgetingandwalletbased;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class categoriesScreen extends Fragment {

    Button addC;
    EditText addField;

    // For the addition to the database.
    Context context;
    categoryDatabase categoryDB;
    SQLiteDatabase sqLiteDatabase;

    // For the listview.
    ListView listOfCategories;
    Cursor cursor;
    categoryListAdapter categoryListAdapter;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getActivity().setTitle("Categories Screen");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_categories_screen, container, false);

        addField = (EditText) view.findViewById(R.id.categoryField);

        addC = (Button) view.findViewById(R.id.addCategory);
        addC.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){

                String categoryName = addField.getText().toString();
                String value = "$0.00";
                String transactions = "";

                categoryDB = new categoryDatabase(context);
                sqLiteDatabase = categoryDB.getWritableDatabase();
                categoryDB.addCategoryInformation(categoryName, value, transactions, sqLiteDatabase);

                Toast.makeText(getActivity(), "Data Created.", Toast.LENGTH_SHORT).show();

                categoryDB.close();

            }
        });

        listOfCategories = (ListView) view.findViewById(R.id.listOfCategories);
        categoryListAdapter = new categoryListAdapter(context, R.layout.category_listview);
        listOfCategories.setAdapter(categoryListAdapter);
        categoryDB = new categoryDatabase(context);
        sqLiteDatabase = categoryDB.getReadableDatabase();
        cursor = categoryDB.getInformations(sqLiteDatabase);
        if (cursor.moveToFirst()){
            do{
                String name = cursor.getString(0);
                String value = cursor.getString(1);
                categoryDataProvider categoryDataProvider = new categoryDataProvider(name, value);

                categoryListAdapter.add(categoryDataProvider);
            }
            while(cursor.moveToNext());
        }

        return view;
    }

}
package michaelkim.budgetingandwalletbased;

import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.InputFilter;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class categoriesScreen extends Fragment {
    // ListView declaration.
    Context context;
    ListView listOfCategories;

    // Database declaration.
    transactionDatabase transactionDatabase;
    categoryDatabase categoryDatabase;
    SQLiteDatabase sqLiteDatabase;
    Cursor cursor;

    // Global ArrayList value.
    ArrayList<Category> categories;
    ArrayList<String> categoryNames;
    ArrayList<Transaction> transactions;

    // Widget declaration.
    Button addC;

    // Mainly used for the toolbar name change.
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Categories Screen");
    }

    // Instantiate the global ArrayLists and populate it if already populated.
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity();

        // Database Information
        categoryDatabase = new categoryDatabase(getActivity().getApplicationContext());
        sqLiteDatabase = categoryDatabase.getReadableDatabase();
        cursor = categoryDatabase.getCategoryInfo(sqLiteDatabase);

        categories = new ArrayList<Category>();
        categoryNames = new ArrayList<String>();
        if (cursor.moveToFirst()){
            do{

                String name, value;
                name = cursor.getString(0);
                value = cursor.getString(1);

                categories.add(new Category(name, Double.parseDouble(value)));
                categoryNames.add(name);

            }
            while(cursor.moveToNext());

            ((globalList) getActivity().getApplication()).setList(categories);
            ((globalList) getActivity().getApplication()).setNames(categoryNames);
        }

        // Retrieve base values from the database.
        if(((globalList) getActivity().getApplication()).getList() == null){
            categories = new ArrayList<Category>();
        }
        else{
            categories = ((globalList) getActivity().getApplication()).getList();
        }

        if (((globalList) getActivity().getApplication()).getNames() == null){
            categoryNames = new ArrayList<String>();
        }
        else{
            categoryNames = ((globalList) getActivity().getApplication()).getNames();
        }

        if (((globalList) getActivity().getApplication()).getTransactions() == null){
            transactions = new ArrayList<Transaction>();
        }
        else{
            transactions = ((globalList) getActivity().getApplication()).getTransactions();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_categories_screen, container, false);

        // View and populate the list of categories.
        listOfCategories = (ListView) view.findViewById(R.id.listOfCategories);
        final categoryAdapter categoryAdapter = new categoryAdapter();
        listOfCategories.setAdapter(categoryAdapter);

        addC = (Button) view.findViewById(R.id.addCategory);
        addC.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                AlertDialog.Builder addDialog = new AlertDialog.Builder(context);
                LayoutInflater inflater = getActivity().getLayoutInflater();

                final View dialogView = inflater.inflate(R.layout.add_category_dialog, null);

                addDialog.setTitle("Add a Category");
                addDialog.setView(dialogView)
                        // When clicking the add category button
                        // Makes sure that the category's name doesn't already exist.
                        // Create a new category with fields:
                        //      Name
                        //      Balance
                        .setPositiveButton("Add", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int whichButton) {

                                EditText nameField = (EditText) dialogView.findViewById(R.id.nameAddDialog);
                                EditText valueField = (EditText) dialogView.findViewById(R.id.valueAddDialog);

                                valueField.setFilters(new InputFilter[] {new DecimalDigitsInputFilter(7,2)});

                                String name = nameField.getText().toString();
                                String value = valueField.getText().toString();
                                if (!categoryNames.contains(name)) {
                                    // Add the category + category name into ArrayLists.
                                    categories.add(new Category(name, Double.parseDouble(value)));
                                    categoryNames.add(name);

                                    // Save the category list.
                                    categoryDatabase = new categoryDatabase(context);
                                    sqLiteDatabase = categoryDatabase.getWritableDatabase();
                                    categoryDatabase.addCategoryInfo(name, value, sqLiteDatabase);
                                    Toast.makeText(context, "Data Saved.", Toast.LENGTH_SHORT).show();
                                    categoryDatabase.close();

                                    // Save the category + category name into the global variables.
                                    ((globalList) getActivity().getApplication()).setList(categories);
                                    ((globalList) getActivity().getApplication()).setNames(categoryNames);

                                    // Update the ListView.
                                    categoryAdapter.notifyDataSetChanged();
                                }
                                else{
                                    Toast.makeText(context, "Category Name already exists.", Toast.LENGTH_SHORT).show();
                                }
                            }
                        })
                        .setNegativeButton("Cancel", null).show();
            }
        });

        return view;
    }

    // Adapter for populating the category ListView with its name and its value.
    // Each category contains a 'Delete Category' button that deletes itself and all the transactions under its name.
    class categoryAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return categoryNames.size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(final int i, View view, ViewGroup viewGroup) {
            view = getActivity().getLayoutInflater().inflate(R.layout.category_listview, null);

            final TextView categoryName = (TextView) view.findViewById(R.id.categoryNameText);
            final TextView categoryValue = (TextView) view.findViewById(R.id.categoryValueText);
            Button deleteCategory = (Button) view.findViewById(R.id.deleteCategory);

            categoryName.setText(categories.get(i).name);
            String value = String.format("%.2f", categories.get(i).value);
            categoryValue.setText("$" + value);

            deleteCategory.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick (View v){
                    int transactionCount = 0;
                    for (int j = 0; j < transactions.size(); j++){
                        if (transactions.get(j).name.equals(categoryNames.get(i))){
                            transactionCount++;
                        }
                    }
                    if (transactionCount > 0) {
                        AlertDialog.Builder deleteConfirmation = new AlertDialog.Builder(context);
                        deleteConfirmation.setTitle("Delete this category?");
                        deleteConfirmation.setMessage("Doing so will delete all transactions under this category.");
                        deleteConfirmation.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int whichButton) {
                                String categoryName = categoryNames.get(i);

                                // Delete any transactions that are from the category.
                                transactionDatabase = new transactionDatabase(context);
                                sqLiteDatabase = transactionDatabase.getReadableDatabase();

                                for (int j = 0; j < transactions.size(); j++){
                                    if (transactions.get(j).name.equals(categoryName)){
                                        transactions.remove(j);
                                        transactionDatabase.deleteTrans(categoryName, sqLiteDatabase);
                                        j--;
                                    }
                                }

                                // Delete category from the database.
                                categoryDatabase = new categoryDatabase(context);
                                sqLiteDatabase = categoryDatabase.getReadableDatabase();
                                categoryDatabase.deleteCategory(categories.get(i).name, sqLiteDatabase);
                                Toast.makeText(context, "Category Deleted", Toast.LENGTH_SHORT).show();

                                categories.remove(i);
                                categoryNames.remove(i);
                                ((globalList) getActivity().getApplication()).setList(categories);
                                ((globalList) getActivity().getApplication()).setNames(categoryNames);
                                notifyDataSetChanged();
                            }
                        });
                        deleteConfirmation.setNegativeButton("Cancel", null).show();
                    }
                    else{
                        // Delete category from the database.
                        categoryDatabase = new categoryDatabase(context);
                        sqLiteDatabase = categoryDatabase.getReadableDatabase();
                        categoryDatabase.deleteCategory(categories.get(i).name, sqLiteDatabase);
                        Toast.makeText(context, "Category Deleted", Toast.LENGTH_SHORT).show();

                        categories.remove(i);
                        categoryNames.remove(i);
                        ((globalList) getActivity().getApplication()).setList(categories);
                        ((globalList) getActivity().getApplication()).setNames(categoryNames);
                        notifyDataSetChanged();
                    }
                }
            });

            return view;
        }
    }

    // Method to make it so that the EditText for balances only accepts up to 2 decimal places.
    public class DecimalDigitsInputFilter implements InputFilter {

        Pattern mPattern;

        public DecimalDigitsInputFilter(int digitsBeforeZero,int digitsAfterZero) {
            mPattern=Pattern.compile("[0-9]{0," + (digitsBeforeZero-1) + "}+((\\.[0-9]{0," + (digitsAfterZero-1) + "})?)||(\\.)?");
        }

        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {

            Matcher matcher=mPattern.matcher(dest);
            if(!matcher.matches())
                return "";
            return null;
        }

    }

}
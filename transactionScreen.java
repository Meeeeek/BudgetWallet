package michaelkim.budgetingandwalletbased;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

public class transactionScreen extends Fragment {

    // Global ArrayList value.
    ArrayList<Category> categories;
    ArrayList<String> categoryNames;
    ArrayList<Transaction> transactions;

    // Database declaration and information retrieval.
    transactionDatabase transactionDatabase;
    SQLiteDatabase sqLiteDatabase;
    Cursor cursor;

    // ArrayList that helps to configure the ListView.
    ArrayList<Transaction> tempTrans;

    // ListView declaration.
    ListView listOfTransactions;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getActivity().setTitle("Transactions Screen");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        transactionDatabase = new transactionDatabase(getActivity().getApplicationContext());
        sqLiteDatabase = transactionDatabase.getReadableDatabase();
        cursor = transactionDatabase.getTransInfo(sqLiteDatabase);

        transactions = new ArrayList<>();
        if (cursor.moveToFirst()){
            do{

                String name, value, location, date;
                name = cursor.getString(0);
                value = cursor.getString(1);
                location = cursor.getString(2);
                date = cursor.getString(3);

                transactions.add(new Transaction(name, value, location, date));

            }
            while(cursor.moveToNext());

            ((globalList) getActivity().getApplication()).setTransactions(transactions);
        }

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

        View view = inflater.inflate(R.layout.fragment_transaction_screen, container, false);

        // ArrayList that helps to configure the ListView instantiation.
        tempTrans = new ArrayList<Transaction>(transactions);

        // View and populate the list of transactions.
        listOfTransactions = (ListView) view.findViewById(R.id.listOfTransactions);
        final transAdapter transAdapter = new transAdapter();
        listOfTransactions.setAdapter(transAdapter);

        // Instantiate the category spinner and populate it with its category names.
        final Spinner categorySpinner = (Spinner) view.findViewById(R.id.categorySpinner);
        final ArrayList<String> withTotal = new ArrayList<String>();
        withTotal.add("All Transactions");
        withTotal.addAll(categoryNames);
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, withTotal);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        categorySpinner.setAdapter(spinnerAdapter);

        // Whenever another item in the spinner is selected,
        //   Show the transactions based on the name on the spinner.
        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                tempTrans = new ArrayList<Transaction>(transactions);
                if (!categorySpinner.getSelectedItem().toString().equals("All Transactions")){
                    for (int i = 0; i < tempTrans.size(); i++) {
                        if (!tempTrans.get(i).name.equals(categorySpinner.getItemAtPosition(position).toString())) {
                            Log.i("Er", tempTrans.get(i).name);
                            tempTrans.remove(i);
                            i--;
                        }
                    }
                }
                transAdapter.notifyDataSetChanged();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }

        });

        return view;

    }

    // Adapter for populating the transaction ListView with its name, location, value, and date.
    class transAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return tempTrans.size();
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
        public View getView(int i, View view, ViewGroup viewGroup) {
            view = getActivity().getLayoutInflater().inflate(R.layout.transaction_listview, null);

            TextView categoryName = (TextView) view.findViewById(R.id.categoryTrans);
            TextView locationName = (TextView) view.findViewById(R.id.locationTrans);
            TextView categoryValue = (TextView) view.findViewById(R.id.valueTrans);
            TextView date = (TextView) view.findViewById(R.id.dateTrans);

            categoryName.setText(tempTrans.get(i).name);
            locationName.setText(tempTrans.get(i).location);
            categoryValue.setText(tempTrans.get(i).value);
            date.setText(tempTrans.get(i).date);

            return view;
        }
    }

}
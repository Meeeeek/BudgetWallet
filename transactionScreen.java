package michaelkim.budgetingandwalletbased;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

    ListView listOfTransactions;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getActivity().setTitle("Transactions Screen");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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

        listOfTransactions = (ListView) view.findViewById(R.id.listOfTransactions);
        final transAdapter transAdapter = new transAdapter();
        listOfTransactions.setAdapter(transAdapter);

        Spinner categorySpinner = (Spinner) view.findViewById(R.id.categorySpinner);
        final ArrayList<String> withTotal = new ArrayList<String>();
        withTotal.add("Total");
        withTotal.addAll(categoryNames);
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, withTotal);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        categorySpinner.setAdapter(spinnerAdapter);

        return view;

    }

    class transAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return transactions.size();
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

            categoryName.setText(transactions.get(i).name);
            locationName.setText(transactions.get(i).location);
            categoryValue.setText(transactions.get(i).value);
            date.setText(transactions.get(i).date);

            return view;
        }
    }

}
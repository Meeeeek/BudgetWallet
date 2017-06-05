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

public class statisticsScreen extends Fragment {

    // Global variable declaration.
    ArrayList<String> categoryNames;
    ArrayList<Category> categories;
    ArrayList<Transaction> transactions;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getActivity().setTitle("Statistics Screen");
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (((globalList) getActivity().getApplication()).getNames() == null){
            categoryNames = new ArrayList<String>();
        }
        else{
            categoryNames = ((globalList) getActivity().getApplication()).getNames();
        }

        if(((globalList) getActivity().getApplication()).getList() == null){
            categories = new ArrayList<Category>();
        }
        else{
            categories = ((globalList) getActivity().getApplication()).getList();
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

        View view = inflater.inflate(R.layout.fragment_statistics_screen, container, false);

        // View and populate the list of statistics.
        ListView listOfStatistics = (ListView) view.findViewById(R.id.listOfStatistics);
        statisticsAdapter statsAdapter = new statisticsAdapter();
        listOfStatistics.setAdapter(statsAdapter);

        // Instantiate the category spinner and populate it with its category names.
        Spinner categorySpinner = (Spinner) view.findViewById(R.id.categorySpinner);
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, categoryNames);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        categorySpinner.setAdapter(spinnerAdapter);

        return view;

    }

    // Adapter for populating the statistics ListView with its total values spent in each location.
    class statisticsAdapter extends BaseAdapter {

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
            view = getActivity().getLayoutInflater().inflate(R.layout.statistics_listview, null);

            final TextView categoryName = (TextView) view.findViewById(R.id.moneySpentStats);
            final TextView categoryValue = (TextView) view.findViewById(R.id.$spentStats);

            categoryName.setText("Money Spent in Category " + categories.get(i).name + " : ");

            double totalSpent = 0;


            for (int j = 0; j < transactions.size(); j++){
                if (transactions.get(j).name.equals(categories.get(i).name) && transactions.get(j).value.charAt(0) == '-'){
                    double value = Double.parseDouble(transactions.get(j).value.substring(3));
                    totalSpent = totalSpent + value;
                }
            }

            categoryValue.setText("- $" + Double.toString(totalSpent));

            return view;
        }
    }

}
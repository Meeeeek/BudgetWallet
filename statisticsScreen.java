package michaelkim.budgetingandwalletbased;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;

public class statisticsScreen extends Fragment {

    // Global variable declaration.
    ArrayList<String> categoryNames;
    ArrayList<Category> categories;
    ArrayList<Transaction> transactions;

    // Decimal format for viewing values in proper monetary format.
    DecimalFormat df = new DecimalFormat("#.00");

    // Random statistics declaration.
    TextView mss;
    TextView fvs;
    TextView ltas;
    TextView lts;

    // General statistics declaration.
    HashMap<String, Double> hashMap = new HashMap<String, Double>();
    ArrayList<Category> sampStats = new ArrayList<Category>();

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

        // Populating all the values of the HashMap and ArrayList for the generic statistics.
        for (int j = 0; j < transactions.size(); j ++){
            if (hashMap.containsKey(transactions.get(j).location) && transactions.get(j).value.charAt(0) == '-'){
                double currValue = hashMap.get(transactions.get(j).location);
                hashMap.put(transactions.get(j).location, currValue + Double.parseDouble(transactions.get(j).value.substring(3)));
                Log.e("VALUE", transactions.get(j).value);
            }
            else if (transactions.get(j).value.charAt(0) == '-' && !hashMap.containsKey(transactions.get(j).location)){
                hashMap.put(transactions.get(j).location, Double.parseDouble(transactions.get(j).value.substring(3)));
                Log.e("VALUE", transactions.get(j).value);
            }
        }

        for (HashMap.Entry<String, Double> entry : hashMap.entrySet()) {
            sampStats.add(new Category(entry.getKey(), entry.getValue()));
        }

        // View and populate the list of statistics.
        ListView listOfStatistics = (ListView) view.findViewById(R.id.listOfStatistics);
        statisticsAdapter statsAdapter = new statisticsAdapter();
        listOfStatistics.setAdapter(statsAdapter);

        // Provide values for random statistics for all categories.
        // Total Money Spent Statistic.
        double totalSpent = 0;
        for (int i = 0; i < transactions.size(); i ++){
            if (transactions.get(i).value.charAt(0) == '-') {
                totalSpent = totalSpent + Double.parseDouble(transactions.get(i).value.substring(3));
            }
        }
        mss = (TextView) view.findViewById(R.id.mSS);
        mss.setText("$" + String.format("%.2f", totalSpent));;

        // Most Visited Location Statistic.
        String highestLocation = "";
        int highestLocationValue = 0;
        HashMap<String, Integer> map = new HashMap<String, Integer>();
        for (int i = 0; i < transactions.size(); i ++){
            if (map.containsKey(transactions.get(i).location)){
                int currValue = map.get(transactions.get(i).location);
                map.put(transactions.get(i).location, currValue + 1);
                if (map.get(transactions.get(i).location) > highestLocationValue){
                    highestLocation = transactions.get(i).location;
                    highestLocationValue = map.get(transactions.get(i).location);
                }
            }
            else {
                map.put(transactions.get(i).location, 1);
                if (map.get(transactions.get(i).location) > highestLocationValue){
                    highestLocation = transactions.get(i).location;
                    highestLocationValue = map.get(transactions.get(i).location);
                }
            }
        }
        fvs = (TextView) view.findViewById(R.id.fVS);
        fvs.setText(highestLocation + " visited " + highestLocationValue + " times.");

        // Largest Sum Added Statistic.
        double largestAdded = 0;
        for (int i = 0; i < transactions.size(); i ++){
            if (transactions.get(i).value.charAt(0) == '+') {
                if (Double.parseDouble(transactions.get(i).value.substring(3)) > largestAdded) {
                    largestAdded = Double.parseDouble(transactions.get(i).value.substring(3));
                }
            }
        }
        ltas = (TextView) view.findViewById(R.id.lTAS);
        ltas.setText("+ $" + String.format("%.2f", largestAdded));;

        // Largest Sum Subtracted Statistic.
        double largestSubtracted = 0;
        for (int i = 0; i < transactions.size(); i ++){
            if (transactions.get(i).value.charAt(0) == '-') {
                if (Double.parseDouble(transactions.get(i).value.substring(3)) > largestSubtracted) {
                    largestSubtracted = Double.parseDouble(transactions.get(i).value.substring(3));
                }
            }
        }
        lts = (TextView) view.findViewById(R.id.lTS);
        lts.setText("- $" + String.format("%.2f", largestSubtracted));

        // Instantiate the category spinner and populate it with its category names.
        final Spinner categorySpinner = (Spinner) view.findViewById(R.id.categorySpinner);
        final ArrayList<String> withTotal = new ArrayList<String>();
        withTotal.add("All Categories");
        withTotal.addAll(categoryNames);
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, withTotal);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        categorySpinner.setAdapter(spinnerAdapter);

        return view;

    }

    // Adapter for populating the statistics ListView with its total values spent in each location.
    class statisticsAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return sampStats.size();
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

            // Create a HashMap that stores all the values of :
            //    The Locations
            //    Total Amount of Money Spent at Each

            categoryName.setText("Money spent at " + sampStats.get(i).name);
            categoryValue.setText(String.format("%.2f", sampStats.get(i).value));

            return view;
        }
    }

}
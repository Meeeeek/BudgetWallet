package michaelkim.budgetingandwalletbased;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.InputFilter;
import android.text.Spanned;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class homeScreen extends Fragment {

    // Widget declaration.
    Spinner categorySpinner;
    Button addV, subV;
    EditText inputV, locationS;
    TextView totalValue;

    // Information to grab the current date for transactions.
    Calendar calendar = Calendar.getInstance();
    SimpleDateFormat df = new SimpleDateFormat("MM/dd/yyyy");

    // Global variable declaration.
    ArrayList<Category> categories;
    ArrayList<String> categoryNames;
    ArrayList<Transaction> transactions;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getActivity().setTitle("Home Screen");
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

        View view = inflater.inflate(R.layout.fragment_home_screen, container, false);

        // Widget instantiation.
        inputV = (EditText) view.findViewById(R.id.inputValue);
        locationS = (EditText) view.findViewById(R.id.locationSpent);
        totalValue = (TextView) view.findViewById(R.id.value);

        // Set the filter so that the EditText's input to 2 decimal places.
        inputV.setFilters(new InputFilter[] {new DecimalDigitsInputFilter(7,2)});

        // Instantiate the category spinner and populate it with its category names.
        categorySpinner = (Spinner) view.findViewById(R.id.categorySpinner);
        final ArrayList<String> withTotal = new ArrayList<String>();
        withTotal.add("Total");
        withTotal.addAll(categoryNames);
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, withTotal);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        categorySpinner.setAdapter(spinnerAdapter);

        // Whenever another item in the spinner is selected,
        //   Show their values on the top.
        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                if (withTotal.get(position).equals("Total") && withTotal.size() != 1){
                    double total = 0;
                    for (int i = 0; i < categories.size(); i++){
                        total = total + categories.get(i).value;
                    }
                    totalValue.setText("$" + String.format("%.2f", total));
                }
                else if (withTotal.size() == 1){
                }
                else {
                    String value = String.format("%.2f", categories.get(position-1).value);
                    totalValue.setText("$" + value);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
            }

        });

        // When the 'Add Value' button is pressed,
        // Makes sure all fields are entered properly.
        // Add and update:
        //    Category (Name, Value)
        //    Transaction (Name, Value, Location, Date)
        //    Change the overhead value.
        addV = (Button) view.findViewById(R.id.addValue);
        addV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int selectedPosition = categorySpinner.getSelectedItemPosition()-1;
                double insertedValue = Double.parseDouble(inputV.getText().toString());

                if (categorySpinner.getSelectedItem().toString().equals("Total") || inputV.getText().toString().equals("") || locationS.getText().toString().equals("")){
                    Toast.makeText(getContext(), "All fields must be entered.", Toast.LENGTH_SHORT).show();
                }
                else{
                    categories.get(selectedPosition).value = categories.get(selectedPosition).value + insertedValue;
                    transactions.add(new Transaction(categories.get(selectedPosition).name, "+ $" + inputV.getText().toString(), locationS.getText().toString(), df.format(calendar.getTime())));
                    ((globalList) getActivity().getApplication()).setList(categories);
                    ((globalList) getActivity().getApplication()).setTransactions(transactions);
                    inputV.setText("");
                    locationS.setText("");
                    totalValue.setText("$" + String.format("%.2f", categories.get(selectedPosition).value));
                }
            }
        });

        // Similar to 'Add Value'
        subV = (Button) view.findViewById(R.id.subtractValue);
        subV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int selectedPosition = categorySpinner.getSelectedItemPosition()-1;
                double insertedValue = Double.parseDouble(inputV.getText().toString());

                if (categorySpinner.getSelectedItem().toString().equals("Total") || inputV.getText().toString().equals("") || locationS.getText().toString().equals("")){
                    Toast.makeText(getContext(), "All fields must be entered.", Toast.LENGTH_SHORT).show();
                }
                else{
                    categories.get(selectedPosition).value = categories.get(selectedPosition).value - insertedValue;
                    transactions.add(new Transaction(categories.get(selectedPosition).name, "- $" + inputV.getText().toString(), locationS.getText().toString(), df.format(calendar.getTime())));
                    ((globalList) getActivity().getApplication()).setList(categories);
                    ((globalList) getActivity().getApplication()).setTransactions(transactions);
                    inputV.setText("");
                    locationS.setText("");
                    totalValue.setText("$" + String.format("%.2f", categories.get(selectedPosition).value));
                }
            }
        });

        return view;
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
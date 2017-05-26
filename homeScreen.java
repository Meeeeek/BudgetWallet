package michaelkim.budgetingandwalletbased;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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

import java.util.ArrayList;

public class homeScreen extends Fragment {

    Spinner categorySpinner;
    Button addV, subV;
    EditText inputV, locationS;
    TextView totalValue;

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

        inputV = (EditText) view.findViewById(R.id.inputValue);
        locationS = (EditText) view.findViewById(R.id.locationSpent);
        totalValue = (TextView) view.findViewById(R.id.value);

        categorySpinner = (Spinner) view.findViewById(R.id.categorySpinner);
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, categoryNames);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        categorySpinner.setAdapter(spinnerAdapter);

        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                totalValue.setText("$" + Double.toString(categories.get(position).value));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });

        addV = (Button) view.findViewById(R.id.addValue);
        addV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int selectedPosition = categorySpinner.getSelectedItemPosition();
                double insertedValue = Double.parseDouble(inputV.getText().toString());

                if (categorySpinner.getSelectedItem().toString().equals("All") || inputV.getText().toString().equals("") || locationS.getText().toString().equals("")){
                    Toast.makeText(getContext(), "All fields must be entered.", Toast.LENGTH_SHORT).show();
                }
                else{
                    categories.get(selectedPosition).value = categories.get(selectedPosition).value + insertedValue;
                    transactions.add(new Transaction(categories.get(selectedPosition).name, "+ $" + inputV.getText().toString(), locationS.getText().toString()));
                    ((globalList) getActivity().getApplication()).setList(categories);
                    ((globalList) getActivity().getApplication()).setTransactions(transactions);
                    inputV.setText("");
                    locationS.setText("");
                }
            }
        });

        subV = (Button) view.findViewById(R.id.subtractValue);
        subV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int selectedPosition = categorySpinner.getSelectedItemPosition();
                double insertedValue = Double.parseDouble(inputV.getText().toString());

                if (categorySpinner.getSelectedItem().toString().equals("All") || inputV.getText().toString().equals("") || locationS.getText().toString().equals("")){
                    Toast.makeText(getContext(), "All fields must be entered.", Toast.LENGTH_SHORT).show();
                }
                else{
                    categories.get(selectedPosition).value = categories.get(selectedPosition).value - insertedValue;
                    transactions.add(new Transaction(categories.get(selectedPosition).name, "- $" + inputV.getText().toString(), locationS.getText().toString()));
                    ((globalList) getActivity().getApplication()).setList(categories);
                    ((globalList) getActivity().getApplication()).setTransactions(transactions);
                    inputV.setText("");
                    locationS.setText("");
                }
            }
        });

        return view;
    }

}
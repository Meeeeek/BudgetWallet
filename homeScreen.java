package michaelkim.budgetingandwalletbased;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class homeScreen extends Fragment {

    Spinner categorySpinner;
    Button addV, subV;
    EditText inputV, locationS;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getActivity().setTitle("Home Screen");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home_screen, container, false);

        String[] categories = {"All", "Chase", "Acorns", "Cash"};

        categorySpinner = (Spinner) view.findViewById(R.id.categorySpinner);
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, categories);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        categorySpinner.setAdapter(spinnerAdapter);

        inputV = (EditText) view.findViewById(R.id.inputValue);
        locationS = (EditText) view.findViewById(R.id.locationSpent);

        addV = (Button) view.findViewById(R.id.addValue);
        addV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (categorySpinner.getSelectedItem().toString().equals("All") || inputV.getText().toString().equals("") || locationS.getText().toString().equals("")){
                    Toast.makeText(getContext(), "All fields must be entered.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        subV = (Button) view.findViewById(R.id.subtractValue);
        subV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (categorySpinner.getSelectedItem().toString().equals("All") || inputV.getText().toString().equals("") || locationS.getText().toString().equals("")){
                    Toast.makeText(getContext(), "All fields must be entered.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }

}
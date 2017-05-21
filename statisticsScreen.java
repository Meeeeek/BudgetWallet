package michaelkim.budgetingandwalletbased;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

public class statisticsScreen extends Fragment {

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getActivity().setTitle("Statistics Screen");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_statistics_screen, container, false);

        String[] categories = {"All", "Chase", "Acorns", "Cash"};

        ListView listOfStatistics = (ListView) view.findViewById(R.id.listOfStatistics);
        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, categories);
        listOfStatistics.setAdapter(categoryAdapter);

        Spinner categorySpinner = (Spinner) view.findViewById(R.id.categorySpinner);
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, categories);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        categorySpinner.setAdapter(spinnerAdapter);

        return view;

    }

}
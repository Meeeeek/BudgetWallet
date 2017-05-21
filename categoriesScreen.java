package michaelkim.budgetingandwalletbased;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class categoriesScreen extends Fragment {

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getActivity().setTitle("Categories Screen");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_categories_screen, container, false);

        String[] categories = {"All", "Chase", "Acorns", "Cash"};

        ListView listOfCategories = (ListView) view.findViewById(R.id.listOfCategories);
        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, categories);
        listOfCategories.setAdapter(categoryAdapter);

        return view;
    }

}
package michaelkim.budgetingandwalletbased;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class categoriesScreen extends Fragment {

    // Global ArrayList value.
    ArrayList<Category> categories;
    ArrayList<String> categoryNames;
    ArrayList<Transaction> transactions;

    DecimalFormat df = new DecimalFormat("#.00");

    // Widget declaration.
    Button addC;
    EditText addField;

    // For the listview.
    Context context;
    ListView listOfCategories;

    // Mainly used for the toolbar name change.
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Categories Screen");
    }

    // Instantiate the global ArrayList and populate it if already populated.
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getActivity();
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

    // Instantiate widgets.
    // Show the custom list view.
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_categories_screen, container, false);

        addField = (EditText) view.findViewById(R.id.categoryField);

        listOfCategories = (ListView) view.findViewById(R.id.listOfCategories);
        final categoryAdapter categoryAdapter = new categoryAdapter();
        listOfCategories.setAdapter(categoryAdapter);

        addC = (Button) view.findViewById(R.id.addCategory);
        addC.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                String name = addField.getText().toString();
                if (!categoryNames.contains(name)) {
                    categories.add(new Category(name, 0.00));
                    categoryNames.add(name);
                    ((globalList) getActivity().getApplication()).setList(categories);
                    ((globalList) getActivity().getApplication()).setNames(categoryNames);
                    categoryAdapter.notifyDataSetChanged();
                    addField.setText("");
                }
                else{
                    Toast.makeText(context, "Category Name already exists.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }

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

            TextView categoryName = (TextView) view.findViewById(R.id.categoryNameText);
            final TextView categoryValue = (TextView) view.findViewById(R.id.categoryValueText);
            Button deleteCategory = (Button) view.findViewById(R.id.deleteCategory);

            categoryName.setText(categories.get(i).name);
            String value = String.format("%.2f", categories.get(i).value);
            categoryValue.setText("$" + value);

            deleteCategory.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick (View v){
                    for (int j = 0; j < transactions.size(); j++){
                        if (transactions.get(j).name.equals(categoryNames.get(i))){
                            transactions.remove(j);
                            j--;
                        }
                    }
                    categories.remove(i);
                    categoryNames.remove(i);
                    notifyDataSetChanged();
                }
            });

            return view;
        }
    }


}
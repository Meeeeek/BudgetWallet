package michaelkim.budgetingandwalletbased;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Michael Kim on 5/24/2017.
 */

public class categoryListAdapter extends ArrayAdapter{

    List list = new ArrayList();

    public categoryListAdapter(@NonNull Context context, @LayoutRes int resource) {
        super(context, resource);
    }

    static class LayoutHandler{
        TextView categoryName, categoryValue;
    }

    @Override
    public void add(@Nullable Object object) {
        super.add(object);
        list.add(object);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Nullable
    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View row = convertView;
        LayoutHandler layoutHandler;
        if (row == null){
            LayoutInflater layoutInflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = layoutInflater.inflate(R.layout.category_listview, parent, false);
            layoutHandler = new LayoutHandler();
            layoutHandler.categoryName = (TextView) row.findViewById(R.id.categoryNameText);
            layoutHandler.categoryValue = (TextView) row.findViewById(R.id.categoryValueText);
            row.setTag(layoutHandler);
        }
        else{
            layoutHandler = (LayoutHandler) row.getTag();
        }
        categoryDataProvider categoryDataProvider = (categoryDataProvider) this.getItem(position);
        layoutHandler.categoryName.setText(categoryDataProvider.getCategoryName());
        layoutHandler.categoryValue.setText(categoryDataProvider.getCategoryValue());

        return row;
    }
}

package michaelkim.budgetingandwalletbased;

import android.app.Application;

import java.util.ArrayList;

/**
 * Created by Michael Kim on 5/24/2017.
 */

public class globalList extends Application {

    private ArrayList<Category> categories;
    private ArrayList<String> names;
    private ArrayList<Transaction> transactions;

    public ArrayList<Category> getList(){
        return categories;
    }

    public ArrayList<String> getNames() {
        return names;
    }

    public void setList (ArrayList<Category> categories){
        this.categories = categories;
    }

    public void setNames (ArrayList<String> names){
        this.names = names;
    }

    public ArrayList<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(ArrayList<Transaction> transactions) {
        this.transactions = transactions;
    }
}

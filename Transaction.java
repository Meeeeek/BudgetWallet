package michaelkim.budgetingandwalletbased;

/**
 * Created by Michael Kim on 5/24/2017.
 */

public class Transaction {

    String name;
    String value;
    String location;
    String date;

    public Transaction (String n, String v, String l, String d) {

        this.name = n;
        this.value = v;
        this.location = l;
        this.date = d;

    }

}

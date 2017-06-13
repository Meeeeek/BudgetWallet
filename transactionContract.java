package michaelkim.budgetingandwalletbased;

/**
 * Created by Michael Kim on 6/9/2017.
 */

public class transactionContract {

    public static abstract class NewTransaction{

        public static final String NAME = "transaction_name";
        public static final String VALUE = "transaction_value";
        public static final String LOCATION = "transaction_location";
        public static final String DATE = "transaction_date";
        public static final String TABLE_NAME = "transaction_info";

    }

}

package michaelkim.budgetingandwalletbased;

/**
 * Created by Michael Kim on 5/24/2017.
 */

public class categoryDataProvider {

    private String categoryName;
    private String categoryValue;

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryValue() {
        return categoryValue;
    }

    public void setCategoryValue(String categoryValue) {
        this.categoryValue = categoryValue;
    }


    public categoryDataProvider(String categoryName, String categoryValue){

        this.categoryName = categoryName;
        this.categoryValue = categoryValue;

    }

}

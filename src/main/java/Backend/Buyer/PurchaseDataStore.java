package Backend.Buyer;

import java.util.ArrayList;
import java.util.List;

public class PurchaseDataStore {
    private static final List<Purchase> purchases = new ArrayList<>();

    public static void addPurchase(Purchase purchase) {
        purchases.add(purchase);
    }

    public static List<Purchase> getAllPurchases() {
        return purchases;
    }

}

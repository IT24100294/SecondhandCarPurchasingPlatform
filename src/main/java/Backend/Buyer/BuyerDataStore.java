package Backend.Buyer;

public class BuyerDataStore {
    private static Buyer[] buyers = new Buyer[10]; // Initial capacity
    private static int size = 0;

    public static void addBuyer(Buyer buyer) {
        if (size == buyers.length) {
            // Resize array
            Buyer[] newBuyers = new Buyer[buyers.length * 2];
            for (int i = 0; i < buyers.length; i++) {
                newBuyers[i] = buyers[i];
            }
            buyers = newBuyers;
        }
        buyers[size++] = buyer;
    }

    public static Buyer[] getBuyers() {
        // Return a copy with only the filled elements
        Buyer[] result = new Buyer[size];
        for (int i = 0; i < size; i++) {
            result[i] = buyers[i];
        }
        return result;
    }
}


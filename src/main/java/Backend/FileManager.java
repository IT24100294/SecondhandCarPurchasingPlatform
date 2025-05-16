package Backend;

import java.io.*;

public class FileManager {
    private static final String USERS_FILE = "users.txt";
    private static final String LISTINGS_FILE = "listings.txt";
    private static final String PURCHASES_FILE = "purchases.txt";

    // --- USERS ---
    public static UserLinkedList loadUsers() {
        UserLinkedList list = new UserLinkedList();
        try (BufferedReader br = new BufferedReader(new FileReader(USERS_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] p = line.split(",");
                if (p.length >= 5)
                    list.add(new UserNode(p[0], p[1], p[2], p[3], p[4]));
            }
        } catch (IOException e) { /* ignore if file not found */ }
        return list;
    }
    public static void saveUsers(UserLinkedList list) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(USERS_FILE))) {
            for (UserNode u : list.getAll())
                bw.write(u.id+","+u.username+","+u.email+","+u.password+","+u.role+"\n");
        } catch (IOException e) { e.printStackTrace(); }
    }

    // --- LISTINGS ---
    public static ListingLinkedList loadListings() {
        ListingLinkedList list = new ListingLinkedList();
        try (BufferedReader br = new BufferedReader(new FileReader(LISTINGS_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] p = line.split(",");
                if (p.length >= 7)
                    list.add(new ListingNode(p[0], p[1], p[2], p[3], Integer.parseInt(p[4]), Double.parseDouble(p[5]), p[6]));
            }
        } catch (IOException e) { /* ignore if file not found */ }
        return list;
    }
    public static void saveListings(ListingLinkedList list) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(LISTINGS_FILE))) {
            for (ListingNode l : list.getAll())
                bw.write(l.id+","+l.sellerId+","+l.make+","+l.model+","+l.year+","+l.price+","+l.status+"\n");
        } catch (IOException e) { e.printStackTrace(); }
    }

    // --- PURCHASES ---
    public static PurchaseLinkedList loadPurchases() {
        PurchaseLinkedList list = new PurchaseLinkedList();
        try (BufferedReader br = new BufferedReader(new FileReader(PURCHASES_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] p = line.split(",");
                if (p.length >= 5)
                    list.add(new PurchaseNode(p[0], p[1], p[2], p[3], p[4]));
            }
        } catch (IOException e) { /* ignore if file not found */ }
        return list;
    }
    public static void savePurchases(PurchaseLinkedList list) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(PURCHASES_FILE))) {
            for (PurchaseNode p : list.getAll())
                bw.write(p.id+","+p.buyerId+","+p.listingId+","+p.date+","+p.status+"\n");
        } catch (IOException e) { e.printStackTrace(); }
    }
}

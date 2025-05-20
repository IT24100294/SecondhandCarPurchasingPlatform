package Backend.Buyer;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Buyer extends AbstractUser implements User {
    private static Buyer[] buyers = new Buyer[10];
    private static int size = 0;
    private LocalDateTime timestamp;
    private static int count = 1;
    private int id;

    public Buyer(String name, String email, String phone) {
        super(name, email, phone);
        this.timestamp = LocalDateTime.now();
        this.id = count++;
        if (!isDuplicate(email)) {
            addBuyer(this);
        }
    }

    private boolean isDuplicate(String email) {
        for (int i = 0; i < size; i++) {
            if (buyers[i].getEmail().equals(email)) {
                return true;
            }
        }
        return false;
    }

    private void addBuyer(Buyer buyer) {
        if (size == buyers.length) {
            Buyer[] newBuyers = new Buyer[buyers.length * 2];
            for (int i = 0; i < buyers.length; i++) {
                newBuyers[i] = buyers[i];
            }
            buyers = newBuyers;
        }
        buyers[size++] = buyer;
    }

    public static Buyer[] getAllBuyers() {
        Buyer[] result = new Buyer[size];
        for (int i = 0; i < size; i++) {
            result[i] = buyers[i];
        }
        return result;
    }

    public String getFormattedTimestamp() {
        return timestamp.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    @Override
    public int getId() { return id; }
    @Override
    public String getName() { return super.getName(); }
    @Override
    public String getEmail() { return super.getEmail(); }
    @Override
    public String getPhone() { return super.getPhone(); }

    public void setId(int id) { this.id = id; }
    
    public void updateBuyer(String name, String email, String phone) {
        super.setName(name);
        super.setEmail(email);
        super.setPhone(phone);
        this.timestamp = LocalDateTime.now();
    }
}
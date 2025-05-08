package Backend.Buyer;

public class Buyer {
    private static int count = 1;
    private int id;
    private String name;
    private String email;
    private String phone;


    public Buyer(String name, String email, String phone) {
        this.id = count++;
        this.name = name;
        this.email = email;
        this.phone = phone;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getPhone() { return phone; }
}
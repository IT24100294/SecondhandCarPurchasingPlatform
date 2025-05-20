package Backend.Buyer;

public abstract class AbstractUser {
    protected static int count = 1;
    protected int id;
    protected String name;
    protected String email;
    protected String phone;

    public AbstractUser(String name, String email, String phone) {
        this.id = count++;
        this.name = name;
        this.email = email;
        this.phone = phone;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getPhone() { return phone; }

    public void setName(String name) { this.name = name; }
    public void setEmail(String email) { this.email = email; }
    public void setPhone(String phone) { this.phone = phone; }
}
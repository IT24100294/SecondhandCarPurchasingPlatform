package Backend.Buyer;

import java.util.Date;

public class Purchase {
    private int buyerId;
    private String buyerName;
    private String buyerPhone;
    private String buyerEmail;
    private String carType;
    private String carPrice;
    private Date purchaseDate;

    public Purchase(int buyerId, String buyerName, String buyerPhone, String buyerEmail, String carType, String carPrice, Date purchaseDate) {
        this.buyerId = buyerId;
        this.buyerName = buyerName;
        this.buyerPhone = buyerPhone;
        this.buyerEmail = buyerEmail;
        this.carType = carType;
        this.carPrice = carPrice;
        this.purchaseDate = purchaseDate;
    }

    public int getBuyerId() { return buyerId; }
    public String getBuyerName() { return buyerName; }
    public String getBuyerPhone() { return buyerPhone; }
    public String getBuyerEmail() { return buyerEmail; }
    public String getCarType() { return carType; }
    public String getCarPrice() { return carPrice; }
    public Date getPurchaseDate() { return purchaseDate; }
}

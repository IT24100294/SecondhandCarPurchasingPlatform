package Backend.Buyer;

import java.util.Date;

public class Purchase {
    private int buyerId;
    private String buyerName;
    private String buyerPhone;
    private String buyerEmail;
    private String carType;
    private Date purchaseDate;

    public Purchase(int buyerId, String buyerName, String buyerPhone, String buyerEmail, String carType, Date purchaseDate) {
        this.buyerId = buyerId;
        this.buyerName = buyerName;
        this.buyerPhone = buyerPhone;
        this.buyerEmail = buyerEmail;
        this.carType = carType;
        this.purchaseDate = purchaseDate;
    }

    public Date getPurchaseDate() {
        return purchaseDate;
    }

}

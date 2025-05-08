// File: src/main/java/Backend/Buyer/PurchaseRequest.java
package Backend.Buyer;

import java.time.LocalDate;

public class PurchaseRequest {
    private String buyerName;
    private String buyerPhone;
    private String carId;
    private String carModel;
    private LocalDate requestDate;

    public PurchaseRequest(String buyerName, String buyerPhone, String carId, String carModel, LocalDate requestDate) {
        this.buyerName = buyerName;
        this.buyerPhone = buyerPhone;
        this.carId = carId;
        this.carModel = carModel;
        this.requestDate = requestDate;
    }

    public String getBuyerName() {
        return buyerName;
    }

    public String getBuyerPhone() {
        return buyerPhone;
    }

    public String getCarId() {
        return carId;
    }

    public String getCarModel() {
        return carModel;
    }

    public LocalDate getRequestDate() {
        return requestDate;
    }
}

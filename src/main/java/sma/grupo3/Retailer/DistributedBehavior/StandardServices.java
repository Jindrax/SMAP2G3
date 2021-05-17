package sma.grupo3.Retailer.DistributedBehavior;

public enum StandardServices {
    WAREHOUSE("Warehouse"),
    TRANSPORTER("Transporter"),
    CUSTOMER("Customer");

    public final String value;

    StandardServices(String value) {
        this.value = value;
    }
}

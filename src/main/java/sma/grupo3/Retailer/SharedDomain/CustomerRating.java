package sma.grupo3.Retailer.SharedDomain;

public class CustomerRating {
    private final String customerAlias;
    private final CustomerOrder customerOrder;
    private final double customerSatisfaction;

    public CustomerRating(String customerAlias, CustomerOrder customerOrder, double customerSatisfaction) {
        this.customerAlias = customerAlias;
        this.customerOrder = customerOrder;
        this.customerSatisfaction = customerSatisfaction;
    }

    public String getCustomerAlias() {
        return customerAlias;
    }

    public CustomerOrder getCustomerOrder() {
        return customerOrder;
    }

    public double getCustomerSatisfaction() {
        return customerSatisfaction;
    }
}

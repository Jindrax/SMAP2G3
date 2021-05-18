package sma.grupo3.Retailer.Agents.Customer.Data;

import BESA.Kernel.Agent.Event.DataBESA;
import sma.grupo3.Retailer.SharedDomain.CustomerOrder;

public class CustomerOrderWrapper extends DataBESA {
    CustomerOrder order;
    String customerId;

    public CustomerOrderWrapper(CustomerOrder order, String customerId) {
        this.order = order;
        this.customerId = customerId;
    }

    public CustomerOrder getOrder() {
        return order;
    }

    public String getCustomerId() {
        return customerId;
    }
}

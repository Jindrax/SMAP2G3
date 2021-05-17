package sma.grupo3.Retailer.SharedDomain;

import BESA.Kernel.Agent.Event.DataBESA;
import sma.grupo3.Retailer.DistributedBehavior.Localities;

public class CustomerOrder extends DataBESA {
    Localities customerLocality;
    String customerAlias;
    Catalog orderProduct;
    int orderQuantity;
    long createdAt;
    OrderState orderState;

    public CustomerOrder(Localities customerLocality, String customerAlias, Catalog orderProduct, int orderQuantity) {
        this.customerLocality = customerLocality;
        this.customerAlias = customerAlias;
        this.orderProduct = orderProduct;
        this.orderQuantity = orderQuantity;
        this.createdAt = System.currentTimeMillis();
        this.orderState = OrderState.ISSUED;
    }

    public Localities getCustomerLocality() {
        return customerLocality;
    }

    public String getCustomerAlias() {
        return customerAlias;
    }

    public Catalog getOrderProduct() {
        return orderProduct;
    }

    public int getOrderQuantity() {
        return orderQuantity;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public OrderState getOrderState() {
        return orderState;
    }

    public void setOrderState(OrderState orderState) {
        this.orderState = orderState;
    }
}

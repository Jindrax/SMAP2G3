package sma.grupo3.Retailer.SharedDomain;

import BESA.Kernel.Agent.Event.DataBESA;
import sma.grupo3.Retailer.DistributedBehavior.Localities;

import java.util.Objects;

public class CustomerOrder extends DataBESA {
    private final Localities customerLocality;
    private final String customerAlias;
    private final Catalog orderProduct;
    private final int orderQuantity;
    private final long createdAt;
    private OrderState orderState;
    private long totalElapsedTime;
    private long deliveryElapsedTime;

    public CustomerOrder(Localities customerLocality, String customerAlias, Catalog orderProduct, int orderQuantity) {
        this.customerLocality = customerLocality;
        this.customerAlias = customerAlias;
        this.orderProduct = orderProduct;
        this.orderQuantity = orderQuantity;
        this.createdAt = System.currentTimeMillis();
        this.orderState = OrderState.ISSUED;
        this.totalElapsedTime = 0;
        this.deliveryElapsedTime = 0;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CustomerOrder that = (CustomerOrder) o;
        return orderQuantity == that.orderQuantity && createdAt == that.createdAt && customerLocality == that.customerLocality && customerAlias.equals(that.customerAlias) && orderProduct == that.orderProduct;
    }

    @Override
    public int hashCode() {
        return Objects.hash(customerLocality, customerAlias, orderProduct, orderQuantity, createdAt, orderState);
    }

    public long getTotalElapsedTime() {
        return totalElapsedTime;
    }

    public long getDeliveryElapsedTime() {
        return deliveryElapsedTime;
    }

    public void addElapsedTime(long time) {
        this.totalElapsedTime += time;
    }

    public void addElapsedTime(long time, boolean isDelivery) {
        if (isDelivery) {
            this.totalElapsedTime += time;
            this.deliveryElapsedTime += time;
        } else {
            this.totalElapsedTime += time;
        }
    }

    @Override
    public String toString() {
        return "CustomerOrder{" +
                "customerAlias='" + customerAlias + '\'' +
                ", orderProduct=" + orderProduct +
                ", orderQuantity=" + orderQuantity +
                '}';
    }
}

package sma.grupo3.Retailer.SharedDomain;

import BESA.Kernel.Agent.Event.DataBESA;
import sma.grupo3.Retailer.Agents.Transporter.Data.CustomerOrderDelivery;
import sma.grupo3.Retailer.DistributedBehavior.Localities;

public class TransportCommand extends DataBESA {
    Localities destination;
    CustomerOrder order;
    boolean isDelivery;
    boolean isPickup;
    CustomerOrderDelivery delivery;
    boolean fulfilled;

    public TransportCommand(Localities destination, CustomerOrder order, boolean isPickup) {
        this.destination = destination;
        this.order = order;
        this.isPickup = isPickup;
        this.isDelivery = !isPickup;
        this.fulfilled = false;
    }

    public TransportCommand(Localities destination, CustomerOrder order, boolean isDelivery, CustomerOrderDelivery delivery) {
        this.destination = destination;
        this.order = order;
        this.isDelivery = isDelivery;
        this.isPickup = !isDelivery;
        this.delivery = delivery;
        this.fulfilled = false;
    }

    public Localities getDestination() {
        return destination;
    }

    public CustomerOrder getOrder() {
        return order;
    }

    public boolean isDelivery() {
        return isDelivery;
    }

    public boolean isPickup() {
        return isPickup;
    }

    public CustomerOrderDelivery getDelivery() {
        return delivery;
    }

    public void fullFiled() {
        this.fulfilled = true;
    }

    public boolean isFulfilled() {
        return fulfilled;
    }
}

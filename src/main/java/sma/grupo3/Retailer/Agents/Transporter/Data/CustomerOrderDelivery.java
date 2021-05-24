package sma.grupo3.Retailer.Agents.Transporter.Data;

import BESA.Kernel.Agent.Event.DataBESA;
import sma.grupo3.Retailer.SharedDomain.CustomerOrder;

public class CustomerOrderDelivery extends DataBESA {
    long pickedUpStamp;
    long deliveredStamp;
    double predictedTime;
    CustomerOrder order;

    public CustomerOrderDelivery(double predictedTime, CustomerOrder order) {
        this.predictedTime = predictedTime;
        this.order = order;
        this.pickedUpStamp = System.currentTimeMillis();
    }

    public long getPickedUpStamp() {
        return pickedUpStamp;
    }

    public long getDeliveredStamp() {
        return deliveredStamp;
    }

    public double getPredictedTime() {
        return predictedTime;
    }

    public CustomerOrder getOrder() {
        return order;
    }

    public void delivered() {
        this.deliveredStamp = System.currentTimeMillis();
    }

    public void addDeliveryElapsedTime(long elapsedTime) {
        this.order.addElapsedTime(elapsedTime, true);
    }

    public void addElapsedTime(long elapsedTime) {
        this.order.addElapsedTime(elapsedTime);
    }
}

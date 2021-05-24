package sma.grupo3.Retailer.Agents.Transporter.Data;

import BESA.Kernel.Agent.Event.DataBESA;
import sma.grupo3.Retailer.SharedDomain.CustomerOrder;

public class CustomerOrderDelivery extends DataBESA {
    double predictedTime;
    CustomerOrder order;

    public CustomerOrderDelivery(double predictedTime, CustomerOrder order) {
        this.predictedTime = predictedTime;
        this.order = order;
    }

    public double getPredictedTime() {
        return predictedTime;
    }

    public CustomerOrder getOrder() {
        return order;
    }
}

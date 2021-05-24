package sma.grupo3.Retailer.Agents.Customer;

import BESA.Kernel.Agent.StateBESA;
import sma.grupo3.Retailer.SharedDomain.CustomerOrder;

import java.util.Timer;

public class CustomerState extends StateBESA {
    CustomerOrder activeOrder;
    Timer cancellationTimer;
    double predictedTime;

    public CustomerState() {
    }

    public CustomerOrder getActiveOrder() {
        return activeOrder;
    }

    public void setActiveOrder(CustomerOrder activeOrder) {
        this.activeOrder = activeOrder;
    }

    public Timer getCancellationTimer() {
        return cancellationTimer;
    }

    public void setCancellationTimer(Timer cancellationTimer) {
        this.cancellationTimer = cancellationTimer;
    }

    public double getPredictedTime() {
        return predictedTime;
    }

    public void setPredictedTime(double predictedTime) {
        this.predictedTime = predictedTime;
    }
}

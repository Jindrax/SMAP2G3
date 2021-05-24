package sma.grupo3.Retailer.Agents.Customer.Behavior;

import BESA.ExceptionBESA;
import BESA.Kernel.Agent.Event.EventBESA;
import BESA.Kernel.Agent.GuardBESA;
import sma.grupo3.Retailer.Agents.Customer.CustomerAgent;
import sma.grupo3.Retailer.Agents.Transporter.Data.CustomerOrderDelivery;
import sma.grupo3.Retailer.SharedDomain.CustomerOrder;
import sma.grupo3.Retailer.SharedDomain.CustomerRating;
import sma.grupo3.Retailer.SharedDomain.Statistics;
import sma.grupo3.Retailer.Utils.ConsoleRainbow;

public class OnOrderDeliveredCustomerGuard extends GuardBESA {
    @Override
    public void funcExecGuard(EventBESA eventBESA) {
        CustomerAgent agent = (CustomerAgent) this.agent;
        CustomerOrderDelivery orderDelivery = (CustomerOrderDelivery) eventBESA.getData();
        CustomerOrder order = orderDelivery.getOrder();
        double customerScore = agent.rateOrder(order.getDeliveryElapsedTime(), orderDelivery.getPredictedTime());
        if (orderDelivery.getPredictedTime() < order.getDeliveryElapsedTime()) {
            ConsoleRainbow.alert(String.format("%s: ha llegado mi pedido en %d ms, tiempo previsto %d ms, califico la entrega en %f", this.getAgent().getAlias(), order.getDeliveryElapsedTime(), (long) orderDelivery.getPredictedTime(), customerScore));
        } else {
            ConsoleRainbow.good(String.format("%s: ha llegado mi pedido en %d ms, tiempo previsto %d ms, califico la entrega en %f", this.getAgent().getAlias(), order.getDeliveryElapsedTime(), (long) orderDelivery.getPredictedTime(), customerScore));
        }

        Statistics.registerFinalizedOrder(new CustomerRating(agent.getAid(), orderDelivery.getOrder(), customerScore));
        try {
            this.getAgent().getAdmLocal().killAgent(this.agent.getAid(), this.agent.getPassword());
        } catch (ExceptionBESA exceptionBESA) {
            exceptionBESA.printStackTrace();
        }
    }
}

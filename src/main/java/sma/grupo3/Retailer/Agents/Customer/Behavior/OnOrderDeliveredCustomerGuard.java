package sma.grupo3.Retailer.Agents.Customer.Behavior;

import BESA.ExceptionBESA;
import BESA.Kernel.Agent.Event.EventBESA;
import BESA.Kernel.Agent.GuardBESA;
import sma.grupo3.Retailer.Agents.Customer.CustomerAgent;
import sma.grupo3.Retailer.Agents.Customer.CustomerState;
import sma.grupo3.Retailer.SharedDomain.CustomerOrder;
import sma.grupo3.Retailer.SharedDomain.CustomerRating;
import sma.grupo3.Retailer.SharedDomain.Statistics;
import sma.grupo3.Retailer.Utils.ConsoleRainbow;

public class OnOrderDeliveredCustomerGuard extends GuardBESA {
    @Override
    public void funcExecGuard(EventBESA eventBESA) {
        CustomerAgent agent = (CustomerAgent) getAgent();
        CustomerState state = (CustomerState) getAgent().getState();
        CustomerOrder order = (CustomerOrder) eventBESA.getData();
        double customerScore = agent.rateOrder(order.getDeliveryElapsedTime(), state.getPredictedTime());
        if (state.getPredictedTime() < order.getDeliveryElapsedTime()) {
            ConsoleRainbow.alert(String.format("%s: ha llegado mi pedido en %d ms, tiempo previsto %d ms, califico la entrega en %f", this.getAgent().getAlias(), order.getDeliveryElapsedTime(), (long) state.getPredictedTime(), customerScore));
        } else {
            ConsoleRainbow.good(String.format("%s: ha llegado mi pedido en %d ms, tiempo previsto %d ms, califico la entrega en %f", this.getAgent().getAlias(), order.getDeliveryElapsedTime(), (long) state.getPredictedTime(), customerScore));
        }

        Statistics.registerFinalizedOrder(new CustomerRating(agent.getAid(), order, customerScore));
        try {
            this.getAgent().getAdmLocal().killAgent(this.agent.getAid(), this.agent.getPassword());
        } catch (ExceptionBESA exceptionBESA) {
            exceptionBESA.printStackTrace();
        }
    }
}

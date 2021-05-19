package sma.grupo3.Retailer.Agents.Customer.Behavior;

import BESA.ExceptionBESA;
import BESA.Kernel.Agent.Event.EventBESA;
import BESA.Kernel.Agent.GuardBESA;
import BESA.Log.ReportBESA;
import sma.grupo3.Retailer.Agents.Customer.CustomerAgent;
import sma.grupo3.Retailer.Agents.Transporter.Data.CustomerOrderDelivery;
import sma.grupo3.Retailer.SharedDomain.CustomerRating;
import sma.grupo3.Retailer.SharedDomain.Statistics;

public class OnOrderDeliveredCustomerGuard extends GuardBESA {
    @Override
    public void funcExecGuard(EventBESA eventBESA) {
        CustomerAgent agent = (CustomerAgent) this.agent;
        CustomerOrderDelivery orderDelivery = (CustomerOrderDelivery) eventBESA.getData();
        long deliveryTime = orderDelivery.getDeliveredStamp() - orderDelivery.getPickedUpStamp();
        double customerScore = agent.rateOrder(deliveryTime, orderDelivery.getPredictedTime());
        ReportBESA.info(String.format("%s: ha llegado mi pedido en %d ms, califico la entrega en %f", this.getAgent().getAlias(), deliveryTime, customerScore));
        Statistics.registerFinalizedOrder(new CustomerRating(agent.getAid(), orderDelivery.getOrder(), customerScore));
        try {
            this.getAgent().getAdmLocal().killAgent(this.agent.getAid(), this.agent.getPassword());
        } catch (ExceptionBESA exceptionBESA) {
            exceptionBESA.printStackTrace();
        }
    }
}

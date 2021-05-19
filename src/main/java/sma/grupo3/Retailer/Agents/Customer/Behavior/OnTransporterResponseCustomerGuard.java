package sma.grupo3.Retailer.Agents.Customer.Behavior;

import BESA.Kernel.Agent.Event.EventBESA;
import BESA.Kernel.Agent.GuardBESA;
import BESA.Log.ReportBESA;
import sma.grupo3.Retailer.Agents.Transporter.Data.CustomerOrderDelivery;

public class OnTransporterResponseCustomerGuard extends GuardBESA {
    @Override
    public void funcExecGuard(EventBESA eventBESA) {
        CustomerOrderDelivery orderDelivery = (CustomerOrderDelivery) eventBESA.getData();
//        ReportBESA.info(this.getAgent().getAlias() + ": me notifican que mi pedido llegara en " + orderDelivery.getPredictedTime() + " ms");
    }
}

package sma.grupo3.Retailer.Agents.Warehouse.Behavior;

import BESA.Kernel.Agent.Event.EventBESA;
import BESA.Kernel.Agent.GuardBESA;
import sma.grupo3.Retailer.SharedDomain.CustomerOrder;

public class OnCustomerOrderPlacedWarehouseGuard extends GuardBESA {
    @Override
    public void funcExecGuard(EventBESA eventBESA) {
        CustomerOrder order = (CustomerOrder) eventBESA.getData();
        System.out.println(this.getAgent().getAlias()
                + ": me ha llegado un pedido de "
                + eventBESA.getSenderAgId()
                + " solicitando "
                + order.getOrderQuantity()
                + " unidades de "
                + order.getOrderProduct().name);
    }
}

package sma.grupo3.Retailer.Agents.Warehouse.Behavior;

import BESA.Kernel.Agent.Event.EventBESA;
import BESA.Kernel.Agent.GuardBESA;

public class TestGuard extends GuardBESA {
    @Override
    public void funcExecGuard(EventBESA eventBESA) {
        System.out.println(this.getAgent().getAlias() +  ": me llego el llamado");
    }
}

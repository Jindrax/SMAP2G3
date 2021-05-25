package sma.grupo3.Retailer.Agents.Controller.Behavior;

import BESA.Kernel.Agent.Event.EventBESA;
import BESA.Kernel.Agent.GuardBESA;
import sma.grupo3.Retailer.Agents.Controller.Data.ServiceUpdateFromLocality;
import sma.grupo3.Retailer.DistributedBehavior.Services;
import sma.grupo3.Retailer.SharedDomain.Annotations.ControllerGuard;

@ControllerGuard
public class OnServiceUpdatedControllerGuard extends GuardBESA {
    @Override
    public void funcExecGuard(EventBESA eventBESA) {
        ServiceUpdateFromLocality data = (ServiceUpdateFromLocality) eventBESA.getData();
        Services.updateServiceFromLocality(data.getFromLocality(), data.getService(), data.getUpdate());
    }
}

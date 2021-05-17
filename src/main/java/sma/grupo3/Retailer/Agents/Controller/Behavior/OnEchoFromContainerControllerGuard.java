package sma.grupo3.Retailer.Agents.Controller.Behavior;

import BESA.Kernel.Agent.Event.EventBESA;
import BESA.Kernel.Agent.GuardBESA;
import sma.grupo3.Retailer.Agents.Controller.Data.EchoFromContainer;

public class OnEchoFromContainerControllerGuard extends GuardBESA {
    @Override
    public void funcExecGuard(EventBESA eventBESA) {
        EchoFromContainer echo = (EchoFromContainer) eventBESA.getData();
        System.out.println("Echo received from " + echo.getFrom().value);
    }
}

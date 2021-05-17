package sma.grupo3.Retailer.Agents.Controller;

import BESA.ExceptionBESA;
import BESA.Kernel.Agent.AgentBESA;
import BESA.Kernel.Agent.Event.EventBESA;
import BESA.Kernel.Agent.KernelAgentExceptionBESA;
import BESA.Kernel.Agent.StateBESA;
import BESA.Kernel.Agent.StructBESA;
import sma.grupo3.Retailer.Agents.Controller.Behavior.OnServiceUpdated;
import sma.grupo3.Retailer.Agents.Controller.Data.ServiceUpdateFromLocality;
import sma.grupo3.Retailer.DistributedBehavior.Services;

import java.util.List;

public class ControllerAgent extends AgentBESA {
    public ControllerAgent(String alias, StateBESA state, StructBESA structAgent, double passwd) throws KernelAgentExceptionBESA {
        super(alias, state, structAgent, passwd);
    }

    @Override
    public void setupAgent() {
        ControllerState state = (ControllerState) this.getState();
        Services.setThisLocality(state.getLocality());
        Services.setAgent(this);
    }

    @Override
    public void shutdownAgent() {

    }

    public void notifyServiceUpdate(String service, List<String> update) {
        ControllerState state = (ControllerState) this.getState();
        EventBESA eventBESA = new EventBESA(OnServiceUpdated.class.getName(), new ServiceUpdateFromLocality(Services.getThisLocality(), service, update));
        for (String aliasLocality : state.getKnownLocalities().values()) {
            try {
                this.getAdmLocal().getHandlerByAlias(aliasLocality).sendEvent(eventBESA);
            } catch (ExceptionBESA exceptionBESA) {
                exceptionBESA.printStackTrace();
            }
        }
    }
}

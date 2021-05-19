package sma.grupo3.Retailer.Agents.Transporter;

import BESA.ExceptionBESA;
import BESA.Kernel.Agent.AgentBESA;
import BESA.Kernel.Agent.Event.EventBESA;
import BESA.Kernel.Agent.KernelAgentExceptionBESA;
import BESA.Kernel.Agent.StateBESA;
import BESA.Kernel.Agent.StructBESA;
import sma.grupo3.Retailer.Agents.Transporter.Behavior.OnMoveRequestTransporterGuard;
import sma.grupo3.Retailer.Agents.Transporter.Data.TransporterMovement;

public class TransporterAgent extends AgentBESA {
    public TransporterAgent(String alias, StateBESA state, StructBESA structAgent, double passwd) throws KernelAgentExceptionBESA {
        super(alias, state, structAgent, passwd);
    }

    @Override
    public void setupAgent() {
        TransporterState state = (TransporterState) this.getState();
        try {
            getAdmLocal().getHandlerByAid(this.getAid()).sendEvent(
                    new EventBESA(
                            OnMoveRequestTransporterGuard.class.getName(),
                            new TransporterMovement(state.getCurrentLocality())
                    )
            );
        } catch (ExceptionBESA exceptionBESA) {
            exceptionBESA.printStackTrace();
        }
    }

    @Override
    public void shutdownAgent() {

    }
}

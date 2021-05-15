package DistributedAgent;

import BESA.ExceptionBESA;
import BESA.Kernel.Agent.AgentBESA;
import BESA.Kernel.Agent.Event.EventBESA;
import BESA.Kernel.Agent.KernelAgentExceptionBESA;
import BESA.Kernel.Agent.StateBESA;
import BESA.Kernel.Agent.StructBESA;
import BESA.Kernel.System.Directory.AgHandlerBESA;
import BESA.Log.ReportBESA;
import sma.grupo3.Retailer.Agents.Warehouse.Behavior.TestGuard;
import sma.grupo3.Retailer.Agents.Warehouse.WarehouseAgent;

import java.util.logging.Level;
import java.util.logging.Logger;

public class DistributedAgent extends AgentBESA {
    public DistributedAgent(String alias, StateBESA state, StructBESA structAgent, double passwd) throws KernelAgentExceptionBESA {
        super(alias, state, structAgent, passwd);
    }

    @Override
    public void setupAgent() {

    }

    @Override
    public void shutdownAgent() {

    }

    public void checkReady() {

        DistributedAgentState state = (DistributedAgentState) this.getState();
        System.out.println(state.getLookupAgent());

        boolean ready = false;
        AgHandlerBESA ah;

        while (!ready) {
            ReportBESA.info("Checkeando agentes");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(WarehouseAgent.class.getName()).log(Level.SEVERE, null, ex);
            }
            try {
                System.out.println(this.getAdmLocal().getHandlerByAlias(state.getLookupAgent()).getAlias());
                ah = this.getAdmLocal().getHandlerByAlias(state.getLookupAgent());
                EventBESA msj = new EventBESA(
                        TestGuard.class.getName(),
                        null
                );
                ah.sendEvent(msj);
                ready = true;
            } catch (ExceptionBESA ex) {
//                ex.printStackTrace();
                ReportBESA.info("Checkeo fallido");
            }
        }
        ReportBESA.info("Checkeo exitoso");
    }
}

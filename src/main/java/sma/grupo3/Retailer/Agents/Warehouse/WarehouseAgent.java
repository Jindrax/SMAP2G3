package sma.grupo3.Retailer.Agents.Warehouse;

import BESA.Kernel.Agent.AgentBESA;
import BESA.Kernel.Agent.KernelAgentExceptionBESA;
import BESA.Kernel.Agent.StateBESA;
import BESA.Kernel.Agent.StructBESA;

public class WarehouseAgent extends AgentBESA {

    public WarehouseAgent(String alias, StateBESA state, StructBESA structAgent, double passwd) throws KernelAgentExceptionBESA {
        super(alias, state, structAgent, passwd);
    }

    @Override
    public void setupAgent() {
        System.out.println(this.getAlias() + " registrado");
    }

    @Override
    public void shutdownAgent() {

    }
}

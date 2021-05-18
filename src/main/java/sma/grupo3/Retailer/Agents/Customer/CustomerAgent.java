package sma.grupo3.Retailer.Agents.Customer;

import BESA.ExceptionBESA;
import BESA.Kernel.Agent.AgentBESA;
import BESA.Kernel.Agent.Event.EventBESA;
import BESA.Kernel.Agent.KernelAgentExceptionBESA;
import BESA.Kernel.Agent.StateBESA;
import BESA.Kernel.Agent.StructBESA;
import sma.grupo3.Retailer.Agents.Customer.Data.CustomerOrderWrapper;
import sma.grupo3.Retailer.Agents.Warehouse.Behavior.OnCustomerOrderPlacedWarehouseGuard;
import sma.grupo3.Retailer.DistributedBehavior.Services;
import sma.grupo3.Retailer.DistributedBehavior.StandardServices;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CustomerAgent extends AgentBESA {

    @Override
    public void setupAgent() {
        CustomerState state = (CustomerState) this.getState();
        List<String> warehouses = new ArrayList<>(Services.getGlobalServiceProviders(StandardServices.WAREHOUSE.value));
        Collections.shuffle(warehouses);
        String knownWarehouse = warehouses.get(0);
        try {
            this.getAdmLocal().getHandlerByAid(knownWarehouse).sendEvent(
                    new EventBESA(OnCustomerOrderPlacedWarehouseGuard.class.getName(), new CustomerOrderWrapper(state.getActiveOrder(), this.getAid())));
        } catch (ExceptionBESA exceptionBESA) {
            exceptionBESA.printStackTrace();
        }
    }

    @Override
    public void shutdownAgent() {

    }

    public CustomerAgent(String alias, StateBESA state, StructBESA structAgent, double passwd) throws KernelAgentExceptionBESA {
        super(alias, state, structAgent, passwd);
    }
}

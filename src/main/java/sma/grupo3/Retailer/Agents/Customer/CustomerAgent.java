package sma.grupo3.Retailer.Agents.Customer;

import BESA.ExceptionBESA;
import BESA.Kernel.Agent.AgentBESA;
import BESA.Kernel.Agent.Event.EventBESA;
import BESA.Kernel.Agent.KernelAgentExceptionBESA;
import BESA.Kernel.Agent.StateBESA;
import BESA.Kernel.Agent.StructBESA;
import sma.grupo3.Retailer.Agents.Warehouse.Behavior.OnCustomerOrderPlacedWarehouseGuard;
import sma.grupo3.Retailer.DistributedBehavior.Services;
import sma.grupo3.Retailer.DistributedBehavior.StandardServices;
import sma.grupo3.Retailer.SharedDomain.Catalog;
import sma.grupo3.Retailer.SharedDomain.CustomerOrder;
import sma.grupo3.Retailer.Utils.Configuration;
import sma.grupo3.Retailer.Utils.Randomizer;

import javax.lang.model.element.ElementVisitor;
import java.util.Optional;

public class CustomerAgent extends AgentBESA {

    @Override
    public void setupAgent() {
        CustomerState state = (CustomerState) this.getState();
        CustomerOrder order = new CustomerOrder(Services.getThisLocality(), this.getAlias(), Randomizer.randomEnum(Catalog.class), Randomizer.randomInt(Configuration.getInt("MAX_QUANTITY_ORDER")));
        state.setActiveOrder(order);
        Optional<String> knownWarehouse = Services.getGlobalServiceProviders(StandardServices.WAREHOUSE.value).stream().findAny();
        if(knownWarehouse.isPresent()){
            try {
                this.getAdmLocal().getHandlerByAid(knownWarehouse.get()).sendEvent(new EventBESA(OnCustomerOrderPlacedWarehouseGuard.class.getName(), order));
            } catch (ExceptionBESA exceptionBESA) {
                exceptionBESA.printStackTrace();
            }
        }
    }

    @Override
    public void shutdownAgent() {

    }

    public CustomerAgent(String alias, StateBESA state, StructBESA structAgent, double passwd) throws KernelAgentExceptionBESA {
        super(alias, state, structAgent, passwd);
    }
}

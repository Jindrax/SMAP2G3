package sma.grupo3.Retailer.Agents.Warehouse;

import BESA.ExceptionBESA;
import BESA.Kernel.Agent.AgentBESA;
import BESA.Kernel.Agent.Event.EventBESA;
import BESA.Kernel.Agent.KernelAgentExceptionBESA;
import BESA.Kernel.Agent.StateBESA;
import BESA.Kernel.Agent.StructBESA;
import BESA.Kernel.System.Directory.AgHandlerBESA;
import sma.grupo3.Retailer.Agents.Customer.Behavior.OnWareHouseResponseCustomerGuard;
import sma.grupo3.Retailer.Agents.Transporter.Behavior.OnTransporterOrderAuctionTransporterGuard;
import sma.grupo3.Retailer.Agents.Transporter.Data.TransporterOrderAuctionResponse;
import sma.grupo3.Retailer.Agents.Warehouse.Data.CustomerOrderWarehouseResponse;
import sma.grupo3.Retailer.Agents.Warehouse.Data.TransporterOrderAuction;
import sma.grupo3.Retailer.Agents.Warehouse.Exceptions.NoTransporterAvailable;
import sma.grupo3.Retailer.DistributedBehavior.Services;
import sma.grupo3.Retailer.DistributedBehavior.StandardServices;
import sma.grupo3.Retailer.SharedDomain.CustomerOrder;

import java.util.HashSet;
import java.util.List;

public class WarehouseAgent extends AgentBESA {

    public WarehouseAgent(String alias, StateBESA state, StructBESA structAgent, double passwd) throws KernelAgentExceptionBESA {
        super(alias, state, structAgent, passwd);
    }

    @Override
    public void setupAgent() {

    }

    @Override
    public void shutdownAgent() {

    }

    public void startTransporterAuction(CustomerOrder order) throws NoTransporterAvailable, ExceptionBESA {
        WarehouseState state = (WarehouseState) getState();
        List<String> availableTransport = Services.getLocalityServiceProviders(state.getLocality(), StandardServices.TRANSPORTER.value);
        if (availableTransport.isEmpty()) {
            availableTransport = Services.getGlobalServiceProviders(StandardServices.TRANSPORTER.value);
        }
        if (availableTransport.isEmpty()) {
            throw new NoTransporterAvailable();
        }
        state.startTransporterAuction(order, new TransporterOrderAuction(new HashSet<>(availableTransport)));
        state.stockCredit(order);
        for (String transporter : availableTransport) {
            AgHandlerBESA transporterHandler = getAdmLocal().getHandlerByAid(transporter);
            TransporterOrderAuctionResponse response = new TransporterOrderAuctionResponse(getAid(), order, transporter);
            EventBESA auctionEvent = new EventBESA(OnTransporterOrderAuctionTransporterGuard.class.getName(), response);
            transporterHandler.sendEvent(auctionEvent);
        }
    }

    public void cancelOrder(CustomerOrder order) {
        try {
            getAdmLocal().getHandlerByAlias(order.getCustomerAlias()).sendEvent(new EventBESA(
                    OnWareHouseResponseCustomerGuard.class.getName(),
                    new CustomerOrderWarehouseResponse(getAid(), order, true)
            ));
        } catch (ExceptionBESA exceptionBESA) {
            exceptionBESA.printStackTrace();
        }
    }
}

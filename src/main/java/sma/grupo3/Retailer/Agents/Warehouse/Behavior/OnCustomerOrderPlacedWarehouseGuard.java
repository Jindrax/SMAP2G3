package sma.grupo3.Retailer.Agents.Warehouse.Behavior;

import BESA.ExceptionBESA;
import BESA.Kernel.Agent.Event.EventBESA;
import BESA.Kernel.Agent.GuardBESA;
import BESA.Kernel.System.AdmBESA;
import sma.grupo3.Retailer.Agents.Customer.Data.CustomerOrderWrapper;
import sma.grupo3.Retailer.Agents.Warehouse.Data.WarehouseOrderAuction;
import sma.grupo3.Retailer.Agents.Warehouse.Data.WarehouseOrderAuctionResponse;
import sma.grupo3.Retailer.Agents.Warehouse.Exceptions.NoTransporterAvailable;
import sma.grupo3.Retailer.Agents.Warehouse.WarehouseAgent;
import sma.grupo3.Retailer.Agents.Warehouse.WarehouseState;
import sma.grupo3.Retailer.DistributedBehavior.Services;
import sma.grupo3.Retailer.DistributedBehavior.StandardServices;
import sma.grupo3.Retailer.Utils.Configuration;

import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

public class OnCustomerOrderPlacedWarehouseGuard extends GuardBESA {

    private static final boolean cooperativeBehavior = Configuration.getBoolean("COOPERATIVE_BEHAVIOR");

    @Override
    public void funcExecGuard(EventBESA eventBESA) {
        CustomerOrderWrapper order = (CustomerOrderWrapper) eventBESA.getData();
        WarehouseState state = (WarehouseState) this.getAgent().getState();
        try {
            if (cooperativeBehavior) {
                cooperativeExecution(state, order);
            } else {
                nonCooperativeExecution(state, order);
            }
        } catch (ExceptionBESA exceptionBESA) {
            exceptionBESA.printStackTrace();
        }
    }

    public void nonCooperativeExecution(WarehouseState state, CustomerOrderWrapper order) throws ExceptionBESA {
        WarehouseAgent agent = (WarehouseAgent) getAgent();
        if (state.evalOrderCapacity(order.getOrder())) {
            try {
                agent.startTransporterAuction(order.getOrder());
            } catch (NoTransporterAvailable ex) {
                agent.cancelOrder(order.getOrder());
            }
        } else {
            agent.cancelOrder(order.getOrder());
        }
    }

    public void cooperativeExecution(WarehouseState state, CustomerOrderWrapper order) throws ExceptionBESA {
        List<String> bidders;
        if (state.evalOrderCapacity(order.getOrder())) {
            bidders = Services.getGlobalServiceProviders(StandardServices.WAREHOUSE.value);
        } else {
            bidders = Services.getGlobalServiceProviders(StandardServices.WAREHOUSE.value).stream().filter(s -> !s.equals(getAgent().getAid())).collect(Collectors.toList());
        }
        WarehouseOrderAuction auction = new WarehouseOrderAuction(new HashSet<>(bidders));
        state.startWarehouseAuction(order.getOrder(), auction);
        AdmBESA admBESA = getAgent().getAdmLocal();
        for (String bidder : bidders) {
            WarehouseOrderAuctionResponse response = new WarehouseOrderAuctionResponse(getAgent().getAid(), order.getOrder(), bidder);
            EventBESA auctionEvent = new EventBESA(OnWarehouseOrderAuctionWarehouseGuard.class.getName(), response);
            admBESA.getHandlerByAid(bidder).sendEvent(auctionEvent);
        }
    }
}

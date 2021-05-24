package sma.grupo3.Retailer.Agents.Transporter;

import BESA.ExceptionBESA;
import BESA.Kernel.Agent.AgentBESA;
import BESA.Kernel.Agent.Event.EventBESA;
import BESA.Kernel.Agent.KernelAgentExceptionBESA;
import BESA.Kernel.Agent.StateBESA;
import BESA.Kernel.Agent.StructBESA;
import BESA.Kernel.System.Directory.AgHandlerBESA;
import sma.grupo3.Retailer.Agents.Transporter.Behavior.OnMoveRequestTransporterGuard;
import sma.grupo3.Retailer.Agents.Transporter.Behavior.OnTransporterOrderAuctionTransporterGuard;
import sma.grupo3.Retailer.Agents.Transporter.Data.TransporterMovement;
import sma.grupo3.Retailer.Agents.Transporter.Data.TransporterOrderAuctionResponse;
import sma.grupo3.Retailer.Agents.Warehouse.Data.TransporterOrderAuction;
import sma.grupo3.Retailer.Agents.Warehouse.Exceptions.NoTransporterAvailable;
import sma.grupo3.Retailer.Agents.Warehouse.WarehouseState;
import sma.grupo3.Retailer.DistributedBehavior.Services;
import sma.grupo3.Retailer.DistributedBehavior.StandardServices;
import sma.grupo3.Retailer.SharedDomain.CustomerOrder;

import java.util.HashSet;
import java.util.List;

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
                            new TransporterMovement(state.getCurrentLocality(), 0)
                    )
            );
        } catch (ExceptionBESA exceptionBESA) {
            exceptionBESA.printStackTrace();
        }
    }

    @Override
    public void shutdownAgent() {

    }

    public void startTransporterAuction(CustomerOrder order) throws NoTransporterAvailable, ExceptionBESA {
        TransporterState state = (TransporterState) getState();
        List<String> availableTransport = Services.getLocalityServiceProviders(state.getCurrentLocality(), StandardServices.TRANSPORTER.value);
        if (availableTransport.isEmpty()) {
            availableTransport = Services.getGlobalServiceProviders(StandardServices.TRANSPORTER.value);
        }
        if (availableTransport.isEmpty()) {
            throw new NoTransporterAvailable();
        }
        state.startTransporterAuction(order, new TransporterOrderAuction(new HashSet<>(availableTransport)));
        for (String transporter : availableTransport) {
            AgHandlerBESA transporterHandler = getAdmLocal().getHandlerByAid(transporter);
            TransporterOrderAuctionResponse response = new TransporterOrderAuctionResponse(getAid(), order, transporter);
            EventBESA auctionEvent = new EventBESA(OnTransporterOrderAuctionTransporterGuard.class.getName(), response);
            transporterHandler.sendEvent(auctionEvent);
        }
    }
}

package sma.grupo3.Retailer.Agents.Transporter;

import BESA.ExceptionBESA;
import BESA.Kernel.Agent.AgentBESA;
import BESA.Kernel.Agent.Event.EventBESA;
import BESA.Kernel.Agent.KernelAgentExceptionBESA;
import BESA.Kernel.Agent.StateBESA;
import BESA.Kernel.Agent.StructBESA;
import BESA.Kernel.System.Directory.AgHandlerBESA;
import sma.grupo3.Retailer.Agents.Transporter.Behavior.OnMoveRequestTransporterGuard;
import sma.grupo3.Retailer.Agents.Transporter.Behavior.OnTransporterCommandAuctionTransporterGuard;
import sma.grupo3.Retailer.Agents.Transporter.Data.TransporterCommandAuction;
import sma.grupo3.Retailer.Agents.Transporter.Data.TransporterCommandAuctionResponse;
import sma.grupo3.Retailer.Agents.Transporter.Data.TransporterMovement;
import sma.grupo3.Retailer.Agents.Warehouse.Exceptions.NoTransporterAvailable;
import sma.grupo3.Retailer.DistributedBehavior.Services;
import sma.grupo3.Retailer.DistributedBehavior.StandardServices;
import sma.grupo3.Retailer.SharedDomain.TransportCommand;

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

    public void startTransporterAuction(TransportCommand transportCommand) throws NoTransporterAvailable, ExceptionBESA {
        TransporterState state = (TransporterState) getState();
        List<String> availableTransport = Services.getNearbyServiceProviders(state.getCurrentLocality(), StandardServices.TRANSPORTER.value, 5);
        state.startTransporterAuction(transportCommand, new TransporterCommandAuction(new HashSet<>(availableTransport)));
        if (availableTransport.isEmpty()) {
            transportCommand.cancelTransfer();
        } else {
            for (String transporter : availableTransport) {
                AgHandlerBESA transporterHandler = getAdmLocal().getHandlerByAid(transporter);
                TransporterCommandAuctionResponse response = new TransporterCommandAuctionResponse(getAid(), StandardServices.TRANSPORTER, transportCommand, transporter);
                EventBESA auctionEvent = new EventBESA(OnTransporterCommandAuctionTransporterGuard.class.getName(), response);
                transporterHandler.sendEvent(auctionEvent);
            }
        }
    }
}

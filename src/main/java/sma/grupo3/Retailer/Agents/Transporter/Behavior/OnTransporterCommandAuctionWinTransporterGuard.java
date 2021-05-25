package sma.grupo3.Retailer.Agents.Transporter.Behavior;

import BESA.Kernel.Agent.Event.EventBESA;
import BESA.Kernel.Agent.GuardBESA;
import sma.grupo3.Retailer.Agents.Transporter.Data.TransporterCommandAuctionResponse;
import sma.grupo3.Retailer.Agents.Transporter.TransporterState;
import sma.grupo3.Retailer.SharedDomain.Annotations.TransporterGuard;


@TransporterGuard
public class OnTransporterCommandAuctionWinTransporterGuard extends GuardBESA {
    @Override
    public void funcExecGuard(EventBESA eventBESA) {
        TransporterCommandAuctionResponse response = (TransporterCommandAuctionResponse) eventBESA.getData();
        TransporterState state = (TransporterState) agent.getState();
        state.getCommandList().add(response.getTransportCommand());
    }
}

package sma.grupo3.Retailer.Agents.Transporter.Behavior;

import BESA.ExceptionBESA;
import BESA.Kernel.Agent.Event.EventBESA;
import BESA.Kernel.Agent.GuardBESA;
import BESA.Kernel.System.Directory.AgHandlerBESA;
import sma.grupo3.Retailer.Agents.Transporter.Data.TransporterCommandAuction;
import sma.grupo3.Retailer.Agents.Transporter.Data.TransporterCommandAuctionResponse;
import sma.grupo3.Retailer.Agents.Transporter.TransporterState;
import sma.grupo3.Retailer.SharedDomain.Annotations.TransporterGuard;


@TransporterGuard
public class OnTransporterCommandAuctionResponseTransporterGuard extends GuardBESA {
    @Override
    public void funcExecGuard(EventBESA eventBESA) {
        TransporterState state = (TransporterState) getAgent().getState();
        TransporterCommandAuctionResponse response = (TransporterCommandAuctionResponse) eventBESA.getData();
        TransporterCommandAuction auction = state.getTransporterAuctions().get(response.getTransportCommand());
        if (auction.addBidderResponse(response)) {
            TransporterCommandAuctionResponse winner = auction.getAuctionWinner();
            if (winner == null) {
                response.getTransportCommand().cancelTransfer();
            } else {
                try {
                    AgHandlerBESA winnerAgent = getAgent().getAdmLocal().getHandlerByAid(winner.getBidder());
                    EventBESA winnerEvent = new EventBESA(OnTransporterCommandAuctionWinTransporterGuard.class.getName(), winner);
                    winnerAgent.sendEvent(winnerEvent);
                } catch (ExceptionBESA exceptionBESA) {
                    exceptionBESA.printStackTrace();
                }
            }
        }
    }
}

package sma.grupo3.Retailer.Agents.Transporter.Behavior;

import BESA.ExceptionBESA;
import BESA.Kernel.Agent.Event.EventBESA;
import BESA.Kernel.Agent.GuardBESA;
import sma.grupo3.Retailer.Agents.Transporter.Data.TransporterCommandAuctionResponse;
import sma.grupo3.Retailer.Agents.Transporter.TransporterState;
import sma.grupo3.Retailer.DistributedBehavior.ConnectionMap;
import sma.grupo3.Retailer.SharedDomain.CustomerOrder;

public class OnTransporterCommandAuctionTransporterGuard extends GuardBESA {
    @Override
    public void funcExecGuard(EventBESA eventBESA) {
        TransporterCommandAuctionResponse response = (TransporterCommandAuctionResponse) eventBESA.getData();
        TransporterState state = (TransporterState) getAgent().getState();
        try {
            double currentLoadWeight = state.getCommandList().stream().mapToDouble(prospect -> prospect.getOrder().getOrderQuantity() * prospect.getOrder().getOrderProduct().weight).reduce(Double::sum).orElse(0);
            CustomerOrder order = response.getTransportCommand().getOrder();
            double orderWeight = order.getOrderQuantity() * order.getOrderProduct().weight;
            double fullWeight = currentLoadWeight + orderWeight;
            boolean fulfillable = fullWeight <= state.getMaxWeightCapacity();
            double bid = ConnectionMap.getTimeFromTo(state.getCurrentLocality(), order.getCustomerLocality()) + fullWeight;
            response.setFulfillable(fulfillable);
            response.setBid(bid);
            EventBESA responseEvent = new EventBESA(OnTransporterCommandAuctionResponseTransporterGuard.class.getName(), response);
            getAgent().getAdmLocal().getHandlerByAid(response.getAuctioneer()).sendEvent(responseEvent);
        } catch (ExceptionBESA exceptionBESA) {
            exceptionBESA.printStackTrace();
        }
    }
}

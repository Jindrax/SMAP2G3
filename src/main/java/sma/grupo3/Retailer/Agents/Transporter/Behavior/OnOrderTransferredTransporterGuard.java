package sma.grupo3.Retailer.Agents.Transporter.Behavior;

import BESA.Kernel.Agent.Event.EventBESA;
import BESA.Kernel.Agent.GuardBESA;
import sma.grupo3.Retailer.Agents.Transporter.TransporterState;
import sma.grupo3.Retailer.SharedDomain.Annotations.TransporterGuard;
import sma.grupo3.Retailer.SharedDomain.TransportCommand;

import java.util.List;


@TransporterGuard
public class OnOrderTransferredTransporterGuard extends GuardBESA {
    @Override
    public void funcExecGuard(EventBESA eventBESA) {
        TransportCommand transportCommand = (TransportCommand) eventBESA.getData();
        TransporterState state = (TransporterState) getAgent().getState();
        List<TransportCommand> commandList = state.getCommandList();
        state.getCurrentLoad().remove(transportCommand.getOrder());
        commandList.remove(transportCommand);
    }
}

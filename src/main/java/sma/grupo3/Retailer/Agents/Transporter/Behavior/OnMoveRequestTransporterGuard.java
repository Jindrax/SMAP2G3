package sma.grupo3.Retailer.Agents.Transporter.Behavior;

import BESA.ExceptionBESA;
import BESA.Kernel.Agent.Event.EventBESA;
import BESA.Kernel.Agent.GuardBESA;
import BESA.Kernel.System.AdmBESA;
import BESA.Kernel.System.Directory.AgHandlerBESA;
import BESA.Log.ReportBESA;
import sma.grupo3.Retailer.Agents.Transporter.Data.TransporterMovement;
import sma.grupo3.Retailer.Agents.Transporter.TransporterAgent;
import sma.grupo3.Retailer.Agents.Transporter.TransporterState;
import sma.grupo3.Retailer.DistributedBehavior.ConnectionMap;
import sma.grupo3.Retailer.DistributedBehavior.Localities;
import sma.grupo3.Retailer.SharedDomain.TransportCommand;

import java.util.List;
import java.util.TimerTask;
import java.util.stream.Collectors;

public class OnMoveRequestTransporterGuard extends GuardBESA {

    static class MovementStep extends TimerTask {

        TransporterMovement transporterMovement;
        AgHandlerBESA handlerBESA;

        public MovementStep(TransporterMovement transporterMovement, AgHandlerBESA handlerBESA) {
            this.transporterMovement = transporterMovement;
            this.handlerBESA = handlerBESA;
        }

        @Override
        public void run() {
            try {
                this.handlerBESA.sendEvent(
                        new EventBESA(OnMoveRequestTransporterGuard.class.getName(), transporterMovement)
                );
            } catch (ExceptionBESA exceptionBESA) {
                exceptionBESA.printStackTrace();
            }
        }
    }

    @Override
    public void funcExecGuard(EventBESA eventBESA) {
        TransporterMovement movement = (TransporterMovement) eventBESA.getData();
        TransporterAgent agent = (TransporterAgent) this.getAgent();
        AdmBESA admBESA = agent.getAdmLocal();
        try {
            TransporterState state = (TransporterState) this.getAgent().getState();
            if (state.getCurrentLocality() != movement.getLocalityArrived()) {
                ReportBESA.info(agent.getAlias() + ": moviendome de " + state.getCurrentLocality().value + " a " + movement.getLocalityArrived().value);
            }
            state.setCurrentLocality(movement.getLocalityArrived());
            List<TransportCommand> commandList = state.getCommandList();
            for (TransportCommand command : commandList.stream()
                    .filter(command -> command.getDestination() == state.getCurrentLocality()).collect(Collectors.toList())) {
                if (command.isPickup()) {
                    ReportBESA.info(String.format("%s: recogiendo en %s el pedido de %s", agent.getAlias(), state.getCurrentLocality().value, command.getOrder().getCustomerAlias()));
                } else {
                    ReportBESA.info(String.format("%s: entregando en %s el pedido de %s", agent.getAlias(), state.getCurrentLocality().value, command.getOrder().getCustomerAlias()));
                }
                EventBESA customerNotification = state.executeCommand(command);
                if (customerNotification != null) {
                    admBESA.getHandlerByAlias(command.getOrder().getCustomerAlias()).sendEvent(customerNotification);
                }
            }
            commandList.removeAll(
                    commandList.stream().filter(TransportCommand::isFulfilled).collect(Collectors.toList())
            );
            //A new destination is polled if available, if not waits until it changes
            if (!commandList.isEmpty()) {
                TransportCommand nextCommand = commandList.get(0);
                List<Localities> nextDestination = ConnectionMap.shortestPath(state.getCurrentLocality(), nextCommand.getDestination());
                if(!nextDestination.isEmpty()){
                    state.scheduleMovement(
                            new MovementStep(
                                    new TransporterMovement(nextDestination.get(0)),
                                    admBESA.getHandlerByAid(this.getAgent().getAid())),
                            (long) ConnectionMap.getTimeFromTo(state.getCurrentLocality(), nextDestination.get(0))
                    );
                }else{
                    state.scheduleMovement(
                            new MovementStep(
                                    new TransporterMovement(state.getCurrentLocality()),
                                    admBESA.getHandlerByAid(this.getAgent().getAid())),
                            (long) ConnectionMap.getTimeFromTo(state.getCurrentLocality(), state.getCurrentLocality())
                    );
                }
            } else {
                state.scheduleMovement(
                        new MovementStep(
                                new TransporterMovement(state.getCurrentLocality()),
                                admBESA.getHandlerByAid(this.getAgent().getAid())),
                        (long) ConnectionMap.getTimeFromTo(state.getCurrentLocality(), state.getCurrentLocality())
                );
            }
        } catch (ExceptionBESA exceptionBESA) {
            exceptionBESA.printStackTrace();
        }
    }
}
